package com.lopez.app.restaurante.models;

import com.lopez.app.restaurante.models.Enum.EnumEstatusDetalleOrder;

public class DescripcioOrden {
    private Long id;
    private Long id_orden;
    private Long id_platillo;
    private Long cantidad;
    private EnumEstatusDetalleOrder estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_orden() {
        return id_orden;
    }

    public void setId_orden(Long id_orden) {
        this.id_orden = id_orden;
    }

    public Long getId_platillo() {
        return id_platillo;
    }

    public void setId_platillo(Long id_platillo) {
        this.id_platillo = id_platillo;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public EnumEstatusDetalleOrder getEstatus() {
        return estatus;
    }

    public void setEstatus(EnumEstatusDetalleOrder estatus) {
        this.estatus = estatus;
    }

}
