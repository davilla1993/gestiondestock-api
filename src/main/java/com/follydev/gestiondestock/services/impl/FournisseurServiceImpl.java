package com.follydev.gestiondestock.services.impl;
import com.follydev.gestiondestock.dto.FournisseurDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.CommandeClient;
import com.follydev.gestiondestock.models.CommandeFournisseur;
import com.follydev.gestiondestock.repository.CommandeFournisseurRepository;
import com.follydev.gestiondestock.repository.FournisseurRepository;
import com.follydev.gestiondestock.services.FournisseurService;
import com.follydev.gestiondestock.validators.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,
                                  CommandeFournisseurRepository commandeFournisseurRepository){
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto fournisseurDto) {
        List<String> errors = FournisseurValidator.validate(fournisseurDto);

        if(!errors.isEmpty()){
            log.error("Fournisseur is not valid {}", fournisseurDto);
            throw new InvalidEntityException("Fournisseur n'est pas valide",
                    ErrorCodes.FOURNISSEUR_NOT_VALID);
        }
        return FournisseurDto.fromEntity(fournisseurRepository.save(
                FournisseurDto.toEntity(fournisseurDto)
        ));
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
            return null;

        }

        return fournisseurRepository.findById(id)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Fournisseur ID is null");
            return;
        }

        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if(!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer ce fournisseur car il est rattaché à plusieurs commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        fournisseurRepository.deleteById(id);
    }
}
