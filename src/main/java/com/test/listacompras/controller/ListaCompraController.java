package com.test.listacompras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.*;
import com.test.listacompras.dto.ListaCompraDTO;

import java.util.List;

@RestController
@RequestMapping("/{lista-compras}")
public class ListaCompraController {

    @Autowired
    GenericCrudService<ListaCompraDTO> genericCrudService;

    @GetMapping
    public ResponseEntity<List<ListaCompraDTO>> findAll() {
        return new ResponseEntity<>(genericCrudService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaCompraDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(genericCrudService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListaCompraDTO> save(@RequestBody ListaCompraDTO listaCompraDTO) {
        return new ResponseEntity<>(genericCrudService.save(listaCompraDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaCompraDTO> update(@PathVariable int id, @RequestBody ListaCompraDTO listaCompraDTO) {
        return new ResponseEntity<>(genericCrudService.update(id, listaCompraDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        genericCrudService.deleteById(id);
    }
}
