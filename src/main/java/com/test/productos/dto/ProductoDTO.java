package com.test.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProducto;

    @Size(max = 15, message = "Longitud máxima de la clave del producto es de 15 caracteres")
    private String clave;

    @Size(max = 150, message = "Longitud máxima de la descripción del producto es de 150 caracteres")
    private String descripcion;

    private boolean activo;

}
