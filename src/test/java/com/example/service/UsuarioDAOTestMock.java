package com.example.service;

import com.example.dao.UsuarioDAO;
import com.example.model.Usuario;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class UsuarioDAOTestMock {

    private UsuarioDAO usuarioDAO;
    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;

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
        int usuarioId = 1;
        Usuario usuario = new Usuario();
        when(session.get(Usuario.class, usuarioId)).thenReturn(usuario);

        boolean result = usuarioDAO.existeUsuario(usuarioId);

        verify(session).get(Usuario.class, usuarioId);
        assert result;
    }
}
