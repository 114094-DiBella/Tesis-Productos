package tesis.productservices.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesis.productservices.dtos.CategoryRequest;
import tesis.productservices.entities.CategoryEntity;
import tesis.productservices.entities.MarcaEntity;
import tesis.productservices.models.Category;
import tesis.productservices.models.Marca;
import tesis.productservices.repositories.CategoryJpaRepository;
import tesis.productservices.services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void delete(Long id) {
        if (categoryJpaRepository.existsById(id)) {
            categoryJpaRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Category with id " + id + " not found");
        }
    }

    @Override
    public List<Category> getCategories() {
        List<CategoryEntity> categoryEntities = categoryJpaRepository.findAll();
        return categoryEntities.stream()
                .map(entity -> modelMapper.map(entity, Category.class))
                .collect(Collectors.toList());
    }

    @Override
    public Category findById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryJpaRepository.findById(id);
        if (categoryEntity.isPresent()) {
            return modelMapper.map(categoryEntity.get(), Category.class);
        }
        throw new RuntimeException("Category with id " + id + " not found");
    }

    @Override
    public Category save(CategoryRequest category) {
        if (category == null) {
            throw new RuntimeException("Category cannot be null");
        }
        CategoryEntity categoryEntity = modelMapper.map(category, CategoryEntity.class);
        categoryJpaRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, Category.class);
    }

    @Override
    public Category update(CategoryRequest category, Long id) {
        Optional<CategoryEntity> categoryEntity = categoryJpaRepository.findById(id);
        if (categoryEntity.isPresent()) {
            CategoryEntity categoryEntityUpdate = categoryEntity.get();
            categoryEntityUpdate.setName(category.getName());
            categoryEntityUpdate.setImageUrl(category.getImageUrl());
            categoryJpaRepository.save(categoryEntityUpdate);
            return modelMapper.map(categoryEntityUpdate, Category.class);
        }
        throw new RuntimeException("Category with id " + id + " not found");
    }

}
