package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto commandeFournisseurDto){
        List<String> errors = new ArrayList<>();

        if(commandeFournisseurDto == null){
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez choisir le fournisseur de la commande");

            return errors;
        }
        if(StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("Veuillez renseigner le code de la commande");
        }
        if(commandeFournisseurDto.getDateCommande() == null){
            errors.add("Veuillez renseigner la date de la commande");
        }
        if(commandeFournisseurDto.getFournisseur() == null){
            errors.add("Veuillez choisir le fournisseur de la commande");
        }

        return errors;
    }
}
