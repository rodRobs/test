package com.test.listacompras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.listacompras.entity.ListaCompra;

import java.util.List;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {

    List<ListaCompra> findByClienteIdCliente(int idCliente);

}
