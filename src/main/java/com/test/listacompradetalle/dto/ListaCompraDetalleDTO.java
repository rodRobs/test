package com.test.listacompradetalle.dto;

import com.test.listacompras.dto.ListaCompraDTO;
import com.test.productos.dto.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompraDetalleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Lista de compra es requerido")
    private ListaCompraDTO listaCompra;

    @NotNull(message = "Producto es requerido")
    private ProductoDTO producto;

    private int cantidad;



}
