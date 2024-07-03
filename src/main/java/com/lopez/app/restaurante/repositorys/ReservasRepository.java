package com.lopez.app.restaurante.repositorys;

import com.lopez.app.restaurante.models.Enum.EnumReservacion;
import com.lopez.app.restaurante.models.Reservacio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasRepository implements IRepository<Reservacio> {
    private Connection conn;

    public ReservasRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Reservacio> lista() throws SQLException {
        List<Reservacio> reservas = new ArrayList<>();
        try (Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM RESERVAS")) {

            while (rs.next()) {
                Reservacio reservacio = this.getReservacio(rs);
                reservas.add(reservacio);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return reservas;

    }

    @Override
    public Reservacio get(Long id) throws SQLException {
        String sql = "SELECT * FROM RESERVAS WHERE ID_RESERVA=?";
        Reservacio reservacio = null;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    reservacio = this.getReservacio(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return reservacio;
    }

    @Override
    public void guardar(Reservacio t) throws SQLException {
        String sql = "";
        if (t.getId() != null && t.getId() > 0) {
            sql = "UPDATE RESERVAS SET ID_MESA=?,ID_CLIENTE=?,FECHA=?,FECHA_A_RESERVAR=?,ESTATUS=? WHERE ID_RESERVA=?";
        } else {
            sql = "INSERT INTO RESERVAS(ID_RESERVA,ID_CLIENTE,ID_MESA,FECHA,FECHA_A_RESERVAR,ESTATUS,idOrderPypal) VALUES(SEQUENCE_RESERVAS.NEXTVAL,?,?,?,?,?,?)";
        }
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setLong(1, t.getId_cliente());
            stm.setLong(2, t.getId_mesa());
            stm.setDate(3, Date.valueOf(t.getFecha()));
            stm.setTimestamp(4, Timestamp.valueOf(t.getFecha_a_reservar()));
            stm.setString(5, t.getEstatus().toString());
            stm.setString(6, t.getIdOrderPypal());
            if (t.getId() != null && t.getId() > 0) {
                stm.setLong(6, t.getId());
            }
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM RESERVAS WHERE ID_RESERVA=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }

    private Reservacio getReservacio(ResultSet rs) throws SQLException {
        Reservacio reservacio = new Reservacio();
        reservacio.setId(rs.getLong("ID_RESERVA"));
        reservacio.setId_mesa(rs.getLong("ID_MESA"));
        reservacio.setId_cliente(rs.getLong("ID_CLIENTE"));
        reservacio.setFecha(rs.getDate("FECHA").toLocalDate());
        reservacio.setFecha_a_reservar(rs.getTimestamp("FECHA_A_RESERVAR").toLocalDateTime());
        reservacio.setEstatus(EnumReservacion.valueOf(rs.getString("ESTATUS")));
        reservacio.setIdOrderPypal(rs.getString("idOrderPypal"));
        return reservacio;
    }

    @Override
    public Long guardarReturnId(Reservacio t) throws SQLException {
        String sql = "INSERT INTO RESERVAS(ID_RESERVA,ID_CLIENTE,ID_MESA,FECHA,FECHA_A_RESERVAR,ESTATUS,idOrderPypal) VALUES(SEQUENCE_RESERVAS.NEXTVAL,?,?,?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql, new String[] { "ID_RESERVA" })) {
            stm.setLong(1, t.getId_cliente());
            stm.setLong(2, t.getId_mesa());
            stm.setDate(3, Date.valueOf(t.getFecha()));
            stm.setTimestamp(4, Timestamp.valueOf(t.getFecha_a_reservar()));
            stm.setString(5, t.getEstatus().toString());
            stm.setString(6, t.getIdOrderPypal());
            stm.executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return null;
    }

}
