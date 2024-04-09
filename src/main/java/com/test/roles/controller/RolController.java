package com.test.roles.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.GenericCrudService;
import com.test.roles.dto.RolDTO;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    GenericCrudService<RolDTO, Integer> genericCrudService;

    @GetMapping
    public ResponseEntity<List<RolDTO>> findAll() {
        return new ResponseEntity<>(genericCrudService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(genericCrudService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RolDTO> save(@RequestBody RolDTO rol) {
        return new ResponseEntity<>(genericCrudService.save(rol), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> update(@PathVariable int id, @RequestBody RolDTO rol) {
        return new ResponseEntity<>(genericCrudService.update(id, rol), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        genericCrudService.deleteById(id);
    }

}
