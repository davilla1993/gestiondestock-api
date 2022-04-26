package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.MvtStkDto;

import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {

    public static List<String> validate(MvtStkDto mvtStkDto){
        List<String> errors = new ArrayList<>();

        if(mvtStkDto == null){
            errors.add("Veuillez choisir la date");
            errors.add("Veuillez choisir la quantite");
            errors.add("Veuiller choisir un article");
            errors.add("Veuillez choisir le type du mouvement");

            return errors;
        }
        if(mvtStkDto.getDateMvt() == null){
            errors.add("Veuillez choisir la date");
        }
        if(mvtStkDto.getQuantite() == null) {
            errors.add("Veuillez choisir la quantite");
        }
        if(mvtStkDto.getArticle() == null){
            errors.add("Veuiller choisir un article");
        }
        if(mvtStkDto.getTypeMvt() == null){
            errors.add("Veuillez choisir le type du mouvement");
        }

        return errors;
    }
}
