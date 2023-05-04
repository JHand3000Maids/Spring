# CRUD

<aside>
📑 Contents

</aside>

## 프로젝트 생성

### 1. Spring Initializr

![Untitled](CRUD%2065071a652daf4f07845a865d25351caf/Untitled.png)

---

### 2. 구조 설계

![Untitled](CRUD%2065071a652daf4f07845a865d25351caf/Untitled%201.png)

1. **domain(entity)**
    - 객체
    - 글 id, 글 제목, 글 내용
    - 장고 모델 Article
2. **repository**
    - Article.save(), Article.findById()
    - 객체 저장, 수정, 삭제, 찾기 등등….
3. **Service**
    - 비즈니스 로직
    - 게시판 글 저장, 게시판 글 삭제, 게시판 등록
4. **Controller**
    - URL + View
    - 사용자 요청 → /article/save(service) → 응답

## Create, Read

### 1. Domain(Model)

```java
package com.example.CRUD_practice.domain;

public class Article {

    private Long id;
    private String title;
    private String content;
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
```

---

### 2. Repository

```java
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
```

---

### 3. Service

```java
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
```

---

### 4. Controller

```java
package com.example.CRUD_practice.controller;

import com.example.CRUD_practice.domain.Article;
import com.example.CRUD_practice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "index";
    }

    @GetMapping("/articles/create")
    public String input(Model model){
        model.addAttribute("article", new Article());
        return "create";
    }
    @PostMapping("/articles/create")
    public String save(@ModelAttribute Article article, Model model) {
        Article savedArticle = articleService.save(article);
        return "redirect:/articles/" + savedArticle.getId();
    }

    @GetMapping("/articles/{id}")
    public String article(@PathVariable Long id, Model model) {
        Article findArticle = articleService.findById(id);
        model.addAttribute("article", findArticle);
        return "detail";
    }
}
```

---