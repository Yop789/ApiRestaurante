package com.lopez.app.restaurante.repositorys;

import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.models.Enum.EnumEstado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepo implements IRepository<Cliente> {
    private Connection conn;

    public ClienteRepo(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Cliente> lista() throws SQLException {
        List<Cliente> Cliente = new ArrayList<>();
        try (Statement stm = this.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM CLIENTES")) {
            while (rs.next()) {
                Cliente a = this.getCliente(rs);
                Cliente.add(a);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return Cliente;
    }

    @Override
    public Cliente get(Long id) throws SQLException {

        Cliente cliente = null;
        try (PreparedStatement stm = conn.prepareStatement("SELECT * FROM CLIENTES WHERE ID_CLIENTE=?")) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    cliente = this.getCliente(rs);

                }
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return cliente;
    }

    @Override
    public void guardar(Cliente t) throws SQLException {
        String sql = "";
        if (t.getId() != null && t.getId() > 0) {
            sql = "UPDATE CLIENTES SET NOMBRE=?, AP_PATERNO=?, AP_MATERNO=?, TELEFONO=?, CORREO=?, " +
                    "CALLE=?, NUMERO_INT=?, NUMERO_EXT=?, COLONIA=?, CIUDAD=?, ESTADO=?, CODIGO_POSTAL=?, " +
                    "FECHA_NACIMIENTO=? WHERE ID_CLIENTE=?";

        } else {
            sql = "INSERT INTO CLIENTES(ID_CLIENTE,NOMBRE,AP_PATERNO,AP_MATERNO,TELEFONO,CORREO,CALLE,NUMERO_INT,NUMERO_EXT,COLONIA,CIUDAD,ESTADO,CODIGO_POSTAL,FECHA_NACIMIENTO) "
                    +
                    "VALUES(SEQUENCE_CLIENTE.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            if (t.getId() != null && t.getId() > 0) {
                stm.setString(1, t.getNombre());
                stm.setString(2, t.getApPaterno());
                stm.setString(3, t.getApMaterno());
                stm.setString(4, t.getTelefono());
                stm.setString(5, t.getCorreo());
                stm.setString(6, t.getCalle());
                stm.setLong(7, t.getNum_interior());
                stm.setLong(8, t.getNum_exterior());
                stm.setString(9, t.getColonia());
                stm.setString(10, t.getCiudad());
                stm.setString(11, t.getEstado().name().toString());
                stm.setInt(12, t.getCp());
                stm.setDate(13, Date.valueOf(t.getFecha_nacimiento()));
                stm.setLong(14, t.getId());
            } else {
                stm.setString(1, t.getNombre());
                stm.setString(2, t.getApPaterno());
                stm.setString(3, t.getApMaterno());
                stm.setString(4, t.getTelefono());
                stm.setString(5, t.getCorreo());
                stm.setString(6, t.getCalle());
                stm.setLong(7, t.getNum_interior());
                stm.setLong(8, t.getNum_exterior());
                stm.setString(9, t.getColonia());
                stm.setString(10, t.getCiudad());
                stm.setString(11, t.getEstado().name().toString());
                stm.setInt(12, t.getCp());
                stm.setDate(13, Date.valueOf(t.getFecha_nacimiento()));
            }

            stm.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM CLIENTES WHERE ID_CLIENTE=?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private Cliente getCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("ID_CLIENTE"));
        c.setNombre(rs.getString("NOMBRE"));
        c.setApPaterno(rs.getString("AP_PATERNO"));
        c.setApMaterno(rs.getString("AP_MATERNO"));
        c.setTelefono(rs.getString("TELEFONO"));
        c.setCorreo(rs.getString("CORREO"));
        c.setCalle(rs.getString("CALLE"));
        c.setNum_interior(rs.getLong("NUMERO_INT"));
        c.setNum_exterior(rs.getLong("NUMERO_EXT"));
        c.setColonia(rs.getString("COLONIA"));
        c.setCiudad(rs.getString("CIUDAD"));
        c.setEstado(EnumEstado.valueOf(rs.getString("ESTADO")));
        c.setCp(rs.getInt("CODIGO_POSTAL"));
        c.setFecha_nacimiento(rs.getDate("FECHA_NACIMIENTO").toLocalDate());
        return c;
    }

    @Override
    public Long guardarReturnId(Cliente t) throws SQLException {
        String sql = "INSERT INTO CLIENTES(ID_CLIENTE, NOMBRE, AP_PATERNO, AP_MATERNO, TELEFONO, CORREO, CALLE, NUMERO_INT, NUMERO_EXT, COLONIA, CIUDAD, ESTADO, CODIGO_POSTAL, FECHA_NACIMIENTO) "
                +
                "VALUES (SEQUENCE_CLIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = conn.prepareStatement(sql, new String[] { "ID_CLIENTE" })) {
            stm.setString(1, t.getNombre());
            stm.setString(2, t.getApPaterno());
            stm.setString(3, t.getApMaterno());
            stm.setString(4, t.getTelefono());
            stm.setString(5, t.getCorreo());
            stm.setString(6, t.getCalle());
            stm.setLong(7, t.getNum_interior());
            stm.setLong(8, t.getNum_exterior());
            stm.setString(9, t.getColonia());
            stm.setString(10, t.getCiudad());
            stm.setString(11, t.getEstado().name()); // Asumiendo que Estado es un Enum
            stm.setInt(12, t.getCp());
            stm.setDate(13, Date.valueOf(t.getFecha_nacimiento()));
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al insertar nuevo cliente", e);
        }

        return 0L;
    }
}
