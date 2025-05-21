package com.example.turistickivodic.repositories.destination;

import com.example.turistickivodic.entities.Destination;
import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDestinationRepository extends MySqlAbstractRepository implements DestinationRepository {

    @Override
    public void save(Destination destination) {
        String query = "INSERT INTO destinations (name, description) VALUES (?, ?)";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, destination.getName());
            statement.setString(2, destination.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving destination: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Destination> findWithPagination(int limit, int offset) {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM destinations LIMIT ? OFFSET ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Destination destination = new Destination();
                    destination.setId(resultSet.getInt("id"));
                    destination.setName(resultSet.getString("name"));
                    destination.setDescription(resultSet.getString("description"));
                    destinations.add(destination);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinations;
    }

    public int getTotalCount() {
        String query = "SELECT COUNT(*) AS total FROM destinations";
        try (Connection connection = this.newConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public List<Destination> findAll() {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM destinations";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Destination destination = new Destination();
                destination.setId(resultSet.getInt("id"));
                destination.setName(resultSet.getString("name"));
                destination.setDescription(resultSet.getString("description"));
                destinations.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinations;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM destinations WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting destination: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Destination findByName(String name) {
        Destination destination = null;
        String query = "SELECT * FROM destinations WHERE name = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    destination = new Destination();
                    destination.setName(resultSet.getString("name"));
                    destination.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destination;
    }

    @Override
    public void update(Destination destination) {
        // Assuming you have an 'id' field in the 'destination' object that you use to identify which destination to update
        String query = "UPDATE destinations SET name = ?, description = ? WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, destination.getName());
            statement.setString(2, destination.getDescription());
            statement.setInt(3, destination.getId());  // Set the ID parameter
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating destination failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating destination: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Destination getById(int id) {
        Destination destination = null;
        String query = "SELECT * FROM destinations WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    destination = new Destination();
                    destination.setName(resultSet.getString("name"));
                    destination.setDescription(resultSet.getString("description"));
                    destination.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destination;
    }

}
