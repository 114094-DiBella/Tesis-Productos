package tesis.productservices.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tesis.productservices.entities.ProductImageEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Products {
    private UUID id;
    private String name;
    private Marca marca;
    private Size size;
    private String color;
    private Category category;
    private BigDecimal price;
    private BigInteger stock;
    private boolean active;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
