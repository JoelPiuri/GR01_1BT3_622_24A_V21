package com.example.service;

import com.example.dao.AlumnoDAO;
import com.example.dao.TutorDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Alumno;
import com.example.model.Tutor;
import com.example.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class RegistroUsuarioService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final TutorDAO tutorDAO = new TutorDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(String nombre, String apellido, String correo, String rolId, String nombreUsuario, String contrasena, InputStream archivoPDF) throws Exception {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio.");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new Exception("La contraseña es obligatoria.");
        }
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio.");
        if (apellido == null || apellido.isEmpty()) throw new Exception("El apellido es obligatorio.");
        if (correo == null || correo.isEmpty()) throw new Exception("El correo es obligatorio.");
        if (rolId == null || rolId.isEmpty()) throw new Exception("El rol es obligatorio.");
        if (isCorreoExistente(correo, rolId)) {
            throw new Exception("El correo ya está registrado: " + correo);
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena);
        usuario.setRolId(Integer.parseInt(rolId));

        usuarioDAO.saveUsuario(usuario); // Guarda el usuario y genera el ID

        int referenciaId;
        if (rolId.equals("1")) {
            referenciaId = registrarAlumno(nombre, apellido, correo, usuario);
        } else if (rolId.equals("2")) {
            referenciaId = registrarTutor(nombre, apellido, correo, usuario, archivoPDF);
        } else {
            throw new IllegalArgumentException("Rol no válido para el registro de usuarios.");
        }

        usuario.setReferenciaId(referenciaId);
        usuarioDAO.updateUsuario(usuario);
    }

    private boolean isCorreoExistente(String correo, String rolId) {
        if ("1".equals(rolId)) {
            return alumnoDAO.findByEmail(correo) != null;
        } else if ("2".equals(rolId)) {
            return tutorDAO.findByEmail(correo) != null;
        }
        return false;
    }

    private int registrarAlumno(String nombre, String apellido, String correo, Usuario usuario) {
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEmail(correo);
        alumno.setRolID("1");
        alumno.setUsuario(usuario);
        alumnoDAO.registrarAlumno(alumno);
        return alumno.getId();
    }

    private int registrarTutor(String nombre, String apellido, String correo, Usuario usuario, InputStream archivoPDF) throws Exception {
        Tutor tutor = new Tutor();
        tutor.setNombre(nombre);
        tutor.setApellido(apellido);
        tutor.setEmail(correo);
        tutor.setEstadoCuenta("Pendiente revision");
        tutor.setRolID("2");
        tutor.setUsuario(usuario);

        // Define la carpeta dentro del proyecto para almacenar PDFs (en resources)
        String relativePath = "/resources/pdfTutores"; // Carpeta relativa en el proyecto
        File pdfDirectory = new File(relativePath);
        if (!pdfDirectory.exists()) {
            pdfDirectory.mkdirs();
        }

        // Define el nombre del archivo
        String pdfFileName = usuario.getNombreUsuario() + "_certificado.pdf";
        File pdfFile = new File(pdfDirectory, pdfFileName);

        // Guardar el archivo PDF en el directorio especificado
        try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = archivoPDF.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Guarda solo la ruta relativa en el campo rutaPdf del tutor
        tutor.setRutaPdf("/resources/pdfTutores/" + pdfFileName);
        tutorDAO.registrarTutor(tutor);
        return tutor.getId();
    }

}
