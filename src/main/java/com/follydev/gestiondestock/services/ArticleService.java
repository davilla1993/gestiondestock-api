package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.dto.LigneCommandeClientDto;
import com.follydev.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.follydev.gestiondestock.dto.LigneVenteDto;
import com.follydev.gestiondestock.models.Article;
import com.follydev.gestiondestock.models.LigneCommandeClient;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto articleDto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategory(Integer idCatgegory);

    void delete(Integer id);
}
