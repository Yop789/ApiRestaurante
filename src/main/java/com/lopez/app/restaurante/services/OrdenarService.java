package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Mesa;
import com.lopez.app.restaurante.models.Mesero;
import com.lopez.app.restaurante.models.Ordenar;
import com.lopez.app.restaurante.repositorys.IRepository;
import com.lopez.app.restaurante.repositorys.OrdenRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OrdenarService implements IOrdenarService<Ordenar> {
    IRepository<Ordenar> ordenarRepo;
    IRepository<Mesa> mesaRepo;
    IRepository<Mesero> meseroRepo;

    public OrdenarService(Connection conn) {
        this.ordenarRepo = new OrdenRepository(conn);
        this.mesaRepo = new MesaRepository(conn);
        this.meseroRepo = new MeseroRepository(conn);
    }

    @Override
    public List<Ordenar> lista() {
        try {
            return ordenarRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Ordenar> getByID(Long id) {
        Optional<Ordenar> optional = null;
        try {
            optional = Optional.ofNullable(ordenarRepo.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return optional;
    }

    @Override
    public void guardar(Ordenar t) {
        try {
            ordenarRepo.guardar(t);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            ordenarRepo.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Mesa> listaMesas() {
        try {
            return mesaRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Mesero> listaMeseros() {
        try {
            return meseroRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long guardarReturnId(Ordenar t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
