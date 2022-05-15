package com.follydev.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.follydev.gestiondestock.dto.ClientDto;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.services.ClientService;
import com.follydev.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto>{

    private FlickrService flickrService;
    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        ClientDto client = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enrégistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        client.setPhoto(urlPhoto);

        return clientService.save(client);
    }
}
