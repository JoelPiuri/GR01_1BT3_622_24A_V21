package com.example.dao;

import com.example.model.Alumno;
import com.example.model.Tutoria;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class AlumnoDAO {

    private final SessionFactory sessionFactory;

    public AlumnoDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }

    public void registrarAlumno(Alumno alumno) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(alumno);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String obtenerEstadoCuenta(int alumnoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT a.estadoCuenta FROM Alumno a WHERE a.id = :alumnoId";
            return session.createQuery(hql, String.class)
                    .setParameter("alumnoId", alumnoId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Alumno findByEmail(String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Alumno WHERE email = :email", Alumno.class)
                    .setParameter("email", email)
                    .uniqueResult(); // Devuelve un alumno si lo encuentra, sino null
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
    public List<Alumno> getAllAlumnos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Alumno a WHERE a.usuario IS NOT NULL";
            return session.createQuery(hql, Alumno.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    // Cambiar el estado del alumno (por ejemplo, a "baneado")
    public void cambiarEstado(String alumnoId, String nuevoEstado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Alumno alumno = session.get(Alumno.class, Integer.parseInt(alumnoId));
        if (alumno != null) {
            alumno.setEstadoCuenta(nuevoEstado);
            session.update(alumno);
        }

        session.getTransaction().commit();
        session.close();
    }
    // Eliminar un alumno por su ID
    public void eliminarAlumno(String alumnoId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Alumno alumno = session.get(Alumno.class, Integer.parseInt(alumnoId));
        if (alumno != null) {
            session.delete(alumno);
        }

        session.getTransaction().commit();
        session.close();
    }
    // Método para obtener un Alumno por su ID
    public Alumno obtenerAlumnoPorId(String alumnoId) {
        Session session = null;
        Alumno alumno = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            // Obtener el alumno por su ID
            alumno = session.get(Alumno.class, Integer.parseInt(alumnoId));

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return alumno;
    }
    public List<Alumno> getAllAlumnosWithUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Alumno a JOIN FETCH a.usuario WHERE a.usuario IS NOT NULL";
            return session.createQuery(hql, Alumno.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public Alumno findById(int id) {
        Transaction transaction = null;
        Alumno alumno = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            alumno = session.get(Alumno.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return alumno;
    }
    public void desasociarUsuario(int usuarioId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "update Alumno set usuario = null where usuario.id = :usuarioId";
            Query query = session.createQuery(hql);
            query.setParameter("usuarioId", usuarioId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



}
