package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Mesa;
import com.lopez.app.restaurante.repositorys.IRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MesaService implements IService<Mesa> {
    private IRepository<Mesa> mesaRepo;

    public MesaService(Connection conn) {
        this.mesaRepo = new MesaRepository(conn);
    }

    @Override
    public List<Mesa> lista() {
        try {
            return mesaRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Mesa> getByID(Long id) {
        try {
            return Optional.ofNullable(mesaRepo.get(id));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Mesa t) {
        try {
            mesaRepo.guardar(t);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            mesaRepo.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Mesa t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
