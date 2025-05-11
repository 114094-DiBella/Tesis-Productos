package tesis.productservices.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Base64ImageDTO {
    private String base64Image;
    private String fileName;
    private String fileType;
    private boolean isMain;
    private int displayOrder;
}