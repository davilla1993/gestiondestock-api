package com.follydev.gestiondestock.dto;

import com.follydev.gestiondestock.models.MvtStk;
import com.follydev.gestiondestock.models.TypeMvtStk;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStkDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDto article;

    private TypeMvtStk typeMvt;

    public static MvtStkDto fromEntity(MvtStk mvtStk) {
        if(mvtStk == null){
            return null;
        }

        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .build();
    }

    public static MvtStk toEntity(MvtStkDto mvtStkDto) {
        if(mvtStkDto == null) {
            return null;
        }

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());

        return mvtStk;

    }
}
