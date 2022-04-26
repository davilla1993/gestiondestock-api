package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.MvtStkDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.repository.MvtStkRepository;
import com.follydev.gestiondestock.services.MvtStkService;
import com.follydev.gestiondestock.validators.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    MvtStkRepository mvtStkRepository;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository){
        this.mvtStkRepository = mvtStkRepository;
    }

    @Override
    public MvtStkDto save(MvtStkDto mvtStkDto) {
        List<String> errors = MvtStkValidator.validate(mvtStkDto);

        if(!errors.isEmpty()){
            log. error("Mouvement stock is not valid {}", mvtStkDto);
            throw new InvalidEntityException("Mouvement stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALID, errors);
        }

        return MvtStkDto.fromEntity(mvtStkRepository.save(
               MvtStkDto.toEntity(mvtStkDto))
        );
    }

    @Override
    public MvtStkDto findById(Integer id) {
        if(id == null){
            log.error("Mvt stock ID is null");
            return null;
        }

        return mvtStkRepository.findById(id)
                .map(MvtStkDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun mvt stock avec l'ID " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.MVT_STK_NOT_FOUND)
                );
    }

    @Override
    public List<MvtStkDto> findAll() {
        return mvtStkRepository.findAll().stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Mvt stock ID is null");
            return;
        }

        mvtStkRepository.deleteById(id);
    }
}
