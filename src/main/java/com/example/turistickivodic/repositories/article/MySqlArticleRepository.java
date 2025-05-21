package com.example.turistickivodic.repositories.article;

import com.example.turistickivodic.entities.Article;
import com.example.turistickivodic.entities.Destination;
import com.example.turistickivodic.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {

    @Override
    public Article save(Article article) {
        String query = "INSERT INTO articles (title, text, visit_count, author_email, destination_id, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getText());
            statement.setInt(3, article.getVisitCount());
            statement.setString(4, article.getAuthorEmail());
            statement.setInt(5, article.getDestinationId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating article failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    article.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating article failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving article: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null or throw a custom exception if you want to handle it higher up
        }
        return article;
    }

    @Override
    public List<Article> findByDestinationId(int destinationId) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE destination_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, destinationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    article.setVisitCount(resultSet.getInt("visit_count"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setDestinationId(resultSet.getInt("destination_id"));
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    article.setVisitCount(resultSet.getInt("visit_count"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setDestinationId(resultSet.getInt("destination_id"));
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public boolean updateVisitCount(int id, int visitCount) {
        String query = "UPDATE articles SET visit_count = ? WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, visitCount);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Article findById(int id) {
        Article article = new Article();
        String query = "SELECT * FROM articles WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    article.setVisitCount(resultSet.getInt("visit_count"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setDestinationId(resultSet.getInt("destination_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public Article update(int id, Article article) {
        String query = "UPDATE articles SET title = ?, text = ? WHERE id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getText());
            statement.setInt(3, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating article failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return article;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM articles WHERE id = ?";
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
    public List<Article> findTop10ByCreatedAtAfterOrderByVisitCountDesc(LocalDate createdAt) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE created_at > ? ORDER BY visit_count DESC LIMIT 10";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(createdAt));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    article.setVisitCount(resultSet.getInt("visit_count"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setDestinationId(resultSet.getInt("destination_id"));
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<Article> findArticlesWithPagination(int offset, int size) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles LIMIT ? OFFSET ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, size);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    article.setDestinationId(resultSet.getInt("destination_id"));
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public int countArticles() {
        String query = "SELECT COUNT(*) FROM articles";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Article> findByDestinationIdWithPagination(int destination_id, int offset, int limit) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE destination_id = ? LIMIT ? OFFSET ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, destination_id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId(resultSet.getInt("id"));
                    article.setTitle(resultSet.getString("title"));
                    article.setText(resultSet.getString("text"));
                    article.setAuthorEmail(resultSet.getString("author_email"));
                    article.setCreatedAt(resultSet.getTimestamp("created_at"));
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }



    @Override
    public int countByDestinationId(int destinationId) {
        String query = "SELECT COUNT(*) AS total FROM articles WHERE destination_id = ?";
        int total = 0;

        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, destinationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }


}
