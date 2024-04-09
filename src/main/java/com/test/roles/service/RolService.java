package com.test.roles.service;

import com.test.exceptions.NotFoundException;
import com.test.roles.entity.Rol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.utils.*;
import com.test.roles.dto.RolDTO;
import com.test.roles.repository.RolRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RolService implements GenericCrudService<RolDTO, Integer> {

    @Autowired
    RolRepository rolRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> findAll() {
        log.debug("RolService::findAll");
        List<Rol> roles = rolRepository.findAll();
        return roles.stream().map(rol -> entityToDto(rol)).toList();
    }

    public RolDTO entityToDto(Rol rol) {
        return RolDTO.builder()
                .idRol(rol.getIdRol())
                .nombre(rol.getNombre())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO findById(Integer id) {
        log.debug("RolService::findById {}", id);
        Rol rol = rolRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFoundId(id);
            return null;
        });
        return entityToDto(rol);
    }

    public void messageErrorNotFoundId(int id) {
        throw new NotFoundException("No se encontró rol con el id: "+id);
    }

    @Override
    @Transactional
    public RolDTO save(RolDTO object) {
        log.debug("RolService::save {}", object);
        singletonValidatorConstraints.validatorConstraints(object);
        Rol rol = dtoToEntity(object);
        rol = rolRepository.save(rol);
        return entityToDto(rol);
    }

    public Rol dtoToEntity(RolDTO rol) {
        return Rol.builder()
                .idRol(rol.getIdRol())
                .nombre(rol.getNombre())
                .build();
    }

    @Override
    @Transactional
    public RolDTO update(Integer id, RolDTO object) {
        log.debug("RolService::update {}, {}", id, object);
        singletonValidatorConstraints.validatorConstraints(object);
        validateId(id);
        Rol rol = dtoToEntity(object);
        rol = rolRepository.saveAndFlush(rol);
        return entityToDto(rol);
    }

    public void validateId(int id) {
        boolean existsRol = existsRolById(id);
        if (!existsRol)
            messageErrorNotFoundId(id);
    }

    public boolean existsRolById(int id) {
        return rolRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("RolService::deleteById {}", id);
        validateId(id);
        rolRepository.deleteById(id);
    }
}
