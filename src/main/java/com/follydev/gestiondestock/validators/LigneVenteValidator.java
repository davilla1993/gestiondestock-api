package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.LigneVenteDto;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {

    public List<String> validate(LigneVenteDto ligneVenteDto){
        List<String> errors = new ArrayList<>();

        if(ligneVenteDto == null){
            errors.add("Veuillez choisir la vente correspondante");
            errors.add("Veuillez saisir la quantité");
            errors.add("Veuillez renseigner le prix unitaire");

            return errors;
        }
        if(ligneVenteDto.getVentes() == null){
            errors.add("Veuillez choisir la vente correspondante");
        }
        if(ligneVenteDto.getQuantite() == null){
            errors.add("Veuillez saisir la quantité");
        }
        if(ligneVenteDto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le prix unitaire");
        }

        return errors;
    }
}
