package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.models.Mesa;
import com.lopez.app.restaurante.models.Reservacio;
import com.lopez.app.restaurante.repositorys.IRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReservasService implements IReservasService<Reservacio> {
    private IRepository<Cliente> clienteRepo;
    private IRepository<Mesa> mesaRepo;
    private IRepository<Reservacio> reservaRepo;

    public ReservasService(Connection conn) {
        this.clienteRepo = new ClienteRepo(conn);
        this.mesaRepo = new MesaRepository(conn);
        this.reservaRepo = new ReservasRepository(conn);
    }

    @Override
    public List<Reservacio> lista() {
        try {
            return reservaRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Reservacio> getByID(Long id) {
        try {
            return Optional.ofNullable(reservaRepo.get(id));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Reservacio t) {
        try {
            reservaRepo.guardar(t);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            reservaRepo.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Cliente> listaClientes() {
        try {
            return clienteRepo.lista();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Mesa> listaMesas() {
        try {
            return mesaRepo.lista();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Reservacio t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
