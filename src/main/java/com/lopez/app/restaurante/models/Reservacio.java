package com.lopez.app.restaurante.models;

import com.lopez.app.restaurante.models.Enum.EnumReservacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservacio {
    private Long id;
    private Long id_mesa;
    private Long id_cliente;
    private LocalDate fecha;
    private LocalDateTime fecha_a_reservar;
    private EnumReservacion estatus;
    private String idOrderPypal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(Long id_mesa) {
        this.id_mesa = id_mesa;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFecha_a_reservar() {
        return fecha_a_reservar;
    }

    public void setFecha_a_reservar(LocalDateTime fecha_a_reservar) {
        this.fecha_a_reservar = fecha_a_reservar;
    }

    public EnumReservacion getEstatus() {
        return estatus;
    }

    public void setEstatus(EnumReservacion estatus) {
        this.estatus = estatus;
    }

    public String getIdOrderPypal() {
        return idOrderPypal;
    }

    public void setIdOrderPypal(String idOrderPypal) {
        this.idOrderPypal = idOrderPypal;
    }

}
