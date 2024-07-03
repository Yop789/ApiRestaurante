package com.lopez.app.restaurante.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.models.Reservacio;
import com.lopez.app.restaurante.models.Enum.EnumEstado;
import com.lopez.app.restaurante.models.Enum.EnumReservacion;
import com.lopez.app.restaurante.services.ClienteService;
import com.lopez.app.restaurante.services.IReservasService;
import com.lopez.app.restaurante.services.IService;
import com.lopez.app.restaurante.services.ReservasService;

@WebServlet("/altaReservacion")
public class ListarClientesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Cliente> serviceCli = new ClienteService(conn);
        IReservasService<Reservacio> servicerRes = new ReservasService(conn);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        String nombre = req.getParameter("nombre");
        String apPaterno = req.getParameter("apPaterno");
        String apMaterno = req.getParameter("apMaterno");
        String telefono = req.getParameter("telefono");
        String correo = req.getParameter("correo");
        String calle = req.getParameter("calle");
        String numInteriorParam = req.getParameter("numInterior");
        String numExteriorParam = req.getParameter("numExterior");
        String colonia = req.getParameter("colonia");
        String ciudad = req.getParameter("ciudad");
        String cpParam = req.getParameter("cp");
        String estado = req.getParameter("estado");
        String payid = req.getParameter("ordenToken");

        Map<String, String> errors = new HashMap<>();

        Long id_mesa = null;
        Long id_cliente = null;

        String fechaAReservarStr = req.getParameter("fecha_a_reservar");
        LocalDateTime fechaAReservar = null;

        try {
            id_mesa = Long.parseLong(req.getParameter("id_mesa"));
        } catch (NumberFormatException e) {
            errors.put("id_mesa", "Debe seleccionar una mesa válida");
        }

        try {
            fechaAReservar = LocalDateTime.parse(fechaAReservarStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            errors.put("fecha_a_reservar", "Debe seleccionar una fecha y hora válidas");
        }

        String fechaNacimiento = req.getParameter("fechaNacimiento");
        LocalDate fecha;
        try {
            Date fechaInput = inputFormat.parse(fechaNacimiento);
            String formato = outputFormat.format(fechaInput);
            fecha = LocalDate.parse(formato, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            fecha = null;
        }

        Long num_interno = 0L;
        Long num_externo = 0L;
        Integer cp = 0;

        if (nombre == null || nombre.isEmpty()) {
            errors.put("nombre", "El nombre es requerido");
        }

        if (apPaterno == null || apPaterno.isEmpty()) {
            errors.put("apPaterno", "El apellido paterno es requerido");
        }

        if (apMaterno == null || apMaterno.isEmpty()) {
            errors.put("apMaterno", "El apellido materno es requerido");
        }

        if (telefono == null || telefono.isEmpty()) {
            errors.put("telefono", "El teléfono es requerido");
        }

        if (correo == null || correo.isEmpty()) {
            errors.put("correo", "El correo es requerido");
        }

        if (calle == null || calle.isEmpty()) {
            errors.put("calle", "La calle es requerida");
        }

        if (numInteriorParam != null && !numInteriorParam.isEmpty()) {
            try {
                num_interno = Long.parseLong(numInteriorParam);
            } catch (NumberFormatException e) {
                errors.put("num_interno", "El número interno debe ser un número válido");
            }
        }

        if (numExteriorParam == null || numExteriorParam.isEmpty()) {
            errors.put("numExterior", "El número externo es requerido");
        } else {
            try {
                num_externo = Long.parseLong(numExteriorParam);
            } catch (NumberFormatException e) {
                errors.put("num_externo", "El número externo debe ser un número válido");
            }
        }

        if (colonia == null || colonia.isEmpty()) {
            errors.put("colonia", "La colonia es requerida");
        }

        if (ciudad == null || ciudad.isEmpty()) {
            errors.put("ciudad", "La ciudad es requerida");
        }

        if (cpParam == null || cpParam.isEmpty()) {
            errors.put("cp", "El código postal es requerido");
        } else {
            try {
                cp = Integer.parseInt(cpParam);
            } catch (NumberFormatException e) {
                errors.put("cp", "El código postal debe ser un número válido");
            }
        }

        if (estado == null || estado.isEmpty()) {
            errors.put("estado", "El estado es requerido");
        }

        if (fecha == null) {
            errors.put("fechaNacimiento",
                    "La fecha de nacimiento es requerida y debe tener el formato válido (YYYY-MM-DD)");
        }

        resp.setContentType("application/json");

        if (!errors.isEmpty()) {
            try (PrintWriter out = resp.getWriter()) {
                Gson gson = new Gson();
                String jsonErrors = gson.toJson(errors);
                out.print(jsonErrors);
            }
        } else {
            try (PrintWriter out = resp.getWriter()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setApPaterno(apPaterno);
                cliente.setApMaterno(apMaterno);
                cliente.setTelefono(telefono);
                cliente.setCorreo(correo);
                cliente.setCalle(calle);
                cliente.setNum_interior(num_interno != 0 ? num_interno : 0);
                cliente.setNum_exterior(num_externo);
                cliente.setColonia(colonia);
                cliente.setCiudad(ciudad);
                cliente.setCp(cp);
                cliente.setEstado(EnumEstado.valueOf(estado));
                cliente.setFecha_nacimiento(fecha);

                try {
                    serviceCli.guardar(cliente);
                    Long id = serviceCli.guardarReturnId(cliente);

                    if (id != null) {
                        Reservacio reservacio = new Reservacio();
                        reservacio.setId_mesa(id_mesa);
                        reservacio.setId_cliente(id);
                        reservacio.setFecha_a_reservar(fechaAReservar);
                        reservacio.setFecha(LocalDate.now());
                        reservacio.setEstatus(EnumReservacion.EN_CURSO);
                        reservacio.setIdOrderPypal(payid);

                        servicerRes.guardar(reservacio);
                        out.print("OK");
                    } else {
                        errors.put("cliente", "Error al guardar el cliente");
                        Gson gson = new Gson();
                        String jsonErrors = gson.toJson(errors);
                        out.print(jsonErrors);
                    }
                } catch (Exception e) {
                    errors.put("reservacion", "Error al guardar la reservación: " + e.getMessage());
                    Gson gson = new Gson();
                    String jsonErrors = gson.toJson(errors);
                    out.print(jsonErrors);
                }
            }
        }
    }

}
