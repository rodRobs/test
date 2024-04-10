package com.test.productos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "productos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "clave", length = 15, unique = true)
    private String clave;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    private boolean activo;


}
