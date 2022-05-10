package com.follydev.gestiondestock.dto;

import com.follydev.gestiondestock.models.CommandeFournisseur;
import lombok.*;

import java.time.Instant;
import java.util.List;
@Builder
@Data
public class CommandeFournisseurDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private FournisseurDto fournisseur;

    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){
        if(commandeFournisseur == null) {
            return null;
        }

        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto) {
            if(commandeFournisseurDto == null) {
                return null;
            }

            CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
            commandeFournisseur.setId(commandeFournisseurDto.getId());
            commandeFournisseur.setCode(commandeFournisseurDto.getCode());
            commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
            commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
            commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());

            return commandeFournisseur;
    }
}
