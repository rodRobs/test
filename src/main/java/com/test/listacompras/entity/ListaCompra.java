package com.test.listacompras.entity;

import com.test.clientes.entity.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "lista_compra")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista")
    private int idLista;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo")
    private boolean activo;

}
