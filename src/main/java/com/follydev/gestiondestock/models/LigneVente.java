package com.follydev.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligne_vente")
public class LigneVente  extends AbstractEntity{

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    @ManyToOne
    @JoinColumn(name = "id_vente")
    private Ventes ventes;

    @ManyToOne
    @JoinColumn(name ="id_article")
    private Article article;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;
}
