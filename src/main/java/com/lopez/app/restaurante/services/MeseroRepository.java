package com.lopez.app.restaurante.services;

import com.lopez.app.restaurante.models.Enum.EnumEstado;
import com.lopez.app.restaurante.repositorys.IRepository;
import com.lopez.app.restaurante.models.Mesero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeseroRepository implements IRepository<Mesero> {
    private Connection conn;

    public MeseroRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Mesero> lista() throws SQLException {
        List<Mesero> meseros = new ArrayList<>();
        try (Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM MESEROS")) {

            while (rs.next()) {
                Mesero mesero = this.getMesero(rs);
                meseros.add(mesero);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return meseros;

    }

    @Override
    public Mesero get(Long id) throws SQLException {
        String sql = "SELECT * FROM MESEROS WHERE ID_MESERO=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return this.getMesero(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return null;
    }

    @Override
    public void guardar(Mesero t) throws SQLException {
        String sql = "";
        if (t.getId() != null && t.getId() > 0) {
            // Si el mesero ya tiene un ID, se actualiza su información
            sql = "UPDATE MESEROS SET NOMBRE=?, AP_PATERNO=?, AP_MATERNO=?, FECHA_NACIMIENTO=?, TELEFONO=?, CORREO=?, EDAD=?, NUM_EMPLEADO=?, CALLE=?, NUMERO_INT=?, NUMERO_EXT=?, COLONIA=?, CIUDAD=?, ESTADO=?, CODIGO_POSTAL=? WHERE ID_MESERO=?";
        } else {
            // Si es un nuevo mesero, se inserta en la base de datos
            sql = "INSERT INTO MESEROS (ID_MESERO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_NACIMIENTO, TELEFONO, CORREO, EDAD, NUM_EMPLEADO, CALLE, NUMERO_INT, NUMERO_EXT, COLONIA, CIUDAD, ESTADO, CODIGO_POSTAL) "
                    +
                    "VALUES (SEQUENCE_MESEROS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, t.getNombre());
            stm.setString(2, t.getApPaterno());
            stm.setString(3, t.getApMaterno());
            stm.setDate(4, Date.valueOf(t.getFecha_nacimiento()));
            stm.setString(5, t.getTelefono());
            stm.setString(6, t.getCorreo());
            stm.setLong(7, t.getEdad());
            stm.setLong(8, t.getNum_Empleado());
            stm.setString(9, t.getCalle());
            stm.setLong(10, t.getNum_interior());
            stm.setLong(11, t.getNum_exterior());
            stm.setString(12, t.getColonia());
            stm.setString(13, t.getCiudad());
            stm.setString(14, t.getEstado().toString());
            stm.setInt(15, t.getCp());

            // Si es una actualización, se añade el ID del mesero al final del conjunto de
            // parámetros
            if (t.getId() != null && t.getId() > 0) {
                stm.setLong(16, t.getId());
            }

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al guardar el mesero: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM MESEROS WHERE NUM_EMPLEADO=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }

    private Mesero getMesero(ResultSet rs) throws SQLException {
        Mesero mesero = new Mesero();
        mesero.setId(rs.getLong("ID_MESERO"));
        mesero.setNombre(rs.getString("NOMBRE"));
        mesero.setApPaterno(rs.getString("AP_PATERNO"));
        mesero.setApMaterno(rs.getString("AP_MATERNO"));
        mesero.setTelefono(rs.getString("TELEFONO"));
        mesero.setCorreo(rs.getString("CORREO"));
        mesero.setCalle(rs.getString("CALLE"));
        mesero.setNum_exterior(rs.getLong("NUMERO_INT"));
        mesero.setNum_exterior(rs.getLong("NUMERO_EXT"));
        mesero.setColonia(rs.getString("COLONIA"));
        mesero.setCiudad(rs.getString("CIUDAD"));
        mesero.setEstado(EnumEstado.valueOf(rs.getString("ESTADO")));
        mesero.setCp(rs.getInt("CODIGO_POSTAL"));
        mesero.setFecha_nacimiento(rs.getDate("FECHA_NACIMIENTO").toLocalDate());
        mesero.setNum_Empleado(rs.getLong("NUM_EMPLEADO"));
        mesero.setEdad(rs.getLong("EDAD"));
        return mesero;
    }

    @Override
    public Long guardarReturnId(Mesero t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }
}
