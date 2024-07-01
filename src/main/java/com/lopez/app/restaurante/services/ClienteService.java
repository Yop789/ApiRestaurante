package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.repositorys.ClienteRepo;
import com.lopez.app.restaurante.repositorys.IRepository;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteService implements IService<Cliente> {
    private IRepository<Cliente> clienteRepo;

    public ClienteService(Connection conn) {
        this.clienteRepo = new ClienteRepo(conn);
    }

    @Override
    public List<Cliente> lista() {
        try {
            return clienteRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Cliente> getByID(Long id) {
        try {
            return Optional.ofNullable(clienteRepo.get(id));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            clienteRepo.guardar(cliente);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public void eliminar(Long id) {
        try {
            clienteRepo.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

}
