package com.dawi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dawi.models.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Integer> {

}
