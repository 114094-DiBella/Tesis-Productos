package tesis.productservices.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private Long id;
    private String imageUrl;
    private boolean main;
    private int displayOrder;
}
