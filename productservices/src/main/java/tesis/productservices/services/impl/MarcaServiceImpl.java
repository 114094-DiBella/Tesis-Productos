package tesis.productservices.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.productservices.dtos.MarcaRequest;
import tesis.productservices.entities.MarcaEntity;
import tesis.productservices.entities.ProductsEntity;
import tesis.productservices.models.Marca;
import tesis.productservices.models.Products;
import tesis.productservices.repositories.MarcaJpaRepository;
import tesis.productservices.services.MarcaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaJpaRepository marcaJpaRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void delete(Long id) {
        if (!marcaJpaRepository.existsById(id)) {
            throw new RuntimeException("Marca with id " + id + " does not exist");
        }
        marcaJpaRepository.deleteById(id);
    }

    @Override
    public List<Marca> findAll() {
        List<MarcaEntity> marcasEntities = marcaJpaRepository.findAll();
        return marcasEntities.stream()
                .map(entity -> modelMapper.map(entity, Marca.class))
                .collect(Collectors.toList());
    }

    @Override
    public Marca findById(Long id) {
        return marcaJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Marca.class))
                .orElseThrow(() -> new EntityNotFoundException("Marca with id " + id + " does not exist"));
    }

    @Override
    public Marca save(MarcaRequest marca) {
        if (marca == null) {
            throw new RuntimeException("Marca request cannot be null");
        }
        MarcaEntity marcaEntity = modelMapper.map(marca, MarcaEntity.class);
        marcaJpaRepository.save(marcaEntity);
        Marca marcaSaved = modelMapper.map(marcaEntity, Marca.class);
        return marcaSaved;
    }

    @Override
    @Transactional
    public Marca update(MarcaRequest marca, Long id) {

        MarcaEntity existingEntity = marcaJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca with id " + id + " does not exist"));

        if (!existingEntity.getName().equals(marca.getName())) {
            Optional<MarcaEntity> entityWithSameName = marcaJpaRepository.findByName(marca.getName());
            if (entityWithSameName.isPresent() && !entityWithSameName.get().getId().equals(id)) {
                throw new IllegalStateException("Marca with name " + marca.getName() + " already exists");
            }
        }

        existingEntity.setName(marca.getName());

        MarcaEntity updatedEntity = marcaJpaRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, Marca.class);
    }

}
