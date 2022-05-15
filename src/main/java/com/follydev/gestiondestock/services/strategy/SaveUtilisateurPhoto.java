package com.follydev.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.follydev.gestiondestock.dto.UtilisateurDto;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.services.FlickrService;
import com.follydev.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto>{

    private UtilisateurService utilisateurService;
    private FlickrService flickrService;

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enrégistrement de la photo de l'utilisateur, ErrorCodes.UPDATE_PHOTO_EXCEPTION");
        }
        utilisateur.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateur);
    }
}
