package tesis.productservices.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tesis.productservices.dtos.ProductRequest;
import tesis.productservices.models.Products;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductsService {


    List<Products> findAll();
    Products findById(UUID id) throws EntityNotFoundException;
    Products save(ProductRequest products);
    void delete(UUID id);
    Products update(ProductRequest products, UUID id);


}
