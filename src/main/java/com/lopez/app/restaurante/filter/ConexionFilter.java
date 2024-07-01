package com.lopez.app.restaurante.filter;

import com.lopez.app.restaurante.utils.ConexionDB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.sql.Connection;

@WebFilter("/*")
public class ConexionFilter implements Filter {

    private Connection getConnection() {
        return ConexionDB.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Connection conn = this.getConnection();
        request.setAttribute("conn", conn);

        try {
            chain.doFilter(request, response);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
