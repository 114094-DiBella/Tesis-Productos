package tesis.productservices.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public interface ProductsImageService {

    Map<String, String> uploadImage(MultipartFile file) throws IOException;
}
