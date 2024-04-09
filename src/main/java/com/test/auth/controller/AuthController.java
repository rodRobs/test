package com.test.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.utils.GenericCrudService;
import com.test.auth.dto.*;
import com.test.auth.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/auths")
@Slf4j
public class AuthController {

    @Autowired
    GenericCrudService<UsuarioDTO, Integer> genericCrudService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return new ResponseEntity<>(genericCrudService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(genericCrudService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO) {
        log.info("Entra a guardar {}", usuarioDTO);
        return new ResponseEntity<>(genericCrudService.save(usuarioDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(usuarioService.login(loginDTO), HttpStatus.OK);
    }

}
