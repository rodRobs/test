package com.test.productos.service;

import com.test.exceptions.NotFoundException;
import com.test.productos.entity.Producto;
import com.test.productos.repository.ProductoRepository;
import com.test.utils.GenericCrudService;
import com.test.productos.dto.ProductoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.test.utils.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProductoServiceImpl implements GenericCrudService<ProductoDTO, Integer>{

    @Autowired
    ProductoRepository productoRepository;

    SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        log.debug("ProductoServiceImpl::findAll");
        List<Producto> productoList = productoRepository.findAll();
        return productoList.stream().map(this::entityToDto).toList();
    }

    public ProductoDTO entityToDto(Producto producto) {
        return ProductoDTO.builder()
                .idProducto(producto.getIdProducto())
                .clave(producto.getClave())
                .descripcion(producto.getDescripcion())
                .activo(producto.isActivo())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Integer id) {
        log.debug("ProductoServiceImpl::findById {}", id);
        Producto producto = productoRepository.findById(id).orElseThrow(() -> {
            messageErrorNotFound(id);
            return null;
        });
        return entityToDto(producto);
    }

    public void messageErrorNotFound(int id) {
        throw new NotFoundException("No existe producto por el id: "+id);
    }

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO object) {
        singletonValidatorConstraints.validatorConstraints(object);
        Producto producto = dtoToEntity(object);
        producto = productoRepository.save(producto);
        return entityToDto(producto);
    }

    public Producto dtoToEntity(ProductoDTO producto) {
        return Producto.builder()
                .idProducto(producto.getIdProducto())
                .clave(producto.getClave())
                .descripcion(producto.getDescripcion())
                .activo(producto.isActivo())
                .build();
    }

    @Override
    @Transactional
    public ProductoDTO update(Integer id, ProductoDTO object) {
        log.debug("ProductoServiceImpl::update {}, {}", id, object);
        singletonValidatorConstraints.validatorConstraints(object);
        validateExistsProducto(id);
        Producto producto = dtoToEntity(object);
        producto = productoRepository.saveAndFlush(producto);
        return entityToDto(producto);
    }

    public void validateExistsProducto(int id) {
        boolean existsProducto = existsProducto(id);
        if (!existsProducto)
            messageErrorNotFound(id);
    }

    public boolean existsProducto(int id) {
        return productoRepository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        log.debug("ProductoServiceImpl::deleteById {}", id);
        validateExistsProducto(id);
        productoRepository.deleteById(id);
    }
}
