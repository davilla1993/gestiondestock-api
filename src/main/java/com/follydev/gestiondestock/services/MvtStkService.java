package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.MvtStkDto;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStkService {

    BigDecimal stockReelArticle(Integer idArticle);

    List<MvtStkDto> mvtStkDto(Integer idArticle);

    MvtStkDto entreeStock(MvtStkDto mvtStkDto);

    MvtStkDto sortieStock(MvtStkDto mvtStkDto);

    MvtStkDto correctionStockPos(MvtStkDto mvtStkDto);

    MvtStkDto correctionStockNeg(MvtStkDto mvtStkDto);


}
