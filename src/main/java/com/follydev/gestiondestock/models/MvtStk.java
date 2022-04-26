package com.follydev.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mvt_stk")
public class MvtStk extends AbstractEntity{

    @Column(name = "date_mvt")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "type_mvt")
    private TypeMvtStk typeMvt;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;
}
