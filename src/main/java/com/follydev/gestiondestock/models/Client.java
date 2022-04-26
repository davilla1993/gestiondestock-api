package com.follydev.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "prenom")
    private String prenom;

    @Embedded
    private Adresse adresse;

    @Column( name = "photo")
    private String photo;

    @Column(name = "mail")
    private String mail;

    @Column(name = "num_tel")
    private String numTel;

    @Column(name = "id_entreprise",
            insertable = false,updatable = false)
    private Integer idEntreprise;

    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;


}
