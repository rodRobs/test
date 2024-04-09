package com.test.listacompradetalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.listacompradetalle.entity.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCompraDetalleRepository extends JpaRepository<ListaCompraDetalle, ListaCompraDetallePK> {


}
