package com.example.turistickivodic.services;

import com.example.turistickivodic.entities.Comment;
import com.example.turistickivodic.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class CommentService {

    @Inject
    CommentRepository commentRepository;

    public List<Comment> getCommentsByArticleId(int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteCommentsByArticleId(int articleId) throws SQLException {
        commentRepository.deleteByArticleId(articleId);
    }
}
