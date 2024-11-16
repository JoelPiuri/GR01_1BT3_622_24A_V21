package com.example.service.dao;

import com.example.dao.TutorDAO;
import com.example.model.Tutor;
import com.example.service.CambiarEstadoCuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertNull;

public class TutorDAOTestParameters {

    private CambiarEstadoCuentaService cambiarEstadoCuentaService;
    private TutorDAO tutorDAO = new TutorDAO();

    @BeforeEach
    void setUp() {
        // Inicializa el servicio de cambio de estado de cuenta antes de cada test
        cambiarEstadoCuentaService = new CambiarEstadoCuentaService();
    }

    @Test
    void testEliminarTutor() {
        // Configuración inicial para la prueba
        String userId = "2";        // ID del usuario tutor
        String action = "eliminar"; // Acción a ejecutar
        String typeUser = "tutor";  // Tipo de usuario

        // Cambia el estado de cuenta del tutor a "eliminado" (descomentar para activar)
        // cambiarEstadoCuentaService.cambiarEstadoCuenta(userId, action, typeUser);

        // Verifica que el tutor fue eliminado consultando por su ID
        Tutor tutor = tutorDAO.obtenerTutorPorId(userId);

        // Asegura que el tutor ya no exista en la base de datos
        assertNull("El tutor debería haber sido eliminado", tutor);
    }
}
