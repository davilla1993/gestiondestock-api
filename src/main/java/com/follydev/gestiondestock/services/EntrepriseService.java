package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto entrepriseDto);

    EntrepriseDto findById(Integer id);

    EntrepriseDto findByNom(String nom);

    List<EntrepriseDto> findAll();

    void delete(Integer id);

}
