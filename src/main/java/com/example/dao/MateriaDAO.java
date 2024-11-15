package com.example.dao;

import com.example.model.Materia;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.transaction.Transactional;
import java.util.List;

public class MateriaDAO {

    // Usamos el SessionFactory de HibernateUtil
    private final SessionFactory factory;

    public MateriaDAO() {
        this.factory = HibernateUtil.getSessionFactory();
    }
    public List<Materia> obtenerMateriasPorCodigo(List<String> codigos) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Materia m WHERE m.codigo IN :codigos";
            return session.createQuery(hql, Materia.class)
                    .setParameter("codigos", codigos)
                    .getResultList();
        }
    }



    // Método para obtener todas las materias
    public List<Materia> getAllMaterias(){

        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();

            // Retornar directamente el resultado de la consulta
            List<Materia> result = consultarMaterias(session);
            session.getTransaction().commit();
            return result;
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
        return null; // Retorna null en caso de que ocurra una excepción
    }


    // Método para consultar Materias
    private static List<Materia> consultarMaterias(Session session) {
        return session.createQuery("from Materia", Materia.class).getResultList(); // Corregido para retornar la lista de resultados
    }

    public Materia findById(int id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            Materia materia = session.get(Materia.class, id);

            // Forzar la inicialización de la colección de tutores
            if (materia != null) {
                materia.getTutores().size(); // Acceder a la colección para inicializarla
            }

            session.getTransaction().commit();
            return materia;
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
        return null;
    }

    // Método para obtener todas las materias de un tutor
    public List<Materia> getMateriasByTutorId(int tutorId) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();

            // Consulta para obtener las materias impartidas por el tutor
            List<Materia> materias = session.createQuery("SELECT m FROM Materia m JOIN m.tutores t WHERE t.id = :tutorId", Materia.class)
                    .setParameter("tutorId", tutorId)
                    .getResultList();

            session.getTransaction().commit();
            return materias;
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
        return null;
    }


    // Método para eliminar una materia por su código
    public void deleteMateria(int codigomateria) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            Materia materia = session.get(Materia.class, codigomateria);
            if (materia != null) {
                session.delete(materia);
            }
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
    }

    // Método para obtener una materia por su código
    public Materia getMateria(int codigomateria) {
        Session session = null;
        Materia materia = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            materia = session.get(Materia.class, codigomateria);
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
        return materia;
    }

}
