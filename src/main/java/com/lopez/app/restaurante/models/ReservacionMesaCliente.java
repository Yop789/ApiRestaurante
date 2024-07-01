package com.lopez.app.restaurante.models;

public class ReservacionMesaCliente {
    Cliente cliente;
    Reservacio reservacio;
    Mesa mesa;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Reservacio getReservacio() {
        return reservacio;
    }

    public void setReservacio(Reservacio reservacio) {
        this.reservacio = reservacio;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

}
