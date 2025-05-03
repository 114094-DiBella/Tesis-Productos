package tesis.productservices.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tesis.productservices.models.Category;
import tesis.productservices.models.Marca;
import tesis.productservices.models.Size;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    private String name;
    private Long marcaId;
    private Size size;
    private String color;
    private Long categoryId;
    private BigInteger stock;
    private List<String> imageUrls; // Para múltiples imágenes
    private BigDecimal price;
    private boolean active;
}