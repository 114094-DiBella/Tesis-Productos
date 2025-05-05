package tesis.productservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.productservices.entities.ProductImageEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImagesJpaRepository extends JpaRepository<ProductImageEntity, Long> {

    List<ProductImageEntity> findByIdIn(List<Long> ids);
}
