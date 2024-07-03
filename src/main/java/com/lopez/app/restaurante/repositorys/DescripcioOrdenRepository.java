package com.lopez.app.restaurante.repositorys;

import com.lopez.app.restaurante.models.DescripcioOrden;
import com.lopez.app.restaurante.models.Enum.EnumEstatusDetalleOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DescripcioOrdenRepository implements IRepository<DescripcioOrden> {
    private Connection conn;

    public DescripcioOrdenRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<DescripcioOrden> lista() throws SQLException {
        List<DescripcioOrden> descripcions = new ArrayList<>();
        try (Statement stm = this.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM DETALLE_PEDIDO")) {
            while (rs.next()) {
                DescripcioOrden descripcio = this.getDescripcio(rs);
                descripcions.add(descripcio);
            }

        } catch (SQLException e) {
            throw new SQLException(e);

        }
        return descripcions;
    }

    @Override
    public DescripcioOrden get(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void guardar(DescripcioOrden t) throws SQLException {
        String sql = "";
        if (t.getId() == null) {

            sql = "INSERT INTO DETALLE_PEDIDO (ID_DETALLEP,ID_PEDIDO,ID_PLATILLO,CANTIDAD,ESTATUS) VALUES (SEQUENCE_DETALLE_PEDIDO.NEXTVAL,?,?,?,?)";
        } else

        {
            sql = "UPDATE DETALLE_PEDIDO SET ID_PEDIDO = ?, ID_PLATILLO = ?, CANTIDAD = ?, ESTATUS = ? WHERE ID_DETALLEP = ?";
        }
        try (
                PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setLong(1, t.getId_orden());
            stm.setLong(2, t.getId_platillo());
            stm.setLong(3, t.getCantidad());
            stm.setString(4, t.getEstatus().name());
            if (t.getId() != null) {
                stm.setLong(5, t.getId());
            }
            stm.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    private DescripcioOrden getDescripcio(ResultSet rs) throws SQLException {
        DescripcioOrden descripcio = new DescripcioOrden();
        descripcio.setId(rs.getLong("ID_DETALLEP"));
        descripcio.setId_orden(rs.getLong("ID_PEDIDO"));
        descripcio.setId_platillo(rs.getLong("ID_PLATILLO"));
        descripcio.setCantidad(rs.getLong("CANTIDAD"));
        descripcio.setEstatus(EnumEstatusDetalleOrder.valueOf(rs.getString("ESTATUS")));
        return descripcio;
    }

    @Override
    public Long guardarReturnId(DescripcioOrden t) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarReturnId'");
    }

}
