package com.example.service;

import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.dao.AdministratorDAO;
import com.example.dao.UsuarioDAO;

public class CambiarEstadoCuentaService {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final TutorDAO tutorDAO = new TutorDAO();
    private final AdministratorDAO administradorDAO = new AdministratorDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public CambiarEstadoCuentaService() {}

    public void cambiarEstadoCuenta(String userId, String usuarioId, String accion, String typeUser) {
        int parsedUsuarioId = Integer.parseInt(usuarioId);

        if ("banear".equals(accion)) {
            banearCuenta(userId, typeUser);
        } else if ("eliminar".equals(accion)) {
            eliminarCuenta(userId, usuarioId, typeUser);
        } else {
            throw new IllegalArgumentException("Acción no válida: " + accion);
        }
    }

    private void banearCuenta(String userId, String typeUser) {
        switch (typeUser) {
            case "alumno":
                alumnoDAO.cambiarEstado(userId, "baneado");
                break;
            case "tutor":
                tutorDAO.cambiarEstado(userId, "baneado");
                break;
            case "admin":
                administradorDAO.cambiarEstado(userId, "baneado");
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + typeUser);
        }
    }

    public void eliminarCuenta(String userId, String usuarioId, String typeUser) {
        int userIntId = Integer.parseInt(userId);
        int usuarioIntId = Integer.parseInt(usuarioId);

        // Primero, desasociar el usuario de las entidades relacionadas
        switch (typeUser) {
            case "alumno":
                alumnoDAO.desasociarUsuario(usuarioIntId); // Eliminar la referencia en la tabla Alumno
                alumnoDAO.eliminarAlumno(userId); // Eliminar el Alumno después de desasociar
                break;
            case "tutor":
                tutorDAO.desasociarUsuario(usuarioIntId); // Eliminar la referencia en la tabla Tutor
                tutorDAO.eliminarTutor(userId); // Eliminar el Tutor después de desasociar
                break;
            case "admin":
                administradorDAO.desasociarUsuario(usuarioIntId); // Eliminar la referencia en la tabla Administrador
                administradorDAO.eliminarAdmin(userId); // Eliminar el Admin después de desasociar
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + typeUser);
        }

        // Finalmente, eliminar el usuario después de todas las desasociaciones
        usuarioDAO.deleteUsuario(usuarioIntId);
    }

}
