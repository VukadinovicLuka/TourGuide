package com.example.turistickivodic.repositories.comment;

import com.example.turistickivodic.entities.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> findByArticleId(int articleId);
    Comment save(Comment comment);
    void deleteByArticleId(int articleId);

}
