package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Ordenar;
import com.lopez.app.restaurante.repositorys.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenRepository implements IRepository<Ordenar> {
    private Connection conn;

    public OrdenRepository(Connection conn) {

        this.conn = conn;
    }

    @Override
    public List<Ordenar> lista() throws SQLException {
        List<Ordenar> ordenes = new ArrayList<>();
        try (Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM PEDIDOS")) {

            while (rs.next()) {
                Ordenar orden = this.getOrden(rs);
                ordenes.add(orden);
            }

        }
        return ordenes;
    }

    @Override
    public Ordenar get(Long id) throws SQLException {
        String sql = "SELECT * FROM PEDIDOS WHERE ID_PEDIDO = " + id;

        try (Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                return this.getOrden(rs);
            }
        }

        return null;
    }

    @Override
    public void guardar(Ordenar t) throws SQLException {
        String sql = "";

        if (t.getId() != null && t.getId() > 0) {
            sql = "UPDATE PEDIDOS SET ID_MESA=?,ID_MESERO=?,FECHA=? WHERE ID_PEDIDO=?";
        } else {
            sql = "INSERT INTO PEDIDOS(ID_PEDIDO,ID_MESA,ID_MESERO,FECHA) VALUES(SEQUENCE_PEDIDOS.NEXTVAL,?,?,?)";
        }
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setLong(1, t.getId_mesa());
            stm.setLong(2, t.getId_mesero());
            stm.setTimestamp(3, Timestamp.valueOf(t.getFecha()));
            if (t.getId() != null && t.getId() > 0) {
                stm.setLong(4, t.getId());
            }
            stm.executeUpdate();
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {

        String sql = "DELETE FROM PEDIDOS WHERE ID_PEDIDO = " + id;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.executeUpdate();
        }
    }

    private Ordenar getOrden(ResultSet rs) throws SQLException {
        Ordenar orden = new Ordenar();
        orden.setId(rs.getLong("ID_PEDIDO"));
        orden.setId_mesa(rs.getLong("ID_MESA"));
        orden.setId_mesero(rs.getLong("ID_MESERO"));
        orden.setFecha(rs.getTimestamp("FECHA").toLocalDateTime());
        return orden;
    }

    @Override
    public Long guardarReturnId(Ordenar t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
