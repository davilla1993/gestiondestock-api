package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.dto.MvtStkDto;
import com.follydev.gestiondestock.dto.VentesDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.*;
import com.follydev.gestiondestock.repository.ArticleRepository;
import com.follydev.gestiondestock.repository.LigneVenteRepository;
import com.follydev.gestiondestock.repository.VenteRepository;
import com.follydev.gestiondestock.services.MvtStkService;
import com.follydev.gestiondestock.services.VentesService;
import com.follydev.gestiondestock.validators.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository;
    private VenteRepository venteRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public VentesServiceImpl(ArticleRepository articleRepository,
            VenteRepository venteRepository, LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public VentesDto save(VentesDto ventesDto) {
        List<String> errors = VenteValidator.validate(ventesDto);
        if(!errors.isEmpty()){
            log.error("Vente n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();

        ventesDto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if(article.isEmpty()) {
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId()+
                        "n'a été trouvé dans la BDD");
            }
        });

        if(!articleErrors.isEmpty()){
            log.error("One or moe articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas été trouvé dans la BDD",
                    ErrorCodes.VENTE_NOT_VALID);
        }

        Ventes savedVentes = venteRepository.save(VentesDto.toEntity(ventesDto));

        ventesDto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = ligneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVentes(savedVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });
        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null){
            log.warn("Vente IS is NULL");
            return null;
        }
        return venteRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente avec l'ID= " + id +"n'a été trouvé",
                        ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Vente CODE is NULL");
            return null;
        }
        return venteRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a été trouvé avec ce code " + code, ErrorCodes.VENTE_NOT_VALID)
                );
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Vente ID is null");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVentes(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer cette vente. ",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        venteRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente ligneVente){

            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.SORTIE)
                    .sourceMvt(SourceMvtStk.VENTE)
                    .quantite(ligneVente.getQuantite())
                    .idEntreprise(ligneVente.getIdEntreprise())
                    .build();

            mvtStkService.entreeStock(mvtStkDto);
    }
}
