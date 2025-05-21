package com.example.turistickivodic.repositories.articleactivity;

import com.example.turistickivodic.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlArticleActivityRepository extends MySqlAbstractRepository implements ArticleActivityRepository {

    @Override
    public void save(int articleId, int activityId) throws SQLException {
        String query = "INSERT INTO article_activities (article_id, activity_id) VALUES (?, ?)";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            statement.setInt(2, activityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to save article activity");
        }
    }

    public List<Integer> findArticleIdsByActivityId(int activityId) {
        List<Integer> articleIds = new ArrayList<>();
        String query = "SELECT article_id FROM article_activities WHERE activity_id = ?";

        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, activityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    articleIds.add(resultSet.getInt("article_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleIds;
    }

    @Override
    public boolean exists(int articleId, int activityId) {
        String query = "SELECT * FROM article_activities WHERE article_id = ? AND activity_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            statement.setInt(2, activityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Integer> findActivityIdsByArticleId(int articleId) {
        List<Integer> activityIds = new ArrayList<>();
        String query = "SELECT activity_id FROM article_activities WHERE article_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    activityIds.add(resultSet.getInt("activity_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityIds;
    }

    @Override
    public boolean deleteArticleActivity(int articleId) {
        String query = "DELETE FROM article_activities WHERE article_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
