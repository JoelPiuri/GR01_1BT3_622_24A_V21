package com.example.service;

import com.example.model.*;
import com.example.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

public class RegistroSistemaServiceTest {

    private RegistroSistemaService registroSistemaService;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final String[] testEmails = {
            "kerlly.vizuete@epn.edu.ec",
            "ale.vizuete@epn.edu.ec"
    };
    private final String[] testUsernames = {
            "kerllyvizuete",
            "kerllyadmin"
    };

    @BeforeEach
    void setUp() {
        registroSistemaService = new RegistroSistemaService();
    }

    @AfterEach
    void tearDown() {
        // Eliminar usuarios y entidades asociadas creadas durante los tests
        for (String email : testEmails) {
            Alumno alumno = alumnoDAO.findByEmail(email);
            if (alumno != null) {
                alumnoDAO.eliminarAlumno(String.valueOf(alumno.getId()));
            }

            Administrador administrador = administradorDAO.findByEmail(email);
            if (administrador != null) {
                administradorDAO.eliminarAdmin(String.valueOf(administrador.getId()));
            }
        }

        for (String username : testUsernames) {
            Usuario usuario = usuarioDAO.findByNombreUsuario(username);
            if (usuario != null) {
                usuarioDAO.deleteUsuario(usuario.getId());
            }
        }
    }

    @Test
    void testRegistrarAlumno() throws Exception {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "kerlly.vizuete@epn.edu.ec";
        String rolId = "1";
        String nombreUsuario = "kerllyvizuete";
        String contrasena = "password123";
        String[] materiasSeleccionadas = new String[0];

        // Intento de registro del alumno
        registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);

        Alumno alumnoTest = alumnoDAO.findByEmail(correo);
        assertNotNull(alumnoTest, "El alumno debería estar registrado.");
        assertEquals(correo, alumnoTest.getEmail(), "El correo del alumno debería coincidir.");

        // Probar que no se permite el registro duplicado por correo
        Exception exception = assertThrows(Exception.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);
        });
        assertEquals("El correo ya está registrado: " + correo, exception.getMessage());
    }

    @Test
    void testRolInvalido() throws Exception {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "ale.vizuete@epn.edu.ec";
        String rolId = "4";
        String nombreUsuario = "alevizuete";
        String contrasena = "password123";
        String[] materiasSeleccionadas = new String[0];

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);
        });
        assertEquals("Rol no válido", exception.getMessage());
    }

    @Test
    void testRegistrarAdministrador() throws Exception {
        String nombre = "Kerlly";
        String apellido = "Vizuete";
        String correo = "ale.vizuete@epn.edu.ec";
        String rolId = "3";
        String nombreUsuario = "kerllyadmin";
        String contrasena = "admin123";
        String[] materiasSeleccionadas = new String[0];

        // Intento de registro del administrador
        registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);

        Administrador administradorTest = administradorDAO.findByEmail(correo);
        assertNotNull(administradorTest, "El administrador debería estar registrado.");
        assertEquals(correo, administradorTest.getEmail(), "El correo del administrador debería coincidir.");

        // Probar que no se permite el registro duplicado por correo
        Exception exception = assertThrows(Exception.class, () -> {
            registroSistemaService.registrarUsuario(nombre, apellido, correo, rolId, nombreUsuario, contrasena, materiasSeleccionadas);
        });
        assertEquals("El correo ya está registrado: " + correo, exception.getMessage());
    }
}
