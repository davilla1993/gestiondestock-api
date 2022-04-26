package com.follydev.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.follydev.gestiondestock.models.Client;
import com.follydev.gestiondestock.models.CommandeClient;
import lombok.*;
import java.util.List;

@Builder
@Data
public class ClientDto {

    private Integer id;

    private String name;

    private String prenom;

    private AdresseDto adresse;

    private String photo;

    private String mail;

    private String numTel;

    @JsonIgnore
    private List<CommandeClient> commandeClients;

    public static ClientDto fromEntity(Client client) {
        if(client == null) {
            return null;
        }

        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .build();
    }

    public static Client toEntity(ClientDto clientDto){
        if(clientDto == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setPrenom(clientDto.getPrenom());
        client.setPhoto(clientDto.getPhoto());
        client.setMail(clientDto.getMail());
        client.setNumTel(clientDto.getNumTel());

        return client;
    }
}
