package com.test.auth.service;

import com.test.auth.dto.TokenDTO;
import com.test.exceptions.BadRequestException;
import com.test.exceptions.NotFoundException;
import com.test.exceptions.UnauthorizedException;
import com.test.roles.entity.Rol;
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
import com.test.roles.dto.RolDTO;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.test.utils.*;
import org.springframework.transaction.annotation.Transactional;

import com.test.auth.jwt.JwtProvider;

import com.test.auth.dto.LoginDTO;

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
        UsuarioDTO usuarioDTO = entityToDto(usuarioDB);
        usuarioDTO.setContrasena(usuarioDB.getContrasena());
        return usuarioDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public TokenDTO login(LoginDTO loginDTO) {
        log.debug("AuthServiceImpl::login {}", loginDTO);
        singletonValidatorConstraints.validatorConstraints(loginDTO);
        String authPassword = decodePassword(loginDTO.getContrasena());
        Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getCorreo(), authPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new TokenDTO(jwt);
    }

    public String decodePassword(String userPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(userPassword);
        return new String(decodedBytes);
    }

    public UsuarioDTO entityToDto(Usuario usuario) {
        log.info("Usuario: {}",usuario);
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .correo(usuario.getCorreo())
                .roles(usuario.getRolesUsuario().stream().map(rol -> entityToDtoRol(rol)).collect(Collectors.toSet()))
                .fechaCreacion(usuario.getFechaCreacion())
                .activo(usuario.isActivo())
                .build();
    }

    public RolDTO entityToDtoRol(Rol rol) {
        return RolDTO.builder()
                .idRol(rol.getIdRol())
                .nombre(rol.getNombre())
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
        completeValues(object);
        Usuario usuario =  dtoToEntity(object);
        usuario = usuarioRepository.save(usuario);
        return entityToDto(usuario);
    }

    public UsuarioDTO completeValues(UsuarioDTO usuario) {
        singletonValidatorConstraints.validatorConstraints(usuario);
        String encodeContrasena = singletonPasswordEncoder.passwordEncoder().encode(usuario.getContrasena());
        usuario.setContrasena(encodeContrasena);
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuario;
    }

    public Usuario dtoToEntity(UsuarioDTO usuario) {
        return Usuario.builder()
                .idUsuario(usuario.getIdUsuario())
                .correo(usuario.getCorreo())
                .contrasena(usuario.getContrasena())
                .rolesUsuario(usuario.getRoles().stream().map(rol -> dtoToEntityRol(rol)).collect(Collectors.toSet()))
                .activo(usuario.isActivo())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }

    public Rol dtoToEntityRol(RolDTO rol) {
        return Rol.builder()
                .idRol(rol.getIdRol())
                .nombre(rol.getNombre())
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
