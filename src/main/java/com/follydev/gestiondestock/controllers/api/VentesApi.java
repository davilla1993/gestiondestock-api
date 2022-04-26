package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.*;

@Api(VENTE_ENDPOINT)
public interface VentesApi {

    @PostMapping(CREATE_VENTE_ENDPOINT)
    VentesDto save(@PathVariable VentesDto ventesDto);

    @GetMapping(FIND_VENTE_BY_ID_ENDPOINT)
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(FIND_VENTE_BY_CODE_ENDPOINT)
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(FIND_ALL_VENTE_ENDPOINT)
    List<VentesDto> findAll();

    @DeleteMapping(DELETE_VENTE_ENDPOINT)
    void delete(Integer id);
}
