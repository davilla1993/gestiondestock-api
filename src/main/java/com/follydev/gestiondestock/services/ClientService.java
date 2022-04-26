package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto clientDto);

    ClientDto findById(Integer id);

    ClientDto findByNom(String nom);

    List<ClientDto> findAll();

    void delete(Integer id);
}
