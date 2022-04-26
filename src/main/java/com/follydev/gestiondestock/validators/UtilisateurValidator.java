package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();

        if(utilisateurDto == null){
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner le prenom d'utilisateur");
            errors.add("Veuillez renseigner une adresse mail utilisateur");
            errors.add("Veuillez mettre un mot de passe utilisateur");
            errors.add("Veuillez renseigner le nom d'utilisateur");

            return errors;
        }
        if(!StringUtils.hasLength(utilisateurDto.getNom())){
                errors.add("Veuillez renseigner le nom d'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veuillez renseigner le prenom d'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuillez renseigner une adresse mail utilisateur");
        }
        if( !StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("Veuillez mettre un mot de passe utilisateur");
        }
        if(utilisateurDto.getEntreprise() == null){
            errors.add("Veuillez renseigner l'entreprise de l'utilisateur");
        }
        if(utilisateurDto.getAdresse() == null){
            errors.add("Veuillez renseigner l'adresse utilisateur");
        } else {

            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())){
                errors.add("Le champ 'Adresse 1' est obligatoire");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())){
                errors.add("Le champ 'Ville' est obligatoire");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())){
                errors.add("Le 'Code postale' est obligatoire");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())){
                errors.add("Le 'Pays' est obligatoire");
            }
        }
            return errors;
        }
    }

