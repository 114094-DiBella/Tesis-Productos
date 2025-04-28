package tesis.productservices.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Products {
    private UUID id;
    private String name;
    private Marca marca;
    private Size size;
    private String Color;
    private Category category;
    private BigDecimal price;
    private String createdAt;
    private String updatedAt;
}
