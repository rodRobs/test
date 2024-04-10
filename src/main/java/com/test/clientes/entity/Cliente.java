package com.test.clientes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.test.listacompras.entity.ListaCompra;

@Entity
@Table(name = "cliente")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<ListaCompra> listaCompras;

}
