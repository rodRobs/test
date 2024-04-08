package com.test.auth.service;

import com.test.auth.dto.TokenDTO;
import com.test.clientes.entity.Cliente;
import com.test.exceptions.BadRequestException;
import com.test.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.utils.GenericCrudService;
import com.test.auth.dto.UsuarioDTO;
import com.test.auth.repository.UsuarioRepository;
import com.test.auth.entity.Usuario;

import java.util.List;
import java.util.Objects;

import com.test.utils.SingletonValidatorConstraints;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, GenericCrudService<UsuarioDTO> {

    @Autowired
    UsuarioRepository usuarioRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findByCorreo(String correo) {
        log.debug("UsuarioServiceImpl::findByCorreo {}", correo);
        Usuario usuarioDB = usuarioRepository.findByCorreo(correo);
        if (Objects.isNull(usuarioDB)) {
            throw new BadRequestException("No existe usuario con el correo: "+ correo);
        }
        return entityToDto(usuarioDB);
    }

    @Override
    public TokenDTO login(UsuarioDTO usuarioDTO) {
        return null;
    }

    public UsuarioDTO entityToDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .correo(usuario.getCorreo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        log.debug("UsuarioServiceImpl::findAll");
        List<Usuario> usuarioList = usuarioRepository.findAll();
        return usuarioList.stream().map(usuario -> entityToDto(usuario)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(int id) {
        log.debug("UsuarioServiceImpl::findById {}", id);
        Usuario usuarioDB = usuarioRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        return entityToDto(usuarioDB);
    }

    public void messageErrorNotFound(int id) {
        throw new NotFoundException("No existe usuario con el id: "+id);
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO object) {
        log.debug("UsuarioServiceImpl::save {}", object);
        singletonValidatorConstraints.validatorConstraints(object);
        Usuario usuario =  dtoToEntity(object);
        usuario = usuarioRepository.save(usuario);
        return entityToDto(usuario);
    }

    public Usuario dtoToEntity(UsuarioDTO usuario) {
        return Usuario.builder()
                .idUsuario(usuario.getIdUsuario())
                .correo(usuario.getCorreo())
                .contrasena(usuario.getContrasena())
                .build();
    }


    @Override
    @Transactional
    public UsuarioDTO update(int id, UsuarioDTO object) {
        log.debug("UsuarioServiceImpl::update {}, {}", id, object);
        validateExistsUsuario(id);
        singletonValidatorConstraints.validatorConstraints(object);
        Usuario usuarioDB = usuarioRepository.findById(id).get();
        Usuario usuarioUpdated = updateUsuario(usuarioDB, object);
        return entityToDto(usuarioUpdated);
    }

    public void validateExistsUsuario(int id) {
        boolean existUsuario = existsById(id);
        if (!existUsuario) {
            messageErrorNotFound(id);
        }
    }

    public boolean existsById(int id) {
        return usuarioRepository.existsById(id);
    }

    public Usuario updateUsuario(Usuario usuarioDB, UsuarioDTO usuarioUpdate) {
        usuarioDB.setCorreo(usuarioUpdate.getCorreo());
        usuarioDB.setContrasena(usuarioUpdate.getContrasena());
        return usuarioDB;
    }

    @Override
    public void deleteById(int id) {
        log.debug("UsuarioServiceImpl::deleteById {}", id);
        validateExistsUsuario(id);
        usuarioRepository.deleteById(id);
    }
}
