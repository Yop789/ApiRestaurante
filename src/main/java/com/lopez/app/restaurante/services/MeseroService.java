package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Mesero;
import com.lopez.app.restaurante.repositorys.IRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MeseroService implements IService<Mesero> {
    private IRepository<Mesero> meseroRepo;

    public MeseroService(Connection conn) {
        this.meseroRepo = new MeseroRepository(conn);
    }

    @Override
    public List<Mesero> lista() {
        try {
            return meseroRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Mesero> getByID(Long id) {
        try {
            return Optional.ofNullable(meseroRepo.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Mesero t) {
        try {
            meseroRepo.guardar(t);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            meseroRepo.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Mesero t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
