package com.prueba.api.auth;

import com.google.gson.Gson;
import com.prueba.api.entity.User;
import com.prueba.api.fileUpload.FileRemover;
import com.prueba.api.fileUpload.FileUpload;
import com.prueba.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final FileUpload fileUpload;
    private final FileRemover fileRemover;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse authResponse = authService.login(request);

        if (authResponse.getToken()==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = "register", consumes = { "multipart/form-data" })
    public ResponseEntity<AuthResponse> register(@RequestParam("registro") String requestString, @RequestParam(name="file", required = false) MultipartFile file){
        String fotoDB = "";
        Gson gson = new Gson();
        RegisterRequest registro = gson.fromJson(requestString, RegisterRequest.class);

        if (file!=null){
            fotoDB = fileUpload.uploadFile(file);
        }

        AuthResponse authResponse = authService.register(registro, fotoDB);

        if (authResponse.getToken()==null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(authResponse);
        }
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping(value = "update", consumes = { "multipart/form-data" })
    public ResponseEntity<AuthResponse> update(@RequestParam("registro") String requestString,
                                               @RequestParam(name="file", required = false) MultipartFile file,
                                               @RequestParam(name = "usernameSession") String username)
    {
        Optional<User> existingUser = userService.findByUser(username);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Gson gson = new Gson();
        RegisterRequest registro = gson.fromJson(requestString, RegisterRequest.class);

        User user = existingUser.get();
        user.setUsername(registro.getUsername());
        user.setName(registro.getName());
        user.setEmail(registro.getEmail());
        if (registro.getPassword() != null && !registro.getPassword().isEmpty()) {
            user.setPassword(authService.encodePassword(registro.getPassword()));
        }

        if (file != null && !file.isEmpty()) {
            String fotoNueva = fileUpload.uploadFile(file);
            if (fotoNueva != null) {
                String fotoAntigua = user.getFoto();
                if (fotoAntigua != null && !FileRemover.FOTO_DEFAULT.equals(fotoAntigua)) {
                    fileRemover.deleteFile(fotoAntigua);
                }
                user.setFoto(fotoNueva);
            }
        }

        userService.saveUser(user);
        AuthResponse authResponse = authService.generateResponse(user);

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}
