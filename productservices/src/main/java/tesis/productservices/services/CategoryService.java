package tesis.productservices.services;

import org.springframework.stereotype.Service;
import tesis.productservices.dtos.CategoryRequest;
import tesis.productservices.models.Category;

import java.util.List;

@Service
public interface CategoryService {


    List<Category> getCategories();
    Category findById(Long id);
    Category save(CategoryRequest category);
    Category update(CategoryRequest category, Long id);
    void delete(Long id);
}
