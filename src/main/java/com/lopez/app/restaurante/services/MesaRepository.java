package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Enum.EnumEstadoMesa;
import com.lopez.app.restaurante.repositorys.IRepository;
import com.lopez.app.restaurante.models.Mesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaRepository implements IRepository<Mesa> {
    private Connection conn;

    public MesaRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Mesa> lista() throws SQLException {
        List<Mesa> mesas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM MESAS")) {

            while (rs.next()) {
                Mesa mesa = this.getMesa(rs);
                mesas.add(mesa);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return mesas;
    }

    @Override
    public Mesa get(Long id) throws SQLException {
        String sql = "SELECT * FROM MESAS WHERE ID_MESA=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return this.getMesa(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return null;
    }

    @Override
    public void guardar(Mesa t) throws SQLException {
        String sql = "";
        if (t.getId_mesa() != null && t.getId_mesa() > 0) {
            sql = "UPDATE MESAS SET NUMERO=?,CAPACIDAD=?,LUGAR=?,ESTATUS=? WHERE ID_MESA=?";
        } else {
            sql = "INSERT INTO MESAS(ID_MESA,NUMERO,CAPACIDAD,LUGAR,ESTATUS) VALUES(SEQUENCE_MESAS.NEXTVAL,?,?,?,?)";
        }
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, t.getNum_mesa());
            stm.setLong(2, t.getCapacidad());
            stm.setString(3, t.getLugar());
            stm.setString(4, t.getEstado().toString());
            if (t.getId_mesa() != null && t.getId_mesa() > 0) {
                stm.setLong(5, t.getId_mesa());
            }
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM MESAS WHERE ID_MESA=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }

    private Mesa getMesa(ResultSet rs) throws SQLException {
        Mesa mesa = new Mesa();
        mesa.setId_mesa(rs.getLong("ID_MESA"));
        mesa.setNum_mesa(rs.getLong("NUMERO"));
        mesa.setCapacidad(rs.getLong("CAPACIDAD"));
        mesa.setLugar(rs.getString("LUGAR"));
        mesa.setEstado(EnumEstadoMesa.valueOf(rs.getString("ESTATUS")));
        return mesa;
    }

    @Override
    public Long guardarReturnId(Mesa t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
