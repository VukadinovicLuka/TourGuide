package com.example.turistickivodic.services;

import com.example.turistickivodic.repositories.articleactivity.ArticleActivityRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleActivityService {

    @Inject
    private ArticleActivityRepository articleActivitiesRepository;

    public void addArticleActivity(int articleId, int activityId) throws Exception {
        if (articleActivitiesRepository.exists(articleId, activityId)) {
            throw new Exception("Activity already exists for this article");
        }
        articleActivitiesRepository.save(articleId, activityId);
    }

    public List<Integer> getActivityIdsByArticleId(int articleId) {
        return articleActivitiesRepository.findActivityIdsByArticleId(articleId);
    }

    public boolean deleteArticleActivity(int articleId) {
        return articleActivitiesRepository.deleteArticleActivity(articleId);
    }

    public List<Integer> getArticleIdsByActivityId(int activityId) {
        return articleActivitiesRepository.findArticleIdsByActivityId(activityId);
    }

}

