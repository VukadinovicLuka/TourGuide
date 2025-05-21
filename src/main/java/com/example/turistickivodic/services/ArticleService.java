package com.example.turistickivodic.services;

import com.example.turistickivodic.entities.Activity;
import com.example.turistickivodic.entities.Article;
import com.example.turistickivodic.entities.Destination;
import com.example.turistickivodic.repositories.article.ArticleRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public Article addArticle(Article article){
        return articleRepository.save(article);
    }

    public List<Article> getArticlesByDestination(int destinationId) {
        return articleRepository.findByDestinationId(destinationId);
    }

    public List<Article> getArticles() {
        return articleRepository.getArticles();
    }

    public Article getArticleById(int id) {
        return articleRepository.findById(id);
    }

    public Article updateArticle(int id, Article article) {
        return articleRepository.update(id, article);
    }

    public boolean deleteArticle(Integer id) {
        return articleRepository.delete(id);
    }

    public boolean updateVisitCount(int id, int visitCount) {
        return articleRepository.updateVisitCount(id, visitCount);
    }

    public List<Article> getArticles(int page, int size) {
        int offset = (page - 1) * size;
        return articleRepository.findArticlesWithPagination(offset, size);
    }

    public int getTotalArticles() {
        return articleRepository.countArticles();
    }

    public List<Article> findTop10ArticlesSince(LocalDate sinceDate) {
        return articleRepository.findTop10ByCreatedAtAfterOrderByVisitCountDesc(sinceDate);
    }

    public List<Article> getArticlesByDestination(int destinationId, int offset, int limit) {
        return articleRepository.findByDestinationIdWithPagination(destinationId, offset, limit);
    }

    public int getTotalArticlesByDestination(int destinationId) {
        return articleRepository.countByDestinationId(destinationId);
    }


}
