package com.example.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
public class RegistroUsuarioServiceTest {

    private RegistroUsuarioService registroUsuarioService;

    @BeforeEach
    public void setup() {
        registroUsuarioService = new RegistroUsuarioService();
    }

    @Test
    public void testRegistrarUsuario_NombreVacio() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El nombre es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_ApellidoVacio() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El apellido es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_CorreoVacio() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El correo es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_RolVacio() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "", "kerlly", "password123", null);
        });
        Assertions.assertEquals("El rol es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_NombreUsuarioVacio() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "", "password123", null);
        });
        Assertions.assertEquals("El nombre de usuario es obligatorio.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_ContrasenaVacia() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "", null);
        });
        Assertions.assertEquals("La contraseña es obligatoria.", exception.getMessage());
    }

    @Test
    public void testRegistrarUsuario_CorreoExistenteAlumno() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registroUsuarioService.registrarUsuario("Ale", "Vizuete", "kerlly.vizuete@epn.edu.ec",
                    "1", "kerlly", "password123", null);
        });
        Assertions.assertTrue(exception.getMessage().contains("El correo ya está registrado"));
    }

    @Test
    public void testRegistrarUsuario_RolIncorrecto() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            registroUsuarioService.registrarUsuario("Kerlly", "Luna", "kerlly.luna@epn.edu.ec",
                    "10", "kerlly-luna", "password789", null);
        });
        Assertions.assertEquals("Rol no válido para el registro de usuarios.", exception.getMessage());
    }
}

