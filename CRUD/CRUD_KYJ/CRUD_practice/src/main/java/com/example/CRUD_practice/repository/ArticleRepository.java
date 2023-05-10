package com.example.CRUD_practice.repository;

import com.example.CRUD_practice.domain.Article;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // 빈 등록
public class ArticleRepository {
    private static final Map<Long, Article> store = new HashMap<>(); // 상수, 최종

    private static long sequence = 0L;

    public Article save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article); // put -> map 함수 기능, 값 넣기 (key: id, val:article)
        return article;
    }

    public Article findById(Long id) {
        return store.get(id);
    }

    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long articleId, Article updateParam) {
        Article findArticle = findById(articleId);

        findArticle.setTitle(updateParam.getTitle());
        findArticle.setContent(updateParam.getContent());
        findArticle.setAuthor(updateParam.getAuthor());
    }

    public void clearStore() {
        store.clear();
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
