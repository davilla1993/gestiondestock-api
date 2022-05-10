package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.CommandeFournisseurDto;
import com.follydev.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.follydev.gestiondestock.models.CommandeFournisseur;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateAticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
