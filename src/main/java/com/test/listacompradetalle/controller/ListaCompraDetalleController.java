package com.test.listacompradetalle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.*;
import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;
import com.test.listacompradetalle.entity.ListaCompraDetallePK;

import java.util.List;

@RestController
@RequestMapping("/lista-compra-detalles")
public class ListaCompraDetalleController {

    @Autowired
    GenericCrudService<ListaCompraDetalleDTO, ListaCompraDetallePK> genericCrudService;

    @GetMapping
    public ResponseEntity<List<ListaCompraDetalleDTO>> findAll() {
        return new ResponseEntity<>(genericCrudService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/producto/{idProducto}/lista-compra/{idListaCompra}")
    public ResponseEntity<ListaCompraDetalleDTO> findById(@PathVariable int idProducto, @PathVariable int idListaCompra) {
        return new ResponseEntity<ListaCompraDetalleDTO>(genericCrudService.findById(new ListaCompraDetallePK(idListaCompra, idProducto)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListaCompraDetalleDTO> save(@RequestBody ListaCompraDetalleDTO listaCompraDetalleDTO) {
        return new ResponseEntity<>(genericCrudService.save(listaCompraDetalleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/producto/{idProducto}/lista-compra/{idListaCompra}")
    public ResponseEntity<ListaCompraDetalleDTO> update(@PathVariable int idProducto, @PathVariable int idListaCompra, @RequestBody ListaCompraDetalleDTO listaCompraDetalleDTO) {
        return new ResponseEntity<>(genericCrudService.update(new ListaCompraDetallePK(idListaCompra, idProducto), listaCompraDetalleDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/producto/{idProducto}/lista-compra/{idListaCompra}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int idProducto, @PathVariable int idListaCompra) {
        genericCrudService.deleteById(new ListaCompraDetallePK(idListaCompra, idProducto));
    }

}
