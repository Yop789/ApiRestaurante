package com.lopez.app.restaurante.services;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    List<T> lista();

    Optional<T> getByID(Long id);

    void guardar(T t);

    void eliminar(Long id);

    Long guardarReturnId(T t);

}
