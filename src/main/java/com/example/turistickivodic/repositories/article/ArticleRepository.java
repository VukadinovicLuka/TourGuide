package com.example.turistickivodic.repositories.article;

import com.example.turistickivodic.entities.Article;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository {

    Article save(Article article);
    List<Article> findByDestinationId(int id);
    Article findById(int id);
    Article update(int id, Article article);
    boolean delete(Integer id);
    List<Article> getArticles();
    boolean updateVisitCount(int id, int visitCount);
    List<Article> findTop10ByCreatedAtAfterOrderByVisitCountDesc(LocalDate createdAt);
    List<Article> findArticlesWithPagination(int offset, int size);
    int countArticles();
    int countByDestinationId(int destinationId);
    List<Article> findByDestinationIdWithPagination(int destinationId, int page, int size);
}
