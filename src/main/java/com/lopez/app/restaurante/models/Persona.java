package com.lopez.app.restaurante.models;

import com.lopez.app.restaurante.models.Enum.EnumEstado;

import java.time.LocalDate;

public class Persona {
    private Long id;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String telefono;
    private String correo;
    private String calle;
    private Long num_interior;
    private Long num_exterior;
    private LocalDate fecha_nacimiento;
    private String colonia;
    private String ciudad;
    private Integer cp;
    private EnumEstado estado;

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Long getNum_interior() {
        return num_interior;
    }

    public void setNum_interior(Long num_interior) {
        this.num_interior = num_interior;
    }

    public Long getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(Long num_exterior) {
        this.num_exterior = num_exterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }

}