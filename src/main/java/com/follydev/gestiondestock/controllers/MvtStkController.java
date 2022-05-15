package com.follydev.gestiondestock.controllers;

import com.follydev.gestiondestock.controllers.api.MvtStkApi;
import com.follydev.gestiondestock.dto.MvtStkDto;
import com.follydev.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvtStkController implements MvtStkApi {

    private MvtStkService mvtStkService;

    @Autowired
    public MvtStkController(MvtStkService mvtStkService) {
        this.mvtStkService = mvtStkService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return mvtStkService.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkDto(Integer idArticle) {
        return mvtStkService.mvtStkDto(idArticle);
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto mvtStkDto) {
        return mvtStkService.entreeStock(mvtStkDto);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto mvtStkDto) {
        return mvtStkService.sortieStock(mvtStkDto);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto mvtStkDto) {
        return mvtStkService.correctionStockPos(mvtStkDto);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto mvtStkDto) {
        return mvtStkService.correctionStockNeg(mvtStkDto);
    }
}
