package tesis.productservices.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tesis.productservices.dtos.Base64ImageDTO;
import tesis.productservices.entities.ProductImageEntity;
import tesis.productservices.repositories.ImagesJpaRepository;
import tesis.productservices.services.ProductsImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

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

    @Override
    public Map<String, String> uploadBase64Image(Base64ImageDTO imageDTO) throws IOException {
        if (imageDTO.getBase64Image() == null || imageDTO.getBase64Image().isEmpty()) {
            throw new IOException("La imagen en Base64 está vacía");
        }

        // Extraer la parte de datos Base64 si incluye el prefijo (data:image/jpeg;base64,)
        String base64Image = imageDTO.getBase64Image();
        String imageType = "jpg"; // Valor por defecto

        if (base64Image.contains(";base64,")) {
            String[] parts = base64Image.split(";base64,");
            if (parts.length > 1) {
                base64Image = parts[1];

                // Intentar extraer el tipo de la imagen
                if (parts[0].contains("/")) {
                    imageType = parts[0].split("/")[1];
                }
            }
        }

        // Generar un nombre único para el archivo
        String fileName = UUID.randomUUID().toString() + "." + imageType;

        // Decodificar la imagen Base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Guardar la imagen en el sistema de archivos
        Path targetPath = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(Paths.get(uploadDir)); // Asegurarse de que el directorio existe
        Files.write(targetPath, imageBytes);

        String imageUrl = "/images/" + fileName;

        // Guardar en la base de datos
        ProductImageEntity imageEntity = new ProductImageEntity();
        imageEntity.setImageUrl(imageUrl);
        imageEntity.setMain(imageDTO.isMain());
        imageEntity.setDisplayOrder(imageDTO.getDisplayOrder());

        ProductImageEntity savedImage = imagesJpaRepository.save(imageEntity);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("imageId", savedImage.getId().toString());

        return response;
    }

    @Override
    public Map<String, String> getImageAsBase64(Long id) throws IOException {
        Optional<ProductImageEntity> imageOptional = imagesJpaRepository.findById(id);

        if (!imageOptional.isPresent()) {
            throw new IOException("No se encontró la imagen con ID: " + id);
        }

        ProductImageEntity imageEntity = imageOptional.get();
        String imageUrl = imageEntity.getImageUrl();

        // Eliminar el prefijo /images/ si existe
        String fileName = imageUrl;
        if (imageUrl.startsWith("/images/")) {
            fileName = imageUrl.substring("/images/".length());
        }

        Path imagePath = Paths.get(uploadDir).resolve(fileName);
        if (!Files.exists(imagePath)) {
            throw new IOException("No se encontró el archivo de imagen en: " + imagePath);
        }

        // Leer la imagen y convertirla a Base64
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Determinar el tipo MIME basado en la extensión del archivo
        String mimeType = "image/jpeg"; // Por defecto
        if (fileName.toLowerCase().endsWith(".png")) {
            mimeType = "image/png";
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            mimeType = "image/gif";
        } else if (fileName.toLowerCase().endsWith(".webp")) {
            mimeType = "image/webp";
        }

        // Crear la URL de datos completa
        String dataUrl = "data:" + mimeType + ";base64," + base64Image;

        Map<String, String> response = new HashMap<>();
        response.put("imageId", id.toString());
        response.put("base64Image", dataUrl);

        return response;
    }
}