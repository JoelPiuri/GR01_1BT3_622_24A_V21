package com.example.service;

import com.example.dao.SolicitudDAO;
import com.example.dao.TutoriaDAO;
import com.example.model.Solicitud;
import com.example.model.Tutoria;
import com.example.model.Tutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.mockito.Mockito.*;

public class AceptarTutoriaServiceTest {

    private SolicitudDAO solicitudDAO;
    private TutoriaDAO tutoriaDAO;
    private AceptarTutoriaService aceptarTutoriaService;
    private final int tutoriaId = 4;
    private final int userId = 1;

    @BeforeEach
    public void setUp() {
        solicitudDAO = mock(SolicitudDAO.class);
        tutoriaDAO = mock(TutoriaDAO.class);
        aceptarTutoriaService = new AceptarTutoriaService(solicitudDAO, tutoriaDAO);

        // Configurar el mock para simular que la tutoría y el tutor existen
        Tutoria tutoriaMock = new Tutoria();  // Crear una instancia simulada de Tutoria
        Tutor tutorMock = new Tutor(); // Crear una instancia simulada de Tutor
        tutorMock.setId(1); // Asignar un ID al tutor simulado
        tutoriaMock.setTutor(tutorMock); // Asignar el tutor simulado a la tutoría

        when(tutoriaDAO.findById(tutoriaId)).thenReturn(tutoriaMock); // Simular que la tutoría existe al consultarla

        // Configurar el mock para simular que la solicitud existe y puede actualizarse
        Solicitud solicitudMock = new Solicitud();  // Crear una instancia simulada de Solicitud
        when(solicitudDAO.findById(tutoriaId)).thenReturn(solicitudMock); // Simular que la solicitud existe
    }

    @AfterEach
    public void tearDown() {
        // Resetear los mocks después de cada prueba
        reset(solicitudDAO);
        reset(tutoriaDAO);
    }

    @Test
    public void testAceptarTutoria() throws Exception {
        // Ejecutar el método a probar
        aceptarTutoriaService.aceptarTutoria(tutoriaId, userId);

        // Verificar que se llamó a `solicitarTutoria`
        verify(solicitudDAO).solicitarTutoria(any(Solicitud.class));
    }

}
