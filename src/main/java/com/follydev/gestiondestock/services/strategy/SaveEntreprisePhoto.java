package com.follydev.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.follydev.gestiondestock.dto.EntrepriseDto;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.services.EntrepriseService;
import com.follydev.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto>{

    private FlickrService flickrService;
    private EntrepriseService entrepriseService;

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enrégistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entreprise.setPhoto(urlPhoto);

        return entrepriseService.save(entreprise);
    }
}
