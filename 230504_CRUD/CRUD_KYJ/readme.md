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

## Update, Delete

### 1. Service

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

    public void update(Long id, Article article) {
        articleRepository.update(id, article);
    }

    public void delete(Long id) {
        articleRepository.delete(id);
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

### 2. Controller

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

    @GetMapping("/articles/{id}/edit")
    public String update_input(@PathVariable Long id, Model model) {
        Article findArticle = articleService.findById(id);
        model.addAttribute("article", findArticle);
        return "update";
    }

    @PostMapping("/articles/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Article article) {
        // @ModelAttribute : form에서 value값을 가지고 같은 이름의 객체가 있으면 그 값을 Article에 매핑

        articleService.update(id, article);
        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/";
    }
}
```

## Template

### 1. create.html

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <link th:href="@{/css/bootstrap.min.css}"
        href="./css/bootstrap.min.css" rel="stylesheet">
  <style>
        .container {
            max-width: 560px;
        }
    </style>
</head>
<body>

<div class="container">

  <div class="py-5 text-center">
    <h2>게시글 등록 폼</h2>
  </div>

  <form action="detail.html" th:action th:object="${article}" method="post">
    <div>
      <label for="title">제목</label>
      <input type="text" id="title" th:field="*{title}" class="form-control" placeholder="제목을 입력하세요">
    </div>
    <div>
      <label for="content">내용</label>
      <input type="text" id="content" th:field="*{content}" class="form-control" placeholder="내용을 입력하세요">
    </div>
    <div>
      <label for="author">작성자</label>
      <input type="text" id="author" th:field="*{author}" class="form-control" placeholder="작성자를 입력하세요">
    </div>

    <div class="row">
      <div class="col">
        <button class="w-100 btn btn-primary btn-lg" type="submit">게시글 등록</button>
      </div>
      <div class="col">
        <button class="w-100 btn btn-secondary btn-lg"
                onclick="location.href='items.html'"
                th:onclick="|location.href='@{/}'|"
                type="button">취소</button>
      </div>
    </div>

  </form>

</div> <!-- /container -->
</body>
</html>
```

---

### 2. detail.html

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="./css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 560px;
        }
    </style>
</head>
<body>

<div class="container">

    <div class="py-5 text-center">
        <h2>게시글 상세</h2>
    </div>

    <!-- 추가 -->
    <h2 th:if="${param.status}" th:text="'저장 완료'"></h2>

    <div>
        <label for="articleId">게시글 ID</label>
        <input type="text" id="articleId" name="articleId" class="form-control" value="1" th:value="${article.id}" readonly>
    </div>
    <div>
        <label for="title">제목</label>
        <input type="text" id="title" name="title" class="form-control" value="제목" th:value="${article.title}" readonly>
    </div>
    <div>
        <label for="content">내용</label>
        <input type="text" id="content" name="content" class="form-control" value="내용" th:value="${article.content}" readonly>
    </div>
    <div>
        <label for="author">작성자</label>
        <input type="text" id="author" name="author" class="form-control" value="작성자" th:value="${article.author}" readonly>
    </div>

    <hr class="my-4">

    <div class="row">
        <div class="col">
            <button class="w-100 btn btn-primary btn-lg"
                    th:onclick="|location.href='@{/articles/{articleId}/edit(articleId=${article.id})}'|"
                    type="button">게시글 수정</button>
        </div>
        <div class="col">
            <button class="w-100 btn btn-secondary btn-lg"
                    th:onclick="|location.href='@{/}'|"
                    type="button">목록으로</button>
        </div>
    </div>

</div> <!-- /container -->
</body>
</html>
```

---

### 3. index.html

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="./css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container" style="max-width: 600px">
    <div class="py-5 text-center">
        <h2>게시글 목록</h2>
    </div>

    <div class="row">
        <div class="col">
            <button class="btn btn-primary float-end"
                    th:onclick="|location.href='@{/articles/create}'|"
                    type="button">게시글 등록</button>
        </div>
    </div>

    <hr class="my-4">
    <div>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>제목</th>
                <th>작성자</th>
                <th>내용</th>
                <th>삭제</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="article : ${articles}">
                <td><a href="article.html" th:href="@{/articles/{articleId}(articleId=${article.id})}" th:text="${article.id}">회원id</a></td>
                <td><a href="article.html" th:href="@{|/articles/${article.id}|}" th:text="${article.title}">제목</a></td>
                <td th:text="${article.author}">작성자</td>
                <td th:text="${article.content}">내용</td>
                <td><button class="btn btn-primary float-end" th:onclick="|location.href='@{|/articles/${article.id}/delete|}'|" type="button">삭제하기</button></td>
            </tr>
            </tbody>
        </table>
    </div>

</div> <!-- /container -->

</body>
</html>
```

---

### 4. update.html

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <link th:href="@{/css/bootstrap.min.css}"
        href="./css/bootstrap.min.css" rel="stylesheet">
  <style>
        .container {
            max-width: 560px;
        }
    </style>
</head>
<body>

<div class="container">

  <div class="py-5 text-center">
    <h2>게시글 상세</h2>
  </div>

  <!-- 추가 -->
  <h2 th:if="${param.status}" th:text="'저장 완료'"></h2>

  <form action="detail.html" th:action th:object="${article}" method="post">
    <div>
      <label for="articleId">게시글 ID</label>
      <input type="text" id="articleId" name="articleId" class="form-control" value="1" th:value="${article.id}" readonly>
    </div>
    <div>
      <label for="title">제목</label>
      <input type="text" id="title" name="title" class="form-control" value="제목" th:value="${article.title}">
    </div>
    <div>
      <label for="content">내용</label>
      <input type="text" id="content" name="content" class="form-control" value="내용" th:value="${article.content}">
    </div>
    <div>
      <label for="author">작성자</label>
      <input type="text" id="author" name="author" class="form-control" value="작성자" th:value="${article.author}" readonly>
    </div>

    <hr class="my-4">

    <div class="row">
      <div class="col">
        <button class="w-100 btn btn-primary btn-lg"
                type="submit">저장</button>
      </div>
      <div class="col">
        <button class="w-100 btn btn-secondary btn-lg"
                th:onclick="|location.href='@{/}'|"
                type="button">목록으로</button>
      </div>
    </div>
  </form>
</div> <!-- /container -->
</body>
</html>
```

---