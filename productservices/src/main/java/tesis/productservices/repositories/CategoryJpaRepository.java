package tesis.productservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.productservices.entities.CategoryEntity;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
