package com.follydev.gestiondestock.services;

import com.follydev.gestiondestock.dto.CategoryDto;

import java.util.List;


public interface CategoryService {

        CategoryDto save(CategoryDto categoryDto);

        CategoryDto findById(Integer id);

        CategoryDto findByCode(String code);

        List<CategoryDto> findAll();

        void delete(Integer id);

    }
