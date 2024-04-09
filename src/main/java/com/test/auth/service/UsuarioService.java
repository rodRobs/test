package com.test.auth.service;

import com.test.auth.dto.*;

public interface UsuarioService {

    UsuarioDTO findByCorreo(String correo);

    TokenDTO login(LoginDTO loginDTO);

}
