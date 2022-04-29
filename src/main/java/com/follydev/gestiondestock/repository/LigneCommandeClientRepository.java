package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.dto.LigneCommandeClientDto;
import com.follydev.gestiondestock.models.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    List<LigneCommandeClientDto> findAllByCommandeClientId(Integer idCommande);
}
