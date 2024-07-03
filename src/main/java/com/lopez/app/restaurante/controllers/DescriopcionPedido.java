package com.lopez.app.restaurante.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lopez.app.restaurante.models.DescripcioOrden;
import com.lopez.app.restaurante.models.InnerDescriopcionPedido;
import com.lopez.app.restaurante.models.Enum.EnumEstatusDetalleOrder;
import com.lopez.app.restaurante.services.DescripcioService;
import com.lopez.app.restaurante.services.IDescripcioOrdenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/descripciones/alta")

public class DescriopcionPedido extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        Map<String, String> errors = new HashMap<>();
        IDescripcioOrdenService<DescripcioOrden> service = new DescripcioService(conn);

        Gson gson = new Gson();

        // Leer el cuerpo de la solicitud
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        System.out.println("JSON recibido: " + json);

        // Convertir el JSON a un mapa
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());

        String idOrden = (String) map.get("idOrden");
        String platillosJson = gson.toJson(map.get("platillos"));

        // Validar el idOrden
        if (idOrden == null || idOrden.isEmpty()) {
            errors.put("idOrden", "El ID de la orden es obligatorio");
        }

        // Validar el JSON de platillos
        if (platillosJson == null || platillosJson.isEmpty()) {
            errors.put("platillos", "La lista de platillos es obligatoria");
        }

        if (!errors.isEmpty()) {
            sendErrorResponse(resp, errors);
            return;
        }

        try {
            List<InnerDescriopcionPedido> platillos = gson.fromJson(platillosJson,
                    new TypeToken<ArrayList<InnerDescriopcionPedido>>() {
                    }.getType());
            if (platillos.isEmpty()) {
                errors.put("platillos", "La lista de platillos no puede estar vacía");
                sendErrorResponse(resp, errors);
                return;
            }

            for (InnerDescriopcionPedido platillo : platillos) {
                // Mostrar en consola cada platillo procesado
                System.out.println(
                        "Procesando platillo: " + platillo.getIdPlatillo() + ", Cantidad: " + platillo.getCantidad());

                DescripcioOrden descripcio = new DescripcioOrden();
                descripcio.setId_orden(Long.parseLong(idOrden));
                descripcio.setId_platillo(Long.parseLong(platillo.getIdPlatillo()));
                descripcio.setCantidad(platillo.getCantidad());
                descripcio.setEstatus(EnumEstatusDetalleOrder.EN_CURSO);
                service.guardar(descripcio);
            }

        } catch (Exception e) {
            errors.put("platillos", e.getMessage());
            sendErrorResponse(resp, errors);
        }
    }

    private void sendErrorResponse(HttpServletResponse resp, Map<String, String> errors) throws IOException {
        Gson gson = new Gson();
        String jsonErrors = gson.toJson(errors);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonErrors);
        }
    }

}
