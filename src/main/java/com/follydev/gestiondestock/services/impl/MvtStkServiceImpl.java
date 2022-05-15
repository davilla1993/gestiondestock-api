package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.MvtStkDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.models.TypeMvtStk;
import com.follydev.gestiondestock.repository.MvtStkRepository;
import com.follydev.gestiondestock.services.ArticleService;
import com.follydev.gestiondestock.services.MvtStkService;
import com.follydev.gestiondestock.validators.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    private MvtStkRepository mvtStkRepository;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository, ArticleService articleService){
        this.mvtStkRepository = mvtStkRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if(idArticle == null){
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkDto(Integer idArticle) {
        return mvtStkRepository.findAllByArticleId(idArticle)
                .stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto mvtStkDto) {
        return entreePostive(mvtStkDto, TypeMvtStk.ENTREE);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto mvtStkDto) {
        return entreePostive(mvtStkDto, TypeMvtStk.SORTIE);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto mvtStkDto) {
        return entreePostive(mvtStkDto, TypeMvtStk.CORRECTION_POS);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto mvtStkDto) {
        return sortieNegative(mvtStkDto, TypeMvtStk.CORRECTION_NEG);
    }

    private MvtStkDto entreePostive(MvtStkDto mvtStkDto, TypeMvtStk typeMvtStk){
        List<String> errors = MvtStkValidator.validate(mvtStkDto);
        if(!errors.isEmpty()){
            log.error("Impossible de faire l'entrée en stock");
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        mvtStkDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvtStkDto.getQuantite().doubleValue())
                ));

        mvtStkDto.setTypeMvt(typeMvtStk);

        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(mvtStkDto))
        );
    }

    private MvtStkDto sortieNegative(MvtStkDto mvtStkDto, TypeMvtStk typeMvtStk){
        List<String> errors = MvtStkValidator.validate(mvtStkDto);
        if(!errors.isEmpty()){
            log.error("Impossible de faire l'entrée en stock");
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        mvtStkDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvtStkDto.getQuantite().doubleValue()) * (-1)
                ));

        mvtStkDto.setTypeMvt(typeMvtStk);

        return MvtStkDto.fromEntity(
                mvtStkRepository.save(MvtStkDto.toEntity(mvtStkDto))
        );
    }
}
