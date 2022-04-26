package com.follydev.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity{

    @Column(name = "code_article")
    private String codeArticle;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prix_unitaire_ht")
    private BigDecimal prixUnitaireHT;

    @Column(name = "taux_tva")
    private BigDecimal tauxTVA;

    @Column(name = "prix_unitaire_ttc")
    private BigDecimal prixUnitaireTTC;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;
}
