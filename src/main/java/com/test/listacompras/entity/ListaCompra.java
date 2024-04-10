package com.test.listacompras.entity;

import com.test.clientes.entity.Cliente;
import com.test.listacompras.dto.ListaCompraDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.test.listacompradetalle.entity.ListaCompraDetalle;
import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;

@Entity
@Table(name = "lista_compra")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaCompra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_compra")
    private int idListaCompra;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "id.listaCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListaCompraDetalle> listaCompraDetalles;

    public ListaCompra(ListaCompraDTO listaCompra) {
        this.setIdListaCompra(listaCompra.getIdListaCompra());
        this.setCliente(listaCompra.getIdCliente());
        this.setNombre(listaCompra.getNombre());
        this.setFechaRegistro(listaCompra.getFechaRegistro());
        this.setFechaActualizacion(listaCompra.getFechaActualizacion());
        this.setListaCompraDetallesDTO(listaCompra.getListaCompraDetalles());
    }

    public void setCliente(Long idCliente) {
        this.cliente = new Cliente();
        this.cliente.setIdCliente(idCliente);
    }

    public void setListaCompraDetallesDTO(List<ListaCompraDetalleDTO> listaCompraDetalleDTOS) {
        this.listaCompraDetalles = listaCompraDetalleDTOS.stream().map(ListaCompraDetalle::new).toList();
        this.listaCompraDetalles.forEach(listaCompraDetalle -> {
            listaCompraDetalle.getId().setListaCompra(this);
        });
    }

}
