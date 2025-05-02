package tesis.productservices.services;

import org.springframework.stereotype.Service;
import tesis.productservices.dtos.MarcaRequest;
import tesis.productservices.models.Marca;

import java.util.List;

@Service
public interface MarcaService {

    Marca save(MarcaRequest marca);
    Marca update(MarcaRequest marca, Long id);
    void delete(Long marca);
    List<Marca> findAll();
    Marca findById(Long id);
}
