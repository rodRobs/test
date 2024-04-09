package com.test.productos.service;

import com.test.exceptions.NotFoundException;
import com.test.productos.entity.Producto;
import com.test.productos.repository.ProductoRepository;
import com.test.utils.GenericCrudService;
import com.test.productos.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.test.utils.*;

@Service
public class ProductoServiceImpl implements GenericCrudService<ProductoDTO>{

    @Autowired
    ProductoRepository productoRepository;

    SingletonValidatorConstraints singletonValidatorConstraints = SingletonValidatorConstraints.getInstance();

    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productoList = productoRepository.findAll();
        return productoList.stream().map(producto -> entityToDto(producto)).toList();
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
    public ProductoDTO findById(int id) {
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
    public ProductoDTO update(int id, ProductoDTO object) {
        singletonValidatorConstraints.validatorConstraints(object);
        validateExistsProducto(id);
        Producto producto = dtoToEntity(object);
        producto = productoRepository.saveAndFlush(producto);
        return entityToDto(producto);
    }

    public void validateExistsProducto(int id) {
        boolean existsProducto = existsProcucto(id);
        if (!existsProducto)
            messageErrorNotFound(id);
    }

    public boolean existsProcucto(int id) {
        return productoRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        validateExistsProducto(id);
        productoRepository.deleteById(id);
    }
}
