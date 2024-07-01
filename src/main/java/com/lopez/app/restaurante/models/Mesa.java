package com.lopez.app.restaurante.models;

import com.lopez.app.restaurante.models.Enum.EnumEstadoMesa;

public class Mesa {

    private Long id_mesa;
    private Long num_mesa;
    private Long capacidad;
    private String lugar;
    private EnumEstadoMesa estado;

    public Long getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(Long id_mesa) {
        this.id_mesa = id_mesa;
    }

    public Long getNum_mesa() {
        return num_mesa;
    }

    public void setNum_mesa(Long num_mesa) {
        this.num_mesa = num_mesa;
    }

    public Long getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Long capacidad) {
        this.capacidad = capacidad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public EnumEstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoMesa estado) {
        this.estado = estado;
    }

}
