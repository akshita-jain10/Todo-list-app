package com.example.Todo.App.entities;

import com.example.Todo.App.dto.UserDTO;
import com.example.Todo.App.enums.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "User")
public class User implements UserDetails {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRole == null) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER")); // fallback default
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDTO getUserDto(){
         UserDTO userDto = new UserDTO();
         userDto.setId(id);
         userDto.setName(name);
         userDto.setEmail(email);
         userDto.setUserRole(userRole);
         return userDto;
    }
}
