package com.test.roles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.test.roles.enums.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RolDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idRol;

    private NombreRol nombre;

    public RolDTO(NombreRol nombre) {
        this.nombre = nombre;
    }

}
