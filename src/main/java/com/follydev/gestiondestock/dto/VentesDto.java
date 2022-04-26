package com.follydev.gestiondestock.dto;

import com.follydev.gestiondestock.models.Ventes;
import lombok.*;
import java.time.Instant;
import java.util.List;


@Data
@Builder
public class VentesDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private String commentaire;

    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes ventes) {
        if(ventes == null){
            return null;
        }

        return VentesDto.builder()
                .id(ventes.getId())
                .code(ventes.getCode())
                .dateVente(ventes.getDateVente())
                .commentaire(ventes.getCommentaire())
                .build();
    }

    public static Ventes toEntity(VentesDto ventesDto) {
        if(ventesDto == null){
            return null;
        }

        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setDateVente(ventesDto.getDateVente());
        ventes.setCommentaire(ventes.getCommentaire());

        return ventes;
    }
}
