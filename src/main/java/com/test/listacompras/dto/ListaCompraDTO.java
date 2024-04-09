package com.test.listacompras.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.test.clientes.dto.ClienteDTO;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompraDTO {

    private int idListaCompra;

    private String nombre;

    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    private boolean activo;

    private ClienteDTO cliente;

}
