package com.example.turistickivodic.repositories.activity;

import com.example.turistickivodic.entities.Activity;
import com.example.turistickivodic.entities.Article;
import com.example.turistickivodic.repositories.MySqlAbstractRepository;
import com.example.turistickivodic.repositories.activity.ActivityRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlActivityRepository extends MySqlAbstractRepository implements ActivityRepository {

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activities";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setId(resultSet.getInt("id"));
                activity.setName(resultSet.getString("name"));
                activity.setDestinationId(resultSet.getInt("destination_id"));
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public Activity save(Activity activity) {
        String query = "INSERT INTO activities (name, destination_id) VALUES (?, ?)";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, activity.getName());
            statement.setInt(2, activity.getDestinationId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating activity failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    activity.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating activity failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving activity: " + e.getMessage());
            e.printStackTrace();
        }
        return activity;
    }


    @Override
    public List<Activity> getByDestinationId(int id) {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activities WHERE destination_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity();
                    activity.setId(resultSet.getInt("id"));
                    activity.setName(resultSet.getString("name"));
                    activities.add(activity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public Activity getById(int id) {
        Activity activity = null;
        String query = "SELECT * FROM activities WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    activity = new Activity();
                    activity.setId(resultSet.getInt("id"));
                    activity.setName(resultSet.getString("name"));
                    activity.setDestinationId(resultSet.getInt("destination_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }


    @Override
    public Activity findByDestinationIdAndName(int destinationId, String name) {
        Activity activity = null;
        String query = "SELECT * FROM activities WHERE destination_id = ? AND name = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, destinationId);
            statement.setString(2, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    activity = new Activity();
                    activity.setId(resultSet.getInt("id"));
                    activity.setName(resultSet.getString("name"));
                    activity.setDestinationId(resultSet.getInt("destination_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }

    @Override
    public boolean deleteByDestinationId(int destinationId) {
        String query = "DELETE FROM activities WHERE destination_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, destinationId);
            int affectedRows = statement.executeUpdate();
            // return true even if no rows were affected because it's not an error
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


