package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.DescripcioOrden;
import com.lopez.app.restaurante.models.Ordenar;
import com.lopez.app.restaurante.models.Platillo;

import java.util.List;

public interface IDescripcioOrdenService<T> extends IService<DescripcioOrden> {
    List<Platillo> listaPlatillos();

    List<Ordenar> listaOrdenes();
}
