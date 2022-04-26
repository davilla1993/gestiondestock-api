package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {

    public List<String> validate(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
       List<String> errors = new ArrayList<>();

        if(ligneCommandeFournisseurDto == null){
            errors.add("Veuillez choisir le ou les articles correspondant(s)");
            errors.add("Veuillez choisir la ou les commandes correspondante(s)");
            errors.add("Veuillez saisir la quantité");
            errors.add("Veuillez saisir le prix unitaire");

            return errors;
        }
        if(ligneCommandeFournisseurDto.getArticle() == null){
            errors.add("Veuillez choisir le ou les articles correspondant(s)");
        }
        if(ligneCommandeFournisseurDto.getCommandeFournisseurs() == null){
            errors.add("Veuillez choisir la ou les commandes correspondante(s)");
        }
        if(ligneCommandeFournisseurDto.getQuantite() == null){
            errors.add("Veuillez saisir la quantité");
        }
        if(ligneCommandeFournisseurDto.getPrixUnitaire() == null){
            errors.add("Veuillez saisir le prix unitaire");
        }
        return errors;
    }
}
