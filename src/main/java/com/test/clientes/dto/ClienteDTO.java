package com.test.clientes.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@ToString @EqualsAndHashCode
public class ClienteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idCliente;

    @NotNull(message = "Nombre del cliente es requerido")
    @Size(max = 50, message = "Longitud máxima del nombre del cliente es de 50 caracteres")
    private String nombre;

    private boolean activo;

}
