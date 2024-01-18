package com.example.spring_crud.services;

import com.example.spring_crud.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save (Product product);
    Optional <Product> update(Long id, Product product);
    Optional<Product> delete(Long id);

}
