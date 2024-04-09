package com.test.listacompradetalle.entity;

import com.test.productos.entity.Producto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import com.test.listacompras.entity.ListaCompra;

@Entity
@Table(name = "lista_compra_detalle")
@Data @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode @ToString
public class ListaCompraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ListaCompraDetallePK id;

    @ManyToOne
    @JoinColumn(name = "id_lista_compra", referencedColumnName = "id_lista_compra", insertable=false, updatable=false)
    private ListaCompra listaCompra;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable=false, updatable=false)
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;


}
