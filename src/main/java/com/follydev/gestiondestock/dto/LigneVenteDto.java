package com.follydev.gestiondestock.dto;

import com.follydev.gestiondestock.models.Article;
import com.follydev.gestiondestock.models.LigneVente;
import lombok.*;
import java.math.BigDecimal;

@Builder
@Data
public class LigneVenteDto {

    private Integer id;

    private VentesDto ventes;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Article article;

    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        if(ligneVente == null){
            return null;
        }

        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto){
        if(ligneVenteDto == null) {
            return null;
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());

        return ligneVente;
    }

}
