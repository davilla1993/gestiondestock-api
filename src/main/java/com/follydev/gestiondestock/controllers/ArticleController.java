package com.follydev.gestiondestock.controllers;

import com.follydev.gestiondestock.controllers.api.ArticleApi;
import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){

        this.articleService = articleService;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {

        return articleService.save(articleDto);
    }

    @Override
    public ArticleDto findById(Integer id) {

        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {

        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {

        return articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
