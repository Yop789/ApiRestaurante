package com.lopez.app.restaurante.repositorys;

import com.lopez.app.restaurante.models.Enum.EnumEstatusPlatillo;
import com.lopez.app.restaurante.models.Platillo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatilloRepository implements IRepository<Platillo> {
    private Connection conn;

    public PlatilloRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Platillo> lista() throws SQLException {
        List<Platillo> platillos = new ArrayList<>();
        try (Statement stm = this.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM PLATILLOS")) {
            while (rs.next()) {
                Platillo a = this.getPlatillo(rs);
                platillos.add(a);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return platillos;
    }

    @Override
    public Platillo get(Long id) throws SQLException {
        String sql = "SELECT * FROM PLATILLOS WHERE ID_PLATILLO = " + id;
        try (Statement stm = this.conn.createStatement();
                ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                return this.getPlatillo(rs);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }

    @Override
    public void guardar(Platillo t) throws SQLException {

        String sql = "";
        if (t.getId() != null && t.getId() > 0) {
            sql = "UPDATE PLATILLOS SET NOMBRE=?,DESCRIPCION=?,PRECIO=?,ESTATUS=? WHERE ID_PLATILLO=?";
        } else {
            sql = "INSERT INTO PLATILLOS(ID_PLATILLO,NOMBRE,DESCRIPCION,PRECIO,ESTATUS) VALUES(SEQUENCE_PLATILLOS.NEXTVAL,?,?,?,?)";
        }
        try (PreparedStatement stm = this.conn.prepareStatement(sql)) {
            stm.setString(1, t.getNombre());
            stm.setString(2, t.getDescripcion());
            stm.setFloat(3, t.getPrecio());
            stm.setString(4, t.getEstatus().toString());
            if (t.getId() != null && t.getId() > 0) {
                stm.setLong(5, t.getId());
            }
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);

        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM PLATILLOS WHERE ID_PLATILLO=?";
        try (PreparedStatement stm = this.conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }

    private Platillo getPlatillo(ResultSet rs) throws SQLException {
        Platillo platillo = new Platillo();
        platillo.setId(rs.getLong("ID_PLATILLO"));
        platillo.setNombre(rs.getString("NOMBRE"));
        platillo.setPrecio(rs.getFloat("PRECIO"));
        platillo.setDescripcion(rs.getString("DESCRIPCION"));
        platillo.setEstatus(EnumEstatusPlatillo.valueOf(rs.getString("ESTATUS")));
        return platillo;
    }

    @Override
    public Long guardarReturnId(Platillo t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
