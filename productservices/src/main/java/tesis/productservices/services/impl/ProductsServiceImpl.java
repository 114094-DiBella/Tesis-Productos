package tesis.productservices.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.productservices.dtos.ProductRequest;
import tesis.productservices.entities.CategoryEntity;
import tesis.productservices.entities.MarcaEntity;
import tesis.productservices.entities.ProductImageEntity;
import tesis.productservices.entities.ProductsEntity;
import tesis.productservices.models.Products;
import tesis.productservices.repositories.CategoryJpaRepository;
import tesis.productservices.repositories.ImagesJpaRepository;
import tesis.productservices.repositories.MarcaJpaRepository;
import tesis.productservices.repositories.ProductsJpaRepository;
import tesis.productservices.services.ProductsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsJpaRepository productsJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImagesJpaRepository imagesJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private MarcaJpaRepository marcaJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Products> findAll() {
        List<ProductsEntity> productsEntities = productsJpaRepository.findAll();
        return productsEntities.stream()
                .map(entity -> modelMapper.map(entity, Products.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Products findById(UUID id) throws EntityNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return productsJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Products.class))
                .orElseThrow(() -> new EntityNotFoundException("No hay productos con ese ID: " + id));
    }
    @Override
    @Transactional
    public Products save(ProductRequest productRequest) {
        validateProductRequest(productRequest);
        validateProductDuplicate(productRequest);

        MarcaEntity marca = getMarcaOrThrow(productRequest.getMarcaId());
        CategoryEntity category = getCategoryOrThrow(productRequest.getCategoryId());

        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setName(productRequest.getName());
        productsEntity.setMarca(marca);
        productsEntity.setCategory(category);
        productsEntity.setSize(productRequest.getSize());
        productsEntity.setColor(productRequest.getColor());
        productsEntity.setPrice(productRequest.getPrice());
        productsEntity.setStock(productRequest.getStock());
        productsEntity.setCreatedAt(LocalDateTime.now());
        productsEntity.setUpdatedAt(LocalDateTime.now());
        productsEntity.setActive(productRequest.getStock() != null ? productRequest.isActive() : false);

        // Asociar imágenes si se proporcionan IDs
        if (productRequest.getImageIds() != null && !productRequest.getImageIds().isEmpty()) {
            List<ProductImageEntity> images = imagesJpaRepository.findByIdIn(productRequest.getImageIds());
            for (ProductImageEntity image : images) {
                image.setProduct(productsEntity);
                productsEntity.getImages().add(image);
            }
        }

        ProductsEntity savedEntity = productsJpaRepository.save(productsEntity);
        return modelMapper.map(savedEntity, Products.class);
    }
    @Override
    @Transactional
    public Products update(ProductRequest products, UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        validateProductRequest(products);

        ProductsEntity existingEntity = productsJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay productos con ese ID: " + id));

        MarcaEntity marca = getMarcaOrThrow(products.getMarcaId());
        CategoryEntity category = getCategoryOrThrow(products.getCategoryId());

        existingEntity.setName(products.getName());
        existingEntity.setMarca(marca);
        existingEntity.setCategory(category);
        existingEntity.setSize(products.getSize());
        existingEntity.setColor(products.getColor());
        existingEntity.setPrice(products.getPrice());
        existingEntity.setStock(products.getStock());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setActive(products.getStock() != null ? products.isActive() : false);

        if (products.getImageIds() != null && !products.getImageIds().isEmpty()) {
            // Remover asociaciones existentes
            for (ProductImageEntity image : existingEntity.getImages()) {
                image.setProduct(null);
            }
            existingEntity.getImages().clear();

            // Agregar nuevas asociaciones
            List<ProductImageEntity> images = imagesJpaRepository.findByIdIn(products.getImageIds());
            for (ProductImageEntity image : images) {
                image.setProduct(existingEntity);
                existingEntity.getImages().add(image);
            }
        }

        ProductsEntity updatedEntity = productsJpaRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, Products.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        if (!productsJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay productos con ese ID: " + id);
        }

        productsJpaRepository.deleteById(id);
    }

    private void validateProductDuplicate(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Product request is null");
        }

        MarcaEntity marca = getMarcaOrThrow(productRequest.getMarcaId());
        CategoryEntity category = getCategoryOrThrow(productRequest.getCategoryId());

        boolean exists = productsJpaRepository.existsByNameAndMarcaAndSizeAndColorAndCategoryAndPrice(
                productRequest.getName(),
                marca,
                productRequest.getSize(),
                productRequest.getColor(),
                category,
                productRequest.getPrice()
        );

        if (exists) {
            throw new IllegalStateException("Ya existe un producto con las mismas características");
        }
    }

    private MarcaEntity getMarcaOrThrow(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Marca ID is null");
        }
        return marcaJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada con ID: " + id));
    }

    private CategoryEntity getCategoryOrThrow(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID is null");
        }
        return categoryJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
    }

    private void validateProductRequest(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Product request is null");
        }
        if (productRequest.getName() == null || productRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is null or empty");
        }
        if (productRequest.getPrice() == null) {
            throw new IllegalArgumentException("Product price is null");
        }
        if (productRequest.getSize() == null) {
            throw new IllegalArgumentException("Product size is null");
        }
        if (productRequest.getCategoryId() == null) {
            throw new IllegalArgumentException("Product category ID is null");
        }
        if (productRequest.getMarcaId() == null) {
            throw new IllegalArgumentException("Product brand ID is null");
        }
        if (productRequest.getColor() == null || productRequest.getColor().trim().isEmpty()) {
            throw new IllegalArgumentException("Product color is null or empty");
        }
    }
}