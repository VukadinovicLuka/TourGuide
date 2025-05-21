package com.example.turistickivodic.repositories.user;

import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.repositories.MySqlAbstractRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User findUser(String email) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setName(resultSet.getString("name"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setStatus(resultSet.getBoolean("status"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("Email: " + user.getEmail() + ", Name: " + user.getName() + ", Lastname: " + user.getLastname());
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
//        if (findByEmail(user.getEmail()) != null) {
//            // Možete baciti SQLException ili neki custom izuzetak da signalizirate da korisnik već postoji
//            throw new SQLException("User with email " + user.getEmail() + " already exists.");
//        }
        String query = "INSERT INTO users (name, lastname, email, status, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getEmail());
            statement.setBoolean(4, user.isStatus());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByEmail(String email) {
        return findUser(email);
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users SET name = ?, lastname = ?, role = ? WHERE email = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(String email, boolean newStatus) {
        String query = "UPDATE users SET status = ? WHERE email = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, newStatus);
            statement.setString(2, email);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new SQLException("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            // Handle exceptions appropriately, possibly rethrow as a custom exception
            e.printStackTrace();
        }
    }

    public List<User> findAllWithPagination(int limit, int offset) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setName(resultSet.getString("name"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    user.setStatus(resultSet.getBoolean("status"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int countAll() {
        String query = "SELECT COUNT(*) AS total FROM users";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
