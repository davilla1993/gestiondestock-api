package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvtStkRepository extends JpaRepository<MvtStk, Integer> {
}
