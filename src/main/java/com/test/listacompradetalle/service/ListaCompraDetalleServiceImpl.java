package com.test.listacompradetalle.service;

import com.test.clientes.entity.Cliente;
import com.test.clientes.dto.ClienteDTO;
import com.test.exceptions.NotFoundException;
import com.test.utils.*;
import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.listacompradetalle.repository.ListaCompraDetalleRepository;
import com.test.listacompradetalle.entity.*;
import com.test.listacompras.entity.ListaCompra;
import com.test.listacompras.dto.ListaCompraDTO;
import com.test.productos.entity.Producto;
import com.test.productos.dto.ProductoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ListaCompraDetalleServiceImpl implements GenericCrudService<ListaCompraDetalleDTO, ListaCompraDetallePK>{

    @Autowired
    ListaCompraDetalleRepository listaCompraDetalleRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<ListaCompraDetalleDTO> findAll() {
        log.debug("ListaCompraDetalleServiceImpl::findAll");
        List<ListaCompraDetalle> listaCompraDetalles = listaCompraDetalleRepository.findAll();
        return listaCompraDetalles.stream().map(listaCompraDetalle -> entityToDto(listaCompraDetalle)).toList();
    }

    public ListaCompraDetalleDTO entityToDto(ListaCompraDetalle listaCompraDetalle) {
        return ListaCompraDetalleDTO.builder()
                .listaCompra(entityToDtoListaCompra(listaCompraDetalle.getListaCompra()))
                .producto(entityToDtoProducto(listaCompraDetalle.getProducto()))
                .cantidad(listaCompraDetalle.getCantidad())
                .build();
    }

    public ListaCompraDTO entityToDtoListaCompra(ListaCompra listaCompra) {
        return ListaCompraDTO.builder()
                .idListaCompra(listaCompra.getIdListaCompra())
                .cliente(entityToDtoCliente(listaCompra.getCliente()))
                .build();
    }

    public ClienteDTO entityToDtoCliente(Cliente cliente) {
        return ClienteDTO.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .build();
    }

    public ProductoDTO entityToDtoProducto(Producto producto) {
        return ProductoDTO.builder()
                .idProducto(producto.getIdProducto())
                .clave(producto.getClave())
                .descripcion(producto.getDescripcion())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ListaCompraDetalleDTO findById(ListaCompraDetallePK id) {
        log.debug("ListaCompraDetalleServiceImpl::findById {}", id);
        ListaCompraDetalle listaCompraDetalle = listaCompraDetalleRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        return entityToDto(listaCompraDetalle);
    }

    public void messageErrorNotFound(ListaCompraDetallePK listaCompraDetallePK) {
        throw new NotFoundException("No se encontro detalle de lista de compra con id de producto: " + listaCompraDetallePK.getIdProducto() + " y id de lista compra: " + listaCompraDetallePK.getIdListaCompra());
    }

    @Override
    @Transactional
    public ListaCompraDetalleDTO save(ListaCompraDetalleDTO object) {
        log.debug("ListaCompraDetalleServiceImpl::save {}", object);
        singletonValidatorConstraints.validatorConstraints(object);
        ListaCompraDetalle listaCompraDetalle = dtoToEntity(object);
        listaCompraDetalle = listaCompraDetalleRepository.save(listaCompraDetalle);
        return entityToDto(listaCompraDetalle);
    }

    public ListaCompraDetalle dtoToEntity(ListaCompraDetalleDTO listaCompraDetalle) {
        return ListaCompraDetalle.builder()
                .listaCompra(dtoToEntityListaCompra(listaCompraDetalle.getListaCompra()))
                .producto(dtoToEntityProducto(listaCompraDetalle.getProducto()))
                .cantidad(listaCompraDetalle.getCantidad())
                .build();
    }

    public ListaCompra dtoToEntityListaCompra(ListaCompraDTO listaCompra) {
        return ListaCompra.builder()
                .idListaCompra(listaCompra.getIdListaCompra())
                .cliente(dtoToEntityCliente(listaCompra.getCliente()))
                .build();
    }

    public Cliente dtoToEntityCliente(ClienteDTO cliente) {
        return Cliente.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .build();
    }

    public Producto dtoToEntityProducto(ProductoDTO producto) {
        return Producto.builder()
                .idProducto(producto.getIdProducto())
                .clave(producto.getClave())
                .descripcion(producto.getDescripcion())
                .build();
    }

    @Override
    @Transactional
    public ListaCompraDetalleDTO update(ListaCompraDetallePK id, ListaCompraDetalleDTO object) {
        log.debug("ListaCompraDetalleServiceImpl::update {}, {}", id, object);
        singletonValidatorConstraints.validatorConstraints(object);
        validateId(id);
        ListaCompraDetalle listaCompraDetalle = dtoToEntity(object);
        listaCompraDetalle = listaCompraDetalleRepository.saveAndFlush(listaCompraDetalle);
        return entityToDto(listaCompraDetalle);
    }

    public void validateId(ListaCompraDetallePK id) {
        boolean existsListaCompraDetalle = existsListaCompraDetalle(id);
        if (!existsListaCompraDetalle)
            messageErrorNotFound(id);
    }

    public boolean existsListaCompraDetalle(ListaCompraDetallePK id) {
        return listaCompraDetalleRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(ListaCompraDetallePK id) {
        log.debug("ListaCompraDetalleServiceImpl::deleteById {}", id);
        validateId(id);
        listaCompraDetalleRepository.deleteById(id);
    }
}