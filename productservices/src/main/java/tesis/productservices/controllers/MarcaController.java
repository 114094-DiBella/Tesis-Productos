package tesis.productservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tesis.productservices.dtos.MarcaRequest;
import tesis.productservices.models.Marca;
import tesis.productservices.models.Products;
import tesis.productservices.services.MarcaService;

import java.net.http.HttpResponse;
import java.util.List;


@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;


    @GetMapping
    public ResponseEntity<List<Marca>> getAllMarcas() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Marca> saveMarca(@RequestBody MarcaRequest marca) {
        return ResponseEntity.ok(marcaService.save(marca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@RequestBody MarcaRequest marca, @PathVariable Long id) {
        return ResponseEntity.ok(marcaService.update(marca, id));
    }


}
