package com.test.listacompradetalle.entity;

import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lista_compra_detalle")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode @ToString
public class ListaCompraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ListaCompraDetallePK id;

    @Column(name = "cantidad")
    private int cantidad;

    public ListaCompraDetalle(ListaCompraDetalleDTO listaCompraDetalle)  {
        this.setId(new ListaCompraDetallePK((Objects.isNull(listaCompraDetalle.getListaCompra())) ? 0 : listaCompraDetalle.getListaCompra().getIdListaCompra(), listaCompraDetalle.getProducto().getIdProducto()));
        this.setCantidad(listaCompraDetalle.getCantidad());
    }

}
