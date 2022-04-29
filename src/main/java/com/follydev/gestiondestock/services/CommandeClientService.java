package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.CommandeClientDto;
import com.follydev.gestiondestock.dto.LigneCommandeClientDto;
import com.follydev.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {


    CommandeClientDto save(CommandeClientDto commandeClientDto);

    CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeClientDto updateClient(Integer idCommande, Integer idClient);

    CommandeClientDto findById(Integer id);

    CommandeClientDto findByCode(String code);

    List<CommandeClientDto> findAll();

    List<LigneCommandeClientDto> findAllLignesCommandesClientsByCommandeClientId(Integer idCommande);

    void delete(Integer id);


}
