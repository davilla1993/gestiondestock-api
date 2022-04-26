package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.follydev.gestiondestock.utils.Constants.*;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {


    @PostMapping(value=APP_ROOT + "/categories/create", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enrégistrer une categorie", notes = "Cette méthode permet d'enrégistrer ou modifier une ccategorie", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie a été bien créé / modifié"),
            @ApiResponse(code = 400, message = "L'objet categorie n'est pas valide")
    })
    CategoryDto save(@RequestBody CategoryDto categoryDto);


    @GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une catégorie par son ID", notes = "Cette méthode permet de rechercher une catégorie par son ID", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La catégorie a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucune cateégorie n'existe dans la BDD avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory);


    @GetMapping(value = APP_ROOT + "/categories/{codeCategory}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une catégorie par son CODE", notes = "Cette méthode permet de rechercher une catégorie par son CODE", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La catégorie a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucune catégorie n'existe dans la BDD avec le CODE fourni")
    })
    CategoryDto findByCodeCategory(@PathVariable("codeCategory") String codeCategory);

    @GetMapping(value = APP_ROOT + "/category/all", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoyer la liste de toutes les catégories", notes = "Cette méthode renvoie la liste de toutes les catégories disponibles dans la BDD", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categories / Une liste vide")
    })
    List<CategoryDto> findAll();

    @GetMapping(value = APP_ROOT + "/categories/delete/{idCategory}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer une catéorie", notes = "Cette méthode permet de supprimer une catégorie", response= CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La catégorie a été supprimée")
    })
    void delete(@PathVariable("idCategory") Integer id);
}
