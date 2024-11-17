package com.example.service;

import com.example.dao.PreguntasEncuestaDAO;
import com.example.model.Materia;
import com.example.model.PreguntasEncuesta;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GetALLPreguntasTest {

    private PreguntasEncuestaDAO preguntasEncuestaDAO;
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        preguntasEncuestaDAO = new PreguntasEncuestaDAO();
        createTestData();
    }

    private void createTestData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Recuperar una Materia existente
        Materia materia = session.get(Materia.class, 1); // Asumiendo que el codigomateria es 1

        if (materia != null) {
            // Crea Preguntas de Encuesta
            PreguntasEncuesta pregunta1 = new PreguntasEncuesta();
            pregunta1.setPregunta("¿Qué te parece la clase?");
            pregunta1.setMateria(materia);
            session.save(pregunta1);

            PreguntasEncuesta pregunta2 = new PreguntasEncuesta();
            pregunta2.setPregunta("¿Cómo calificarías al profesor?");
            pregunta2.setMateria(materia);
            session.save(pregunta2);
        }

        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() {
        clearTestData();
    }

    private void clearTestData() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Eliminar todas las preguntas de encuesta creadas en esta prueba
        session.createQuery("DELETE FROM PreguntasEncuesta WHERE pregunta IN (:preguntas)")
                .setParameterList("preguntas", Arrays.asList("¿Qué te parece la clase?", "¿Cómo calificarías al profesor?"))
                .executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGetAllPreguntas() {
        List<PreguntasEncuesta> preguntas = preguntasEncuestaDAO.getAllPreguntas();

        // Verifica que se hayan guardado las preguntas
        Assert.assertNotNull(preguntas);
        Assert.assertEquals(2, preguntas.size()); // Debería ser 2 porque hemos creado 2 preguntas
    }
}
