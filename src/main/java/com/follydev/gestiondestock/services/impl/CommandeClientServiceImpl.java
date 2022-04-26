package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.CommandeClientDto;
import com.follydev.gestiondestock.dto.LigneCommandeClientDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.models.Article;
import com.follydev.gestiondestock.models.Client;
import com.follydev.gestiondestock.models.CommandeClient;
import com.follydev.gestiondestock.models.LigneCommandeClient;
import com.follydev.gestiondestock.repository.ArticleRepository;
import com.follydev.gestiondestock.repository.ClientRepository;
import com.follydev.gestiondestock.repository.CommandeClientRepository;
import com.follydev.gestiondestock.repository.LigneCommandeClientRepository;
import com.follydev.gestiondestock.services.CommandeClientService;
import com.follydev.gestiondestock.validators.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
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

        if(!errors.isEmpty()){
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide",
                    ErrorCodes.COMMANDE_CLIENT_NOT_VALID,errors);
        }

        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId());
        if(!client.isPresent()){
            log.warn("Client with ID {} as not found in the DB", commandeClientDto.getClient().getId());
            throw  new EntityNotFoundException("Aucun client avec l'ID " +commandeClientDto.getClient().getId()+
                    "n'a été trouvé dans la BDD");
        }

        List<String> articleErrors = new ArrayList<>();

        if(commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if(ligCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if(article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enrégistrer une commande avec un article nul");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD",
                    ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient saveCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));

        if(commandeClientDto.getLigneCommandeClients() != null) {
        commandeClientDto.getLigneCommandeClients().forEach(ligCmdClt -> {
            LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
            ligneCommandeClient.setCommandeClient(saveCmdClt);
            ligneCommandeClientRepository.save(ligneCommandeClient);

        });
     }
        return CommandeClientDto.fromEntity(saveCmdClt);
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
}
