package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.*;

@Api(FOURNISSEUR_ENDPOINT)
public interface FournisseurApi {

    @PostMapping(CREATE_FOURNISSEUR_ENDPOINT)
    FournisseurDto save(@RequestBody FournisseurDto fournisseurDto);

    @GetMapping(FIND_FOURNISSEUR_BY_ID_ENDPOINT)
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(FIND_ALL_FOURNISSEUR_ENDPOINT)
    List<FournisseurDto> findAll();

    @DeleteMapping(DELETE_FOURNISSEUR_ENDPOINT)
    void delete(Integer id);
}
