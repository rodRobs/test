package com.test.listacompras.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.test.clientes.dto.ClienteDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompraDTO {

    private int idListaCompra;

    @NotNull(message = "Cliente es requerido")
    private ClienteDTO cliente;

    @NotNull(message = "Nombre es requerido")
    @Size(max = 50, message = "Longitud máxima del nombre es de 50 caracteres")
    private String nombre;

    private LocalDateTime fechaRegistro;

    private LocalDateTime fechaActualizacion;

    private boolean activo;

}
