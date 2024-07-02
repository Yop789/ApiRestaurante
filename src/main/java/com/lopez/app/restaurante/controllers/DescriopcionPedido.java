package com.lopez.app.restaurante.controllers;

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
import com.lopez.app.restaurante.services.IDescripcioOrdenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/descripciones/alta")

public class DescriopcionPedido extends HttpServlet {

    public class InnerDescriopcionPedido {
        String idPlatillo;
        Integer cantidad;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        Map<String, String> errors = new HashMap<>();
        IDescripcioOrdenService<DescripcioOrden> service = new IDescripcioOrdenService<T>(conn);

        // Ayudame a optener esto
        // idOrden
        // :
        // "2"
        // platillos
        // :
        // [{idPlatillo: "2", nombrePlatillo: "PLATILLO 2 $500.0 ", cantidad: "30"},…]
        // 0
        // :
        // {idPlatillo: "2", nombrePlatillo: "PLATILLO 2 $500.0 ", cantidad: "30"}
        // 1
        // :
        // {idPlatillo: "3", nombrePlatillo: "PLATILLO 3 $5000.0 ", cantidad: "20"}

        // }

        String idOrden = req.getParameter("idOrden");
        String platillosJson = req.getParameter("platillos");

        // Validar el idOrden
        if (idOrden == null || idOrden.isEmpty()) {
            errors.put("idOrden", "El ID de la orden es obligatorio");
        }

        // Validar el JSON de platillos
        if (platillosJson == null || platillosJson.isEmpty()) {
            errors.put("platillos", "La lista de platillos es obligatoria");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errores", errors);

            return;
        }

        try {
            Gson gson = new Gson();
            List<InnerDescriopcionPedido> platillos = gson.fromJson(platillosJson,
                    new TypeToken<ArrayList<InnerDescriopcionPedido>>() {
                    }.getType());

            for (InnerDescriopcionPedido platillo : platillos) {

            }

        } catch (Exception e) {
            try (PrintWriter out = resp.getWriter()) {
                Gson gson = new Gson();
                String jsonErrors = gson.toJson(errors);
                out.print(jsonErrors);
            }
        }

    }

}
