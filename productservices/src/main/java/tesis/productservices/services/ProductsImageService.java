package tesis.productservices.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tesis.productservices.dtos.Base64ImageDTO;

import java.io.IOException;
import java.util.Map;

@Service
public interface ProductsImageService {
    Map<String, String> uploadImage(MultipartFile file) throws IOException;
    Map<String, String> uploadBase64Image(Base64ImageDTO imageDTO) throws IOException;
    Map<String, String> getImageAsBase64(Long id) throws IOException;
}
