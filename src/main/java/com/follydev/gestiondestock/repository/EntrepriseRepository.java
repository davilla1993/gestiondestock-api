package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    Optional<Entreprise> findEntrepriseByNom(String nom);
}
