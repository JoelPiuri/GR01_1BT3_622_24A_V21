package com.example.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SeleccionRolServiceTest {

    private SeleccionRolService seleccionRolService;
    private String rol;
    private String redireccionEsperada;

    // Constructor para los parámetros del test
    public SeleccionRolServiceTest(String rol, String redireccionEsperada) {
        this.rol = rol;
        this.redireccionEsperada = redireccionEsperada;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"usuario", "User/usuario.jsp"},           // Caso para rol "usuario"
                {"tutor", "Tutor/tutor.jsp"},              // Caso para rol "tutor"
                {"administrator", "Administrator/Administrador.jsp"},  // Caso para rol "administrator"
                {"invalido", null},                       // Caso para un rol no válido
                {null, null}                              // Caso para un rol null
        });
    }

    @Before
    public void setUp() {
        seleccionRolService = new SeleccionRolService();
    }

    @Test
    public void testObtenerRedireccionPorRol() {

        //String resultado = seleccionRolService.obtenerRedireccionPorRol(rol);

        //assertEquals(redireccionEsperada, resultado);
    }
}