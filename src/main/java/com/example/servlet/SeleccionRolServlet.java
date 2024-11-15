package com.example.servlet;

import com.example.service.SeleccionRolService;
import com.example.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/SeleccionRolServlet")
public class SeleccionRolServlet extends HttpServlet {

    private SeleccionRolService seleccionRolService;

    public void init() {
        seleccionRolService = new SeleccionRolService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");

        // Validar las credenciales y obtener el usuario
        Usuario usuario = seleccionRolService.validarCredenciales(nombreUsuario, contrasena);

        if (usuario != null) {
            // Obtener el estado de la cuenta desde la tabla correspondiente
            String estadoCuenta = seleccionRolService.obtenerEstadoCuenta(usuario.getRolId(), usuario.getReferenciaId());

            // Verificar si el estado de la cuenta es "pendiente revisión"
            if ("Pendiente revision".equalsIgnoreCase(estadoCuenta)) {
                // Redirigir al login con un mensaje de estado
                request.setAttribute("errorMessage", "Su cuenta está en estado 'pendiente de revisión'.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("userId", usuario.getId());
            session.setAttribute("userRole", usuario.getRolId());
            session.setAttribute("userReferenceId", usuario.getReferenciaId());

            // Obtener el nombre y apellido basados en rolId y referenciaId
            String[] nombreApellido = seleccionRolService.obtenerNombreYApellido(usuario.getRolId(), usuario.getReferenciaId());
            session.setAttribute("nombre", nombreApellido[0]);
            session.setAttribute("apellido", nombreApellido[1]);

            // Verificar si el usuario es un estudiante y guardar su ID de estudiante
            if (usuario.getRolId() == 1) { // Asumiendo que 1 corresponde a Estudiante
                session.setAttribute("alumnoId", usuario.getReferenciaId()); // Guardar `referenciaId` como `alumnoId`
            }

            // Convertir el rol numérico a un texto representativo
            String rolTexto = "";
            switch (usuario.getRolId()) {
                case 3:
                    rolTexto = "Administrador";
                    break;
                case 1:
                    rolTexto = "Estudiante";
                    break;
                case 2:
                    rolTexto = "Tutor";
                    break;
                default:
                    rolTexto = "Usuario";
            }
            session.setAttribute("rolUsuario", rolTexto);

            // Redirigir a la página correspondiente
            String redireccion = seleccionRolService.obtenerRedireccionPorRol(usuario.getRolId());
            response.sendRedirect(redireccion);
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Usuario no existe");

        }
    }
}
