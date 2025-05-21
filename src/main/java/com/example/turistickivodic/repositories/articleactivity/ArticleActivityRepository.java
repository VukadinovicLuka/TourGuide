package com.example.turistickivodic.repositories.articleactivity;

import java.sql.SQLException;
import java.util.List;

public interface ArticleActivityRepository {

    void save(int articleId, int activityId) throws SQLException;
    boolean exists(int articleId, int activityId);
    List<Integer> findActivityIdsByArticleId(int articleId);
    boolean deleteArticleActivity(int articleId);
    List<Integer> findArticleIdsByActivityId(int activityId);
}
