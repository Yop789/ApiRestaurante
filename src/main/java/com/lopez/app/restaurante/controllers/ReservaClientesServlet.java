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
public class ReservaClientesServlet extends HttpServlet {

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
        LocalDateTime fechaAReservar = null;

        // Validación de parámetros
        validateParameters(req, errors);

        // Validación y parseo de id_mesa
        try {
            id_mesa = Long.parseLong(req.getParameter("id_mesa"));
        } catch (NumberFormatException e) {
            errors.put("id_mesa", "Debe seleccionar una mesa válida");
        }

        // Validación y parseo de fechaAReservar
        try {
            fechaAReservar = LocalDateTime.parse(req.getParameter("fecha_a_reservar"),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            errors.put("fecha_a_reservar", "Debe seleccionar una fecha y hora válidas");
        }

        // Validación y parseo de fechaNacimiento
        LocalDate fechaNacimiento = parseFechaNacimiento(req, errors, inputFormat, outputFormat);

        resp.setContentType("application/json");

        if (!errors.isEmpty()) {
            sendJsonResponse(resp, errors, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (PrintWriter out = resp.getWriter()) {
            Cliente cliente = createCliente(req, fechaNacimiento);

            Long clienteId = serviceCli.guardarReturnId(cliente);

            if (clienteId != null) {
                Reservacio reservacion = createReservacion(id_mesa, clienteId, fechaAReservar, payid);
                Long reservacionId = servicerRes.guardarReturnId(reservacion);
                resp.setStatus(HttpServletResponse.SC_OK);
                if (reservacionId != null) {
                    Map<String, Object> response = new HashMap<>();
                    resp.setStatus(201);
                    response.put("reservacionId", reservacionId);
                    response.put("status", "success");
                    String json = new Gson().toJson(response);

                    out.print(json);
                    return;
                }
                out.print("OK");
            } else {
                errors.put("cliente", "Error al guardar el cliente");
                sendJsonResponse(resp, errors, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            errors.put("reservacion", "Error al guardar la reservación: " + e.getMessage());
            sendJsonResponse(resp, errors, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private void validateParameters(HttpServletRequest req, Map<String, String> errors) {
        // Validaciones de los parámetros recibidos
        if (isNullOrEmpty(req.getParameter("nombre"))) {
            errors.put("nombre", "El nombre es requerido");
        }
        if (isNullOrEmpty(req.getParameter("apPaterno"))) {
            errors.put("apPaterno", "El apellido paterno es requerido");
        }
        if (isNullOrEmpty(req.getParameter("apMaterno"))) {
            errors.put("apMaterno", "El apellido materno es requerido");
        }
        if (isNullOrEmpty(req.getParameter("telefono"))) {
            errors.put("telefono", "El teléfono es requerido");
        }
        if (isNullOrEmpty(req.getParameter("correo"))) {
            errors.put("correo", "El correo es requerido");
        }
        if (isNullOrEmpty(req.getParameter("calle"))) {
            errors.put("calle", "La calle es requerida");
        }
        if (isNullOrEmpty(req.getParameter("numExterior"))) {
            errors.put("numExterior", "El número externo es requerido");
        }
        if (isNullOrEmpty(req.getParameter("colonia"))) {
            errors.put("colonia", "La colonia es requerida");
        }
        if (isNullOrEmpty(req.getParameter("ciudad"))) {
            errors.put("ciudad", "La ciudad es requerida");
        }
        if (isNullOrEmpty(req.getParameter("cp"))) {
            errors.put("cp", "El código postal es requerido");
        }
        if (isNullOrEmpty(req.getParameter("estado"))) {
            errors.put("estado", "El estado es requerido");
        }
        if (isNullOrEmpty(req.getParameter("fechaNacimiento"))) {
            errors.put("fechaNacimiento",
                    "La fecha de nacimiento es requerida y debe tener el formato válido (YYYY-MM-DD)");
        }
    }

    private boolean isNullOrEmpty(String param) {
        return param == null || param.isEmpty();
    }

    private LocalDate parseFechaNacimiento(HttpServletRequest req, Map<String, String> errors,
            SimpleDateFormat inputFormat, SimpleDateFormat outputFormat) {
        LocalDate fechaNacimiento = null;
        try {
            Date fechaInput = inputFormat.parse(req.getParameter("fechaNacimiento"));
            String formattedDate = outputFormat.format(fechaInput);
            fechaNacimiento = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            errors.put("fechaNacimiento", "La fecha de nacimiento es inválida");
        }
        return fechaNacimiento;
    }

    private Cliente createCliente(HttpServletRequest req, LocalDate fechaNacimiento) {
        Cliente cliente = new Cliente();
        cliente.setNombre(req.getParameter("nombre"));
        cliente.setApPaterno(req.getParameter("apPaterno"));
        cliente.setApMaterno(req.getParameter("apMaterno"));
        cliente.setTelefono(req.getParameter("telefono"));
        cliente.setCorreo(req.getParameter("correo"));
        cliente.setCalle(req.getParameter("calle"));
        cliente.setNum_interior(parseLongOrDefault(req.getParameter("numInterior"), 0L));
        cliente.setNum_exterior(parseLongOrDefault(req.getParameter("numExterior"), 0L));
        cliente.setColonia(req.getParameter("colonia"));
        cliente.setCiudad(req.getParameter("ciudad"));
        cliente.setCp(parseIntOrDefault(req.getParameter("cp"), 0));
        cliente.setEstado(EnumEstado.valueOf(req.getParameter("estado")));
        cliente.setFecha_nacimiento(fechaNacimiento);
        return cliente;
    }

    private Reservacio createReservacion(Long id_mesa, Long id_cliente, LocalDateTime fechaAReservar, String payid) {
        Reservacio reservacion = new Reservacio();
        reservacion.setId_mesa(id_mesa);
        reservacion.setId_cliente(id_cliente);
        reservacion.setFecha_a_reservar(fechaAReservar);
        reservacion.setFecha(LocalDate.now());
        reservacion.setEstatus(EnumReservacion.EN_CURSO);
        reservacion.setIdOrderPypal(payid);

        return reservacion;
    }

    private Long parseLongOrDefault(String param, Long defaultValue) {
        try {
            return Long.parseLong(param);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Integer parseIntOrDefault(String param, Integer defaultValue) {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, Map<String, String> errors, int statusCode)
            throws IOException {
        resp.setStatus(statusCode);
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            String jsonErrors = gson.toJson(errors);
            out.print(jsonErrors);
        }
    }
}