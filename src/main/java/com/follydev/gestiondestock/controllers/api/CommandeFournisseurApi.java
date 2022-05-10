package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.dto.CommandeClientDto;
import com.follydev.gestiondestock.dto.CommandeFournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.*;

@Api(COMMANDE_FOURNISSEUR_ENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "Enrégistrer une commande fournisseur", notes = "Cette méthode permet d'enrégistrer ou modifier une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a été bien créée / modifiée"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto commandeFournisseurDto);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    @ApiOperation(value = "Rechercher une commande fournisseur par son ID", notes = "Cette méthode permet de rechercher une commande fournisseur par son ID", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucun fournisseur n'existe dans la BDD avec l'ID fourni")
    })
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    @ApiOperation(value = "Rechercher une commande fournisseur par son CODE", notes = "Cette méthode permet de rechercher une commande fournisseur par son CODE", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a été trouvée dans la BDD"),
            @ApiResponse(code = 400, message = "Aucune commande fournisseur n'existe dans la BDD avec le CODE fourni")
    })
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur")String code);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "Renvoyer la liste de toutes les commandes fournisseur", notes = "Cette méthode renvoie la liste de toutes les commandes fournisseur disponibles dans la BDD", responseContainer = "List<CommandeFournisseurDtoDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des commandes fournisseur / Une liste vide")
    })
    List<CommandeFournisseurDto> findAll();

    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "Supprimer une commande fournisseur", notes = "Cette méthode permet de supprimer une commande fournisseur", response= CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a bien été supprimée")
    })
    void delete(Integer id);
}
