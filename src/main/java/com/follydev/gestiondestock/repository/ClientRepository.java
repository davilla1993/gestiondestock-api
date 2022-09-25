package com.follydev.gestiondestock.repository;

import com.follydev.gestiondestock.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByName(String name);
}
