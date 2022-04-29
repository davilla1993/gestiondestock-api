package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.ClientDto;
import com.follydev.gestiondestock.dto.CommandeClientDto;
import com.follydev.gestiondestock.models.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandeClients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandeClients")
    @ApiOperation(value = "Enrégistrer une commande client", notes = "Cette méthode permet d'enrégistrer ou modifier une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a été bien créée / modifiée"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto commandeClientDto);

    @PostMapping(APP_ROOT + "/commandeClients/update/etat/{idCommande}/{etatCommande}")
    @ApiOperation(value = "Modifier l'etat d'une commande client", notes = "Cette méthode permet de modifier l'etat d'une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                         @PathVariable("etatCommande")EtatCommande etatCommande);

    @PostMapping(APP_ROOT + "/commandeClients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @ApiOperation(value = "Modifier la quantite d'une commande client", notes = "Cette méthode permet de modifier la quantite d'une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La quantite a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La quantite n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande,
       @PathVariable("idLigneCommande")Integer idLigneCommande, @PathVariable("quantite")BigDecimal quantite);


    @PostMapping(APP_ROOT + "/commandeClients/update/client/{idCommande}/{idClient}")
    @ApiOperation(value = "Modifier le client", notes = "Cette méthode permet de modifier le client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été modifiée avec succès"),
            @ApiResponse(code = 400, message = "La client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient);

    @GetMapping(APP_ROOT + "/commandeClients/{idCommandeClient}")
    @ApiOperation(value = "Rechercher une commande client par son ID", notes = "Cette méthode permet de rechercher une commande client par son ID", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucun client n'existe dans la BDD avec l'ID fourni")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(APP_ROOT + "/commandeClients/{codeCommandeClient}")
    @ApiOperation(value = "Rechercher une commande client par son CODE", notes = "Cette méthode permet de rechercher une commande client par son CODE", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a été trouvée dans la BDD"),
            @ApiResponse(code = 400, message = "Aucune commande client n'existe dans la BDD avec le CODE fourni")
    })
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient")String code);

    @GetMapping(APP_ROOT + "/commandeClients/all")
    @ApiOperation(value = "Renvoyer la liste de toutes les commandes les clients", notes = "Cette méthode renvoie la liste de toutes les commandes clients disponibles dans la BDD", responseContainer = "List<CommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des commandes clients / Une liste vide")
    })
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(APP_ROOT + "/commandeClients/delete{idCommandeClient}")
    @ApiOperation(value = "Supprimer une commande client", notes = "Cette méthode permet de supprimer une commande client", response= CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a bien été supprimée")
    })
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id);
}
