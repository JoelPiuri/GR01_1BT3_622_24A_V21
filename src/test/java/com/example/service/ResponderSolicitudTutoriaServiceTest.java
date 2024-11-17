package com.example.service;

import com.example.model.SolicitudTutoria;
import com.example.dao.SolicitudTutoriaDAO;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ResponderSolicitudTutoriaServiceTest {

    private final int solicitudId;
    private final String accion;
    private final String estadoInicial;
    private final String estadoEsperado;
    private SolicitudTutoriaDAO solicitudTutoriaDAO;
    private ResponderSolicitudTutoriaService service;
    private SolicitudTutoria solicitud;

    public ResponderSolicitudTutoriaServiceTest(int solicitudId, String accion, String estadoInicial, String estadoEsperado) {
        this.solicitudId = solicitudId;
        this.accion = accion;
        this.estadoInicial = estadoInicial;
        this.estadoEsperado = estadoEsperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {1, "aceptar", "aceptada", "aceptada"},
                {2, "rechazar", "rechazada", "rechazada"},
                {3, "aceptar", "pendiente", "aceptada"},
                {4, "rechazar", "pendiente", "rechazada"}
        });
    }

    @Before
    public void setUp() {
        // Crear una instancia de SolicitudTutoriaDAO
        solicitudTutoriaDAO = new SolicitudTutoriaDAO() {
            @Override
            public SolicitudTutoria getById(int id) {
                // Simular obtener la solicitud según si existe o no
                return (solicitud != null && solicitud.getId() == id) ? solicitud : null;
            }

            @Override
            public void update(SolicitudTutoria solicitud) {
                // Actualizar el estado en la solicitud
                ResponderSolicitudTutoriaServiceTest.this.solicitud.setEstado(solicitud.getEstado());
            }
        };

        service = new ResponderSolicitudTutoriaService(solicitudTutoriaDAO);

        // Verificar si la solicitud ya existe o inicializarla con el estado inicial
        solicitud = solicitudTutoriaDAO.getById(solicitudId);
        if (solicitud == null) {
            solicitud = new SolicitudTutoria();
            solicitud.setId(solicitudId);
            solicitud.setEstado(estadoInicial);
        } else {
            solicitud.setEstado(estadoInicial);
        }
    }

    @Test
    public void testResponderSolicitud() {
        // Ejecutar el método bajo prueba
        service.responderSolicitud(solicitudId, accion);

        // Validar que el estado final es el esperado
        assertEquals(estadoEsperado, solicitud.getEstado());
    }

    @After
    public void tearDown() {
        // Limpiar la solicitud creada para la prueba
        solicitud = null;
    }
}
