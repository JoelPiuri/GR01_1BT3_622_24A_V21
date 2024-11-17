package com.example.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistroUsuarioServiceTest {

    private RegistroUsuarioService registroUsuarioService;

    @BeforeEach
    public void setup() {
        registroUsuarioService = new RegistroUsuarioService();
    }

    @Test
    public void testRegistrarUsuario_NombreVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El nombre es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_ApellidoVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El apellido es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_CorreoVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El correo es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_RolVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El rol es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_NombreUsuarioVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "", "password123", null);
        });
        Assertions.assertEquals("El nombre de usuario es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_ContrasenaVacia() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "", null);
        });
        Assertions.assertEquals("La contraseña es obligatoria.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_CorreoExistenteAlumno() {
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Juan", "Proanio", "juan.proanio@epn.edu.ec",
                    "1", "Juan", "Juan123", null);
        });
        Assertions.assertTrue(exception.getMessage().contains("El correo ya está registrado"));
    }

    @Test
    public void testRegistrarUsuario_NombreUsuarioNulo() {
        RegistroUsuarioService registroUsuarioService = new RegistroUsuarioService();
        Exception exception = assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Juan", "Proanio", "juan.proanio@epn.edu.ec",
                    "1", null, "Juan123", null);
        });
        assert (exception.getMessage().contains("El nombre de usuario es obligatorio."));
    }
}

