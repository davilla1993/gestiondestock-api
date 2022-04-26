package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.MvtStkDto;

import java.util.List;

public interface MvtStkService {

    MvtStkDto save(MvtStkDto mvtStkDto);

    MvtStkDto findById(Integer id);

    List<MvtStkDto> findAll();

    void delete(Integer id);
}
