package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.*;

@Api(UTILISATEUR_ENDPOINT)
public interface UtilisateurApi {

    @PostMapping(CREATE_UTILISATEUR_ENDPOINT)
    UtilisateurDto save(UtilisateurDto utilisateurDto);

    @GetMapping(FIND_UTILISATEUR_BY_ID_ENDPOINT)
    UtilisateurDto findById(Integer id);

    @GetMapping(FIND_ALL_UTILISATEUR_ENDPOINT)
    List<UtilisateurDto> findAll();

    @DeleteMapping(DELETE_UTILISATEUR_ENDPOINT)
    void delete(Integer id);
}
