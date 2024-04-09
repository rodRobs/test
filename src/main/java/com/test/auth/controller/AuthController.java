package com.test.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.utils.GenericCrudService;
import com.test.auth.dto.*;
import com.test.auth.service.UsuarioService;

@RestController
@RequestMapping("/auths")
public class AuthController {

    @Autowired
    GenericCrudService<UsuarioDTO> genericCrudService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(genericCrudService.save(usuarioDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.login(usuarioDTO), HttpStatus.OK);
    }

}
