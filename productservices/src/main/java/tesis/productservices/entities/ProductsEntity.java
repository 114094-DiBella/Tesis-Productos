package tesis.productservices.entities;

import jakarta.persistence.*;
import lombok.Data;
import tesis.productservices.models.Category;
import tesis.productservices.models.Marca;
import tesis.productservices.models.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "products")
@Data
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Marca marca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}