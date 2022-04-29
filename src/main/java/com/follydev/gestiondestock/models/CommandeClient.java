package com.follydev.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commande_client")
public class CommandeClient  extends AbstractEntity{

    @Column(name = "code")
    private String code;

    @Column( name = "date_commande")
    private Instant dateCommande;

    @Column(name = "etat_commande")
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn( name = "id_client")
    private Client client;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;



}
