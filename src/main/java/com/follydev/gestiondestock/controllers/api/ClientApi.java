package com.follydev.gestiondestock.controllers.api;


import com.follydev.gestiondestock.dto.ClientDto;
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

@Api(APP_ROOT + "/clients")
public interface ClientApi {


    @PostMapping(value=APP_ROOT + "/clients/create", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enrégistrer un client", notes = "Cette méthode permet d'enrégistrer ou modifier un client", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été bien créé / modifié"),
            @ApiResponse(code = 400, message = "Le client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto clientDto);


    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par son ID", notes = "Cette méthode permet de rechercher un client par son ID", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucun client n'existe dans la BDD avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer idClient);


    @GetMapping(value = APP_ROOT + "/clients/{nomClient}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par son CODE", notes = "Cette méthode permet de rechercher un client par son CODE", responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été trouvé dans la BDD"),
            @ApiResponse(code = 400, message = "Aucun client n'existe dans la BDD avec le CODE fourni")
    })
    ClientDto findByNomClient(@PathVariable("nomClient") String nomClient);

    @GetMapping(value = APP_ROOT + "/clients/all", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoyer la liste de tous les clients", notes = "Cette méthode renvoie la liste de tous les clients disponibles dans la BDD", responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des clients / Une liste vide")
    })
    List<ClientDto> findAll();

    @GetMapping(value = APP_ROOT + "/clients/delete/{idClient}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer un client", notes = "Cette méthode permet de supprimer un client", response= ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La client a été supprimé")
    })
    void delete(@PathVariable("idClient") Integer id);
}
