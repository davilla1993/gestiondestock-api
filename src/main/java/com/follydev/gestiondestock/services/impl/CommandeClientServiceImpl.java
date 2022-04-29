package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.ArticleDto;
import com.follydev.gestiondestock.dto.ClientDto;
import com.follydev.gestiondestock.dto.CommandeClientDto;
import com.follydev.gestiondestock.dto.LigneCommandeClientDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.*;
import com.follydev.gestiondestock.repository.ArticleRepository;
import com.follydev.gestiondestock.repository.ClientRepository;
import com.follydev.gestiondestock.repository.CommandeClientRepository;
import com.follydev.gestiondestock.repository.LigneCommandeClientRepository;
import com.follydev.gestiondestock.services.CommandeClientService;
import com.follydev.gestiondestock.validators.ArticleValidator;
import com.follydev.gestiondestock.validators.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneCommandeClientDto ligneCommandeClientDto;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;


    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, ClientRepository clientRepository,
                                     ArticleRepository articleRepository, LigneCommandeClientRepository ligneCommandeClientRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;

    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {
        List<String> errors = CommandeClientValidator.validate(commandeClientDto);

        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide",
                    ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }

        if (commandeClientDto.getId() != null && commandeClientDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande car elle est déjà livrée",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }

        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId());
        if (!client.isPresent()) {
            log.warn("Client with ID {} as not found in the DB", commandeClientDto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID " + commandeClientDto.getClient().getId() +
                    "n'a été trouvé dans la BDD");
        }

        List<String> articleErrors = new ArrayList<>();

        if (commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enrégistrer une commande avec un article nul");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD",
                    ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient saveCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));

        if (commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(saveCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);

            });
        }
        return CommandeClientDto.fromEntity(saveCmdClt);
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {

        checkIdCommande(idCommande);

        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande client IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        commandeClient.setEtatCommande(etatCommande);

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande,
                                                    BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("ID commande IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou zero",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

            return commandeClient;
        }


    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);

        if (idClient == null) {
            log.error("ID client IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }


        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if(clientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client n'a été trouve avec l'ID " + idClient,
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
            commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient)));
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()) {
            throw new EntityNotFoundException("Aucun artile n'a ete trouve avec l'ID " + idArticle,
                    ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(!errors.isEmpty()){
            throw new EntityNotFoundException("Aurticle invalide", ErrorCodes.ARTICLE_NOT_FOUND, errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("Commande client ID is NULL");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande client avec l'ID =" + id + "n'a été trouvé",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND)
                );
    }

    @Override
    public CommandeClientDto findByCode(String code) {
    if(code == null) {
        log.error("Commande client CODE is NULL");
    }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec le CODE = " + code + "n'a été trouvé",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<CommandeClientDto> findAll() {

        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Commande client ID is NULL");
            return;
        }
        commandeClientRepository.deleteById(id);
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande){
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientsByCommandeClientId(Integer idCommande){
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    private void checkIdCommande(Integer idCommande){
        if (idCommande == null) {
            log.error("Commande client IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if (idLigneCommande == null) {
            log.error("L'etat de la commande client IS is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String message){
        if (idArticle == null) {
            log.error("L'ID Article is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + message + "ID article",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande){
        CommandeClientDto commandeClient = findById(idCommande);

        if (commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande car elle est déjà livrée",
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }

        return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient( Integer idLigneCommande){
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);
        if(ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException("Aucune ligne commande client n'a ete trouve avec l'ID" + idLigneCommande,
                    ErrorCodes.COMMANDE_CLIENT_NOT_UNCHANGEABLE);
        }
        return ligneCommandeClientOptional;
    }
}
