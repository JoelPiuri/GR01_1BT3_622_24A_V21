package com.example.service;

import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class CambiarEstadoCuentaServiceTest {

    private CambiarEstadoCuentaService cambiarEstadoCuentaService;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @BeforeEach
    void setUp() {
        cambiarEstadoCuentaService = new CambiarEstadoCuentaService();
        // Crear un tutor de prueba
        Tutor tutor = tutorDAO.findByEmail("tutor_test@example.com");
        if (tutor == null) {
            tutor = new Tutor();
            tutor.setNombre("Tutor Test");
            tutor.setApellido("Apellido Test");
            tutor.setEmail("tutor_test@example.com");
            tutor.setEstadoCuenta("activo");
            tutor.setRolID("1");
            tutorDAO.registrarTutor(tutor);
        }

        // Crear un usuario de prueba
        Usuario usuario = usuarioDAO.findByNombreUsuario("usuario_test");
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setNombreUsuario("usuario_test");
            usuario.setContrasena("test_password");
            usuario.setRolId(2);
            usuarioDAO.saveUsuario(usuario);
        }
    }

    @AfterEach
    void tearDown() {
        // Eliminar el tutor creado
        Tutor tutor = tutorDAO.findByEmail("tutor_test@example.com");
        if (tutor != null) {
            tutorDAO.eliminarTutor(String.valueOf(tutor.getId()));
        }

        // Eliminar el usuario creado
        Usuario usuario = usuarioDAO.findByNombreUsuario("usuario_test");
        if (usuario != null) {
            usuarioDAO.deleteUsuario(usuario.getId());
        }

        // Eliminar archivo PDF
        File pdfFile = new File("resources/pdfTutores/usuario_test_certificado.pdf");
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    @Test
    void testBanearTutor() {
        // Asegurar que el tutor de prueba existe
        Tutor tutor = tutorDAO.findByEmail("tutor_test@example.com");
        assertNotNull(tutor, "El tutor debería existir antes de la prueba");

        String userId = String.valueOf(tutor.getId());
        String usuarioId = tutor.getRolID(); // Usar el ID del rol asignado
        String accion = "banear";
        String typeUser = "tutor";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, usuarioId, accion, typeUser);

        Tutor tutorActualizado = tutorDAO.obtenerTutorPorId(userId);
        assertNotNull(tutorActualizado, "El tutor debería seguir existiendo en la base de datos");
        assertEquals("baneado", tutorActualizado.getEstadoCuenta(), "El estado del tutor debería cambiar a 'baneado'");
    }

    @Test
    void testEliminarAlumno() {
        String userId = "2"; // ID ficticio de prueba
        String usuarioId = "20";
        String accion = "eliminar";
        String typeUser = "alumno";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, usuarioId, accion, typeUser);

        Alumno alumno = alumnoDAO.obtenerAlumnoPorId(userId);
        assertNull(alumno, "El alumno debería haber sido eliminado de la base de datos");
    }

    @Test
    void testBanearUsuarioInexistente() {
        String userId = "9999";
        String usuarioId = "9999";
        String accion = "banear";
        String typeUser = "alumno";

        cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, usuarioId, accion, typeUser);

        Alumno alumno = alumnoDAO.obtenerAlumnoPorId(userId);
        assertNull(alumno, "El alumno no debería existir en la base de datos");
    }

    @Test
    void testAccionNoValida() {
        String userId = "3";
        String usuarioId = "30";
        String accion = "invalid";
        String typeUser = "alumno";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, usuarioId, accion, typeUser);
        });

        assertEquals("Acción no válida: invalid", exception.getMessage());
    }

    @Test
    public void testBanearAdministrador() {
        String email = "testemail@epn.edu.ec";
        String accionBanear = "banear";
        String accionEliminar = "eliminar";
        String typeUser = "admin";

        Administrador existingAdmin = administradorDAO.findByEmail(email);
        if (existingAdmin != null) {
            cambiarEstadoCuentaService.cambiarEstadoCuenta(
                    String.valueOf(existingAdmin.getId()),
                    existingAdmin.getRolID(),
                    accionEliminar,
                    typeUser
            );
        }

        Administrador admin = new Administrador();
        admin.setEmail(email);
        admin.setNombre("Admin Prueba");
        admin.setApellido("Apellido Admin");
        admin.setEstadoCuenta("activo");
        admin.setRolID("3");
        administradorDAO.registrarAdmin(admin);

        cambiarEstadoCuentaService.cambiarEstadoCuenta(
                String.valueOf(admin.getId()),
                admin.getRolID(),
                accionBanear,
                typeUser
        );

        admin = administradorDAO.findByEmail(email);
        assertNotNull(admin, "El administrador debería existir en la base de datos");
        assertEquals("baneado", admin.getEstadoCuenta());

        cambiarEstadoCuentaService.cambiarEstadoCuenta(
                String.valueOf(admin.getId()),
                admin.getRolID(),
                accionEliminar,
                typeUser
        );
        admin = administradorDAO.findByEmail(email);
        assertNull(admin, "El administrador debería haber sido eliminado de la base de datos");
    }
}
