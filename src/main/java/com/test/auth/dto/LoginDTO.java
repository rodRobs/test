package com.test.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Correo es requerido")
    private String correo;

    @NotNull(message = "Contraseña es requerida")
    private String contrasena;

}
