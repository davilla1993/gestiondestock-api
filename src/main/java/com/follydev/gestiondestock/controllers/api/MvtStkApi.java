package com.follydev.gestiondestock.controllers.api;

import com.follydev.gestiondestock.dto.MvtStkDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.follydev.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/mvtStk")
public interface MvtStkApi {

    @GetMapping(APP_ROOT + "/mvtStk/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle")Integer idArticle);

    @GetMapping(APP_ROOT + "/mvtStk/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkDto(@PathVariable("idArticle")Integer idArticle);

    @PostMapping(APP_ROOT + "/mvtStk/entree")
    MvtStkDto entreeStock(@RequestBody MvtStkDto mvtStkDto);

    @PostMapping(APP_ROOT + "/mvtStk/sortie")
    MvtStkDto sortieStock(@RequestBody MvtStkDto mvtStkDto);

    @PostMapping(APP_ROOT + "/mvtStk/correctionpos")
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto mvtStkDto);

    @PostMapping(APP_ROOT + "/mvtStk/correctionneg")
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto mvtStkDto);
}
