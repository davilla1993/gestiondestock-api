package com.follydev.gestiondestock.dto;

import com.follydev.gestiondestock.models.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class ArticleDto {

    private Integer id;

    private String codeArticle;

    private String designation;

    private BigDecimal prixUnitaireHT;

    private BigDecimal tauxTVA;

    private BigDecimal prixUnitaireTTC;

    private String photo;

    private CategoryDto category;

    public static ArticleDto fromEntity(Article article) {
        if(article == null){
            return null;
        }

        return ArticleDto.builder()
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHT(article.getPrixUnitaireHT())
                .tauxTVA(article.getTauxTVA())
                .prixUnitaireTTC(article.getPrixUnitaireTTC())
                .photo(article.getPhoto())
                .build();

    }

    public static Article toEntity(ArticleDto articleDto) {

        if(articleDto == null) {
            return null;
        }

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHT(articleDto.getPrixUnitaireHT());
        article.setTauxTVA(articleDto.getTauxTVA());
        article.setPrixUnitaireTTC(articleDto.getPrixUnitaireTTC());
        article.setPhoto(articleDto.getPhoto());

        return article;
    }
}
