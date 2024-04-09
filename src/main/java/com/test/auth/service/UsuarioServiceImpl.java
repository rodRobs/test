package com.test.auth.service;

import com.test.auth.dto.TokenDTO;
import com.test.exceptions.BadRequestException;
import com.test.exceptions.NotFoundException;
import com.test.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.test.utils.GenericCrudService;
import com.test.auth.dto.UsuarioDTO;
import com.test.auth.repository.UsuarioRepository;
import com.test.auth.entity.Usuario;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

import com.test.utils.*;
import org.springframework.transaction.annotation.Transactional;

import com.test.auth.jwt.JwtProvider;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, GenericCrudService<UsuarioDTO, Integer>, AuthenticationManager {

    private static final String AUTHENTICATION_ERROR = "Error de autenticación";
    private static final String USER_INACTIVE = "Usuario no activo";

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtProvider jwtProvider;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    private SingletonPasswordEncoder singletonPasswordEncoder = SingletonPasswordEncoder.getInstance();

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
    @Transactional(readOnly = true)
    public TokenDTO login(UsuarioDTO usuarioDTO) {
        log.debug("AuthServiceImpl::login {}", usuarioDTO);
        singletonValidatorConstraints.validatorConstraints(usuarioDTO);
        String authPassword = decodePassword(usuarioDTO.getContrasena());
        Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getCorreo(), authPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new TokenDTO(jwt);
    }

    public String decodePassword(String userPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(userPassword);
        return new String(decodedBytes);
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
    public UsuarioDTO findById(Integer id) {
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
    public UsuarioDTO update(Integer id, UsuarioDTO object) {
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
    public void deleteById(Integer id) {
        log.debug("UsuarioServiceImpl::deleteById {}", id);
        validateExistsUsuario(id);
        usuarioRepository.deleteById(id);
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) {
        log.debug("AuthServiceImpl::authenticate {}", authentication);
        UsuarioDTO usuarioDTO = findByCorreo(authentication.getName());
        usuarioIsActivo(usuarioDTO.isActivo());
        if (singletonPasswordEncoder.passwordEncoder().matches(authentication.getCredentials().toString(), usuarioDTO.getContrasena())) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), usuarioDTO.getAuthorities());
        } else {
            throw new UnauthorizedException(AUTHENTICATION_ERROR);
        }
    }

    public void usuarioIsActivo(boolean activo) {
        if (!activo)
            throw new UnauthorizedException(USER_INACTIVE);
    }
}
