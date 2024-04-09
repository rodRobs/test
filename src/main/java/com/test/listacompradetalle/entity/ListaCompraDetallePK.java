package com.test.listacompradetalle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString @EqualsAndHashCode
public class ListaCompraDetallePK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id_lista_compra")
    private int idListaCompra;

    @Column(name = "id_producto")
    private int idProducto;

}
