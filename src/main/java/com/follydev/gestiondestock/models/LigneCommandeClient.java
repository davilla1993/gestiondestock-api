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
@Table(name = "ligne_commande_client")
public class LigneCommandeClient extends AbstractEntity{

    @ManyToOne
    @JoinColumn( name = "id_article")
    private Article article;

    @ManyToOne
    @JoinColumn( name = "id_commande_client")
    private CommandeClient commandeClient;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;
}
