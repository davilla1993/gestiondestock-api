package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.*;

@Api(ENTREPRISE_ENDPOINT)
public interface EntrepriseApi {

    @PostMapping(CREATE_ENTREPRISE_ENDPOINT)
    EntrepriseDto save(@RequestBody EntrepriseDto entrepriseDto);

    @GetMapping(FIND_ENTREPRISE_BY_ID_ENDPOINT)
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(FIND_ALL_ENTREPRISE_ENDPOINT)
    List<EntrepriseDto> findAll();

    @DeleteMapping(DELETE_ENTREPRISE_ENDPOINT)
    void delete(Integer id);
}
