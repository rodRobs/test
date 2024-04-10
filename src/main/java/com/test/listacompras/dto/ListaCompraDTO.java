package com.test.listacompras.dto;

import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;
import com.test.listacompradetalle.entity.ListaCompraDetalle;
import com.test.listacompras.entity.ListaCompra;
import com.test.productos.dto.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompraDTO {

    private int idListaCompra;

    @NotNull(message = "Id de cliente es requerido")
    private Long idCliente;

    @NotNull(message = "Nombre es requerido")
    @Size(max = 50, message = "Longitud máxima del nombre es de 50 caracteres")
    private String nombre;

    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    private boolean activo;

    private List<ListaCompraDetalleDTO> listaCompraDetalles;

}
