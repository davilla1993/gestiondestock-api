package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.EntrepriseDto;
import com.follydev.gestiondestock.dto.RolesDto;
import com.follydev.gestiondestock.dto.UtilisateurDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.repository.EntrepriseRepository;
import com.follydev.gestiondestock.repository.RolesRepository;
import com.follydev.gestiondestock.services.EntrepriseService;
import com.follydev.gestiondestock.services.UtilisateurService;
import com.follydev.gestiondestock.validators.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService,
                                 RolesRepository rolesRepository){
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
        List<String> errors = EntrepriseValidator.validate(entrepriseDto);

        if(!errors.isEmpty()){
            log.error("Entreprise IS is null {}", entrepriseDto);
            throw new InvalidEntityException("L'Entrprise n'est pas valide",
                    ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
         EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(entrepriseRepository.save(
                        EntrepriseDto.toEntity(entrepriseDto))
        );

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto entrepriseDto){
        return UtilisateurDto.builder()
                .adresse(entrepriseDto.getAdresse())
                .nom(entrepriseDto.getNom())
                .prenom(entrepriseDto.getCodeFiscal())
                .email(entrepriseDto.getEmail())
                .motDePasse(generateRandomPassword())
                .entreprise(entrepriseDto)
                .dateDeNaissance(Instant.now())
                .photo(entrepriseDto.getPhoto())
                .build();
    }


    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id == null){
            log.error("Entreprise ID is null");
            return null;

        }

        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
                );
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        if(StringUtils.hasLength(nom)) {
            log.error("Entreprise NAME is null");
            return null;
        }
        return entrepriseRepository.findEntrepriseByNom(nom)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune Entreprise avec le NOM = " + nom + "n'a été trouvé dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
                );
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Entreprise ID is null");
            return;
        }

        entrepriseRepository.deleteById(id);
    }
}
