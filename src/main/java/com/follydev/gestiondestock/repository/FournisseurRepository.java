package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    Optional<Fournisseur> findFournisseurByNom(String nom);
}
