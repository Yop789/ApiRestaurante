package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.DescripcioOrden;
import com.lopez.app.restaurante.models.Ordenar;
import com.lopez.app.restaurante.models.Platillo;
import com.lopez.app.restaurante.repositorys.DescripcioOrdenRepository;
import com.lopez.app.restaurante.repositorys.IRepository;
import com.lopez.app.restaurante.repositorys.OrdenRepository;
import com.lopez.app.restaurante.repositorys.PlatilloRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DescripcioService implements IDescripcioOrdenService<DescripcioOrden> {
    private IRepository<DescripcioOrden> descripcioRepo;
    private IRepository<Platillo> platilloRepo;
    private IRepository<Ordenar> ordenarRepo;

    public DescripcioService(Connection conn) {
        this.descripcioRepo = new DescripcioOrdenRepository(conn);
        this.platilloRepo = new PlatilloRepository(conn);
        this.ordenarRepo = new OrdenRepository(conn);
    }

    @Override
    public List<DescripcioOrden> lista() {
        try {
            return descripcioRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<DescripcioOrden> getByID(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByID'");
    }

    @Override
    public void guardar(DescripcioOrden t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public void eliminar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public List<Platillo> listaPlatillos() {
        try {
            return platilloRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Ordenar> listaOrdenes() {
        try {
            return ordenarRepo.lista();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

}
