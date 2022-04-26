package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();

        if(entrepriseDto == null){
            errors.add("Veuiller renseigner le nom de l'entreprise");
            errors.add("Veuiller donner une description de l'entreprise");
            errors.add("Veuiller renseigner de l'entreprise");
            errors.add("Veuiller renseigner le code fiscal de l'entreprise");
            errors.add("Veuiller renseigner l'adresse email de l'entreprise");
            errors.add("Veuiller renseigner le numero de telephone de l'entreprise");

            return errors;

        }
        if(!StringUtils.hasLength(entrepriseDto.getNom())){
            errors.add("Veuiller renseigner le nom de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getDescription())){
            errors.add("Veuiller donner une description de l'entreprise");
        }
        if(entrepriseDto.getAdresse() == null){
            errors.add("Veuiller renseigner de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getCodeFiscal())){
            errors.add("Veuiller renseigner le code fiscal de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getEmail())){
            errors.add("Veuiller renseigner l'adresse email de l'entreprise");
        }
        if(!StringUtils.hasLength(entrepriseDto.getNumTel())){
            errors.add("Veuiller renseigner le numero de telephone de l'entreprise");
        }

        return errors;
    }
}
