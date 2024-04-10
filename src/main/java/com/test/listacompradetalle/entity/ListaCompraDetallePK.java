package com.test.listacompradetalle.entity;

import com.test.listacompras.entity.ListaCompra;
import com.test.productos.entity.Producto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString @EqualsAndHashCode
public class ListaCompraDetallePK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_lista_compra", referencedColumnName = "id_lista_compra", insertable=false, updatable=false)
    private ListaCompra listaCompra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable=false, updatable=false)
    private Producto producto;

    public ListaCompraDetallePK(int idListaCompra, int idProducto) {
        this.setListaCompra(ListaCompra.builder().idListaCompra(idListaCompra).build());
        this.setProducto(Producto.builder().idProducto(idProducto).build());
    }

}
