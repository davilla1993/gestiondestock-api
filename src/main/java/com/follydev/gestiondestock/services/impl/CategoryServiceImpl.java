package com.follydev.gestiondestock.services.impl;

import com.follydev.gestiondestock.dto.CategoryDto;
import com.follydev.gestiondestock.exceptions.EntityNotFoundException;
import com.follydev.gestiondestock.exceptions.ErrorCodes;
import com.follydev.gestiondestock.exceptions.InvalidEntityException;
import com.follydev.gestiondestock.exceptions.InvalidOperationException;
import com.follydev.gestiondestock.models.Article;
import com.follydev.gestiondestock.repository.ArticleRepository;
import com.follydev.gestiondestock.repository.CategoryRepository;
import com.follydev.gestiondestock.services.CategoryService;
import com.follydev.gestiondestock.validators.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;


    @Autowired
    CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository){

        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);

        if(!errors.isEmpty()){
            log.error("Category is not valid {}", categoryDto);
            throw new InvalidEntityException("La categorie n'est pas valide",
                    ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(categoryRepository.save(
                CategoryDto.toEntity(categoryDto))
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return null;

        }

        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune categorie avec l'ID " + id + "n'a ??t?? trouv?? dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public CategoryDto findByCode(String code) {
        if(StringUtils.hasLength(code)) {
            log.error("Category CODE is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune categorie avec le CODE = " + code + "n'a ??t?? trouv?? dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("Category ID is null");
            return;
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if(!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer cette cat??gorie car elle est contient plusieurs articles",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }

        categoryRepository.deleteById(id);
    }
}
