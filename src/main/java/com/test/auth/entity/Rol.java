package com.test.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.test.auth.enums.NombreRol;

@Entity
@Table(name = "rol")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private int idRol;

    @Column(name = "nombre", nullable = false)
    private NombreRol nombre;

}
