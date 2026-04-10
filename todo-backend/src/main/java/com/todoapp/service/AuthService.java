package com.todoapp.service;

import java.time.Duration;
import java.util.Set;

import com.todoapp.entity.User;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    @Transactional
    public User register(String username, String password) {
        if(User.findByUsername(username) != null) {
            throw new IllegalArgumentException("USername já existe");
        }
        User user = new User();
        user.username = username;
        user.password = hashPassword(password);
        user.persist();
        return user;
    }
 
    public String login(String username, String password) {
        User user = User.findByUsername(username);
        if(user == null || !verifyPassword(password, user.password)) {
            throw new SecurityException("Credenciais inválidas");
        }
        return generateToken(user);
    }
    
    private String generateToken(User user) {
        return Jwt.issuer("https://todoapp.com")
        .subject(user.username)
        .groups(Set.of(user.role))
        .expiresIn(Duration.ofHours(8))
        .sign();
    }

    private boolean verifyPassword(String raw, String hashed) {
        return hashPassword(raw).equals(hashed);
    }

    private String hashPassword(String password) {
        // Simplificado para estudo
        return Integer.toHexString(password.hashCode());
    }

}
