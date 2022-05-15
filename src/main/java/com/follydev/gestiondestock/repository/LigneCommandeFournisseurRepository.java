package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {
    List<LigneCommandeFournisseur> findAllByCommandeFournisseursId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByArticleId(Integer idCommande);

}
