package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.CommandeClient;
import com.follydev.gestiondestock.models.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List<CommandeFournisseur> findAllByFournisseurId(Integer id);
}
