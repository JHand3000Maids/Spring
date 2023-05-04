package com.example.CRUD_practice.service;

import com.example.CRUD_practice.domain.Article;
import com.example.CRUD_practice.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository; // 멤버 변수로 쓰기 위해 선언
    @Autowired // 생성자 주입
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article findById(Long id){
        return articleRepository.findById(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
