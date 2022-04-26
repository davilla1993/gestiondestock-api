package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VentesDto ventesDto){
        List<String> errors = new ArrayList<>();

        if(ventesDto == null){
            errors.add("Veuiller renseigner le code de la vente");
            errors.add("Veuiller renseigner la date de la vente");
            errors.add("Veuiller mettre un commentaire");

            return errors;

        }
        if(!StringUtils.hasLength(ventesDto.getCode())){
            errors.add("Veuiller renseigner le code de la vente");
        }
        if(ventesDto.getDateVente() == null){
            errors.add("Veuiller renseigner la date de la vente");
        }
        if(!StringUtils.hasLength(ventesDto.getCommentaire())){
            errors.add("Veuiller mettre un commentaire");
        }

        return errors;
    }
}
