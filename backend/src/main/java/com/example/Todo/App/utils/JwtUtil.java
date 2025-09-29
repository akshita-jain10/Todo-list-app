package com.example.Todo.App.utils;

import com.example.Todo.App.entities.User;
import com.example.Todo.App.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private  final UserRepository userRepository;
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    
    private  String generateToken(Map<String, Object>extraClaims,UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();


    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("Xz20e4oSBWNz8RQ23jQ3IQQ6x6fZRLTNBmmA207fJvE="); // Remove \n
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
final  String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
       return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final  Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
//    public User getLoggedInUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication!= null && authentication.isAuthenticated()){
//User user = (User) authentication.getPrincipal();
//Optional <User> optionalUser = userRepository.findById(user.getId());
//            return optionalUser.orElse(null);
//
//        }
//        return null;
//    }
public User getLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            // First load User by email/username
            return userRepository.findByEmail(userDetails.getUsername())
                    .orElse(null); // Now you have full User, including ID
        }
    }
    return null;
}

}
