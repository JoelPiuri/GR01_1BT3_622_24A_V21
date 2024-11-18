package com.example.service;

import com.example.dao.UsuarioDAO;
import com.example.model.Usuario;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UsuarioDAOTestParam {

    private UsuarioDAO usuarioDAO;
    private Session session;
    private Transaction transaction;

    // Variables para pruebas parametrizadas
    private int usuarioId;
    private String nombreUsuario;

    // Constructor para las pruebas parametrizadas
    public UsuarioDAOTestParam(int usuarioId, String nombreUsuario) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, "usuario1_unique"},
                {2, "usuario2_unique"}
        });
    }

    @Before
    public void setUp() {
        usuarioDAO = new UsuarioDAO();

        // Iniciar sesión y transacción manualmente
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Limpiar cualquier usuario existente con el mismo nombreUsuario
        Usuario usuarioExistente = session.createQuery(
                        "FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        if (usuarioExistente != null) {
            session.delete(usuarioExistente);
            transaction.commit();
            transaction = session.beginTransaction();
        }

        // Registrar un usuario en la base de datos para la prueba
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena("password123"); // Asignar un valor a la propiedad obligatoria
        session.save(usuario);
        transaction.commit();
    }

    @Test
    public void testSaveUsuario() {
        transaction = session.beginTransaction();
        Usuario usuario = session.createQuery(
                        "FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        transaction.commit();

        assertNotNull("El usuario debería haberse guardado correctamente", usuario);
        assertEquals("El nombre de usuario debería coincidir", nombreUsuario, usuario.getNombreUsuario());
    }

    @Test
    public void testDeleteUsuario() {
        transaction = session.beginTransaction();
        Usuario usuario = session.createQuery(
                        "FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        if (usuario != null) {
            session.delete(usuario);
        }
        transaction.commit();

        transaction = session.beginTransaction();
        Usuario usuarioEliminado = session.createQuery(
                        "FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        transaction.commit();

        assertNull("El usuario debería haberse eliminado correctamente", usuarioEliminado);
    }

    @After
    public void tearDown() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
