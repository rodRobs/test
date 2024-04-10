package com.test.clientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.GenericCrudService;
import com.test.clientes.dto.ClienteDTO;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    GenericCrudService<ClienteDTO, Long> genericCRUDService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return new ResponseEntity<>(genericCRUDService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(genericCRUDService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(genericCRUDService.save(clienteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(genericCRUDService.update(id, clienteDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        genericCRUDService.deleteById(id);
    }

}
