package tesis.productservices.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tesis.productservices.entities.ProductImageEntity;
import tesis.productservices.repositories.ImagesJpaRepository;
import tesis.productservices.services.ProductsImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductsImageServiceImpl implements ProductsImageService {

    @Value("${app.upload.dir:${user.home}/product-images}")
    private String uploadDir;

    @Autowired
    private ImagesJpaRepository imagesJpaRepository;

    @Override
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("El archivo no es una imagen");
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetPath = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "/images/" + fileName;

        // Guardar en la base de datos
        ProductImageEntity imageEntity = new ProductImageEntity();
        imageEntity.setImageUrl(imageUrl);
        imageEntity.setMain(false);
        imageEntity.setDisplayOrder(0);

        ProductImageEntity savedImage = imagesJpaRepository.save(imageEntity);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("imageId", savedImage.getId().toString());

        return response;
    }
}