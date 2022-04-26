package com.follydev.gestiondestock.validators;

import com.follydev.gestiondestock.dto.RolesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RolesValidator {
    public List<String> validate(RolesDto rolesDto){
        List<String> errors = new ArrayList<>();

        if(rolesDto == null){
            errors.add("Veuillez renseigner le role");
            errors.add("Veuillez choisir un utilisateur");

            return errors;
        }
        if(!StringUtils.hasLength(rolesDto.getRoleName())){
            errors.add("Veuillez renseigner le role");
        }

        if(rolesDto.getUtilisateur() == null){
            errors.add("Veuillez choisir un utilisateur");
        }

        return errors;
    }
}
