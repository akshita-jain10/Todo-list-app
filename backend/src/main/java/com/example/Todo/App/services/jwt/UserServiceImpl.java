package com.example.Todo.App.services.jwt;
import com.example.Todo.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userRepository.findFirstByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

            // Convert user role to GrantedAuthority
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    java.util.Collections.singletonList(authority)
            );
        };
    }
}

