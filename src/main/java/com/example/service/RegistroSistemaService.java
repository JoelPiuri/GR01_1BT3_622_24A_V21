package com.example.service;

import com.example.dao.AdministratorDAO;
import com.example.dao.AlumnoDAO;
import com.example.dao.MateriaDAO;
import com.example.dao.TutorDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Administrador;
import com.example.model.Alumno;
import com.example.model.Materia;
import com.example.model.Tutor;
import com.example.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RegistroSistemaService {
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private TutorDAO tutorDAO = new TutorDAO();
    private AdministratorDAO administradorDAO = new AdministratorDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public RegistroSistemaService() {}

    public void registrarUsuario(String nombre, String apellido, String correo, String rolId, String nombreUsuario, String contrasena, String[] materiasSeleccionadas) throws Exception {
        // Validar datos
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio.");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new Exception("La contraseña es obligatoria.");
        }

        // Validar si el correo ya existe
        if (isCorreoExistente(correo, rolId)) {
            throw new Exception("El correo ya está registrado: " + correo);
        }

        // Crear y guardar el objeto Usuario para el login
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena);
        usuario.setRolId(Integer.parseInt(rolId));

        usuarioDAO.saveUsuario(usuario); // Guarda el usuario y genera el ID

        // Registrar la entidad específica y actualizar el `referenciaId`
        int referenciaId = registrarEntidadEspecifica(nombre, apellido, correo, rolId, usuario, materiasSeleccionadas);

        // Actualizar el `referenciaId` en el objeto `Usuario` y guardar el cambio
        usuario.setReferenciaId(referenciaId);
        usuarioDAO.updateUsuario(usuario);
    }

    private boolean isCorreoExistente(String correo, String rolId) {
        switch (rolId) {
            case "1": // Alumno
                return alumnoDAO.findByEmail(correo) != null;
            case "2": // Tutor
                return tutorDAO.findByEmail(correo) != null;
            case "3": // Administrador
                return administradorDAO.findByEmail(correo) != null;
            default:
                throw new IllegalArgumentException("Rol no válido");
        }
    }

    private int registrarEntidadEspecifica(String nombre, String apellido, String correo, String rolId, Usuario usuario, String[] materiasSeleccionadas) {
        switch (rolId) {
            case "1": // Registrar Alumno
                Alumno alumno = new Alumno();
                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setEmail(correo);
                alumno.setRolID(rolId);
                alumno.setUsuario(usuario); // Asocia el usuario al alumno
                alumnoDAO.registrarAlumno(alumno);
                return alumno.getId();

            case "2": // Registrar Tutor
                Tutor tutor = new Tutor();
                tutor.setNombre(nombre);
                tutor.setApellido(apellido);
                tutor.setEmail(correo);
                tutor.setRolID(rolId);
                tutor.setUsuario(usuario); // Asocia el usuario al tutor

                List<Materia> materias = new ArrayList<>();
                if (materiasSeleccionadas != null) {
                    for (String materiaId : materiasSeleccionadas) {
                        Materia materia = materiaDAO.findById(Integer.parseInt(materiaId));
                        if (materia != null) {
                            materia.getTutores().add(tutor);
                            materias.add(materia);
                        } else {
                            throw new RuntimeException("Materia no encontrada con ID: " + materiaId);
                        }
                    }
                }
                tutor.setMaterias(materias);
                tutorDAO.registrarTutor(tutor);
                return tutor.getId();

            case "3": // Registrar Administrador
                Administrador admin = new Administrador();
                admin.setNombre(nombre);
                admin.setApellido(apellido);
                admin.setEmail(correo);
                admin.setRolID(rolId);
                admin.setUsuario(usuario); // Asocia el usuario al administrador
                administradorDAO.registrarAdmin(admin);
                return admin.getId();

            default:
                throw new IllegalArgumentException("Rol no válido");
        }
    }
}
