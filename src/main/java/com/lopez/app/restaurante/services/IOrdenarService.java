package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Mesa;
import com.lopez.app.restaurante.models.Mesero;
import com.lopez.app.restaurante.models.Ordenar;

import java.util.List;

public interface IOrdenarService<T> extends IService<Ordenar> {
    List<Mesa> listaMesas();

    List<Mesero> listaMeseros();

}
