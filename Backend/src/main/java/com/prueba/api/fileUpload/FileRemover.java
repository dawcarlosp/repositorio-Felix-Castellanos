package com.prueba.api.fileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileRemover {

    public static final String FOTO_DEFAULT = "Thunderdome_logo.jpg";

    @Value("${app.upload.dir.usuarios}")
    private String uploadDir;

    public void deleteFile(String fileName) {
        String folderPath = System.getProperty("user.dir") + "/" + uploadDir;
        String filePath = folderPath + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }
}
