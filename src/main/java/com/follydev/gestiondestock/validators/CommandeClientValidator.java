package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto commandeClientDto){
        List<String> errors = new ArrayList<>();

        if(commandeClientDto == null){
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez choisir le client de la commande");

            return errors;
        }
        if(!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("Veuillez renseigner le code de la commande");
        }
        if(commandeClientDto.getDateCommande() == null){
            errors.add("Veuillez renseigner la date de la commande");
        }
        if(commandeClientDto.getClient() == null){
            errors.add("Veuillez choisir le client de la commande");
        }
        return errors;
    }
}
