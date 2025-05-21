package com.example.turistickivodic.repositories.comment;

import com.example.turistickivodic.entities.Comment;
import com.example.turistickivodic.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    @Override
    public List<Comment> findByArticleId(int articleId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE article_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setId(resultSet.getInt("id"));
                    comment.setArticle_id(resultSet.getInt("article_id"));
                    comment.setAuthor_name(resultSet.getString("author_name"));
                    comment.setText(resultSet.getString("text"));
                    comment.setCreated_at(resultSet.getTimestamp("created_at"));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public Comment save(Comment comment) {
        String query = "INSERT INTO comments (article_id, author_name, text, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, comment.getArticle_id());
            statement.setString(2, comment.getAuthor_name());
            statement.setString(3, comment.getText());
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating comment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return comment;
    }

    @Override
    public void deleteByArticleId(int articleId) {
        String query = "DELETE FROM comments WHERE article_id = ?";
        try (Connection connection = this.newConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, articleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
