package com.example.servlet;

import com.example.service.TutorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ActualizarEstadoYMateriasServlet")
public class ActualizarEstadoYMateriasServlet extends HttpServlet {

    private final TutorService tutorService = new TutorService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tutorId = Integer.parseInt(request.getParameter("tutorId"));
        String[] materiasSeleccionadas = request.getParameterValues("materiasSeleccionadas");

        try {
            // Llamar al servicio para aprobar el tutor y asignar materias
            tutorService.aprobarTutorYAsignarMaterias(tutorId, materiasSeleccionadas);

            // Redireccionar a una página de éxito o confirmar acción
            response.sendRedirect(request.getContextPath() + "/SolicitudeTutoreServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
