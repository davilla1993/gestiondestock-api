package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto) {
        List<String> errors = new ArrayList<>();

        if(fournisseurDto == null){
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prenom du fournisseur");
            errors.add("Veuillez renseigner le prenom du fournisseur");
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");

            return errors;
        }
        if(!StringUtils.hasLength(fournisseurDto.getNom())){
            errors.add("Veuillez renseigner le nom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())){
            errors.add("Veuillez renseigner le prenom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getMail())){
            errors.add("Veuillez renseigner l'adresse mail du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())){
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");
        }
        if(fournisseurDto.getAdresse() == null){
            errors.add("Veuillez renseigner l'adresse du fournisseur");
        } else {

            if(!StringUtils.hasLength(fournisseurDto.getAdresse().getAdresse1())){
                errors.add("Le champ 'Adresse 1' est obligatoire");
            }
            if(!StringUtils.hasLength(fournisseurDto.getAdresse().getVille())){
                errors.add("Le champ 'Ville' est obligatoire");
            }
            if(!StringUtils.hasLength(fournisseurDto.getAdresse().getCodePostale())){
                errors.add("Le 'Code postale' est obligatoire");
            }
            if(!StringUtils.hasLength(fournisseurDto.getAdresse().getPays())){
                errors.add("Le 'Pays' est obligatoire");
            }
        }
        return errors;
    }
}
