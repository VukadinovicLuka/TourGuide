package com.example.turistickivodic.repositories.user;

import com.example.turistickivodic.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    User findUser(String email);
    List<User> findAll();
    void save(User user);
    User findByEmail(String email);
    void update(User user);
    void updateStatus(String email, boolean newStatus);
    int countAll();
    List<User> findAllWithPagination(int limit, int offset);
}
