package com.example.service;

import com.example.dao.UsuarioDAO;
import com.example.model.Usuario;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;
    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;

    private String nombreUsuario;
    private String contrasena;
    private int rolId;
    private int referenciaId;
    private int usuarioId;
    private boolean expectedExistence;

    // Constructor para pruebas parametrizadas
    public UsuarioDAOTest(String nombreUsuario, String contrasena, int rolId, int referenciaId, int usuarioId, boolean expectedExistence) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rolId = rolId;
        this.referenciaId = referenciaId;
        this.usuarioId = usuarioId;
        this.expectedExistence = expectedExistence;
    }

    // Parámetros para pruebas parametrizadas
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"testUser1", "password1", 1, 100, 1, true},
                {"testUser2", "password2", 2, 101, 2, false}
        });
    }

    @Before
    public void setUp() {
        usuarioDAO = new UsuarioDAO();

        // Mock de la sesión y transacción de Hibernate
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        sessionFactory = mock(SessionFactory.class);

        // Configuración de HibernateUtil para usar la sessionFactory mock
        when(sessionFactory.openSession()).thenReturn(session);
        HibernateUtil.setSessionFactory(sessionFactory);
    }

    // Pruebas con Mocks

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");
        usuario.setContrasena("password");
        usuario.setRolId(1);
        usuario.setReferenciaId(100);

        when(session.beginTransaction()).thenReturn(transaction);
        usuarioDAO.saveUsuario(usuario);

        // Verificar que se guarda el usuario y se confirma la transacción
        verify(session).save(usuario);
        verify(transaction).commit();
    }

    @Test
    public void testExisteUsuario() {
        Usuario usuario = expectedExistence ? new Usuario() : null;
        when(session.get(Usuario.class, usuarioId)).thenReturn(usuario);

        boolean result = usuarioDAO.existeUsuario(usuarioId);

        assertEquals("La existencia del usuario debería coincidir con la expectativa", expectedExistence, result);
        verify(session).get(Usuario.class, usuarioId);
    }

    // Pruebas Parametrizadas

    @Test
    public void testSaveUsuarioParametrized() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena);
        usuario.setRolId(rolId);
        usuario.setReferenciaId(referenciaId);

        when(session.beginTransaction()).thenReturn(transaction);

        usuarioDAO.saveUsuario(usuario);

        verify(session).save(usuario);
        verify(transaction).commit();
    }

    // Prueba parametrizada para verificar la existencia de un usuario
    @Test
    public void testExisteUsuarioParametrized() {
        Usuario usuario = expectedExistence ? new Usuario() : null;
        when(session.get(Usuario.class, usuarioId)).thenReturn(usuario);

        boolean result = usuarioDAO.existeUsuario(usuarioId);

        assertEquals("La existencia del usuario debería coincidir con la expectativa", expectedExistence, result);
        verify(session).get(Usuario.class, usuarioId);
    }
}
