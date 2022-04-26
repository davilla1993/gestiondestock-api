package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.ArticleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {

    @PostMapping(value=APP_ROOT + "/articles/create", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enrégistrer un article", notes = "Cette méthode permet d'enrégistrer ou modifier un article", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article a été bien créé / modifié"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto articleDto);


    @GetMapping(value = APP_ROOT + "/articles/{idArticle}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par son ID", notes = "Cette méthode permet de rechercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);


    @GetMapping(value = APP_ROOT + "/articles/{codeArticle}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par son CODE", notes = "Cette méthode permet de rechercher un article par son CODE", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = APP_ROOT + "/articles/all", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoyer la liste de tous les articles", notes = "Cette méthode renvoi la liste de tous les articles disponibles dans la BDD", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles / Une liste vide")
    })
    List<ArticleDto> findAll();

    @GetMapping(value = APP_ROOT + "/articles/delete/{idArticle}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer un article", notes = "Cette méthode permet de supprimer un article", response= ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé")
    })
    void delete(@PathVariable("idArticle") Integer id);
}
