package com.test.clientes.service;

import com.test.clientes.entity.Cliente;
import com.test.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.utils.GenericCrudService;
import com.test.clientes.dto.ClienteDTO;
import com.test.clientes.repository.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.test.utils.SingletonValidatorConstraints;

@Service
@Slf4j
public class ClienteServiceImpl implements GenericCrudService<ClienteDTO, Integer>{

    @Autowired
    ClienteRepository clienteRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        log.debug("ClienteServiceImpl::findAll");
        List<Cliente> clienteList = clienteRepository.findAll();
        return clienteList.stream().map(cliente -> entityToDTO(cliente)).toList();
    }

    public ClienteDTO entityToDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .activo(cliente.isActivo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findById(Integer id) {
        log.debug("ClienteServiceImpl::findById {}", id);
        Cliente clienteDB = clienteRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        return entityToDTO(clienteDB);
    }

    public void messageErrorNotFound(int id) {
        throw new NotFoundException("No se encontro cliente con id: "+id);
    }

    @Override
    @Transactional
    public ClienteDTO save(ClienteDTO object) {
        log.debug("ClienteServiceImpl::save {}", object);
        singletonValidatorConstraints.validatorConstraints(object);
        Cliente cliente = dtoToEntity(object);
        cliente = clienteRepository.save(cliente);
        return entityToDTO(cliente);
    }

    public Cliente dtoToEntity(ClienteDTO cliente) {
        return Cliente.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .activo(cliente.isActivo())
                .build();
    }

    @Override
    @Transactional
    public ClienteDTO update(Integer id, ClienteDTO object) {
        log.debug("ClienteServiceImpl::update {}, {}", id, object);
        singletonValidatorConstraints.validatorConstraints(object);
        validateExists(id);
        Cliente cliente = dtoToEntity(object);
        cliente = clienteRepository.saveAndFlush(cliente);
        return entityToDTO(cliente);
    }

    public void validateExists(int id) {
        boolean clienteExists = existsCliente(id);
        if (!clienteExists) {
            messageErrorNotFound(id);
        }
    }

    public boolean existsCliente(int id) {
        return clienteRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("ClienteServiceImpl::deleteById {}", id);
        validateExists(id);
        clienteRepository.deleteById(id);
    }
}
