package tesis.productservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.productservices.entities.MarcaEntity;

import java.util.Optional;

@Repository
public interface MarcaJpaRepository extends JpaRepository<MarcaEntity, Long> {
    Optional<MarcaEntity> findByName(String name);
}
