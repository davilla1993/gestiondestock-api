package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {
}
