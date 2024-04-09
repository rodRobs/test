package com.test.productos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.*;
import com.test.productos.dto.ProductoDTO;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    GenericCrudService<ProductoDTO, Integer> genericCrudService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        return new ResponseEntity<>(genericCrudService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(genericCrudService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> save(@RequestBody ProductoDTO productoDTO) {
        return new ResponseEntity<>(genericCrudService.save(productoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable int id, @RequestBody ProductoDTO productoDTO) {
        return new ResponseEntity<>(genericCrudService.update(id, productoDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        genericCrudService.deleteById(id);
    }

}
