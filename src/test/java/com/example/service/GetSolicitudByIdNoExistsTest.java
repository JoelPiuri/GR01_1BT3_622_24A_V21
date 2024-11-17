package com.example.service;

import com.example.dao.SolicitudDAO;
import com.example.model.Solicitud;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetSolicitudByIdNoExistsTest {

    private SolicitudDAO solicitudDAO;
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        solicitudDAO = new SolicitudDAO();
        createTestData(); // Crea datos de prueba antes de cada prueba
    }

    private void createTestData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Crear una solicitud para la prueba
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1);  // Asegúrate de que este ID no colida con otros existentes
        solicitud.setEstado("Estado de prueba"); // Establecer solo el estado
        session.save(solicitud);

        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() {
        clearTestData();  // Limpia después de cada prueba
    }

    private void clearTestData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Eliminar todas las solicitudes creadas en las pruebas
        session.createQuery("DELETE FROM Solicitud").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGetSolicitudByIdNotExists() {
        Solicitud solicitud = solicitudDAO.getSolicitudById(999); // Asumimos que este ID no existe

        // Verifica que la solicitud devuelta sea nula
        Assert.assertNull(solicitud);
    }
}
