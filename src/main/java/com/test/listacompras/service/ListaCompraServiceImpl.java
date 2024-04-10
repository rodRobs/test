package com.test.listacompras.service;

import com.test.clientes.entity.Cliente;
import com.test.exceptions.NotFoundException;
import com.test.productos.dto.ProductoDTO;
import com.test.utils.GenericCrudService;
import com.test.listacompras.dto.ListaCompraDTO;
import com.test.listacompras.entity.ListaCompra;
import com.test.listacompras.repository.ListaCompraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.listacompradetalle.entity.ListaCompraDetalle;
import com.test.listacompradetalle.dto.ListaCompraDetalleDTO;

import com.test.utils.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ListaCompraServiceImpl implements GenericCrudService<ListaCompraDTO, Integer>, ListaCompraService{

    @Autowired
    ListaCompraRepository listaCompraRepository;

    private SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<ListaCompraDTO> findAll() {
        log.debug("ListaCompraServiceImpl::findAll");
        List<ListaCompra> listaCompras = listaCompraRepository.findAll();
        return entityListToDtoList(listaCompras);
    }

    public List<ListaCompraDTO> entityListToDtoList(List<ListaCompra> listaCompras) {
        return listaCompras.stream().map(this::entityToDto).toList();
    }

    public ListaCompraDTO entityToDto(ListaCompra listaCompra) {
        return ListaCompraDTO.builder()
                .idListaCompra(listaCompra.getIdListaCompra())
                .idCliente(listaCompra.getCliente().getIdCliente())
                .nombre(listaCompra.getNombre())
                .fechaRegistro(listaCompra.getFechaRegistro())
                .fechaActualizacion(listaCompra.getFechaActualizacion())
                .activo(listaCompra.isActivo())
                .listaCompraDetalles(entityListToDtoListListaCompraDetalle(listaCompra.getListaCompraDetalles()))
                .build();
    }

    public List<ListaCompraDetalleDTO> entityListToDtoListListaCompraDetalle(List<ListaCompraDetalle> listaCompraDetalles) {
        return listaCompraDetalles.stream().map(this::entityToDtoListaCompraDetalle).toList();
    }

    public ListaCompraDetalleDTO entityToDtoListaCompraDetalle(ListaCompraDetalle listaCompraDetalle) {
        return ListaCompraDetalleDTO.builder()
                .listaCompra(ListaCompraDTO.builder().idListaCompra(listaCompraDetalle.getId().getListaCompra().getIdListaCompra()).build())
                .producto(ProductoDTO.builder().idProducto(listaCompraDetalle.getId().getProducto().getIdProducto()).clave(listaCompraDetalle.getId().getProducto().getClave()).descripcion(listaCompraDetalle.getId().getProducto().getDescripcion()).build())
                .cantidad(listaCompraDetalle.getCantidad())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ListaCompraDTO findById(Integer id) {
        log.debug("ListaCompraServiceImpl::findById {}", id);
        ListaCompra listaCompra = listaCompraRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        log.info("Lista Compra {} ",listaCompra);
        return entityToDto(listaCompra);
    }

    public void messageErrorNotFound(int id) {
        throw new NotFoundException("No se encontró lista de compra con el id: "+id);
    }

    @Override
    @Transactional
    public ListaCompraDTO save(ListaCompraDTO object) {
        log.debug("ListaCompraServiceImpl::save {}", object);
        singletonValidatorConstraints.validatorConstraints(object);
        object = completeValuesData(object);
        ListaCompra listaCompra = new ListaCompra(object);
        listaCompra = listaCompraRepository.save(listaCompra);
        return entityToDto(listaCompra);
    }

    public ListaCompraDTO completeValuesData(ListaCompraDTO listaCompraDTO) {
        listaCompraDTO.setFechaRegistro(LocalDateTime.now());
        listaCompraDTO.setActivo(true);
        return listaCompraDTO;
    }

    public ListaCompra dtoToEntity(ListaCompraDTO listaCompra) {
        return ListaCompra.builder()
                .idListaCompra(listaCompra.getIdListaCompra())
                .cliente(dtoToEntityCliente(listaCompra.getIdCliente()))
                .nombre(listaCompra.getNombre())
                .fechaRegistro(listaCompra.getFechaRegistro())
                .fechaActualizacion(listaCompra.getFechaActualizacion())
                .activo(listaCompra.isActivo())
                .build();
    }

    public Cliente dtoToEntityCliente(Long idCliente) {
        return Cliente.builder()
                .idCliente(idCliente)
                .build();
    }

    @Override
    @Transactional
    public ListaCompraDTO update(Integer id, ListaCompraDTO object) {
        log.debug("ListaCompraServiceImpl::update {}, {}", id, object);
        validateExistsById(id);
        singletonValidatorConstraints.validatorConstraints(object);
        ListaCompra listaCompra = dtoToEntity(object);
        listaCompra.setFechaActualizacion(LocalDateTime.now());
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
    public void deleteById(Integer id) {
        log.debug("ListaCompraServiceImpl::deleteById {}", id);
        validateExistsById(id);
        listaCompraRepository.deleteById(id);
    }

    @Override
    public List<ListaCompraDTO> findByIdCliente(int idCliente) {
        log.debug("ListaCompraServiceImpl::findByIdCliente {}", idCliente);
        List<ListaCompra> listaCompras = listaCompraRepository.findByClienteIdCliente(idCliente);
        return entityListToDtoList(listaCompras);
    }
}
