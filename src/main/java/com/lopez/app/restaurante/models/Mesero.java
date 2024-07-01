package com.lopez.app.restaurante.models;

public class Mesero extends Persona {

    private Long num_Empleado;
    private Long edad;

    public Long getNum_Empleado() {
        return num_Empleado;
    }

    public void setNum_Empleado(Long num_Empleado) {
        this.num_Empleado = num_Empleado;
    }

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

}
