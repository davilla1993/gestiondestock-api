package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.follydev.gestiondestock.dto.UtilisateurDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.Utilisateur;
import com.follydev.gestiondestock.repository.UtilisateurRepository;
import com.follydev.gestiondestock.services.UtilisateurService;
import com.follydev.gestiondestock.validators.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;


    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;

    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateurDto);

        if(!errors.isEmpty()) {
            log.error("Utilisatuer is not VALID {}", utilisateurDto);
            throw new InvalidEntityException("Utilisateur n'est pas valide",
                    ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }
        return UtilisateurDto.fromEntity(utilisateurRepository.save(
                UtilisateurDto.toEntity(utilisateurDto))
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return null;

        }

        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID= " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

    }

    @Override
    public UtilisateurDto findByEmail(String email){
        if(StringUtils.hasLength(email)) {
            log.error("Email address is null");
            return null;
        }
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'adresse mail = " + email + "n'a été trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Utilisateur ID is null");
            return;
        }

        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto changerMotDePasseUtilisateurDto) {
        validate(changerMotDePasseUtilisateurDto);

        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(changerMotDePasseUtilisateurDto.getId());
        if(utilisateurOptional.isEmpty()) {
            log.warn("Aucun utilisateur n'a été trouvé avec l'ID " + changerMotDePasseUtilisateurDto.getId());
            throw new InvalidOperationException("Aucun utilisateur n'a été trouvé avec l'ID \" + changerMotDePasseUtilisateurDto.getId()",
                    ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(changerMotDePasseUtilisateurDto.getMotDePasse());

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if(dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException("Aucune information n'a été fournit pour changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("Mots de passe non valides");
            throw new InvalidOperationException("Les mots de passe ne sont pas valides",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("Les deux mots de passes ne concordent pas");
            throw new InvalidOperationException("Les deux mots de passes ne concordent pas",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
