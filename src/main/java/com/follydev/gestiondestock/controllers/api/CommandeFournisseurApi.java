package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.CommandeFournisseurDto;
import com.follydev.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.follydev.gestiondestock.models.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto commandeFournisseurDto);

    @PatchMapping(APP_ROOT + "/commandeFournisseurs/update/etat/{idCommande}/{etatCommande}")
    @ApiOperation(value = "Modifier l'etat d'une commande fournisseur", notes = "Cette méthode permet de modifier l'etat d'une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                         @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandeFournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @ApiOperation(value = "Modifier la quantite d'une commande fournisseur", notes = "Cette méthode permet de modifier la quantite d'une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La quantite a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La quantite n'est pas valide")
    })
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande,
                                                             @PathVariable("idLigneCommande")Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);


    @PatchMapping(APP_ROOT + "/commandeFournisseurs/update/client/{idCommande}/{idClient}")
    @ApiOperation(value = "Modifier le fournisseur", notes = "Cette méthode permet de modifier le fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La fournisseur n'est pas valide")
    })
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande,
                                                   @PathVariable("idClient") Integer idFournisseur);

    @PatchMapping(APP_ROOT + "/commandeFournisseurs/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    @ApiOperation(value = "Modifier les articles", notes = "Cette méthode permet de modifier les articles sur une commande", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "L'article n'est pas valide")
    })
    ResponseEntity<CommandeFournisseurDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
                                                    @PathVariable("idLigneCommande")Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    @ApiOperation(value = "Rechercher une commande fournisseur par son ID", notes = "Cette méthode permet de rechercher une commande fournisseur par son ID", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucun fournisseur n'existe dans la BDD avec l'ID fourni")
    })
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    @ApiOperation(value = "Rechercher une commande fournisseur par son CODE", notes = "Cette méthode permet de rechercher une commande fournisseur par son CODE", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a été trouvée dans la BDD"),
            @ApiResponse(code = 400, message = "Aucune commande fournisseur n'existe dans la BDD avec le CODE fourni")
    })
    ResponseEntity<CommandeFournisseurDto> findByCode(@PathVariable("codeCommandeFournisseur")String code);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "Renvoyer la liste de toutes les commandes fournisseur", notes = "Cette méthode renvoie la liste de toutes les commandes fournisseur disponibles dans la BDD", responseContainer = "List<CommandeFournisseurDtoDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des commandes fournisseur / Une liste vide")
    })
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @GetMapping(APP_ROOT + "/commandeFournisseurs/LigneCommande/{idCommande}")
    @ApiOperation(value = "Consulter toutes les lignes commandes fournissseur", notes = "Cette méthode permet de toutes les lignes commandes relatives à une commande", responseContainer = "List<CommandeFournisseurDto.class>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste de toutes les lignes commandes fournisseur / Une liste vide"),
    })
    ResponseEntity<List<LigneCommandeFournisseurDto>> findAllLignesCommandesFournisseursByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);


    @PatchMapping(APP_ROOT + "/commandeFournisseurs/delete/article/{idCommande}/{idLigneCommande}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette méthode permet de supprimer les articles sur une commande", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé avec succès"),
            @ApiResponse(code = 400, message = "L'artice n'est pas valide")
    })
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande")Integer idCommande, @PathVariable("idLigneCommande")Integer idLigneCommande);

    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @ApiOperation(value = "Supprimer une commande fournisseur", notes = "Cette méthode permet de supprimer une commande fournisseur", response= CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a bien été supprimée")
    })
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id);
}
