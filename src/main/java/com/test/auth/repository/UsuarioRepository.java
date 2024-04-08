package com.test.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.auth.entity.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String correo);

}
