package com.test.listacompras.service;

import com.test.clientes.entity.Cliente;
import com.test.exceptions.NotFoundException;
import com.test.utils.GenericCrudService;
import com.test.listacompras.dto.ListaCompraDTO;
import com.test.listacompras.entity.ListaCompra;
import com.test.listacompras.repository.ListaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.clientes.dto.ClienteDTO;

import com.test.utils.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListaCompraServiceImpl implements GenericCrudService<ListaCompraDTO>{

    @Autowired
    ListaCompraRepository listaCompraRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<ListaCompraDTO> findAll() {
        List<ListaCompra> listaCompras = listaCompraRepository.findAll();
        return listaCompras.stream().map(listaCompra -> entityToDto(listaCompra)).toList();
    }

    public ListaCompraDTO entityToDto(ListaCompra listaCompra) {
        return ListaCompraDTO.builder()
                .idLista(listaCompra.getIdLista())
                .cliente(entityToDtoCliente(listaCompra.getCliente()))
                .nombre(listaCompra.getNombre())
                .fechaRegistro(listaCompra.getFechaRegistro())
                .fechaActualizacion(listaCompra.getFechaActualizacion())
                .activo(listaCompra.isActivo())
                .build();
    }

    public ClienteDTO entityToDtoCliente(Cliente cliente) {
        return ClienteDTO.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .activo(cliente.isActivo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ListaCompraDTO findById(int id) {
        ListaCompra listaCompra = listaCompraRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        return entityToDto(listaCompra);
    }

    public void messageErrorNotFound(int id) {
        throw new NotFoundException("No se encontro lista de compra con el id: "+id);
    }

    @Override
    @Transactional
    public ListaCompraDTO save(ListaCompraDTO object) {
        singletonValidatorConstraints.validatorConstraints(object);
        ListaCompra listaCompra = dtoToEntity(object);
        listaCompra = listaCompraRepository.save(listaCompra);
        return entityToDto(listaCompra);
    }

    public ListaCompra dtoToEntity(ListaCompraDTO listaCompra) {
        return ListaCompra.builder()
                .idLista(listaCompra.getIdLista())
                .cliente(entityToDtoCliente(listaCompra.getCliente()))
                .nombre(listaCompra.getNombre())
                .fechaRegistro(listaCompra.getFechaRegistro())
                .fechaActualizacion(listaCompra.getFechaActualizacion())
                .activo(listaCompra.isActivo())
                .build();
    }

    public Cliente entityToDtoCliente(ClienteDTO cliente) {
        return Cliente.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .activo(cliente.isActivo())
                .build();
    }

    @Override
    @Transactional
    public ListaCompraDTO update(int id, ListaCompraDTO object) {
        validateExistsById(id);
        singletonValidatorConstraints.validatorConstraints(object);
        ListaCompra listaCompra = dtoToEntity(object);
        listaCompra = listaCompraRepository.saveAndFlush(listaCompra);
        return entityToDto(listaCompra);
    }

    public void validateExistsById(int id) {
        boolean existsListaCompra = existsById(id);
        if (!existsListaCompra)
            messageErrorNotFound(id);
    }

    public boolean existsById(int id) {
        return listaCompraRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        validateExistsById(id);
        listaCompraRepository.deleteById(id);
    }
}
