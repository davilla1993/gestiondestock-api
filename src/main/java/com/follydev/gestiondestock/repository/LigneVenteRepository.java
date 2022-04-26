package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {
}
