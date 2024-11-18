package com.example.dao;

import com.example.model.Tutoria;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query; // Importar la clase Query de Hibernate

import java.util.List;

public class TutoriaDAO {

    private final SessionFactory sessionFactory;

    public TutoriaDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }
    // Método para encontrar una Tutoria por su ID
    public Tutoria findById(int id) {
        Transaction transaction = null;
        Tutoria tutoria = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tutoria = session.get(Tutoria.class, id); // Obtener la tutoría por su ID
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return tutoria;
    }
    public double obtenerPromedioEncuestas(int tutoriaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener el promedio de calificaciones de las respuestas de la tutoría
            String hql = "SELECT AVG(r.calificacion) " +
                    "FROM RespuestaEncuesta r " +
                    "JOIN r.solicitud s " +
                    "WHERE s.tutoria.id = :tutoriaId";
            Double promedio = session.createQuery(hql, Double.class)
                    .setParameter("tutoriaId", tutoriaId)
                    .uniqueResult();
            return promedio != null ? promedio : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public List<Tutoria> buscarTutoriasDisponiblesConFiltros(int alumnoId, Integer materiaId, String ordenFecha) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Tutoria t WHERE NOT EXISTS " +
                    "(SELECT 1 FROM Solicitud s WHERE s.tutoria.id = t.id AND s.alumno.id = :alumnoId)";

            if (materiaId != null) {
                hql += " AND t.materia.id = :materiaId";
            }

            if (ordenFecha != null && ordenFecha.equals("desc")) {
                hql += " ORDER BY t.fecha DESC";
            } else {
                hql += " ORDER BY t.fecha ASC";
            }

            Query<Tutoria> query = session.createQuery(hql, Tutoria.class);
            query.setParameter("alumnoId", alumnoId);

            if (materiaId != null) {
                query.setParameter("materiaId", materiaId);
            }

            return query.getResultList();
        }
    }


    // Método para obtener tutorías no solicitadas por el alumno
    public List<Tutoria> buscarTutoriasDisponibles(int alumnoId) {
        try (Session session = sessionFactory.openSession()) {
            // Modificamos la consulta para usar "EXISTS" en lugar de "NOT IN"
            Query<Tutoria> query = session.createQuery(
                    "FROM Tutoria t WHERE NOT EXISTS " +
                            "(SELECT 1 FROM Solicitud s WHERE s.tutoria.id = t.id AND s.alumno.id = :alumnoId)",
                    Tutoria.class);
            query.setParameter("alumnoId", alumnoId);
            return query.getResultList();
        }
    }

    // Obtener todas las tutorías
    public List<Tutoria> getAllTutorias() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Tutoria", Tutoria.class).list();
        }
    }

    // Guardar o actualizar una tutoría
    public void reguistarTutoria(Tutoria tutoria) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(tutoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    // Obtener una tutoría por su ID
    public Tutoria getTutoriaById(int tutoriaId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tutoria.class, tutoriaId);
        }
    }

    // Obtener las tutorías por el ID del tutor
    public List<Tutoria> listarSolicitudesPendientes(int tutorId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tutoria t WHERE t.tutor.id = :tutorId", Tutoria.class)
                    .setParameter("tutorId", tutorId)
                    .list();
        }
    }


}
