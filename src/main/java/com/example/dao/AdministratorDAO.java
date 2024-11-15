package com.example.dao;

import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class AdministratorDAO {

    private final SessionFactory sessionFactory;

    public AdministratorDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory(); // Inicializa sessionFactory en el constructor
    }
    public void desasociarUsuario(int usuarioId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "update Administrador set usuario = null where usuario.id = :usuarioId";
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
    public String obtenerEstadoCuenta(int adminId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT a.estadoCuenta FROM Administrador a WHERE a.id = :adminId";
            return session.createQuery(hql, String.class)
                    .setParameter("adminId", adminId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public void registrarAdmin(Administrador admin) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Administrador findByEmail(String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Administrador WHERE email = :email", Administrador.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<Administrador> getAllAdministradores() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Administrador", Administrador.class).list();
        }
    }

    public void cambiarEstado(String adminId, String nuevoEstado) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Administrador admin = session.get(Administrador.class, Integer.parseInt(adminId));
        if (admin != null) {
            admin.setEstadoCuenta(nuevoEstado);
            session.update(admin);
        }

        session.getTransaction().commit();
        session.close();
    }


    public void eliminarAdmin(String adminId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Administrador admin = session.get(Administrador.class, Integer.parseInt(adminId));
        if (admin != null) {
            session.delete(admin);
        }

        session.getTransaction().commit();
        session.close();
    }
    public List<Administrador> getAllAdministradoresWithUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Administrador a JOIN FETCH a.usuario", Administrador.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Administrador findById(int id) {
        Transaction transaction = null;
        Administrador administrador = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            administrador = session.get(Administrador.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return administrador;
    }


}
