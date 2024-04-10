package com.test.listacompras.service;

import java.util.List;
import com.test.listacompras.dto.ListaCompraDTO;

public interface ListaCompraService {

    List<ListaCompraDTO> findByIdCliente(int idCliente);

}
