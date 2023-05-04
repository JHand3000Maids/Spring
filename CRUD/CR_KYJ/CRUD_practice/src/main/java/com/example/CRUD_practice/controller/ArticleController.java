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
