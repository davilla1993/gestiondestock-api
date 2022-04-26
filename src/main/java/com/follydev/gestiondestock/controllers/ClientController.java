package com.follydev.gestiondestock.controllers;

import com.follydev.gestiondestock.controllers.api.ClientApi;
import com.follydev.gestiondestock.dto.ClientDto;
import com.follydev.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @Override
    public ClientDto findById(Integer idClient) {
        return clientService.findById(idClient);
    }

    @Override
    public ClientDto findByNomClient(String nomClient) {

        return clientService.findByNom(nomClient);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
