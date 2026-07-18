package com.prueba.api.fileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadEjercicios {

    @Value("${app.upload.dir.ejercicios}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomFoto = file.getOriginalFilename();
            String[] partes = nomFoto.split("\\.");
            String nomNuevo = UUID.randomUUID().toString() + UUID.randomUUID().toString() + "." + partes[partes.length - 1];

            Path filePath = uploadPath.resolve(nomNuevo);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return nomNuevo;
        } catch (IOException e) {
            return null;
        }
    }
}
