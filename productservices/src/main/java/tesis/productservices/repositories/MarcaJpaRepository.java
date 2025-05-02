package tesis.productservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.productservices.entities.MarcaEntity;

@Repository
public interface MarcaJpaRepository extends JpaRepository<MarcaEntity, Long> {
    boolean findByName(String name);
}
