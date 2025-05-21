package com.example.turistickivodic.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user){
        user.setPassword(hashPassword(user.getPassword())); // Hash password before saving
        userRepository.save(user);
    }

    public void updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        userRepository.update(user);
    }

    public void updateUserStatus(String email, boolean status) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setStatus(status);
            userRepository.updateStatus(email,status);
        }
    }

    public String getRoleFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("role").asString();
    }

    public String login(String email, String password) {
        User user = userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashPassword(password))) {
            return null;
        }
        return generateJWT(user);
    }

    private String generateJWT(User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret"); // Secret should be stored securely
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86_400_000)) // 24 hours
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole())
                .withClaim("status", user.isStatus())
                .withClaim("email",user.getEmail())
                .sign(algorithm);
    }

    private String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public boolean isAuthorized(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            boolean userExists = userRepository.findByEmail(jwt.getSubject()) != null;
            System.out.println("User exists: " + userExists);
            return userExists;
        } catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }

    public List<User> getUsersWithPagination(int page, int size) {
        int offset = (page - 1) * size;
        return userRepository.findAllWithPagination(size, offset);
    }

    public int getTotalUserCount() {
        return userRepository.countAll();
    }


}
