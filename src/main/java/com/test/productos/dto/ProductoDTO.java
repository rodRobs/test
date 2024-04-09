package com.test.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProducto;

    private String clave;

    private String descripcion;

    private boolean activo;

}
