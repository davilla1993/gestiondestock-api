package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.ClientDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.repository.ClientRepository;
import com.follydev.gestiondestock.services.ClientService;
import com.follydev.gestiondestock.validators.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){

        this.clientRepository = clientRepository;
    }
    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> errors = ClientValidator.validate(clientDto);

        if(!errors.isEmpty()){
            log. error("Client is not valid {}", clientDto);
            throw new InvalidEntityException("Le client n'est pas valide",
                    ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(clientRepository.save(
                ClientDto.toEntity(clientDto))
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null){
            log.error("Client ID is null");
            return null;
        }

        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'ID " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public ClientDto findByNom(String nom) {
        if(StringUtils.hasLength(nom)) {
            log.error("Client NOM is null");
            return null;
        }
        return clientRepository.findClientByName(nom)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec le NOM = " + nom + "n'a été trouvé dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Category ID is null");
            return;
        }

        clientRepository.deleteById(id);
    }
}
