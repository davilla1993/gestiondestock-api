package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.*;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.*;
import com.follydev.gestiondestock.repository.*;
import com.follydev.gestiondestock.services.CommandeFournisseurService;
import com.follydev.gestiondestock.services.MvtStkService;
import com.follydev.gestiondestock.validators.ArticleValidator;
import com.follydev.gestiondestock.validators.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
    LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, FournisseurRepository fournisseurRepository,
            ArticleRepository articleRepository, MvtStkService mvtStkService) {

        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;

    }
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {
        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseurDto);

        if(!errors.isEmpty()){
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("La commande fournisseur n'est pas valide",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getFournisseur().getId());
        if(!fournisseur.isPresent()){
            log.warn("Fournisseur with ID {} as not found in the DB", commandeFournisseurDto.getFournisseur().getId());
            throw  new EntityNotFoundException("Aucun fournisseur avec l'ID " + commandeFournisseurDto.getFournisseur().getId()+
                    "n'a été trouvé dans la BDD");
        }

        List<String> articleErrors = new ArrayList<>();

        if(commandeFournisseurDto.getLigneCommandeFournisseurs() != null) {
            commandeFournisseurDto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if(ligCmdFrs.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if(article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdFrs.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enrégistrer une commande avec un article NULL");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD",
                    ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeFournisseur saveCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));

        if(commandeFournisseurDto.getLigneCommandeFournisseurs() != null) {
            commandeFournisseurDto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
                ligneCommandeFournisseur.setCommandeFournisseurs(saveCmdFrs);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

            });
        }
        return CommandeFournisseurDto.fromEntity(saveCmdFrs);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Commande fournisseur ID is NULL");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande fournisseur avec l'ID =" + id + "n'a été trouvé",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(code == null) {
            log.error("Commande fournisseur CODE is NULL");
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec le CODE = " + code + "n'a été trouvé",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);

        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if(commandeFournisseur.isCommandeLivree()){
            updateMvtStk(idCommande);
        }

        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("ID commande IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou zero",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);
        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);

        if (idFournisseur == null) {
            log.error("ID fournisseur IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if(fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client n'a été trouve avec l'ID " + idFournisseur,
                    ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur)));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()) {
            throw new EntityNotFoundException("Aucun artile n'a ete trouve avec l'ID " + idArticle,
                    ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(!errors.isEmpty()){
            throw new EntityNotFoundException("Aurticle invalide", ErrorCodes.ARTICLE_NOT_FOUND, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }



    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Commande client ID is NULL");
            return;
        }
        commandeFournisseurRepository.deleteById(id);
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande == null) {
            log.error("Commande fournisseur IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande == null) {
            log.error("L'etat de la commande fournisseur IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String message){
        if (idArticle == null) {
            log.error("L'ID Article is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + message + "ID article",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }
    }

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande){
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);

        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande car elle est déjà livrée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }

        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur( Integer idLigneCommande){
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if(ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException("Aucune ligne commande client n'a ete trouve avec l'ID" + idLigneCommande,
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_UNCHANGEABLE);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande);
        ligneCommandeFournisseurs.forEach(lig -> {
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.ENTREE)
                    .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();

            mvtStkService.entreeStock(mvtStkDto);
        });
    }
}
