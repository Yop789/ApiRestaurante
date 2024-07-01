package com.lopez.app.restaurante.controllers;

import com.google.gson.Gson;
import com.lopez.app.restaurante.models.Cliente;
import com.lopez.app.restaurante.services.ClienteService;
import com.lopez.app.restaurante.services.IService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/clientes/listar")
public class ListarClientesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Cliente> services = new ClienteService(conn);
        List<Cliente> clientes = services.lista();

        resp.setContentType("application/jason");
        Map<String, String> response = new HashMap<>();

        // Long id = null;
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(201);
            response.put("mensaje", clientes.toString());
            response.put("status", "success");
            String json = new Gson().toJson(response);

            out.print(json);
        }

        // for (Cliente c : clientes) {
        // resp.getWriter().println("<h1>" + c.getId() + "->"
        // + c.getNombre() + "->" + c.getApPaterno() + "</h1>");
        // }

    }

}
