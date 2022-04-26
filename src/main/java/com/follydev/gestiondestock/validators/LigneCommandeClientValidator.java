package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.LigneCommandeClientDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {

    public List<String> validate(LigneCommandeClientDto ligneCommandeClientDto) {
        List<String> errors = new ArrayList<>();

        if(ligneCommandeClientDto == null){
            errors.add("Veuillez choisir le ou les articles correspondant(s)");
            errors.add("Veuillez choisir la ou les commandes correspondante(s)");
            errors.add("Veuillez saisir la quantité");
            errors.add("Veuillez saisir le prix unitaire");

            return errors;
        }
        if(ligneCommandeClientDto.getArticle() == null){
            errors.add("Veuillez choisir le ou les articles correspondant(s)");
        }
        if(ligneCommandeClientDto.getCommandeClient() == null){
            errors.add("Veuillez choisir la ou les commandes correspondante(s)");
        }
        if(ligneCommandeClientDto.getQuantite() == null){
            errors.add("Veuillez saisir la quantité");
        }
        if(ligneCommandeClientDto.getPrixUnitaire() == null){
            errors.add("Veuillez saisir le prix unitaire");
        }
        return errors;
    }
}
