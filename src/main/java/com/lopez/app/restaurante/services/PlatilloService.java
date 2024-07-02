package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Platillo;
import com.lopez.app.restaurante.repositorys.IRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class PlatilloService implements IService<Platillo> {

    private IRepository<Platillo> platilloRepo;

    public PlatilloService(Connection conn) {
        this.platilloRepo = new PlatilloRepository(conn);
    }

    @Override
    public List<Platillo> lista() {
        try {
            return platilloRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Platillo> getByID(Long id) {
        try {
            return Optional.ofNullable(platilloRepo.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Platillo t) {
        try {
            platilloRepo.guardar(t);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            platilloRepo.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Platillo t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
