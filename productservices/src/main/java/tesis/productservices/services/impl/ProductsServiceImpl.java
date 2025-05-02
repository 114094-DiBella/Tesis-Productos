package tesis.productservices.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesis.productservices.dtos.ProductRequest;
import tesis.productservices.entities.ProductsEntity;
import tesis.productservices.models.Products;
import tesis.productservices.repositories.ProductsJpaRepository;
import tesis.productservices.services.ProductsService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsJpaRepository productsJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Products> findAll() {
        List<ProductsEntity> productsEntities = productsJpaRepository.findAll();
        return productsEntities.stream()
                .map(entity -> modelMapper.map(entity, Products.class))
                .collect(Collectors.toList());
    }

    @Override
    public Products findById(UUID id) throws EntityNotFoundException {
        if (id == null) {
            throw new EntityNotFoundException("id is null");
        }
        Optional<ProductsEntity> productsEntityOptional = productsJpaRepository.findById(id);
        if (productsEntityOptional.isPresent()) {
            return modelMapper.map(productsEntityOptional.get(), Products.class);
        } else {
            throw new EntityNotFoundException("No hay productos con ese ID: " + id);
        }
    }
    @Override
    public Products save(ProductRequest productRequest) {
        validateProductRequest(productRequest);
        validateProductDuplicate(productRequest);
        ProductsEntity productsEntity = modelMapper.map(productRequest, ProductsEntity.class);
        productsEntity.setCreatedAt(LocalDateTime.now());
        productsEntity.setUpdatedAt(LocalDateTime.now());
        productsEntity.setActive(true);
        if (productRequest.getStock() == null) {
            productsEntity.setActive(false);
        }

        ProductsEntity savedEntity = productsJpaRepository.save(productsEntity);
        return modelMapper.map(savedEntity, Products.class);
    }

    private void validateProductRequest(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Product request is null");
        }
        if (productRequest.getName() == null) {
            throw new IllegalArgumentException("Product name is null");
        }
        if (productRequest.getPrice() == null) {
            throw new IllegalArgumentException("Product price is null");
        }
        if (productRequest.getCategory() == null) {
            throw new IllegalArgumentException("Product category is null");
        }
        if (productRequest.getSize() == null) {
            throw new IllegalArgumentException("Product size is null");
        }
        if (productRequest.getMarca() == null) {
            throw new IllegalArgumentException("Product brand is null");
        }
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        Optional<ProductsEntity> productsEntityOptional = productsJpaRepository.findById(id);
        productsEntityOptional.ifPresent(productsEntity -> productsJpaRepository.delete(productsEntity));
    }

    @Override
    public Products update(ProductRequest products, UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        validateProductRequest(products);

        Optional<ProductsEntity> existingProductOpt = productsJpaRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new EntityNotFoundException("No hay productos con ese ID: " + id);
        }

        ProductsEntity existingEntity = existingProductOpt.get();

        existingEntity.setName(products.getName());
        existingEntity.setPrice(products.getPrice());
        existingEntity.setCategory(products.getCategory());
        existingEntity.setMarca(products.getMarca());
        existingEntity.setSize(products.getSize());
        existingEntity.setColor(products.getColor());
        existingEntity.setStock(products.getStock());
        existingEntity.setUpdatedAt(LocalDateTime.now());

        existingEntity.setActive(true);
        if (existingEntity.getStock() == null) {
            existingEntity.setActive(false);
        }


        ProductsEntity updatedEntity = productsJpaRepository.save(existingEntity);

        return modelMapper.map(updatedEntity, Products.class);
    }

    private void validateProductDuplicate(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Product request is null");
        }

        boolean exists = productsJpaRepository.existsByNameAndMarcaAndSizeAndColorAndCategoryAndPrice(
                productRequest.getName(),
                productRequest.getMarca(),
                productRequest.getSize(),
                productRequest.getColor(),
                productRequest.getCategory(),
                productRequest.getPrice()
        );

        if (exists) {
            throw new IllegalStateException("Ya existe un producto con las mismas características");
        }
    }
}
