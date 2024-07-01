package com.lopez.app.restaurante.models;

import com.lopez.app.restaurante.models.Enum.EnumEstatusPlatillo;

public class Platillo {

    private Long id;
    private String nombre;
    private Float precio;
    private String descripcion;
    private EnumEstatusPlatillo estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EnumEstatusPlatillo getEstatus() {
        return estatus;
    }

    public void setEstatus(EnumEstatusPlatillo estatus) {
        this.estatus = estatus;
    }

}
