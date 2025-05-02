package tesis.productservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tesis.productservices.entities.CategoryEntity;
import tesis.productservices.entities.MarcaEntity;
import tesis.productservices.entities.ProductsEntity;
import tesis.productservices.models.Category;
import tesis.productservices.models.Marca;
import tesis.productservices.models.Products;
import tesis.productservices.models.Size;

import java.math.BigDecimal;
import java.util.UUID;
@Repository
public interface ProductsJpaRepository extends JpaRepository<ProductsEntity, UUID> {
    boolean existsByNameAndMarcaAndSizeAndColorAndCategoryAndPrice(
            String name,
            MarcaEntity marca,
            Size size,
            String color,
            CategoryEntity category,
            BigDecimal price
    );

}
