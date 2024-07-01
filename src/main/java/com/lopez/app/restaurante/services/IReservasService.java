package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.models.Mesa;
import com.lopez.app.restaurante.models.Reservacio;

import java.util.List;

public interface IReservasService<T> extends IService<Reservacio> {
    List<Cliente> listaClientes();

    List<Mesa> listaMesas();

}
