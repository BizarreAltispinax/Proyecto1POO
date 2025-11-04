/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import jakarta.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Ventana de inicio de sesión con opción de restablecer contraseña.
 * Envía un correo real usando Outlook (SMTP).
 */
public class ProjectoPOO extends JFrame {
    Sistema sistema = Sistema.getInstancia();

    /**
     * Constructor de la clase VentanaPrincipal.
     * <p>
     * Configura los componentes gráficos iniciales: botones para elegir
     * el tipo de usuario y un título.
     * </p>
     */
    public ProjectoPOO() {
        setTitle("Sistema de Inicio");
        
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
        
        JLabel titulo = new JLabel("Seleccione su tipo de usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnAdmin = new JButton("Administrador");
        JButton btnEstudiante = new JButton("Estudiante");
        JButton btnProfesor = new JButton("Profesor");
        

        panelBotones.add(btnAdmin);
        panelBotones.add(btnEstudiante);
        panelBotones.add(btnProfesor);
        

        add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btnAdmin.addActionListener(e -> {JOptionPane.showMessageDialog(this,
                "Bienvenido, Administrador. Acceso directo permitido.");
                prueba(this,"Administrador");
                abrirAdministrador(this,"Administrador");
        
        });

        btnEstudiante.addActionListener(e -> {
            
            abrirLogin(this,"Estudiante");
            
                });

        btnProfesor.addActionListener(e -> {

            abrirLogin(this,"Profesor");

                });
        
        
        
    }

    /**
     * Abre la ventana de inicio de sesión correspondiente al tipo de usuario indicado.
     *
     * @param tipoUsuario Tipo de usuario que intenta iniciar sesión ("Estudiante" o "Profesor").
     */
    private void abrirLogin(ProjectoPOO ProjectoPOO,String tipoUsuario) {
        new VentanaLogin(ProjectoPOO,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }

    /**
     * Clase interna que representa la ventana de inicio de sesión.
     * <p>
     * Contiene campos para ingresar usuario y contraseña, y un botón para validar la entrada.
     * </p>
     */
    private class VentanaLogin extends JFrame {
        private ProjectoPOO ventanaPrincipal;

            
        public VentanaLogin(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Inicio de sesión - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 250);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(6, 1, 5, 5));
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    //guardarInformacionAlCerrar();

                    // 🔹 Mostramos confirmación opcional
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea salir del programa?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION

                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        sistema.guardar();
                        System.out.println("Cerrando aplicación...");
                        System.exit(0); // 🔹 Cierra completamente el programa
                    }
                }
            });

            JLabel lblUsuario = new JLabel("Identificacion:");
            JTextField txtIdentificacion = new JTextField();
            JLabel lblContrasena = new JLabel("Contraseña:");
            JPasswordField txtContrasena = new JPasswordField();
            JButton btnIngresar = new JButton("Ingresar");
            JButton btnRestablecer = new JButton("Restablecer contraseña");
            String tempPassword = generarContrasenaTemporal();
            add(lblUsuario);
            add(txtIdentificacion);
            add(lblContrasena);
            add(txtContrasena);
            add(btnIngresar);
            add(btnRestablecer);
            
            // Acción para ingresar
            btnIngresar.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                String contrasena = new String(txtContrasena.getPassword());

                if ((!identificacion.isEmpty()) && (!contrasena.isEmpty())) {
                    
                    if(tipoUsuario.equals("Estudiante")&& sistema.devEstudiante(identificacion)!=null && sistema.devEstudiante(identificacion).verificarContraseña(contrasena)){
                        JOptionPane.showMessageDialog(this, "Bienvenido: " + sistema.devEstudiante(identificacion).getNombre());
                        JOptionPane.showMessageDialog(this, "Ah entrado como estudiante");
                        abrirEstudiante(ventanaPrincipal,tipoUsuario,sistema.devEstudiante(identificacion));
                        this.dispose();
                    }else{
                        if(tipoUsuario.equals("Profesor")&& sistema.devProfesor(identificacion)!=null&& sistema.devProfesor(identificacion).verificarContraseña(contrasena)){
                            JOptionPane.showMessageDialog(this, "Bienvenido: " + sistema.devProfesor(identificacion).getNombre());
                            JOptionPane.showMessageDialog(this, "Ah entrado como profesor");
                            abrirProfesor(ventanaPrincipal,tipoUsuario,sistema.devProfesor(identificacion));
                            this.dispose();
                        }else{
                            JOptionPane.showMessageDialog(this,
                            "No se encontro el usuario",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe ingresar usuario y contraseña.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Acción para restablecer contraseña
            btnRestablecer.addActionListener(e -> {
                String identificacion = JOptionPane.showInputDialog(this,
                        "Ingrese su identificacion:");
               
                
                if (identificacion != null && !identificacion.isEmpty() && ((tipoUsuario.equals("Estudiante") && sistema.todasIdentificaciones(identificacion))|| (tipoUsuario.equals("Profesor")&&sistema.todasIdentificacionesProfe(identificacion)))) {
                    if(tipoUsuario.equals("Estudiante")){
                        correoRestablecer(sistema.devEstudiante(identificacion).getCorreoElectronico(),this,tempPassword);
                        abrirRestablecerCon(ventanaPrincipal,sistema.devEstudiante(identificacion),tempPassword);
                    }else{
                        if(tipoUsuario.equals("Profesor")){
                            correoRestablecer(sistema.devProfesor(identificacion).getCorreoElectronico(),this,tempPassword);
                            abrirRestablecerCon(ventanaPrincipal,sistema.devProfesor(identificacion),tempPassword);
                        }
                    }
                    
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe ingresar una identificacion existente segun el tipo de usuario.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        /**
         * Genera una contraseña temporal aleatoria de 8 caracteres.
         *
         * @return Contraseña generada
         */
        
    }
    
    private void abrirRestablecerCon(ProjectoPOO ventanaPrincipal,Usuarios tipoUsuario,String contraTemp) {
        new VentanaRestablecerCon(ventanaPrincipal,tipoUsuario,contraTemp).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaRestablecerCon extends JFrame {
        private ProjectoPOO ventanaPrincipal;

            
        public VentanaRestablecerCon(ProjectoPOO ventanaPrincipal,Usuarios tipoUsuario,String contraTemp) {
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Inicio de sesión - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 250);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 1, 5, 5));
            
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    //guardarInformacionAlCerrar();

                    // 🔹 Mostramos confirmación opcional
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea salir del programa?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION

                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        sistema.guardar();
                        System.out.println("Cerrando aplicación...");
                        System.exit(0); // 🔹 Cierra completamente el programa
                    }
                }
            });
            
            
            JLabel lblContrasenaTemp = new JLabel("Contraseña temporal enviada al correo:");
            JTextField txtContrasenaTemp = new JTextField();
            JLabel lblContrasena = new JLabel("Nueva Contraseña:");
            JTextField txtContrasena = new JTextField();
            JButton btnRestablecer = new JButton("Restablecer");

            add(lblContrasenaTemp);
            add(txtContrasenaTemp);
            add(lblContrasena);
            add(txtContrasena);
            add(btnRestablecer);
            
            btnRestablecer.addActionListener(e -> {
                String ingContraTemp = txtContrasenaTemp.getText();
                String contraseña = txtContrasena.getText();
                if (ingContraTemp.equals(contraTemp)){
                    
                    boolean tieneMayuscula = contraseña.matches(".*[A-Z].*");
                    boolean tieneNumero    = contraseña.matches(".*[0-9].*");
                    boolean tieneEspecial  = contraseña.matches(".*[^a-zA-Z0-9].*");
                    if (contraseña.length() < 8 && !tieneMayuscula && !tieneNumero && !tieneEspecial) {
                        
                        JOptionPane.showMessageDialog(this, "Error, la contraseña debe de tener 8 o mas caracteres y tener mayuscula, numero y caracter especial");
                    }else{
                        tipoUsuario.setContraseña(contraseña);
                        if (tipoUsuario instanceof Estudiantes){
                            abrirLogin(ventanaPrincipal,"Estudiante");
                        }else{
                            abrirLogin(ventanaPrincipal,"Profesor");
                        }
                        
                        this.dispose();
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(this, "Error, la contraseña ingresada no es igual a la temporal");
                }
                
            });

        }
    }
    
    
    private void abrirAdministrador(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAdministrador(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.setVisible(false); // Cierra la ventana principal
    }
    private class VentanaAdministrador extends JFrame {
        private ProjectoPOO ventanaPrincipal;
        public VentanaAdministrador(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            JLabel lblHora = new JLabel("Administrador accedió en: " + ahora.format(formato));
            
            add(lblHora);
            
            JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
            JButton btnEstudiante = new JButton("Estudiante");
            JButton btnProfesor = new JButton("Profesor");
            JButton btnCurso = new JButton("Curso");
            JButton btnReporte = new JButton("Reportes");
            JButton btnGrupo = new JButton("Grupo");
            JButton btnAsoProf = new JButton("Asociar Profesores/Grupo");
            JButton btnSalir = new JButton("Salir");
            
            
            panelBotones.add(btnEstudiante);
            panelBotones.add(btnProfesor);
            panelBotones.add(btnCurso);
            panelBotones.add(btnGrupo);
            panelBotones.add(btnAsoProf);
            panelBotones.add(btnReporte);
            panelBotones.add(btnSalir);

            add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btnEstudiante.addActionListener(e -> {
            this.dispose();
            abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
                });

        btnProfesor.addActionListener(e -> {
            this.dispose();
            abrirAdministradorProfesor(ventanaPrincipal,tipoUsuario);
                });
        
        
        btnCurso.addActionListener(e -> {
            this.dispose();
            abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
                });
        
        btnGrupo.addActionListener(e -> {
            this.dispose();
            abrirAsociarCursoGrupo(ventanaPrincipal,tipoUsuario);
                });
        btnAsoProf.addActionListener(e -> {
            this.dispose();
            abrirAsociarProfesorGrupo(ventanaPrincipal,tipoUsuario);
                });
        btnReporte.addActionListener(e -> {
            this.dispose();
            abrirReportes(ventanaPrincipal,tipoUsuario);
                });
        
          
        //btnProfesor.addActionListener(e ->);    
        btnSalir.addActionListener(e -> {
            this.dispose();
            ventanaPrincipal.setVisible(true); // Muestra ventana principal
                });
        }
    }
    
    private void abrirEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario, Estudiantes est) {
        new VentanaEstudiante(ventanaPrincipal,tipoUsuario,est).setVisible(true);
        this.setVisible(false); // Cierra la ventana principal
    }
    private class VentanaEstudiante extends JFrame {
        private ProjectoPOO ventanaPrincipal;
        public VentanaEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario,Estudiantes est){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            
            JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
            JButton btninfo = new JButton("Informacion general");
            JButton btnMatricula = new JButton("Matricula");
            JButton btnEvaluaciones = new JButton("Evaluaciones asignadas");
            JButton btnDesempeño = new JButton("Desempeño personal");
            
            JButton btnSalir = new JButton("Salir");
            
            
            panelBotones.add(btninfo);
            panelBotones.add(btnMatricula);
            panelBotones.add(btnEvaluaciones);
            panelBotones.add(btnDesempeño);
            panelBotones.add(btnSalir);

            add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btninfo.addActionListener(e -> {
            
            JFrame ventana = new JFrame("Informacion general");
            ventana.setSize(400, 300);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLayout(null);

            // Campo de fecha
            JTextArea lblFecha = new JTextArea(10, 50);
            lblFecha.setText(est.toString());
            lblFecha.setEditable(false);
            lblFecha.setBounds(20, 20, 350, 170);
            lblFecha.setBackground(ventana.getBackground());
            ventana.add(lblFecha);

            // Botón para cerrar la ventana
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setBounds(150, 210, 100, 30); // posición y tamaño del botón
            btnCerrar.addActionListener(w -> ventana.dispose());
            ventana.add(btnCerrar);

            ventana.setVisible(true);
                        
                        
                        
                        
                });

        btnMatricula.addActionListener(e -> {
            this.dispose();
            abrirMatriculaEstudiante(ventanaPrincipal,tipoUsuario,est);
                });
        
        
        btnEvaluaciones.addActionListener(e -> {
            abrirEvaluacionesPendientes(ventanaPrincipal,tipoUsuario,est);
            this.dispose();
            
                });
        
        btnDesempeño.addActionListener(e -> {
            this.dispose();
            
                });

        
          
        //btnProfesor.addActionListener(e ->);    
        btnSalir.addActionListener(e -> {
            this.dispose();
            ventanaPrincipal.setVisible(true); // Muestra ventana principal
                });
        }
        
        
    }
    
    
    private void abrirProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario, Profesores prof) {
        new VentanaProfesor(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.setVisible(false); // Cierra la ventana principal
    }
    private class VentanaProfesor extends JFrame {
        private ProjectoPOO ventanaPrincipal;
        public VentanaProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            
            JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
            JButton btninfo = new JButton("Informacion general");
            JButton btnEvaluaciones = new JButton("Evaluaciones (CRUD)");
            JButton btnPrev = new JButton("Previzualizacion de evaluaciones");
            JButton btnRep = new JButton("Reporte evaluacion");
            JButton btnAsoDesa = new JButton("Asociar/Desasociar evaluaciones");
            JButton btnEvAsignadas = new JButton("Evaluaciones asignadas");
            JButton btnEvRealizadas = new JButton("Evaluaciones realizadas");
            
            JButton btnSalir = new JButton("Salir");
            
            
            panelBotones.add(btninfo);
            panelBotones.add(btnEvaluaciones);
            panelBotones.add(btnPrev);
            panelBotones.add(btnRep);
            panelBotones.add(btnAsoDesa);
            panelBotones.add(btnEvAsignadas);
            panelBotones.add(btnEvRealizadas);
            panelBotones.add(btnSalir);

            add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btninfo.addActionListener(e -> {
            
            JFrame ventana = new JFrame("Informacion general");
            ventana.setSize(400, 300);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLayout(null);

            // Campo de fecha
            JTextArea lblFecha = new JTextArea(10, 50);
            lblFecha.setText(prof.toString());
            lblFecha.setEditable(false);
            lblFecha.setBounds(20, 20, 350, 170);
            lblFecha.setBackground(ventana.getBackground());
            ventana.add(lblFecha);

            // Botón para cerrar la ventana
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setBounds(150, 210, 100, 30); // posición y tamaño del botón
            btnCerrar.addActionListener(w -> ventana.dispose());
            ventana.add(btnCerrar);

            ventana.setVisible(true);
                        
                        
                        
                        
                });

        btnEvaluaciones.addActionListener(e -> {
            this.dispose();
            abrirEvaluacionesCRUD(ventanaPrincipal,tipoUsuario,prof);
                });
        
        
        btnPrev.addActionListener(e -> {
            this.dispose();
            abrirPrevisualizacionEvaluacion(ventanaPrincipal,tipoUsuario,prof);
            
                });
        btnRep.addActionListener(e -> {
            String identificacion = JOptionPane.showInputDialog(this,
                        "Ingrese la identificacion de la evaluacion:");
            int id =Integer.parseInt(identificacion);
                if (sistema.devEva(id)!=null){
                    JOptionPane.showMessageDialog(this, "Reporte realizado");
                    sistema.ReporteEvaluacionesDetalle(id);
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro la evaluacion");
                }
            
            
                });
        btnAsoDesa.addActionListener(e -> {
            this.dispose();
            abrirAsociarGrupoEvaluacion(ventanaPrincipal,tipoUsuario,prof);
            
                });
        
          
        //btnProfesor.addActionListener(e ->);    
        btnSalir.addActionListener(e -> {
            this.dispose();
            ventanaPrincipal.setVisible(true); // Muestra ventana principal
                });
        }
        
        
    }
    private void abrirEvaluacionesCRUD(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaEvaluacionesCRUD(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaEvaluacionesCRUD extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaEvaluacionesCRUD(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnCrear = new JButton("Crear");
            JButton btnConsultar = new JButton("Consultar");
            JButton btnModificar = new JButton("Modificar");
            JButton btnEliminar = new JButton("Eliminar");
            
            
            
            
            panelBotones.add(btnCrear);
            panelBotones.add(btnConsultar);
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);
       

            add(panelBotones, BorderLayout.CENTER);
            
            btnCrear.addActionListener(e -> {
                this.dispose();
                abrirCrearEvaluacion(ventanaPrincipal,tipoUsuario, prof);
                    });
            
            btnConsultar.addActionListener(e -> {
                this.dispose();
                abrirMostrarEvaluacion(ventanaPrincipal,tipoUsuario, prof);
                    });
            
            btnModificar.addActionListener(e -> {
                this.dispose();
                abrirModificarEvaluacion(ventanaPrincipal,tipoUsuario,prof);
                    });
            
           btnEliminar.addActionListener(e -> {
                this.dispose();
                abrirEliminarEvaluacion(ventanaPrincipal,tipoUsuario,prof);
                    });
            
            
        }
    }
    
    private void abrirCrearEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaCrearEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaCrearEvaluacion extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        private JRadioButton rbOpcion1, rbOpcion2;
        private ArrayList<Ejercicios> listaEjercicios = new ArrayList<Ejercicios>();
        public VentanaCrearEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(21, 1, 5, 5));
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JLabel lblNombre = new JLabel("Nombre: ");
            JTextField txtNombre = new JTextField();
            JLabel lblInstrucciones = new JLabel("Instrucciones generales:");
            JTextField txtInstrucciones = new JTextField();
            JLabel lblObjetivos = new JLabel("Objetivos: ");
            JTextField txtObjetivos = new JTextField();
            JLabel lblDuracion = new JLabel("Duracion: ");
            JTextField txtDuracion = new JTextField();
            

            JButton btnAgregarEjercicio = new JButton("Agregar ejercicio");
            
            JButton btnCrear = new JButton("Crear");
  
            
            
            add(lblNombre); 
            add(txtNombre); 
            add(lblInstrucciones); 
            add(txtInstrucciones); 
            add(lblObjetivos); 
            add(txtObjetivos); 
            add(lblDuracion); 
            add(txtDuracion); 
            
            rbOpcion1 = new JRadioButton("Orden preguntas aleatorias");
            rbOpcion1.setBounds(50, 40, 100, 30);
            add(rbOpcion1);

            rbOpcion2 = new JRadioButton("Orden respuestas aleatorias");
            rbOpcion2.setBounds(50, 70, 100, 30);
            add(rbOpcion2);
            
            add(btnAgregarEjercicio);
            add(btnCrear);
            
           btnAgregarEjercicio.addActionListener(e -> {
               new VentanaAgregarEjercicios(listaEjercicios).setVisible(true);
           });
            
            
            
            
            btnCrear.addActionListener(e -> {
                
                
                
                boolean valor1 = rbOpcion1.isSelected();
                boolean valor2 = rbOpcion2.isSelected();
                String nombre = txtNombre.getText().trim();
                String instrucciones = txtInstrucciones.getText().trim();
                String objetivos = txtObjetivos.getText().trim();
                String duracion = txtDuracion.getText().trim();
                if (!duracion.isEmpty() && !nombre.isEmpty() && !instrucciones.isEmpty() && !objetivos.isEmpty()&& !listaEjercicios.isEmpty()){
                    int duracionT = Integer.parseInt(duracion);
                    ArrayList<String> listaObjetivos = new ArrayList<String>();
                
                
                
                    String[] arregloObjetivos = objetivos.split("\\s*,\\s*");
                    for (int i = 0; i < arregloObjetivos.length; i++) {
                        listaObjetivos.add(arregloObjetivos[i]);
                    }


                        try{
                            Evaluaciones eva = new Evaluaciones(sistema.getEvaluaciones().size()+1,nombre,instrucciones,listaObjetivos,duracionT,valor1,valor2);
                            for (Ejercicios ej : listaEjercicios){
                                eva.agregarEjercicio(ej);
                            }
                            sistema.agregarEvaluacion(eva);
                            this.dispose();
                            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                        } catch(IllegalArgumentException ex){
                            JOptionPane.showMessageDialog(this,
                                        "Error:\n" + ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
                        }
                }else{
                    JOptionPane.showMessageDialog(this,
                                    "Error: Espacios en blanco o ejercicios no agregados\n",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
                

                
                
                
                
            });
        }
    }
    
    public class VentanaAgregarEjercicios extends JFrame {

        public VentanaAgregarEjercicios(ArrayList<Ejercicios> listaEjercicios) {
            setTitle("Tipos de Ejercicio");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 1, 10, 10));

            JButton btnOpcionUnica = new JButton("Opción Única");
            JButton btnOpcionMultiple = new JButton("Opción Múltiple");
            JButton btnVerdaderoFalso = new JButton("Verdadero/Falso");
            JButton btnPareo = new JButton("Pareo");
            JButton btnSopaLetras = new JButton("Sopa de Letras");

            add(btnOpcionUnica);
            add(btnOpcionMultiple);
            add(btnVerdaderoFalso);
            add(btnPareo);
            add(btnSopaLetras);

            // Acciones para cada botón → Abren una ventana diferente
            btnOpcionUnica.addActionListener(e -> {new VentanaOpcionUnica(listaEjercicios).setVisible(true);
                this.dispose();
                    });
            btnOpcionMultiple.addActionListener(e -> {new VentanaOpcionMultiple(listaEjercicios).setVisible(true);
                this.dispose();
                    });
            btnVerdaderoFalso.addActionListener(e -> {new VentanaVerdaderoFalso(listaEjercicios).setVisible(true);
                this.dispose();
                    });
            btnPareo.addActionListener(e -> {new VentanaPareo(listaEjercicios).setVisible(true);
                this.dispose();
                    });
            btnSopaLetras.addActionListener(e -> {new VentanaSopaLetras(listaEjercicios).setVisible(true);
                this.dispose();
                    });
        }
    }
    
    
    private void abrirMostrarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaConsultarEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    public class VentanaConsultarEvaluacion extends JFrame {
        private JTextField txtId;
    private JButton btnConsultar;
    private JButton btnSalir;


    
    public VentanaConsultarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        setTitle("Consultar Evaluación");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));



        JPanel panelId = new JPanel(new GridLayout(1, 2, 10, 10));
        panelId.setBorder(BorderFactory.createTitledBorder("Datos de la evaluación"));

        panelId.add(new JLabel("ID de evaluación:"));
        txtId = new JTextField();
        panelId.add(txtId);

        add(panelId, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnConsultar = new JButton("Consultar");
        btnSalir = new JButton("Salir");
        panelBotones.add(btnConsultar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {this.dispose();
            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                });

        btnConsultar.addActionListener(e -> {
            String id = txtId.getText().trim();
            int idInt = Integer.parseInt(id);
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de evaluación.");
                return;
            }
            if (!sistema.getEvaluaciones().isEmpty()){
                for (Evaluaciones eva1 : sistema.getEvaluaciones()){
                if (eva1 != null && eva1.getIdentificacion()==idInt) {
                    abrirFrameConsulta(eva1);
                } else {
                    JOptionPane.showMessageDialog(this, "Evaluación no encontrada.");
                }
            }
            }else{
                JOptionPane.showMessageDialog(this, "No hay evaluaciones creadas.");
            }
            
        });
    }

    private void abrirFrameConsulta(Evaluaciones eval) {
        JFrame frameConsulta = new JFrame("Detalles de Evaluación");
        frameConsulta.setSize(600, 500);
        frameConsulta.setLayout(new BorderLayout());

        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setText(eval.toString() + "\n\n" + eval.imprimir());

        JScrollPane scroll = new JScrollPane(txtInfo);
        frameConsulta.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> frameConsulta.dispose());
        frameConsulta.add(btnCerrar, BorderLayout.SOUTH);

        frameConsulta.setLocationRelativeTo(null);
        frameConsulta.setVisible(true);
    }
    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void abrirModificarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new abrirModificarEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class abrirModificarEvaluacion extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        private JRadioButton rbOpcion1, rbOpcion2;
        private ArrayList<Ejercicios> listaEjercicios = new ArrayList<Ejercicios>();
        public abrirModificarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(25, 1, 5, 5));
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JLabel lblIdentificacion = new JLabel("Identificacion: ");
            JTextField txtIdentificacion = new JTextField();
            JLabel lblNombre = new JLabel("Nombre: ");
            JTextField txtNombre = new JTextField();
            JLabel lblInstrucciones = new JLabel("Instrucciones generales:");
            JTextField txtInstrucciones = new JTextField();
            JLabel lblObjetivos = new JLabel("Objetivos: ");
            JTextField txtObjetivos = new JTextField();
            JLabel lblDuracion = new JLabel("Duracion: ");
            JTextField txtDuracion = new JTextField();
            

            JButton btnAgregarEjercicio = new JButton("Agregar ejercicio");
            JButton btnModificarEjercicio = new JButton("Modificar ejercicio");
            JButton btnEliminarEjercicio = new JButton("Eliminar ejercicio");
            JButton btnCompletar = new JButton("Terminar modificaciones");
            JButton btnSalir = new JButton("Salir");
  
            
            add(lblIdentificacion); 
            add(txtIdentificacion);
            add(lblNombre); 
            add(txtNombre); 
            add(lblInstrucciones); 
            add(txtInstrucciones); 
            add(lblObjetivos); 
            add(txtObjetivos); 
            add(lblDuracion); 
            add(txtDuracion); 
            
            rbOpcion1 = new JRadioButton("Orden preguntas aleatorias");
            rbOpcion1.setBounds(50, 40, 100, 30);
            add(rbOpcion1);

            rbOpcion2 = new JRadioButton("Orden respuestas aleatorias");
            rbOpcion2.setBounds(50, 70, 100, 30);
            add(rbOpcion2);
            
            add(btnAgregarEjercicio);
            add(btnModificarEjercicio);
            add(btnEliminarEjercicio);
            add(btnCompletar);
            add(btnSalir);
            
           btnAgregarEjercicio.addActionListener(e -> {
               String identificacion = txtIdentificacion.getText().trim();
               try {
                    int identificacionInt = Integer.parseInt(identificacion);
                    for (Evaluaciones ev :sistema.getEvaluaciones()){
                      if (ev.getGrupo()!=null && ev.getIdentificacion()==identificacionInt ) {
                          JOptionPane.showMessageDialog(this,
                            "Error: La Evaluacion esta asociada a un grupo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                      }
                   }
                    
                   
                    
                        
                        if (!identificacion.isEmpty()){
                            new VentanaAgregarEjercicios(listaEjercicios).setVisible(true);

                        }else{
                             JOptionPane.showMessageDialog(this,
                                             "Error: No hay identificacion\n",
                                             "Error", JOptionPane.ERROR_MESSAGE);
                         }
                   
               
               }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error: debes ingresar un número entero válido.");
                }
               
               
               
           });
            
            
            
            
            btnModificarEjercicio.addActionListener(e -> {
                
                String identificacion = txtIdentificacion.getText().trim();
                
                
               
                
                
                try{
                    if (!identificacion.isEmpty()){
                   int identificacionInt = Integer.parseInt(identificacion);
                   
                   for (Evaluaciones ev :sistema.getEvaluaciones()){
                      if (ev.getGrupo()!=null && ev.getIdentificacion()==identificacionInt ) {
                          JOptionPane.showMessageDialog(this,
                            "Error: La Evaluacion esta asociada a un grupo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                      }
                   }
                   
                   
                   
                   
                   
                   for (Evaluaciones eva : sistema.getEvaluaciones()){
                    if (eva.getIdentificacion()==identificacionInt){
                        new VentanaModificarEjercicios(eva).setVisible(true);;
                    }
                   }
               
                
                
                }else{
                    JOptionPane.showMessageDialog(this,
                                    "Error: No hay identificacion\n",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error: debes ingresar un número entero válido.");
                }
                
                

                
                
                
                
            });
            
            
            btnEliminarEjercicio.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText().trim();
                
                try{
                    int identificacionInt = Integer.parseInt(identificacion);
                for (Evaluaciones ev :sistema.getEvaluaciones()){
                      if (ev.getGrupo()!=null && ev.getIdentificacion()==identificacionInt ) {
                          JOptionPane.showMessageDialog(this,
                            "Error: La Evaluacion esta asociada a un grupo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                      }
                   }
                
                
                if (!identificacion.isEmpty()){
                   
                   for (Evaluaciones eva : sistema.getEvaluaciones()){
                    if (eva.getIdentificacion()==identificacionInt){
                        new VentanaEliminarEjercicios(eva).setVisible(true);;
                    }
                   }
               
                
                
                }else{
                    JOptionPane.showMessageDialog(this,
                                    "Error: No hay identificacion\n",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
                }catch (NumberFormatException ej) {
                    JOptionPane.showMessageDialog(this, "Error: debes ingresar un número entero válido.");
                }
                
               
           });
            
            btnCompletar.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText().trim();
                boolean valor1 = rbOpcion1.isSelected();
                boolean valor2 = rbOpcion2.isSelected();
                String nombre = txtNombre.getText().trim();
                String instrucciones = txtInstrucciones.getText().trim();
                String objetivos = txtObjetivos.getText().trim();
                String duracion = txtDuracion.getText().trim();
                if (!duracion.isEmpty() && !nombre.isEmpty() && !instrucciones.isEmpty() && !objetivos.isEmpty()&& !identificacion.isEmpty()){
                    
                    try{
                        int duracionT = Integer.parseInt(duracion);
                        int identificacionInt = Integer.parseInt(identificacion);


                        for (Evaluaciones ev :sistema.getEvaluaciones()){
                          if (ev.getGrupo()!=null && ev.getIdentificacion()==identificacionInt ) {
                              JOptionPane.showMessageDialog(this,
                                "Error: La Evaluacion esta asociada a un grupo.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                          }
                       }





                        ArrayList<String> listaObjetivos = new ArrayList<String>();



                        String[] arregloObjetivos = objetivos.split("\\s*,\\s*");
                        for (int i = 0; i < arregloObjetivos.length; i++) {
                            listaObjetivos.add(arregloObjetivos[i]);
                        }







                            try{
                                for (Evaluaciones ev: sistema.getEvaluaciones()){
                                    if (ev.getIdentificacion()==identificacionInt){
                                        for (Ejercicios ej:listaEjercicios){

                                            ev.agregarEjercicio(ej);

                                        }
                                        ev.setNombre(nombre);
                                        ev.setInstrucciones(instrucciones);
                                        ev.setObjetivos(listaObjetivos);
                                        ev.setDuracion(duracionT);
                                        ev.setPregAl(valor1);
                                        ev.setResAl(valor2);

                                        this.dispose();
                                        abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                                    }
                                }






                            } catch(IllegalArgumentException ex){
                                JOptionPane.showMessageDialog(this,
                                            "Error:\n" + ex.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
                            }
                    }catch (NumberFormatException ej) {
                        JOptionPane.showMessageDialog(this, "Error: debes ingresar un número entero válido.");
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(this,
                                    "Error: Espacios en blanco\n",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
               
           });
            
            btnSalir.addActionListener(e -> {
                abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                this.dispose();
               
           });
            
            
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    class VentanaModificarEjercicios extends JFrame {
        private Evaluaciones evaluacion;
        private JPanel panelBotones;

        public VentanaModificarEjercicios(Evaluaciones evaluacion) {
            this.evaluacion = evaluacion;

            setTitle("Ejercicios de la Evaluación " + evaluacion.getIdentificacion());
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            panelBotones = new JPanel();
            panelBotones.setLayout(new GridLayout(0, 1, 5, 5));
            add(new JScrollPane(panelBotones), BorderLayout.CENTER);

            cargarBotones();
        }

        private void cargarBotones() {
            panelBotones.removeAll(); // limpiar panel antes de volver a cargar

            for (Ejercicios e : evaluacion.getEjercicios()) {
                JButton btn = new JButton(e.getEnunciado());
                panelBotones.add(btn);

                btn.addActionListener(ev -> {
                    
                    if (e instanceof OpcionUnicaPanel) {
                        new VentanaOpcionUnicaMod((OpcionUnicaPanel)e).setVisible(true);
                        this.dispose();
                    } else if (e instanceof OpcionMultiplePanel) {
                        new VentanaOpcionMultipleMod((OpcionMultiplePanel)e).setVisible(true);
                        this.dispose();
                    } else if (e instanceof VerdaderoFalsoPanel) {
                        new VentanaVerdaderoFalsoMod((VerdaderoFalsoPanel)e).setVisible(true);
                        this.dispose();
                    } else if (e instanceof PareoPanel) {
                        new VentanaPareoMod((PareoPanel)e).setVisible(true);
                        this.dispose();
                    } else if (e instanceof SopaDeLetrasPanel) {
                        new VentanaSopaLetrasMod((SopaDeLetrasPanel)e).setVisible(true);
                        this.dispose();
                    }
                    this.dispose();
                    
                });
            }



        }
    }
    class VentanaOpcionUnicaMod extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JTextField txtCorrecta;
        private JPanel panelOtrasOpciones;
        private ArrayList<JTextField> camposOtras;
        private ArrayList<String> opciones;  // ← donde se guardan todas las opciones

        public VentanaOpcionUnicaMod(OpcionUnicaPanel eje) {
            setTitle("Opción Única");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            opciones = new ArrayList<>();
            camposOtras = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central: Correcta y Otras ----------
            JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

            // Columna izquierda - Opción correcta
            JPanel panelCorrecta = new JPanel(new BorderLayout(5, 5));
            panelCorrecta.setBorder(BorderFactory.createTitledBorder("Opción correcta"));
            txtCorrecta = new JTextField();
            panelCorrecta.add(txtCorrecta, BorderLayout.NORTH);
            panelCentro.add(panelCorrecta);

            // Columna derecha - Otras opciones
            JPanel panelDerecha = new JPanel(new BorderLayout(5, 5));
            panelDerecha.setBorder(BorderFactory.createTitledBorder("Otras opciones"));

            panelOtrasOpciones = new JPanel();
            panelOtrasOpciones.setLayout(new BoxLayout(panelOtrasOpciones, BoxLayout.Y_AXIS));

            // Campo inicial
            agregarCampoOpcion();

            JScrollPane scroll = new JScrollPane(panelOtrasOpciones);
            panelDerecha.add(scroll, BorderLayout.CENTER);

            JButton btnAgregarCampo = new JButton("Agregar opción");
            btnAgregarCampo.addActionListener(e -> agregarCampoOpcion());
            panelDerecha.add(btnAgregarCampo, BorderLayout.SOUTH);

            panelCentro.add(panelDerecha);
            add(panelCentro, BorderLayout.CENTER);

            // ---------- Panel inferior: Botón agregar ----------
            JButton btnAgregar = new JButton("Modificar");
            
            btnAgregar.addActionListener(e -> {guardarOpciones();
            String enunciado = txtEnunciado.getText();
            String puntaje=txtPuntaje.getText();
            int PuntajeInt = Integer.parseInt(puntaje);
                try{
                    eje.setEnunciado(enunciado);
                    eje.setPuntaje(PuntajeInt);
                    eje.setOpciones(opciones);
                    
                    this.dispose();
                }catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
                    });
            add(btnAgregar, BorderLayout.SOUTH);
        }

        // Agrega un nuevo campo de texto en la columna "otras opciones"
        private void agregarCampoOpcion() {
            JTextField campo = new JTextField();
            camposOtras.add(campo);
            panelOtrasOpciones.add(campo);
            panelOtrasOpciones.revalidate();
            panelOtrasOpciones.repaint();
        }

        // Reúne toda la información y la guarda en el ArrayList
        private void guardarOpciones() {
            opciones.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            String correcta = txtCorrecta.getText().trim();

            if (enunciado.isEmpty() || puntaje.isEmpty() || correcta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar enunciado, puntaje y opción correcta");
                return;
            }

            opciones.add(correcta); // la primera es la correcta

            for (JTextField campo : camposOtras) {
                String texto = campo.getText().trim();
                if (!texto.isEmpty()) {
                    opciones.add(texto);
                }
            }

            
        }

        // (Opcional) puedes obtener la lista para integrarla a tu lista general
        public ArrayList<String> getOpciones() {
            return opciones;
        }
    }

    class VentanaOpcionMultipleMod extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JPanel panelCorrectas, panelOtras;
        private ArrayList<JTextField> camposCorrectas, camposOtras;
        private ArrayList<String> opciones;
        private Set<Integer> correctas;

        public VentanaOpcionMultipleMod(OpcionMultiplePanel eje) {
            setTitle("Opción Múltiple");
            setSize(550, 420);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            opciones = new ArrayList<>();
            correctas = new HashSet<>();
            camposCorrectas = new ArrayList<>();
            camposOtras = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central con dos columnas ----------
            JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

            // Columna izquierda - Opciones correctas
            JPanel panelIzq = new JPanel(new BorderLayout(5, 5));
            panelIzq.setBorder(BorderFactory.createTitledBorder("Opciones correctas"));

            panelCorrectas = new JPanel();
            panelCorrectas.setLayout(new BoxLayout(panelCorrectas, BoxLayout.Y_AXIS));
            JScrollPane scrollCorrectas = new JScrollPane(panelCorrectas);

            JButton btnAgregarCorrecta = new JButton("Agregar correcta");
            btnAgregarCorrecta.addActionListener(e -> agregarCampoCorrecta());

            // Campo inicial
            agregarCampoCorrecta();

            panelIzq.add(scrollCorrectas, BorderLayout.CENTER);
            panelIzq.add(btnAgregarCorrecta, BorderLayout.SOUTH);

            // Columna derecha - Otras opciones
            JPanel panelDer = new JPanel(new BorderLayout(5, 5));
            panelDer.setBorder(BorderFactory.createTitledBorder("Otras opciones"));

            panelOtras = new JPanel();
            panelOtras.setLayout(new BoxLayout(panelOtras, BoxLayout.Y_AXIS));
            JScrollPane scrollOtras = new JScrollPane(panelOtras);

            JButton btnAgregarOtra = new JButton("Agregar opción");
            btnAgregarOtra.addActionListener(e -> agregarCampoOtra());

            // Campo inicial
            agregarCampoOtra();

            panelDer.add(scrollOtras, BorderLayout.CENTER);
            panelDer.add(btnAgregarOtra, BorderLayout.SOUTH);

            panelCentro.add(panelIzq);
            panelCentro.add(panelDer);

            add(panelCentro, BorderLayout.CENTER);

            // ---------- Botón inferior ----------
            
            JButton btnGuardar = new JButton("Modificar");
            
            btnGuardar.addActionListener(e -> {guardarOpciones();
            
            
            String enunciado = txtEnunciado.getText();
            String puntaje=txtPuntaje.getText();
            if (!puntaje.isEmpty() && !enunciado.isEmpty()){
                int PuntajeInt = Integer.parseInt(puntaje);
                try{
                    eje.setEnunciado(enunciado);
                    eje.setPuntaje(PuntajeInt);
                    eje.setOpciones(opciones);
                    eje.setCorrectas(correctas);
                this.dispose();
            }catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
                    });
            add(btnGuardar, BorderLayout.SOUTH);
        }

        private void agregarCampoCorrecta() {
            JTextField campo = new JTextField();
            camposCorrectas.add(campo);
            panelCorrectas.add(campo);
            panelCorrectas.revalidate();
            panelCorrectas.repaint();
        }

        private void agregarCampoOtra() {
            JTextField campo = new JTextField();
            camposOtras.add(campo);
            panelOtras.add(campo);
            panelOtras.revalidate();
            panelOtras.repaint();
        }

        private void guardarOpciones() {
            opciones.clear();
            correctas.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();

            // Validar enunciado y puntaje
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar enunciado y puntaje", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar campos vacíos en correctas
            boolean hayCorrectaVacia = camposCorrectas.stream().anyMatch(c -> c.getText().trim().isEmpty());
            boolean hayOtraVacia = camposOtras.stream().anyMatch(c -> c.getText().trim().isEmpty());

            if (hayCorrectaVacia || hayOtraVacia) {
                JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las opciones", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que haya al menos una correcta
            if (camposCorrectas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos una opción correcta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregar las correctas primero
            for (JTextField campo : camposCorrectas) {
                String texto = campo.getText().trim();
                correctas.add(opciones.size()); // índice actual
                opciones.add(texto);
            }

            // Agregar las otras opciones
            for (JTextField campo : camposOtras) {
                String texto = campo.getText().trim();
                opciones.add(texto);
            }

            // Mostrar confirmación
            
        }

        // Métodos para obtener los datos
        public ArrayList<String> getOpciones() {
            return opciones;
        }

        public Set<Integer> getCorrectas() {
            return correctas;
        }
    }

    class VentanaVerdaderoFalsoMod extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JRadioButton rbVerdadero;
        private JRadioButton rbFalso;
        private boolean respuestaCorrecta; // true si es verdadero, false si es falso

        public VentanaVerdaderoFalsoMod(VerdaderoFalsoPanel eje) {
            setTitle("Ejercicio Verdadero / Falso");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central: opciones Verdadero/Falso ----------
            JPanel panelOpciones = new JPanel();
            panelOpciones.setBorder(BorderFactory.createTitledBorder("Seleccione la respuesta correcta"));
            panelOpciones.setLayout(new GridLayout(2, 1, 5, 5));

            ButtonGroup grupo = new ButtonGroup();

            rbVerdadero = new JRadioButton("Verdadero");
            rbFalso = new JRadioButton("Falso");

            grupo.add(rbVerdadero);
            grupo.add(rbFalso);

            panelOpciones.add(rbVerdadero);
            panelOpciones.add(rbFalso);

            add(panelOpciones, BorderLayout.CENTER);

            // ---------- Botón inferior ----------
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> {
                guardarRespuesta(eje);
                
            });
            add(btnGuardar, BorderLayout.SOUTH);
        }

        private void guardarRespuesta(VerdaderoFalsoPanel eje) {
            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            int puntajeInt=Integer.parseInt(puntaje);

            // Validaciones
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!rbVerdadero.isSelected() && !rbFalso.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Verdadero o Falso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar la respuesta
            respuestaCorrecta = rbVerdadero.isSelected();
            
            try{
                eje.setEnunciado(enunciado);
                eje.setPuntaje(puntajeInt);
                eje.setCorrecta(respuestaCorrecta);
                this.dispose();
            }catch(IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
            
            
            
                 
            
            
            
            
           
        }

        // Getters para obtener los datos desde fuera
        public String getEnunciado() {
            return txtEnunciado.getText().trim();
        }

        public int getPuntaje() {
            try {
                return Integer.parseInt(txtPuntaje.getText().trim());
            } catch (NumberFormatException e) {
                return 0; // por si el usuario escribió algo no numérico
            }
        }

        public boolean getRespuestaCorrecta() {
            return respuestaCorrecta;
        }
    }

    class VentanaPareoMod extends JFrame {
            private JTextField txtEnunciado;
    private JTextField txtPuntaje;
    private JPanel panelPareo;
    private ArrayList<JTextField> camposPalabra;
    private ArrayList<JTextField> camposDefinicion;
    private Map<String, String> pares;

    public VentanaPareoMod(PareoPanel eje) {
        setTitle("Ejercicio de Pareo");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pares = new LinkedHashMap<>();
        camposPalabra = new ArrayList<>();
        camposDefinicion = new ArrayList<>();

        // ---------- Panel superior: Enunciado y Puntaje ----------
        JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

        panelDatos.add(new JLabel("Enunciado:"));
        txtEnunciado = new JTextField();
        panelDatos.add(txtEnunciado);

        panelDatos.add(new JLabel("Puntaje:"));
        txtPuntaje = new JTextField();
        panelDatos.add(txtPuntaje);

        add(panelDatos, BorderLayout.NORTH);

        // ---------- Panel central: columnas Palabra / Definición ----------
        panelPareo = new JPanel();
        panelPareo.setLayout(new GridLayout(0, 2, 10, 10));
        panelPareo.setBorder(BorderFactory.createTitledBorder("Palabras y definiciones"));

        // Etiquetas de encabezado
        panelPareo.add(new JLabel("Palabra", SwingConstants.CENTER));
        panelPareo.add(new JLabel("Definición", SwingConstants.CENTER));

        // Primer par inicial
        agregarParCampos();

        JScrollPane scroll = new JScrollPane(panelPareo);
        add(scroll, BorderLayout.CENTER);

        // ---------- Panel inferior con botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar campo");
        btnAgregar.addActionListener(e -> agregarParCampos());

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPareo(eje));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnGuardar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarParCampos() {
        JTextField campoPalabra = new JTextField();
        JTextField campoDefinicion = new JTextField();

        camposPalabra.add(campoPalabra);
        camposDefinicion.add(campoDefinicion);

        panelPareo.add(campoPalabra);
        panelPareo.add(campoDefinicion);

        panelPareo.revalidate();
        panelPareo.repaint();
    }

    private void guardarPareo(PareoPanel eje) {
        pares.clear();

        String enunciado = txtEnunciado.getText().trim();
        String puntaje = txtPuntaje.getText().trim();
        int puntajeInt = Integer.parseInt(puntaje);

        // Validaciones
        if (enunciado.isEmpty() || puntaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que no haya campos vacíos
        for (int i = 0; i < camposPalabra.size(); i++) {
            String palabra = camposPalabra.get(i).getText().trim();
            String definicion = camposDefinicion.get(i).getText().trim();

            if (palabra.isEmpty() || definicion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las palabras o definiciones.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregar al mapa
            pares.put(palabra, definicion);
        }
        try{
                eje.setEnunciado(enunciado);
                eje.setPuntaje(puntajeInt);
                eje.setPares(pares);
                this.dispose();
            }catch(IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
            }

        

        // Cerrar ventana
        this.dispose();
    }

    // ---------- Getters ----------
    public String getEnunciado() {
        return txtEnunciado.getText().trim();
    }

    public int getPuntaje() {
        try {
            return Integer.parseInt(txtPuntaje.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Map<String, String> getPares() {
        return pares;
    }
    }

    class VentanaSopaLetrasMod extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JPanel panelPalabras;
        private ArrayList<JTextField> camposPalabra;
        private ArrayList<JTextField> camposDefinicion;
        private Map<String, String> mapaPalabras;

        public VentanaSopaLetrasMod(SopaDeLetrasPanel eje) {
            setTitle("Ejercicio de Sopa de Letras");
            setSize(600, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            mapaPalabras = new LinkedHashMap<>();
            camposPalabra = new ArrayList<>();
            camposDefinicion = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central con columnas Palabra / Definición ----------
            panelPalabras = new JPanel();
            panelPalabras.setLayout(new GridLayout(0, 2, 10, 10));
            panelPalabras.setBorder(BorderFactory.createTitledBorder("Palabras y definiciones"));

            // Encabezados
            JLabel lblPalabra = new JLabel("Palabra", SwingConstants.CENTER);
            lblPalabra.setFont(lblPalabra.getFont().deriveFont(Font.BOLD));
            JLabel lblDefinicion = new JLabel("Definición", SwingConstants.CENTER);
            lblDefinicion.setFont(lblDefinicion.getFont().deriveFont(Font.BOLD));

            panelPalabras.add(lblPalabra);
            panelPalabras.add(lblDefinicion);

            // Agregar 10 pares por defecto
            for (int i = 0; i < 10; i++) {
                agregarParCampos();
            }

            JScrollPane scroll = new JScrollPane(panelPalabras);
            add(scroll, BorderLayout.CENTER);

            // ---------- Panel inferior con botones ----------
            JPanel panelBotones = new JPanel(new FlowLayout());

            JButton btnAgregar = new JButton("Agregar campo");
            btnAgregar.addActionListener(e -> agregarParCampos());

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> guardarMapa(eje));

            panelBotones.add(btnAgregar);
            panelBotones.add(btnGuardar);

            add(panelBotones, BorderLayout.SOUTH);
        }

        private void agregarParCampos() {
            JTextField campoPalabra = new JTextField();
            JTextField campoDefinicion = new JTextField();

            camposPalabra.add(campoPalabra);
            camposDefinicion.add(campoDefinicion);

            panelPalabras.add(campoPalabra);
            panelPalabras.add(campoDefinicion);

            panelPalabras.revalidate();
            panelPalabras.repaint();
        }

        private void guardarMapa(SopaDeLetrasPanel eje) {
            mapaPalabras.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            int puntajeInt = Integer.parseInt(puntaje);

            // Validaciones
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que no haya campos vacíos
            for (int i = 0; i < camposPalabra.size(); i++) {
                String palabra = camposPalabra.get(i).getText().trim();
                String definicion = camposDefinicion.get(i).getText().trim();

                if (palabra.isEmpty() || definicion.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las palabras o definiciones.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mapaPalabras.put(palabra.toUpperCase(), definicion);
            }

        try{
                    eje.setEnunciado(enunciado);
                    eje.setPuntaje(puntajeInt);
                    eje.setPalabras(mapaPalabras);
                        this.dispose();
                }catch(IllegalArgumentException w) {
                            JOptionPane.showMessageDialog(this,
                                    "Error:\n" + w.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }


            // Cerrar ventana
            this.dispose();
        }

        // ---------- Getters ----------
        public String getEnunciado() {
            return txtEnunciado.getText().trim();
        }

        public int getPuntaje() {
            try {
                return Integer.parseInt(txtPuntaje.getText().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        public Map<String, String> getMapaPalabras() {
            return mapaPalabras;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    class VentanaEliminarEjercicios extends JFrame {
    private Evaluaciones evaluacion;
    private JPanel panelBotones;

    public VentanaEliminarEjercicios(Evaluaciones evaluacion) {
        this.evaluacion = evaluacion;

        setTitle("Ejercicios de la Evaluación " + evaluacion.getIdentificacion());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(0, 1, 5, 5));
        add(new JScrollPane(panelBotones), BorderLayout.CENTER);

        cargarBotones();
    }

    private void cargarBotones() {
        panelBotones.removeAll(); // limpiar panel antes de volver a cargar

        for (Ejercicios e : evaluacion.getEjercicios()) {
            JButton btn = new JButton(e.getEnunciado());
            panelBotones.add(btn);

            btn.addActionListener(ev -> {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que desea eliminar este ejercicio?\n" + e.getEnunciado(),
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    evaluacion.getEjercicios().remove(e);
                    JOptionPane.showMessageDialog(this, "Ejercicio eliminado.");
                    cargarBotones(); // recargar lista de botones
                    this.dispose();
                }
            });
        }

        // Si ya no quedan ejercicios, mostrar mensaje
        if (evaluacion.getEjercicios().isEmpty()) {
            panelBotones.add(new JLabel("No hay ejercicios en esta evaluación.", SwingConstants.CENTER));
        }

        panelBotones.revalidate();
        panelBotones.repaint();
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    private void abrirEliminarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaEliminarEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    public class VentanaEliminarEvaluacion extends JFrame {
        private JTextField txtId;
    private JButton btnEliminar;
    private JButton btnSalir;


    
    public VentanaEliminarEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        setTitle("Eliminar Evaluación");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));



        JPanel panelId = new JPanel(new GridLayout(1, 2, 10, 10));
        panelId.setBorder(BorderFactory.createTitledBorder("Datos de la evaluación"));

        panelId.add(new JLabel("ID de evaluación:"));
        txtId = new JTextField();
        panelId.add(txtId);

        add(panelId, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnEliminar = new JButton("Eliminar");
        btnSalir = new JButton("Salir");
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {this.dispose();
            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                });

        btnEliminar.addActionListener(e -> {
            String id = txtId.getText().trim();
            
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de evaluación.");
                return;
            }
            
            int idInt = Integer.parseInt(id);
            Evaluaciones encontrada = null;
            for (Evaluaciones eva1 : sistema.getEvaluaciones()) {
                if (eva1 != null && eva1.getGrupo() == null && eva1.getIdentificacion() == idInt) {
                    encontrada = eva1;
                    break;
                }
            }

            if (encontrada != null) {
                sistema.getEvaluaciones().remove(encontrada); // ✅ ya fuera del for
                JOptionPane.showMessageDialog(this, "Evaluación eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Evaluación no encontrada o está asociada a un grupo.");
            }
        });
    }

    
    } 
    
    
    
    class VentanaOpcionUnica extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JTextField txtCorrecta;
        private JPanel panelOtrasOpciones;
        private ArrayList<JTextField> camposOtras;
        private ArrayList<String> opciones;  // ← donde se guardan todas las opciones

        public VentanaOpcionUnica(ArrayList<Ejercicios> listaEjercicios) {
            setTitle("Opción Única");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            opciones = new ArrayList<>();
            camposOtras = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central: Correcta y Otras ----------
            JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

            // Columna izquierda - Opción correcta
            JPanel panelCorrecta = new JPanel(new BorderLayout(5, 5));
            panelCorrecta.setBorder(BorderFactory.createTitledBorder("Opción correcta"));
            txtCorrecta = new JTextField();
            panelCorrecta.add(txtCorrecta, BorderLayout.NORTH);
            panelCentro.add(panelCorrecta);

            // Columna derecha - Otras opciones
            JPanel panelDerecha = new JPanel(new BorderLayout(5, 5));
            panelDerecha.setBorder(BorderFactory.createTitledBorder("Otras opciones"));

            panelOtrasOpciones = new JPanel();
            panelOtrasOpciones.setLayout(new BoxLayout(panelOtrasOpciones, BoxLayout.Y_AXIS));

            // Campo inicial
            agregarCampoOpcion();

            JScrollPane scroll = new JScrollPane(panelOtrasOpciones);
            panelDerecha.add(scroll, BorderLayout.CENTER);

            JButton btnAgregarCampo = new JButton("Agregar opción");
            btnAgregarCampo.addActionListener(e -> agregarCampoOpcion());
            panelDerecha.add(btnAgregarCampo, BorderLayout.SOUTH);

            panelCentro.add(panelDerecha);
            add(panelCentro, BorderLayout.CENTER);

            // ---------- Panel inferior: Botón agregar ----------
            JButton btnAgregar = new JButton("Agregar al ArrayList");
            
            btnAgregar.addActionListener(e -> {guardarOpciones();
            String enunciado = txtEnunciado.getText();
            String puntaje=txtPuntaje.getText();
            int PuntajeInt = Integer.parseInt(puntaje);
                try{
                    OpcionUnicaPanel nuevo = new OpcionUnicaPanel(enunciado,getOpciones(),0,PuntajeInt);
                    listaEjercicios.add(nuevo);
                    this.dispose();
                }catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
                    });
            add(btnAgregar, BorderLayout.SOUTH);
        }

        // Agrega un nuevo campo de texto en la columna "otras opciones"
        private void agregarCampoOpcion() {
            JTextField campo = new JTextField();
            camposOtras.add(campo);
            panelOtrasOpciones.add(campo);
            panelOtrasOpciones.revalidate();
            panelOtrasOpciones.repaint();
        }

        // Reúne toda la información y la guarda en el ArrayList
        private void guardarOpciones() {
            opciones.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            String correcta = txtCorrecta.getText().trim();

            if (enunciado.isEmpty() || puntaje.isEmpty() || correcta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar enunciado, puntaje y opción correcta");
                return;
            }

            opciones.add(correcta); // la primera es la correcta

            for (JTextField campo : camposOtras) {
                String texto = campo.getText().trim();
                if (!texto.isEmpty()) {
                    opciones.add(texto);
                }
            }

            
        }

        // (Opcional) puedes obtener la lista para integrarla a tu lista general
        public ArrayList<String> getOpciones() {
            return opciones;
        }
    }

    class VentanaOpcionMultiple extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JPanel panelCorrectas, panelOtras;
        private ArrayList<JTextField> camposCorrectas, camposOtras;
        private ArrayList<String> opciones;
        private Set<Integer> correctas;

        public VentanaOpcionMultiple(ArrayList<Ejercicios> listaEjercicios) {
            setTitle("Opción Múltiple");
            setSize(550, 420);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            opciones = new ArrayList<>();
            correctas = new HashSet<>();
            camposCorrectas = new ArrayList<>();
            camposOtras = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central con dos columnas ----------
            JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

            // Columna izquierda - Opciones correctas
            JPanel panelIzq = new JPanel(new BorderLayout(5, 5));
            panelIzq.setBorder(BorderFactory.createTitledBorder("Opciones correctas"));

            panelCorrectas = new JPanel();
            panelCorrectas.setLayout(new BoxLayout(panelCorrectas, BoxLayout.Y_AXIS));
            JScrollPane scrollCorrectas = new JScrollPane(panelCorrectas);

            JButton btnAgregarCorrecta = new JButton("Agregar correcta");
            btnAgregarCorrecta.addActionListener(e -> agregarCampoCorrecta());

            // Campo inicial
            agregarCampoCorrecta();

            panelIzq.add(scrollCorrectas, BorderLayout.CENTER);
            panelIzq.add(btnAgregarCorrecta, BorderLayout.SOUTH);

            // Columna derecha - Otras opciones
            JPanel panelDer = new JPanel(new BorderLayout(5, 5));
            panelDer.setBorder(BorderFactory.createTitledBorder("Otras opciones"));

            panelOtras = new JPanel();
            panelOtras.setLayout(new BoxLayout(panelOtras, BoxLayout.Y_AXIS));
            JScrollPane scrollOtras = new JScrollPane(panelOtras);

            JButton btnAgregarOtra = new JButton("Agregar opción");
            btnAgregarOtra.addActionListener(e -> agregarCampoOtra());

            // Campo inicial
            agregarCampoOtra();

            panelDer.add(scrollOtras, BorderLayout.CENTER);
            panelDer.add(btnAgregarOtra, BorderLayout.SOUTH);

            panelCentro.add(panelIzq);
            panelCentro.add(panelDer);

            add(panelCentro, BorderLayout.CENTER);

            // ---------- Botón inferior ----------
            
            JButton btnGuardar = new JButton("Agregar al ArrayList");
            
            btnGuardar.addActionListener(e -> {guardarOpciones();
            
            
            String enunciado = txtEnunciado.getText();
            String puntaje=txtPuntaje.getText();
            if (!puntaje.isEmpty() && !enunciado.isEmpty()){
                int PuntajeInt = Integer.parseInt(puntaje);
                try{
                OpcionMultiplePanel nuevo = new OpcionMultiplePanel(enunciado,getOpciones(),getCorrectas(),PuntajeInt);
                listaEjercicios.add(nuevo);
                this.dispose();
            }catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
                    });
            add(btnGuardar, BorderLayout.SOUTH);
        }

        private void agregarCampoCorrecta() {
            JTextField campo = new JTextField();
            camposCorrectas.add(campo);
            panelCorrectas.add(campo);
            panelCorrectas.revalidate();
            panelCorrectas.repaint();
        }

        private void agregarCampoOtra() {
            JTextField campo = new JTextField();
            camposOtras.add(campo);
            panelOtras.add(campo);
            panelOtras.revalidate();
            panelOtras.repaint();
        }

        private void guardarOpciones() {
            opciones.clear();
            correctas.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();

            // Validar enunciado y puntaje
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar enunciado y puntaje", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar campos vacíos en correctas
            boolean hayCorrectaVacia = camposCorrectas.stream().anyMatch(c -> c.getText().trim().isEmpty());
            boolean hayOtraVacia = camposOtras.stream().anyMatch(c -> c.getText().trim().isEmpty());

            if (hayCorrectaVacia || hayOtraVacia) {
                JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las opciones", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que haya al menos una correcta
            if (camposCorrectas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos una opción correcta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregar las correctas primero
            for (JTextField campo : camposCorrectas) {
                String texto = campo.getText().trim();
                correctas.add(opciones.size()); // índice actual
                opciones.add(texto);
            }

            // Agregar las otras opciones
            for (JTextField campo : camposOtras) {
                String texto = campo.getText().trim();
                opciones.add(texto);
            }

            // Mostrar confirmación
            
        }

        // Métodos para obtener los datos
        public ArrayList<String> getOpciones() {
            return opciones;
        }

        public Set<Integer> getCorrectas() {
            return correctas;
        }
    }

    class VentanaVerdaderoFalso extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JRadioButton rbVerdadero;
        private JRadioButton rbFalso;
        private boolean respuestaCorrecta; // true si es verdadero, false si es falso

        public VentanaVerdaderoFalso(ArrayList<Ejercicios> listaEjercicios) {
            setTitle("Ejercicio Verdadero / Falso");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central: opciones Verdadero/Falso ----------
            JPanel panelOpciones = new JPanel();
            panelOpciones.setBorder(BorderFactory.createTitledBorder("Seleccione la respuesta correcta"));
            panelOpciones.setLayout(new GridLayout(2, 1, 5, 5));

            ButtonGroup grupo = new ButtonGroup();

            rbVerdadero = new JRadioButton("Verdadero");
            rbFalso = new JRadioButton("Falso");

            grupo.add(rbVerdadero);
            grupo.add(rbFalso);

            panelOpciones.add(rbVerdadero);
            panelOpciones.add(rbFalso);

            add(panelOpciones, BorderLayout.CENTER);

            // ---------- Botón inferior ----------
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> {
                guardarRespuesta(listaEjercicios);
                
            });
            add(btnGuardar, BorderLayout.SOUTH);
        }

        private void guardarRespuesta(ArrayList<Ejercicios> listaEjercicios) {
            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            int puntajeInt=Integer.parseInt(puntaje);

            // Validaciones
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!rbVerdadero.isSelected() && !rbFalso.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Verdadero o Falso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar la respuesta
            respuestaCorrecta = rbVerdadero.isSelected();
            
            try{
                VerdaderoFalsoPanel nuevo = new VerdaderoFalsoPanel(enunciado,respuestaCorrecta,puntajeInt);
                listaEjercicios.add(nuevo);
                this.dispose();
            }catch(IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
            
            
            
                 
            
            
            
            
           
        }

        // Getters para obtener los datos desde fuera
        public String getEnunciado() {
            return txtEnunciado.getText().trim();
        }

        public int getPuntaje() {
            try {
                return Integer.parseInt(txtPuntaje.getText().trim());
            } catch (NumberFormatException e) {
                return 0; // por si el usuario escribió algo no numérico
            }
        }

        public boolean getRespuestaCorrecta() {
            return respuestaCorrecta;
        }
    }

    class VentanaPareo extends JFrame {
            private JTextField txtEnunciado;
    private JTextField txtPuntaje;
    private JPanel panelPareo;
    private ArrayList<JTextField> camposPalabra;
    private ArrayList<JTextField> camposDefinicion;
    private Map<String, String> pares;

    public VentanaPareo(ArrayList<Ejercicios> listaEjercicios) {
        setTitle("Ejercicio de Pareo");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pares = new LinkedHashMap<>();
        camposPalabra = new ArrayList<>();
        camposDefinicion = new ArrayList<>();

        // ---------- Panel superior: Enunciado y Puntaje ----------
        JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

        panelDatos.add(new JLabel("Enunciado:"));
        txtEnunciado = new JTextField();
        panelDatos.add(txtEnunciado);

        panelDatos.add(new JLabel("Puntaje:"));
        txtPuntaje = new JTextField();
        panelDatos.add(txtPuntaje);

        add(panelDatos, BorderLayout.NORTH);

        // ---------- Panel central: columnas Palabra / Definición ----------
        panelPareo = new JPanel();
        panelPareo.setLayout(new GridLayout(0, 2, 10, 10));
        panelPareo.setBorder(BorderFactory.createTitledBorder("Palabras y definiciones"));

        // Etiquetas de encabezado
        panelPareo.add(new JLabel("Palabra", SwingConstants.CENTER));
        panelPareo.add(new JLabel("Definición", SwingConstants.CENTER));

        // Primer par inicial
        agregarParCampos();

        JScrollPane scroll = new JScrollPane(panelPareo);
        add(scroll, BorderLayout.CENTER);

        // ---------- Panel inferior con botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar campo");
        btnAgregar.addActionListener(e -> agregarParCampos());

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPareo(listaEjercicios));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnGuardar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarParCampos() {
        JTextField campoPalabra = new JTextField();
        JTextField campoDefinicion = new JTextField();

        camposPalabra.add(campoPalabra);
        camposDefinicion.add(campoDefinicion);

        panelPareo.add(campoPalabra);
        panelPareo.add(campoDefinicion);

        panelPareo.revalidate();
        panelPareo.repaint();
    }

    private void guardarPareo(ArrayList<Ejercicios> listaEjercicios) {
        pares.clear();

        String enunciado = txtEnunciado.getText().trim();
        String puntaje = txtPuntaje.getText().trim();
        int puntajeInt = Integer.parseInt(puntaje);

        // Validaciones
        if (enunciado.isEmpty() || puntaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que no haya campos vacíos
        for (int i = 0; i < camposPalabra.size(); i++) {
            String palabra = camposPalabra.get(i).getText().trim();
            String definicion = camposDefinicion.get(i).getText().trim();

            if (palabra.isEmpty() || definicion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las palabras o definiciones.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregar al mapa
            pares.put(palabra, definicion);
        }
        try{
                PareoPanel nuevo = new PareoPanel(enunciado,pares,puntajeInt);
                listaEjercicios.add(nuevo);
                this.dispose();
            }catch(IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
            }

        

        // Cerrar ventana
        this.dispose();
    }

    // ---------- Getters ----------
    public String getEnunciado() {
        return txtEnunciado.getText().trim();
    }

    public int getPuntaje() {
        try {
            return Integer.parseInt(txtPuntaje.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Map<String, String> getPares() {
        return pares;
    }
    }

    class VentanaSopaLetras extends JFrame {
        private JTextField txtEnunciado;
        private JTextField txtPuntaje;
        private JPanel panelPalabras;
        private ArrayList<JTextField> camposPalabra;
        private ArrayList<JTextField> camposDefinicion;
        private Map<String, String> mapaPalabras;

        public VentanaSopaLetras(ArrayList<Ejercicios> listaEjercicios) {
            setTitle("Ejercicio de Sopa de Letras");
            setSize(600, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));

            mapaPalabras = new LinkedHashMap<>();
            camposPalabra = new ArrayList<>();
            camposDefinicion = new ArrayList<>();

            // ---------- Panel superior: Enunciado y Puntaje ----------
            JPanel panelDatos = new JPanel(new GridLayout(2, 2, 10, 10));
            panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));

            panelDatos.add(new JLabel("Enunciado:"));
            txtEnunciado = new JTextField();
            panelDatos.add(txtEnunciado);

            panelDatos.add(new JLabel("Puntaje:"));
            txtPuntaje = new JTextField();
            panelDatos.add(txtPuntaje);

            add(panelDatos, BorderLayout.NORTH);

            // ---------- Panel central con columnas Palabra / Definición ----------
            panelPalabras = new JPanel();
            panelPalabras.setLayout(new GridLayout(0, 2, 10, 10));
            panelPalabras.setBorder(BorderFactory.createTitledBorder("Palabras y definiciones"));

            // Encabezados
            JLabel lblPalabra = new JLabel("Palabra", SwingConstants.CENTER);
            lblPalabra.setFont(lblPalabra.getFont().deriveFont(Font.BOLD));
            JLabel lblDefinicion = new JLabel("Definición", SwingConstants.CENTER);
            lblDefinicion.setFont(lblDefinicion.getFont().deriveFont(Font.BOLD));

            panelPalabras.add(lblPalabra);
            panelPalabras.add(lblDefinicion);

            // Agregar 10 pares por defecto
            for (int i = 0; i < 10; i++) {
                agregarParCampos();
            }

            JScrollPane scroll = new JScrollPane(panelPalabras);
            add(scroll, BorderLayout.CENTER);

            // ---------- Panel inferior con botones ----------
            JPanel panelBotones = new JPanel(new FlowLayout());

            JButton btnAgregar = new JButton("Agregar campo");
            btnAgregar.addActionListener(e -> agregarParCampos());

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> guardarMapa(listaEjercicios));

            panelBotones.add(btnAgregar);
            panelBotones.add(btnGuardar);

            add(panelBotones, BorderLayout.SOUTH);
        }

        private void agregarParCampos() {
            JTextField campoPalabra = new JTextField();
            JTextField campoDefinicion = new JTextField();

            camposPalabra.add(campoPalabra);
            camposDefinicion.add(campoDefinicion);

            panelPalabras.add(campoPalabra);
            panelPalabras.add(campoDefinicion);

            panelPalabras.revalidate();
            panelPalabras.repaint();
        }

        private void guardarMapa(ArrayList<Ejercicios> listaEjercicios) {
            mapaPalabras.clear();

            String enunciado = txtEnunciado.getText().trim();
            String puntaje = txtPuntaje.getText().trim();
            int puntajeInt = Integer.parseInt(puntaje);

            // Validaciones
            if (enunciado.isEmpty() || puntaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar el enunciado y el puntaje.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que no haya campos vacíos
            for (int i = 0; i < camposPalabra.size(); i++) {
                String palabra = camposPalabra.get(i).getText().trim();
                String definicion = camposDefinicion.get(i).getText().trim();

                if (palabra.isEmpty() || definicion.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No deben haber espacios vacíos en las palabras o definiciones.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mapaPalabras.put(palabra.toUpperCase(), definicion);
            }

        try{
                    SopaDeLetrasPanel nuevo = new SopaDeLetrasPanel(enunciado,mapaPalabras,puntajeInt);
                    listaEjercicios.add(nuevo);
                    this.dispose();
                }catch(IllegalArgumentException w) {
                            JOptionPane.showMessageDialog(this,
                                    "Error:\n" + w.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }


            // Cerrar ventana
            this.dispose();
        }

        // ---------- Getters ----------
        public String getEnunciado() {
            return txtEnunciado.getText().trim();
        }

        public int getPuntaje() {
            try {
                return Integer.parseInt(txtPuntaje.getText().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        public Map<String, String> getMapaPalabras() {
            return mapaPalabras;
        }
    }
    
    
    
    
    private void abrirAdministradorEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAdministradorEstudiante(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAdministradorEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAdministradorEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnCrear = new JButton("Crear");
            JButton btnConsultar = new JButton("Consultar");
            JButton btnModificar = new JButton("Modificar");
            JButton btnEliminar = new JButton("Eliminar");
            
            
            
            
            panelBotones.add(btnCrear);
            panelBotones.add(btnConsultar);
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);
       

            add(panelBotones, BorderLayout.CENTER);
            
            btnCrear.addActionListener(e -> {
                this.dispose();
                abrirCrearEstudiante(ventanaPrincipal,tipoUsuario);
                    });
            
            btnConsultar.addActionListener(e -> {
                this.dispose();
                abrirMostrarEstudiante(ventanaPrincipal,tipoUsuario);
                    });
            
            btnModificar.addActionListener(e -> {
                this.dispose();
                abrirModificarEstudiante(ventanaPrincipal,tipoUsuario);
                    });
            
           btnEliminar.addActionListener(e -> {
                this.dispose();
                abrirEliminarEstudiante(ventanaPrincipal,tipoUsuario);
                    });
            
            
        }
    }
    private void abrirCrearEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaCrearEstudiante(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaCrearEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaCrearEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(21, 1, 5, 5));
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblPrimerapellido = new JLabel("Primer apellido: ");
            JTextField txtPrimerapellido = new JTextField();
            JLabel lblSegundoapellido = new JLabel("Segundo apellido: ");
            JTextField txtSegundoapellido = new JTextField();
            JLabel lblIdentificacion = new JLabel("Identificacion: ");
            JTextField txtIdentificacion = new JTextField();
            JLabel lblTelefono = new JLabel("Telefono:");
            JTextField txtTelefono = new JTextField();
            JLabel lblCorreoElectronico = new JLabel("Correo Electronico: ");
            JTextField txtCorreoElectronico = new JTextField();
            JLabel lblDireccion = new JLabel("Direccion: ");
            JTextField txtDireccion = new JTextField();
            JLabel lblContraseña = new JLabel("Contraseña: ");
            JPasswordField txtContraseña = new JPasswordField();
            JLabel lblOrganizacion = new JLabel("Organizacion: ");
            JTextField txtOrganizacion = new JTextField();
            JLabel lblTemasInteres = new JLabel("Temas de interes: ");
            JTextField txtTemasInteres = new JTextField();
            JButton btnCrear = new JButton("Crear");
            
            
            
            
            
            add(lblNombre); 
            add(txtNombre); 
            add(lblPrimerapellido); 
            add(txtPrimerapellido); 
            add(lblSegundoapellido); 
            add(txtSegundoapellido); 
            add(lblIdentificacion); 
            add(txtIdentificacion); 
            add(lblTelefono); 
            add(txtTelefono); 
            add(lblCorreoElectronico); 
            add(txtCorreoElectronico); 
            add(lblDireccion); 
            add(txtDireccion); 
            add(lblContraseña); 
            add(txtContraseña); 
            add(lblOrganizacion); 
            add(txtOrganizacion); 
            add(lblTemasInteres);
            add(txtTemasInteres);
            
            
            add(btnCrear);
            
           
            
            
            
            
            btnCrear.addActionListener(e -> {
                boolean verificador=true;
                
                String nombre=txtNombre.getText();
                if (nombre.length() < 2 || nombre.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el nombre debe de tener entre 2 y 20 caracteres");
                }
                
                String apellido1=txtPrimerapellido.getText();
                if (apellido1.length() < 2 || apellido1.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el primer apellido debe de tener entre 2 y 20 caracteres");
                }
                
                String apellido2=txtSegundoapellido.getText();
                if (apellido2.length() < 2 || apellido2.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el segundo apellido debe de tener entre 2 y 20 caracteres");
                }
                
                String identificacion=txtIdentificacion.getText();
                
                if (identificacion.length() < 9 || sistema.todasIdentificaciones(identificacion)) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la identificacion debe de tener 9 o mas caracteres");
                }
                
                String telefono=txtTelefono.getText();
                if (telefono.length() <8) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el numero de telefono debe de tener 9 o mas caracteres");
                }
                
                String correo=txtCorreoElectronico.getText();
                String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
                if (!correo.matches(regex) || sistema.todosCorreos(correo)==false) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "No cumple con la estructura de un correo");
                }
                
                String direccion=txtDireccion.getText();
                if (direccion.length() < 5 || direccion.length() > 60) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la direccion debe de tener entre 5 y 60 caracteres");
                }
                
                String contraseña=txtContraseña.getText();
                boolean tieneMayuscula = contraseña.matches(".*[A-Z].*");
                boolean tieneNumero    = contraseña.matches(".*[0-9].*");
                boolean tieneEspecial  = contraseña.matches(".*[^a-zA-Z0-9].*");
                if (contraseña.length() < 8 && !tieneMayuscula && !tieneNumero && !tieneEspecial) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la contraseña debe de tener 8 o mas caracteres y tener mayuscula, numero y caracter especial");
                }
                
                String organizacion=txtOrganizacion.getText();
                if (organizacion.length() > 40) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la organizacion debe de tener hasta 40 caracteres");
                }
                
                
                String intereses=txtTemasInteres.getText();
                
                ArrayList<String> listaIntereses = new ArrayList<String>();
                
                if (intereses.isEmpty()){
                    
                    JOptionPane.showMessageDialog(this, "Debe ingresar los intereses");
                }else{
                    String[] arregloIntereses = intereses.split("\\s*,\\s*");
                    for (int i = 0; i < arregloIntereses.length; i++) {
                        listaIntereses.add(arregloIntereses[i]);
                    }
                    for (String interes : listaIntereses) {
                        if (interes.length() < 5 || interes.length() > 30 ) {
                            verificador=false;
                            JOptionPane.showMessageDialog(this, "Error, cada interes debe de tener entre 5 y 30 caracteres");
                        }
                    }
                }
                
                
                
                
                
                if(verificador==true){
                    Estudiantes est = new Estudiantes(nombre,apellido1,apellido2,identificacion,telefono,correo,direccion,organizacion,listaIntereses,contraseña);
                    sistema.agregarEstudiantes(est); 
                    try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Creacion de usuario",
                                "Un usuario con el correo " + correo + " se ah creado"
                        );

                        JOptionPane.showMessageDialog(this,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    
                    this.dispose();
                    abrirAdministrador(ventanaPrincipal,tipoUsuario);
                }
                
            });
        }
    }
    
    
    
    
    private void abrirMostrarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaMostrarEstudiante(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaMostrarEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaMostrarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del estudiante: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnNombre = new JButton("Nombre");
            
            JButton btnPrimerApellido = new JButton("Primer Apellido");
            JButton btnSegundoApellido = new JButton("Segundo Apellido");

            JButton btnTelefono = new JButton("Telefono");
            JButton btnCorreo = new JButton("Correo");
            JButton btnDireccion = new JButton("Direccion");
            JButton btnOrganizacion = new JButton("Organizacion");
            JButton btnTemasInteres = new JButton("Temas de interes");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnPrimerApellido);
            panelBotones.add(btnSegundoApellido);
            panelBotones.add(btnTelefono);
            panelBotones.add(btnCorreo);
            panelBotones.add(btnDireccion);
            panelBotones.add(btnOrganizacion);
            panelBotones.add(btnTemasInteres);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            
            
            btnNombre.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(1,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Nombre");
                }
                
            });
            
            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(2,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Primer apellido");
                }
            });
            
            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(3,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Segundo apellido");
                }
            });
            
            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(4,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Telefono");
                }
            });
            
            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(5,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Correo");
                }
            });
            
            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(6,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Direccion");
                }
            });
            
            btnOrganizacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(7,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Organizacion");
                }
            });
            
            btnTemasInteres.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(8,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,null,"Temas de interes");
                }
            });
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    private void abrirModificarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaModificarEstudiante(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaModificarEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaModificarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del estudiante: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnIdentificacion = new JButton("Identificacion");
            JButton btnNombre = new JButton("Nombre");
            
            JButton btnPrimerApellido = new JButton("Primer Apellido");
            JButton btnSegundoApellido = new JButton("Segundo Apellido");

            JButton btnTelefono = new JButton("Telefono");
            JButton btnCorreo = new JButton("Correo");
            JButton btnDireccion = new JButton("Direccion");
            JButton btnOrganizacion = new JButton("Organizacion");
            JButton btnTemasInteres = new JButton("Temas de interes");
            JButton btnContraseña = new JButton("Contraseña");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnIdentificacion);
            panelBotones.add(btnPrimerApellido);
            panelBotones.add(btnSegundoApellido);
            panelBotones.add(btnTelefono);
            panelBotones.add(btnCorreo);
            panelBotones.add(btnDireccion);
            panelBotones.add(btnOrganizacion);
            panelBotones.add(btnTemasInteres);
            panelBotones.add(btnContraseña);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            
            
            btnNombre.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Nnombre = JOptionPane.showInputDialog(this,
                        "Ingrese el nuevo nombre del estudiante:");
                if (Nnombre.length() >= 2 && Nnombre.length() <= 20 && sistema.devolverEstudiante(1, identificacion, Nnombre, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Nombre");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
                
            });
            
            btnIdentificacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Nidentificacion = JOptionPane.showInputDialog(this,
                        "Ingrese la nueva identificacion del estudiante:");
                if (Nidentificacion.length() >= 9 && sistema.todasIdentificaciones(Nidentificacion)==false && sistema.devolverEstudiante(10, identificacion, Nidentificacion, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,Nidentificacion),this,null,"Identificacion");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
                
            });
            
            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Napellido1 = JOptionPane.showInputDialog(this,
                        "Ingrese el nuevo primer apellido del estudiante:");
                if (Napellido1.length() >= 2 && Napellido1.length() <= 20 && sistema.devolverEstudiante(2, identificacion, Napellido1, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Primer Apellido");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Napellido2 = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del estudiante:");
                if (Napellido2.length() >= 2 && Napellido2.length() <= 20 && sistema.devolverEstudiante(3, identificacion, Napellido2, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Segundo Apellido");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ntelefono = JOptionPane.showInputDialog(this,
                        "Ingrese el telefono del estudiante:");
                if (Ntelefono.length() >= 8 && sistema.devolverEstudiante(4, identificacion, Ntelefono, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Telefono");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
                String Ncorreo = JOptionPane.showInputDialog(this,
                        "Ingrese el telefono del estudiante:");
                if (Ncorreo.matches(regex) && sistema.devolverEstudiante(5, identificacion, Ncorreo, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Correo");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ndireccion = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del estudiante:");
                if (Ndireccion.length() >= 5 && Ndireccion.length() <= 60 && sistema.devolverEstudiante(6, identificacion, Ndireccion, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Direccion");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnOrganizacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Norganizacion = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del estudiante:");
                if (Norganizacion.length() <= 40 && sistema.devolverEstudiante(7, identificacion, Norganizacion, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Organizacion");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            btnTemasInteres.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Nintereses = JOptionPane.showInputDialog(this,
                        "Ingrese los temas de interes del estudiante:");
                
                ArrayList<String> listaIntereses = new ArrayList<String>();
                
                if (Nintereses.isEmpty()){
                    
                    JOptionPane.showMessageDialog(this, "Debe ingresar los intereses");
                }else{
                    String[] arregloIntereses = Nintereses.split("\\s*,\\s*");
                    for (int i = 0; i < arregloIntereses.length; i++) {
                        listaIntereses.add(arregloIntereses[i]);
                    }
                    for (String interes : listaIntereses) {
                        if (interes.length() >= 5 && interes.length() <= 30 && sistema.devolverEstudiante(8, identificacion, null, listaIntereses)==true) {
                           
                            JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                            correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,null,"Temas de interes");
                        }else{
                            JOptionPane.showMessageDialog(this, "Modificacion erronea");
                        }
                    }
                }
            });
            
            btnContraseña.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                
                String NContraseña = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del estudiante:");
                boolean tieneMayuscula = NContraseña.matches(".*[A-Z].*");
                boolean tieneNumero    = NContraseña.matches(".*[0-9].*");
                boolean tieneEspecial  = NContraseña.matches(".*[^a-zA-Z0-9].*");
                if (NContraseña.length() >= 8 && tieneMayuscula && tieneNumero && tieneEspecial && sistema.devolverEstudiante(9, identificacion, NContraseña, null)==true){
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this, null,"Contraseña");
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            
            
            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    
    
    
    private void abrirEliminarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaEliminarEstudiante(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaEliminarEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaEliminarEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del estudiante: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();
            
            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnEliminar = new JButton("Eliminar");
            JButton btnSalir = new JButton("Salir");
            
            panelBotones.add(btnEliminar);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            btnEliminar.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                if (sistema.todasIdentificaciones(identificacion)){
                    sistema.eliminarEstudiantes(identificacion);
                    JOptionPane.showMessageDialog(this, "Eliminacion exitosa");
                    correoEliminar(sistema.encontrarEstudiante(5,identificacion),this, null);
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro al estudiante");
                }
                
            });
            

            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }




    private void abrirAdministradorProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAdministradorProfesor(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAdministradorProfesor extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAdministradorProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    //guardarInformacionAlCerrar();

                    // 🔹 Mostramos confirmación opcional
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea salir del programa?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION

                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        sistema.guardar();
                        System.out.println("Cerrando aplicación...");
                        System.exit(0); // 🔹 Cierra completamente el programa
                    }
                }
            });

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnCrear = new JButton("Crear");
            JButton btnConsultar = new JButton("Consultar");
            JButton btnModificar = new JButton("Modificar");
            JButton btnEliminar = new JButton("Eliminar");




            panelBotones.add(btnCrear);
            panelBotones.add(btnConsultar);
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);


            add(panelBotones, BorderLayout.CENTER);

            btnCrear.addActionListener(e -> {
                this.dispose();
                abrirCrearProfesor(ventanaPrincipal,tipoUsuario);
            });

            btnConsultar.addActionListener(e -> {
                this.dispose();
                abrirMostrarProfesor(ventanaPrincipal,tipoUsuario);
            });

            btnModificar.addActionListener(e -> {
                this.dispose();
                abrirModificarProfesor(ventanaPrincipal,tipoUsuario);
            });

            btnEliminar.addActionListener(e -> {
                this.dispose();
                abrirEliminarProfesor(ventanaPrincipal,tipoUsuario);
            });


        }
    }
    private void abrirCrearProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaCrearProfesor(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaCrearProfesor extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaCrearProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            setLayout(new GridLayout(21, 1, 5, 5));

            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblPrimerapellido = new JLabel("Primer apellido: ");
            JTextField txtPrimerapellido = new JTextField();
            JLabel lblSegundoapellido = new JLabel("Segundo apellido: ");
            JTextField txtSegundoapellido = new JTextField();
            JLabel lblIdentificacion = new JLabel("Identificacion: ");
            JTextField txtIdentificacion = new JTextField();
            JLabel lblTelefono = new JLabel("Telefono:");
            JTextField txtTelefono = new JTextField();
            JLabel lblCorreoElectronico = new JLabel("Correo Electronico: ");
            JTextField txtCorreoElectronico = new JTextField();
            JLabel lblDireccion = new JLabel("Direccion: ");
            JTextField txtDireccion = new JTextField();
            JLabel lblContraseña = new JLabel("Contraseña: ");
            JPasswordField txtContraseña = new JPasswordField();
            JLabel lblCertificaciones = new JLabel("Certificaciones del profesor: ");
            JTextField txtCertificaciones = new JTextField();
            JLabel lblTitulos = new JLabel("Titulos del profesor: ");
            JTextField txtTitulos = new JTextField();
            JButton btnCrear = new JButton("Crear");





            add(lblNombre);
            add(txtNombre);
            add(lblPrimerapellido);
            add(txtPrimerapellido);
            add(lblSegundoapellido);
            add(txtSegundoapellido);
            add(lblIdentificacion);
            add(txtIdentificacion);
            add(lblTelefono);
            add(txtTelefono);
            add(lblCorreoElectronico);
            add(txtCorreoElectronico);
            add(lblDireccion);
            add(txtDireccion);
            add(lblContraseña);
            add(txtContraseña);
            add(lblCertificaciones);
            add(txtCertificaciones);
            add(lblTitulos);
            add(txtTitulos);


            add(btnCrear);






            btnCrear.addActionListener(e -> {
                boolean verificador=true;

                String nombre=txtNombre.getText();
                if (nombre.length() < 2 || nombre.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el nombre debe de tener entre 2 y 20 caracteres");
                }

                String apellido1=txtPrimerapellido.getText();
                if (apellido1.length() < 2 || apellido1.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el primer apellido debe de tener entre 2 y 20 caracteres");
                }

                String apellido2=txtSegundoapellido.getText();
                if (apellido2.length() < 2 || apellido2.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el segundo apellido debe de tener entre 2 y 20 caracteres");
                }

                String identificacion=txtIdentificacion.getText();

                if (identificacion.length() < 9 || sistema.todasIdentificaciones(identificacion)) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la identificacion debe de tener 9 o mas caracteres");
                }

                String telefono=txtTelefono.getText();
                if (telefono.length() <8) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, el numero de telefono debe de tener 9 o mas caracteres");
                }

                String correo=txtCorreoElectronico.getText();
                String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
                if (!correo.matches(regex) || sistema.todosCorreos(correo)==false) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "No cumple con la estructura de un correo");
                }

                String direccion=txtDireccion.getText();
                if (direccion.length() < 5 || direccion.length() > 60) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la direccion debe de tener entre 5 y 60 caracteres");
                }

                String contraseña=txtContraseña.getText();
                boolean tieneMayuscula = contraseña.matches(".*[A-Z].*");
                boolean tieneNumero    = contraseña.matches(".*[0-9].*");
                boolean tieneEspecial  = contraseña.matches(".*[^a-zA-Z0-9].*");
                if (contraseña.length() < 8 && !tieneMayuscula && !tieneNumero && !tieneEspecial) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Error, la contraseña debe de tener 8 o mas caracteres y tener mayuscula, numero y caracter especial");
                }

                String certificaciones=txtCertificaciones.getText();

                ArrayList<String> listaCertifiaciones = new ArrayList<String>();

                if (certificaciones.isEmpty()){

                    JOptionPane.showMessageDialog(this, "Debe ingresar las certificaciones");
                }else{
                    String[] arregloCertificaciones = certificaciones.split("\\s*,\\s*");
                    for (int i = 0; i < arregloCertificaciones.length; i++) {
                        listaCertifiaciones.add(arregloCertificaciones[i]);
                    }
                    for (String sCertificaciones : listaCertifiaciones) {
                        if (sCertificaciones.length() < 5 || sCertificaciones.length() > 40 ) {
                            verificador=false;
                            JOptionPane.showMessageDialog(this, "Error, cada certificacion debe de tener entre 5 y 40 caracteres");
                        }
                    }
                }

                String titulos=txtTitulos.getText();

                ArrayList<String> listaTitulos = new ArrayList<String>();

                if (titulos.isEmpty()){

                    JOptionPane.showMessageDialog(this, "Debe ingresar los titulos");
                }else{
                    String[] arregloTitulos = titulos.split("\\s*,\\s*");
                    for (int i = 0; i < arregloTitulos.length; i++) {
                        listaTitulos.add(arregloTitulos[i]);
                    }
                    for (String sTitulos : listaTitulos) {
                        if (sTitulos.length() < 5 || sTitulos.length() > 40 ) {
                            verificador=false;
                            JOptionPane.showMessageDialog(this, "Error, cada titulo debe de tener entre 5 y 40 caracteres");
                        }
                    }
                }





                if(verificador==true){
                    Profesores prf = new Profesores(nombre,apellido1,apellido2,identificacion,telefono,correo,direccion,contraseña,listaCertifiaciones,listaTitulos);
                    sistema.agregarProfesores(prf);
                    try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Creacion de usuario",
                                "Un usuario con el correo " + correo + " se ah creado"
                        );

                        JOptionPane.showMessageDialog(this,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }


                    this.dispose();
                    abrirAdministrador(ventanaPrincipal,tipoUsuario);
                }

            });
        }
    }




    private void abrirMostrarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaMostrarProfesor(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaMostrarProfesor extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaMostrarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });




            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del profesor: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnNombre = new JButton("Nombre");

            JButton btnPrimerApellido = new JButton("Primer Apellido");
            JButton btnSegundoApellido = new JButton("Segundo Apellido");

            JButton btnTelefono = new JButton("Telefono");
            JButton btnCorreo = new JButton("Correo");
            JButton btnDireccion = new JButton("Direccion");
            JButton btnCertificaciones = new JButton("Certificaciones del profesor");
            JButton btnTitulos = new JButton("Titulos del profesor");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnPrimerApellido);
            panelBotones.add(btnSegundoApellido);
            panelBotones.add(btnTelefono);
            panelBotones.add(btnCorreo);
            panelBotones.add(btnDireccion);
            panelBotones.add(btnCertificaciones);
            panelBotones.add(btnTitulos);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);




            btnNombre.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(1,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Nombre");
                }

            });

            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(2,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Primer apellido");
                }
            });

            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(3,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Segundo apellido");
                }
            });

            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(4,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Telefono");
                }
            });

            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(5,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Correo");
                }
            });

            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(6,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Direccion");
                }
            });

            btnCertificaciones.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(7,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Certificaciones");
                }
            });

            btnTitulos.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarProfesor(8,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                    correoConsulta(sistema.encontrarProfesor(5,identificacion),null, this,"Titulos");
                }
            });

            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });

        }
    }
    private void abrirModificarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaModificarProfesor(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaModificarProfesor extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaModificarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            setLayout(new BorderLayout());

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del profesor: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnIdentificacion = new JButton("Identificacion");
            JButton btnNombre = new JButton("Nombre");

            JButton btnPrimerApellido = new JButton("Primer Apellido");
            JButton btnSegundoApellido = new JButton("Segundo Apellido");

            JButton btnTelefono = new JButton("Telefono");
            JButton btnCorreo = new JButton("Correo");
            JButton btnDireccion = new JButton("Direccion");
            JButton btnCertificaciones = new JButton("Certificaciones");
            JButton btnTtitulos = new JButton("Titulos");
            JButton btnContraseña = new JButton("Contraseña");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnIdentificacion);
            panelBotones.add(btnPrimerApellido);
            panelBotones.add(btnSegundoApellido);
            panelBotones.add(btnTelefono);
            panelBotones.add(btnCorreo);
            panelBotones.add(btnDireccion);
            panelBotones.add(btnCertificaciones);
            panelBotones.add(btnTtitulos);
            panelBotones.add(btnContraseña);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);




            btnNombre.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Nnombre = JOptionPane.showInputDialog(this,
                        "Ingrese el nuevo nombre del profesor:");
                if (Nnombre.length() >= 2 && Nnombre.length() <= 20 && sistema.devolverProfesor(1, identificacion, Nnombre, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Nombre");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }

            });

            btnIdentificacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Nidentificacion = JOptionPane.showInputDialog(this,
                        "Ingrese la nueva identificacion del profesor:");
                if (Nidentificacion.length() >= 9 && sistema.todasIdentificaciones(Nidentificacion)==false && sistema.devolverProfesor(10, identificacion, Nidentificacion, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,Nidentificacion),null, this,"Identificacion");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }

            });

            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Napellido1 = JOptionPane.showInputDialog(this,
                        "Ingrese el nuevo primer apellido del profesor:");
                if (Napellido1.length() >= 2 && Napellido1.length() <= 20 && sistema.devolverProfesor(2, identificacion, Napellido1, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Primer Apellido");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });

            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Napellido2 = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del profesor:");
                if (Napellido2.length() >= 2 && Napellido2.length() <= 20 && sistema.devolverProfesor(3, identificacion, Napellido2, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Segundo Apellido");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });

            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ntelefono = JOptionPane.showInputDialog(this,
                        "Ingrese el telefono del profesor:");
                if (Ntelefono.length() >= 8 && sistema.devolverProfesor(4, identificacion, Ntelefono, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Telefono");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });

            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
                String Ncorreo = JOptionPane.showInputDialog(this,
                        "Ingrese el telefono del profesor:");
                if (Ncorreo.matches(regex) && sistema.devolverProfesor(5, identificacion, Ncorreo, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Correo");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });

            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ndireccion = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del profesor:");
                if (Ndireccion.length() >= 5 && Ndireccion.length() <= 60 && sistema.devolverProfesor(6, identificacion, Ndireccion, null, null)==true){
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Direccion");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });

            btnCertificaciones.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ncertificaciones = JOptionPane.showInputDialog(this,
                        "Ingrese las certificaciones del profesor:");

                ArrayList<String> listaCertificaciones = new ArrayList<String>();

                if (Ncertificaciones.isEmpty()){

                    JOptionPane.showMessageDialog(this, "Debe ingresar las certificaciones");
                }else{
                    String[] arregloTitulos = Ncertificaciones.split("\\s*,\\s*");
                    for (int i = 0; i < arregloTitulos.length; i++) {
                        listaCertificaciones.add(arregloTitulos[i]);
                    }
                    for (String certificaciones : listaCertificaciones) {
                        if (certificaciones.length() >= 5 && certificaciones.length() <= 40 && sistema.devolverProfesor(8, identificacion, null, listaCertificaciones, null)==true) {

                            JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                            correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Certificaciones");
                        }else{
                            JOptionPane.showMessageDialog(this, "Modificacion erronea");
                        }
                    }
                }
            });

            btnTtitulos.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                String Ntitulos = JOptionPane.showInputDialog(this,
                        "Ingrese los titulos del profesor:");

                ArrayList<String> listaTitulos = new ArrayList<String>();

                if (Ntitulos.isEmpty()){

                    JOptionPane.showMessageDialog(this, "Debe ingresar los titulos");
                }else{
                    String[] arregloTitulos = Ntitulos.split("\\s*,\\s*");
                    for (int i = 0; i < arregloTitulos.length; i++) {
                        listaTitulos.add(arregloTitulos[i]);
                    }
                    for (String titulos : listaTitulos) {
                        if (titulos.length() >= 5 && titulos.length() <= 40 && sistema.devolverProfesor(8, identificacion, null, null, listaTitulos)==true) {

                            JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                            correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Titulos");
                        }else{
                            JOptionPane.showMessageDialog(this, "Modificacion erronea");
                        }
                    }
                }
            });

            btnContraseña.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();

                String NContraseña = JOptionPane.showInputDialog(this,
                        "Ingrese el segundo apellido del profesor:");
                boolean tieneMayuscula = NContraseña.matches(".*[A-Z].*");
                boolean tieneNumero    = NContraseña.matches(".*[0-9].*");
                boolean tieneEspecial  = NContraseña.matches(".*[^a-zA-Z0-9].*");
                if (NContraseña.length() >= 8 && tieneMayuscula && tieneNumero && tieneEspecial && sistema.devolverProfesor(9, identificacion, NContraseña, null,null)==true){
                    correoModificacion(sistema.encontrarProfesor(5,identificacion),null, this,"Contraseña");
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });




            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });

        }
    }



    private void abrirEliminarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaEliminarProfesor(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaEliminarProfesor extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaEliminarProfesor(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });




            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del profesor: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);

            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnEliminar = new JButton("Eliminar");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnEliminar);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);


            btnEliminar.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                if (sistema.todasIdentificaciones(identificacion)){
                    sistema.eliminarProfesor(identificacion);
                    JOptionPane.showMessageDialog(this, "Eliminacion exitosa");
                    correoEliminar(sistema.encontrarProfesor(5,identificacion),null, this);
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro al profesor");
                }

            });



            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });

        }
    }







    private void abrirAdministradorCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAdministradorCurso(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAdministradorCurso extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAdministradorCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnCrear = new JButton("Crear");
            JButton btnConsultar = new JButton("Consultar");
            JButton btnModificar = new JButton("Modificar");
            JButton btnEliminar = new JButton("Eliminar");
            
            
            
            
            panelBotones.add(btnCrear);
            panelBotones.add(btnConsultar);
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);
       

            add(panelBotones, BorderLayout.CENTER);
            
            btnCrear.addActionListener(e -> {
                this.dispose();
                abrirCrearCurso(ventanaPrincipal,tipoUsuario);
                    });
            
            btnConsultar.addActionListener(e -> {
                this.dispose();
                abrirMostrarCurso(ventanaPrincipal,tipoUsuario);
                    });
            
            btnModificar.addActionListener(e -> {
                this.dispose();
                abrirModificarCurso(ventanaPrincipal,tipoUsuario);
                    });
            
           btnEliminar.addActionListener(e -> {
                this.dispose();
                abrirEliminarCurso(ventanaPrincipal,tipoUsuario);
                    });
            
            
        }
    }
    private void abrirCrearCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaCrearCurso(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaCrearCurso extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaCrearCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(21, 1, 5, 5));
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JLabel lblIdentificacion = new JLabel("Identificacion: ");
            JTextField txtIdentificacion = new JTextField();
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblDescripcion = new JLabel("Descripcion: ");
            JTextField txtDescripcion = new JTextField();
            JLabel lblHorasporDia = new JLabel("Horas por dia: ");
            JTextField txtHorasporDia = new JTextField();
            JLabel lblModalidad = new JLabel("Modalidad:");
            JTextField txtModalidad = new JTextField();
            
            
            JLabel lblminEstudiantes = new JLabel("Minimo de estudiantes: ");
            JTextField txtminEstudiantes = new JTextField();
            
            
            JLabel lblmaxEstudiantes = new JLabel("Maximo de estudiantes: ");
            JTextField txtmaxEstudiantes = new JTextField();
            
            
            JLabel lblTipoCurso = new JLabel("Tipo de curso: ");
            JTextField txtTipoCurso = new JTextField();
            
            
            
            JLabel lblcalMinima = new JLabel("Calificacion minima: ");
            JTextField txtcalMinima = new JTextField();
            
            
            
            
            
            JButton btnCrear = new JButton("Crear");
            
            
            
            
            
            add(lblNombre); 
            add(txtNombre); 
            add(lblIdentificacion); 
            add(txtIdentificacion); 
            add(lblDescripcion); 
            add(txtDescripcion); 
            add(lblHorasporDia); 
            add(txtHorasporDia); 
            add(lblModalidad); 
            add(txtModalidad); 
            add(lblminEstudiantes); 
            add(txtminEstudiantes); 
            add(lblmaxEstudiantes); 
            add(txtmaxEstudiantes); 
            add(lblTipoCurso); 
            add(txtTipoCurso); 
            add(lblcalMinima); 
            add(txtcalMinima); 
            
            
            
            add(btnCrear);
            
           
            
            
            
            
            btnCrear.addActionListener(e -> {
                
                String nombre = txtNombre.getText().trim();
                String identificacion = txtIdentificacion.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String modalidad = txtModalidad.getText().trim();
                String tipoCurso = txtTipoCurso.getText().trim();
                String horasTxt = txtHorasporDia.getText().trim();
                String minTxt = txtminEstudiantes.getText().trim();
                String maxTxt = txtmaxEstudiantes.getText().trim();
                String calTxt = txtcalMinima.getText().trim();

                if (nombre.isEmpty() || identificacion.isEmpty() || descripcion.isEmpty() ||
                    modalidad.isEmpty() || tipoCurso.isEmpty() || horasTxt.isEmpty() ||
                    minTxt.isEmpty() || maxTxt.isEmpty() || calTxt.isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos antes de continuar.");
                    return; // salir del método para evitar errores
                }else{
                    int horas = Integer.parseInt(horasTxt);
                    int min = Integer.parseInt(minTxt);
                    int max = Integer.parseInt(maxTxt);
                    int cal = Integer.parseInt(calTxt);
                    try{
                        Cursos cur = new Cursos(identificacion,nombre,descripcion,horas,modalidad,min,max,tipoCurso,cal);
                        sistema.agregarCursos(cur);
                        this.dispose();
                        abrirAdministrador(ventanaPrincipal,tipoUsuario);
                    } catch(IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(this,
                                    "Error:\n" + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } 
            });
        }
    }
    
    
    
    
    private void abrirMostrarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaMostrarCurso(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaMostrarCurso extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaMostrarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del curso: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            
            
                /*
            private String identificacion;
            private String nombre;
            private String descripcion;
            private int horasPorDia;
            private String modalidad;
            private int minEstudiantes;
            private int maxEstudiantes;
            private String tipoCurso;
            private int calificacionMinima;
            */
            
            
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnNombre = new JButton("Nombre");
            
            JButton btnDescripcion = new JButton("Descripcion");
            JButton btnHorasDia = new JButton("Horas por dia");

            JButton btnModalidad = new JButton("Modalidad");
            JButton btnMin = new JButton("Minimo de estudiantes");
            JButton btnMax = new JButton("Maximo de estudaintes");
            JButton btnTipoCurso = new JButton("Tipo de curso");
            JButton btnCal = new JButton("Calificacion minima");
            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnDescripcion);
            panelBotones.add(btnHorasDia);
            panelBotones.add(btnModalidad);
            panelBotones.add(btnMin);
            panelBotones.add(btnMax);
            panelBotones.add(btnTipoCurso);
            panelBotones.add(btnCal);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            
            
            btnNombre.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                if(sistema.devCursos(identificacion)!=null){
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getNombre());
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro el curso");
                }
                
                
                
            });
            
            btnDescripcion.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getDescripcion());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnHorasDia.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getHorasPorDia());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnModalidad.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getModalidad());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnMin.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getMinEstudiantes());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnMax.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getMaxEstudiantes());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnTipoCurso.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getTipoCurso());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });

            btnCal.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    JOptionPane.showMessageDialog(this, sistema.devCursos(identificacion).getCalificacionMinima());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso");
                }
            });
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    private void abrirModificarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaModificarCurso(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaModificarCurso extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaModificarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del estudiante: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();

            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnIdentificacion = new JButton("Identificacion");
            JButton btnNombre = new JButton("Nombre");
            
            JButton btnDescripcion = new JButton("Descripcion");
            JButton btnHorasDia = new JButton("Horas por dia");

            JButton btnModalidad = new JButton("Modalidad");
            JButton btnMinEstudiantes = new JButton("Minimo de estudiantes");
            JButton btnMaxEstudiantes = new JButton("Maximo de estudiantes");
            JButton btnTipoCurso = new JButton("Tipo de curso");
            JButton btnCal = new JButton("Calificacion minima");

            JButton btnSalir = new JButton("Salir");

            panelBotones.add(btnNombre);
            panelBotones.add(btnIdentificacion);
            panelBotones.add(btnDescripcion);
            panelBotones.add(btnHorasDia);
            panelBotones.add(btnModalidad);
            panelBotones.add(btnMinEstudiantes);
            panelBotones.add(btnMaxEstudiantes);
            panelBotones.add(btnTipoCurso);
            panelBotones.add(btnCal);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            
            
            btnIdentificacion.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    String nuevaId = JOptionPane.showInputDialog(this, "Ingrese la nueva identificación del curso:");
                    try {
                        if (nuevaId != null && sistema.devCursos(nuevaId) == null) {
                            sistema.devCursos(identificacion).setIdentificacion(nuevaId);
                            JOptionPane.showMessageDialog(this, "Modificación exitosa");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error: la identificación ya existe o es inválida");
                        }
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnNombre.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre del curso:");
                    try {
                        if (nuevoNombre != null) {
                            sistema.devCursos(identificacion).setNombre(nuevoNombre);
                            JOptionPane.showMessageDialog(this, "Modificación exitosa");
                        }
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnDescripcion.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    String nuevaDescripcion = JOptionPane.showInputDialog(this, "Ingrese la nueva descripción del curso:");
                    try {
                        if (nuevaDescripcion != null) {
                            sistema.devCursos(identificacion).setDescripcion(nuevaDescripcion);
                            JOptionPane.showMessageDialog(this, "Modificación exitosa");
                        }
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this,
                                "Error:\n" + w.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnHorasDia.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    try {
                        int nuevasHoras = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese las nuevas horas por día:"));
                        sistema.devCursos(identificacion).setHorasPorDia(nuevasHoras);
                        JOptionPane.showMessageDialog(this, "Modificación exitosa");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnModalidad.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    String nuevaModalidad = JOptionPane.showInputDialog(this, "Ingrese la nueva modalidad del curso:");
                    try {
                        if (nuevaModalidad != null) {
                            sistema.devCursos(identificacion).setModalidad(nuevaModalidad);
                            JOptionPane.showMessageDialog(this, "Modificación exitosa");
                        }
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnMinEstudiantes.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    try {
                        int nuevoMin = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la nueva cantidad mínima de estudiantes:"));
                        sistema.devCursos(identificacion).setMinEstudiantes(nuevoMin);
                        JOptionPane.showMessageDialog(this, "Modificación exitosa");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnMaxEstudiantes.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    try {
                        int nuevoMax = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la nueva cantidad máxima de estudiantes:"));
                        sistema.devCursos(identificacion).setMaxEstudiantes(nuevoMax);
                        JOptionPane.showMessageDialog(this, "Modificación exitosa");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnTipoCurso.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    String nuevoTipo = JOptionPane.showInputDialog(this, "Ingrese el nuevo tipo de curso:");
                    try {
                        if (nuevoTipo != null) {
                            sistema.devCursos(identificacion).setTipoCurso(nuevoTipo);
                            JOptionPane.showMessageDialog(this, "Modificación exitosa");
                        }
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCal.addActionListener(e -> {
                String identificacion = txtIdentificacion.getText();
                if (sistema.devCursos(identificacion) != null) {
                    try {
                        int nuevaCal = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la nueva calificación mínima:"));
                        sistema.devCursos(identificacion).setCalificacionMinima(nuevaCal);
                        JOptionPane.showMessageDialog(this, "Modificación exitosa");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException w) {
                        JOptionPane.showMessageDialog(this, "Error:\n" + w.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            
            
            
            
            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    
    
    
    private void abrirEliminarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaEliminarCurso(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaEliminarCurso extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaEliminarCurso(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            

            JPanel panelIdentificacion = new JPanel(new BorderLayout());
            JLabel lblIdentificacion = new JLabel("Identificacion del curso: ", SwingConstants.CENTER);
            lblIdentificacion.setFont(new Font("Arial", Font.BOLD, 16));

            JTextField txtIdentificacion = new JTextField();
            
            panelIdentificacion.add(lblIdentificacion, BorderLayout.NORTH);
            panelIdentificacion.add(txtIdentificacion, BorderLayout.CENTER);
            
            panelIdentificacion.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
            add(panelIdentificacion, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnEliminar = new JButton("Eliminar");
            JButton btnSalir = new JButton("Salir");
            
            panelBotones.add(btnEliminar);
            panelBotones.add(btnSalir);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
            add(panelBotones, BorderLayout.CENTER);
            
            
            btnEliminar.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                if (sistema.devCursos(identificacion)!=null){
                    sistema.eliminarCursos(identificacion);
                    JOptionPane.showMessageDialog(this, "Eliminacion exitosa");
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro al estudiante");
                }
                
            });
            

            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    
    private void abrirAsociarCursoGrupo(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAsociarCursoGrupo(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAsociarCursoGrupo extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAsociarCursoGrupo(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(6, 1, 5, 5));

            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            JLabel lblIdentificacionCurso = new JLabel("Identificacion de curso:");
            JTextField txtIdentificacionCurso = new JTextField();
            JLabel lblFechaInicio = new JLabel("Fecha de inicio (dd/MM/yyyy):");
            JTextField txtFechaInicio = new JTextField();
            JLabel lblFechaFinal = new JLabel("Fecha de finalizacion (dd/MM/yyyy):");
            JTextField txtFechaFinal = new JTextField();
            JButton btnAsociar = new JButton("Asociar");
            

            add(lblIdentificacionCurso);
            add(txtIdentificacionCurso);
            add(lblFechaInicio);
            add(txtFechaInicio);
            add(lblFechaFinal);
            add(txtFechaFinal);
            add(btnAsociar);
            

                        
            
            btnAsociar.addActionListener(e ->{
                String identificacionCurso=txtIdentificacionCurso.getText();
                String fechaInicio = txtFechaInicio.getText();
                String fechaFinal = txtFechaFinal.getText();
                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaIni = LocalDate.parse(fechaInicio, formato);
                    LocalDate fechaFin = LocalDate.parse(fechaFinal, formato);
                    if (sistema.devCursos(identificacionCurso)!=null && fechaIni.isBefore(fechaFin)){
                        sistema.devCursos(identificacionCurso).crearGrupo(fechaIni, fechaFin,sistema.devCursos(identificacionCurso));
                        this.dispose();
                        abrirAdministrador(ventanaPrincipal,tipoUsuario);
                    }else{
                        JOptionPane.showMessageDialog(this, "Revisar identificacion del curso o fechas");
                    }
                    JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                } catch (DateTimeParseException w) {
                    JOptionPane.showMessageDialog(this, "Formato inválido. Debe ser dd/MM/yyyy");
                }
                
                
                
            });
            

            
            
            
        }
    }
    
    
    
    private void abrirAsociarProfesorGrupo(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaAsociarProfesorGrupo(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAsociarProfesorGrupo extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAsociarProfesorGrupo(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(7, 1, 5, 5));

            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JLabel lblIdentificacionCurso = new JLabel("Identificacion de curso:");
            JTextField txtIdentificacionCurso = new JTextField();
            JLabel lblIdentificacionGrupo = new JLabel("Identificacion de grupo:");
            JTextField txtIdentificacionGrupo = new JTextField();
            JLabel lblIdentificacionProfesor = new JLabel("Identificaion del profesor:");
            JTextField txtIdentificacionProfesor = new JTextField();
            JButton btnAsociar = new JButton("Asociar");
            
            add(lblIdentificacionCurso);
            add(txtIdentificacionCurso);
            add(lblIdentificacionGrupo);
            add(txtIdentificacionGrupo);
            add(lblIdentificacionProfesor);
            add(txtIdentificacionProfesor);

            add(btnAsociar);
            

                        
            
            btnAsociar.addActionListener(e ->{
                String identificacionCurso=txtIdentificacionCurso.getText();
                String identificacionGrupo=txtIdentificacionGrupo.getText();
                String identificacionProfesor=txtIdentificacionProfesor.getText();
                

                try {
                    int idGrupo= Integer.parseInt(identificacionGrupo);
                    if (sistema.devCursos(identificacionCurso)!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo)!=null && sistema.devProfesor(identificacionProfesor)!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo).getProfesor()==null){
                        sistema.devCursos(identificacionCurso).devGrupos(idGrupo).asignarProfesor(sistema.devProfesor(identificacionProfesor));
                        this.dispose();
                        abrirAdministrador(ventanaPrincipal,tipoUsuario);
                        JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                    }else{
                        JOptionPane.showMessageDialog(this, "Revisar identificaciones o el grupo ya contiene un profesor");
                    }
                    
                } catch (NumberFormatException w) {
                    JOptionPane.showMessageDialog(this, "Formato inválido de la identificacion de grupo");
                }
                
                
                
            });
            

            
            
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void abrirMatriculaEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario,Estudiantes est) {
        new VentanaMatriculaEstudiante(ventanaPrincipal,tipoUsuario,est).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaMatriculaEstudiante extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaMatriculaEstudiante(ProjectoPOO ventanaPrincipal,String tipoUsuario,Estudiantes est){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(7, 1, 5, 5));

            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JLabel lblIdentificacionCurso = new JLabel("Identificacion de curso:");
            JTextField txtIdentificacionCurso = new JTextField();
            JLabel lblIdentificacionGrupo = new JLabel("Identificacion de grupo:");
            JTextField txtIdentificacionGrupo = new JTextField();
            
            JButton btnAsociar = new JButton("Asociar");
            JButton btnAtras = new JButton("Atras");
            
            
            add(lblIdentificacionCurso);
            add(txtIdentificacionCurso);
            add(lblIdentificacionGrupo);
            add(txtIdentificacionGrupo);
            

            add(btnAsociar);
            
            add(btnAtras);
                        
            
            btnAsociar.addActionListener(e ->{
                String identificacionCurso=txtIdentificacionCurso.getText();
                String identificacionGrupo=txtIdentificacionGrupo.getText();
                
                

                try {
                    
                    int idGrupo= Integer.parseInt(identificacionGrupo);
                    
                    if (sistema.devCursos(identificacionCurso)!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo)!=null && est!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo).getEstudiantes().contains(est)==false && sistema.verificarEstudiante(identificacionCurso, est)){
                        sistema.devCursos(identificacionCurso).devGrupos(idGrupo).agregarEstudiantes(est);
                        JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                        this.dispose();
                        abrirEstudiante(ventanaPrincipal,tipoUsuario,est);
                        
                    }else{
                        JOptionPane.showMessageDialog(this, "Revisar identificaciones o el grupo ya contiene ese estudiante");
                    }
                } catch (NumberFormatException w) {
                    JOptionPane.showMessageDialog(this, "Formato inválido de la identificacion de grupo");
                }
                
                
                
            });
            

            btnAtras.addActionListener(e ->{
                this.dispose();
                abrirEstudiante(ventanaPrincipal,tipoUsuario,est);

                
                
                
            });
            
            
        }
    }
    
    
    
    
    
    
    private void abrirAsociarGrupoEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaAsociarGrupoEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaAsociarGrupoEvaluacion extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaAsociarGrupoEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(12, 1, 5, 5));

            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JLabel lblIdentificacionCurso = new JLabel("Identificacion de curso:");
            JTextField txtIdentificacionCurso = new JTextField();
            JLabel lblIdentificacionGrupo = new JLabel("Identificacion de grupo:");
            JTextField txtIdentificacionGrupo = new JTextField();
            JLabel lblIdentificacionProfesor = new JLabel("Identificaion de la evaluacion:");
            JTextField txtIdentificacionProfesor = new JTextField();
            JLabel lblFechaInicial = new JLabel("Fecha de inicio:");
            JTextField txtFechaInicial = new JTextField();
            JLabel lblHoraInicial = new JLabel("Hora de inicio:");
            JTextField txtHoraInicial = new JTextField();
            JButton btnAsociar = new JButton("Asociar");
            JButton btnDesasociar = new JButton("Desasociar");
            
            add(lblIdentificacionCurso);
            add(txtIdentificacionCurso);
            add(lblIdentificacionGrupo);
            add(txtIdentificacionGrupo);
            add(lblIdentificacionProfesor);
            add(txtIdentificacionProfesor);
            add(lblFechaInicial);
            add(txtFechaInicial);
            add(lblHoraInicial);
            add(txtHoraInicial);

            add(btnAsociar);
            add(btnDesasociar);

                        
            
            btnAsociar.addActionListener(e ->{
                String identificacionCurso=txtIdentificacionCurso.getText();
                String identificacionGrupo=txtIdentificacionGrupo.getText();
                String identificacionEvaluacion=txtIdentificacionProfesor.getText();
                try{
                    String fechaStr = txtFechaInicial.getText().trim();
                    String horaStr = txtHoraInicial.getText().trim();
                    LocalDate fecha = LocalDate.parse(fechaStr);
                    LocalTime hora = LocalTime.parse(horaStr);
                    LocalDateTime fechaHoraInicial = LocalDateTime.of(fecha, hora);

                    try {
                        int idGrupo= Integer.parseInt(identificacionGrupo);
                        int idEvaluacion = Integer.parseInt(identificacionEvaluacion);
                        if (sistema.devCursos(identificacionCurso)!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo)!=null && sistema.devEva(idEvaluacion)!=null&&sistema.devEva(idEvaluacion).getGrupo()==null){
                            LocalDateTime fechaHoraFinal = fechaHoraInicial.plusMinutes(sistema.devEva(idEvaluacion).getDur());
                            
                            sistema.devEva(idEvaluacion).setHoraDeInicio(fechaHoraInicial);
                            sistema.devEva(idEvaluacion).setHoraDeFinal(fechaHoraFinal);
                            
                            
                            sistema.devCursos(identificacionCurso).devGrupos(idGrupo).asignarEvaluacion(sistema.devEva(idEvaluacion));
                            sistema.devEva(idEvaluacion).agregarGrupo(sistema.devCursos(identificacionCurso).devGrupos(idGrupo));
                            JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                            this.dispose();
                            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                            
                        }else{
                            JOptionPane.showMessageDialog(this, "Revisar identificaciones o la evaluacion ya esta asociada");
                        }

                    } catch (NumberFormatException w) {
                        JOptionPane.showMessageDialog(this, "Formato inválido de la identificacion de grupo");
                    }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: revisa los formatos (yyyy-MM-dd y HH:mm)");
                }

                
                
                
                
            });
            

            btnDesasociar.addActionListener(e ->{
                String identificacionCurso=txtIdentificacionCurso.getText();
                String identificacionGrupo=txtIdentificacionGrupo.getText();
                String identificacionEvaluacion=txtIdentificacionProfesor.getText();
               

                    try {
                        int idGrupo= Integer.parseInt(identificacionGrupo);
                        int idEvaluacion = Integer.parseInt(identificacionEvaluacion);
                        if (sistema.devCursos(identificacionCurso)!=null && sistema.devCursos(identificacionCurso).devGrupos(idGrupo)!=null && sistema.devEva(idEvaluacion)!=null&&sistema.devEva(idEvaluacion).getGrupo()!=null){
                            
                            

                            
                            
                            sistema.devCursos(identificacionCurso).devGrupos(idGrupo).eliminarEvaluacion(sistema.devEva(idEvaluacion));
                            sistema.devEva(idEvaluacion).agregarGrupo(null);
                            this.dispose();
                            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                            JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                        }else{
                            JOptionPane.showMessageDialog(this, "Revisar identificaciones o la evaluacion ya esta asociada");
                        }

                    } catch (NumberFormatException w) {
                        JOptionPane.showMessageDialog(this, "Formato inválido de la identificacion de grupo");
                    }
               

                
                
                
                
            });
            
            
        }
    }
    
    
    private void abrirPrevisualizacionEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        new VentanaPrevisualizacionEvaluacion(ventanaPrincipal,tipoUsuario,prof).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    public class VentanaPrevisualizacionEvaluacion extends JFrame {
        private JTextField txtId;
    private JButton btnConsultar;
    private JButton btnSalir;


    
    public VentanaPrevisualizacionEvaluacion(ProjectoPOO ventanaPrincipal,String tipoUsuario,Profesores prof) {
        setTitle("Consultar Evaluación");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));



        JPanel panelId = new JPanel(new GridLayout(1, 2, 10, 10));
        panelId.setBorder(BorderFactory.createTitledBorder("Datos de la evaluación"));

        panelId.add(new JLabel("ID de evaluación:"));
        txtId = new JTextField();
        panelId.add(txtId);

        add(panelId, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnConsultar = new JButton("Previsualizar");
        btnSalir = new JButton("Salir");
        panelBotones.add(btnConsultar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {this.dispose();
            abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                });

        btnConsultar.addActionListener(e -> {
            String id = txtId.getText().trim();
            int idInt = Integer.parseInt(id);
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de evaluación.");
                return;
            }
            if (!sistema.getEvaluaciones().isEmpty()){
                for (Evaluaciones eva1 : sistema.getEvaluaciones()){
                if (eva1 != null && eva1.getIdentificacion()==idInt && eva1.getFechaInicio()!=null && eva1.getFechaFin()!=null && eva1.getFechaInicio().isBefore(LocalDateTime.now()) && eva1.getFechaFin().isAfter(LocalDateTime.now())) {
                    eva1.iniciarProfesor();
                    this.dispose();
                    abrirProfesor(ventanaPrincipal,tipoUsuario,prof);
                } else {
                    JOptionPane.showMessageDialog(this, "Evaluación no encontrada o sin fechas.");
                }
            }
            }else{
                JOptionPane.showMessageDialog(this, "No hay evaluaciones creadas.");
            }
            
        });
    }
    
    
    }
    
    
    
    
    
    
    private void abrirEvaluacionesPendientes(ProjectoPOO ventanaPrincipal,String tipoUsuario,Estudiantes est) {
        new VentanaEvaluacionesPendientes(ventanaPrincipal,tipoUsuario,est).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    
    
    public class VentanaEvaluacionesPendientes extends JFrame {

    private Map<String, ArrayList<Evaluaciones>> mapaEvaluaciones;
    private String claveBuscada;
    private int idBuscado;

    public VentanaEvaluacionesPendientes(ProjectoPOO ventanaPrincipal,String tipoUsuario , Estudiantes est) {

        this.claveBuscada = est.getNombre();
  

        setTitle("Evaluaciones disponibles");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        revisarEvaluaciones(ventanaPrincipal,tipoUsuario,est);

        setVisible(true);
    }

    private void revisarEvaluaciones(ProjectoPOO ventanaPrincipal,String tipoUsuario ,Estudiantes est) {
        boolean hayBotones = false;
        for (Cursos curso : sistema.getCursos()) {
            for (Grupos grupo : curso.getGrupos()) {
                if(grupo.getEstudiantes().contains(est)){
                    mapaEvaluaciones = grupo.getRespuestasAlumnos();
                    for (Evaluaciones eva : grupo.getEvaluaciones()) {

                        boolean agregarBoton = false;

                        // Caso 1: no existe la clave en el mapa
                        if (!mapaEvaluaciones.containsKey(claveBuscada)) {
                            agregarBoton = true;
                        } else {
                            // Caso 2: la clave existe pero ninguna evaluación coincide en id
                            ArrayList<Evaluaciones> lista = mapaEvaluaciones.get(claveBuscada);
                            boolean encontrada = false;

                            for (Evaluaciones ev : lista) {
                                if (ev.getIdentificacion() == eva.getIdentificacion()) {
                                    encontrada = true;
                                    break;
                                }
                            }

                            if (!encontrada) {
                                agregarBoton = true;
                            }
                        }

                        // Crear el botón dinámicamente si corresponde
                        if (agregarBoton) {
                            hayBotones = true;

                            JPanel panelInfo = new JPanel();
                            panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
                            panelInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                            panelInfo.setBackground(new Color(245, 245, 245));
                            panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

                            JLabel lblCurso = new JLabel("Curso: " + curso.getIdentificacion());
                            JLabel lblGrupo = new JLabel("Grupo: " + grupo.getIdGrupo());
                            JLabel lblEvaluacion = new JLabel("Evaluación: " + eva.getIdentificacion());
                            JLabel lblEvaluacionNom = new JLabel("Evaluación: " + eva.getNombre());
                            JLabel lblEvaluacionFI = new JLabel("Evaluación: " + eva.getFechaInicio());
                            JLabel lblEvaluacionFF = new JLabel("Evaluación: " + eva.getFechaFin());

                            JButton btnIniciar = new JButton("Iniciar evaluación");
                            btnIniciar.addActionListener(e -> {
                                
                                eva.iniciar();
                                abrirEstudiante(ventanaPrincipal,tipoUsuario,est);
                                dispose();  // cerrar la ventana al presionar
                            });

                            panelInfo.add(lblCurso);
                            panelInfo.add(lblGrupo);
                            panelInfo.add(lblEvaluacion);
                            panelInfo.add(lblEvaluacionNom);
                            panelInfo.add(lblEvaluacionFI);
                            panelInfo.add(lblEvaluacionFF);
                            panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
                            panelInfo.add(btnIniciar);
                            panelInfo.add(Box.createRigidArea(new Dimension(0, 10)));

                            add(panelInfo);
                        }
                }   }
            }
        }
        if (!hayBotones) {
            add(new JLabel("No se encontraron evaluaciones disponibles para iniciar."));
            JButton btnSalir = new JButton("Salir");
            add(btnSalir);
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirEstudiante(ventanaPrincipal,tipoUsuario,est);
            });
            
        }

        revalidate();
        repaint();
    }
    
    }
    
    
    
    
    
    
    
    
    
    
    
    private void abrirReportes(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaReportes(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaReportes extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaReportes(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(3, 1, 5, 5));

            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            
            JButton btnLista = new JButton("Lista de estudiantes");
            JButton btnEsta = new JButton("Estadistica de matricula");
            JButton btnSalir = new JButton("Salir");
            

            
            add(btnLista);
            add(btnEsta);
            add(btnSalir);
            

                        
            
            btnLista.addActionListener(e ->{
                try{
                    sistema.ReporteEstudiantesCurso();
                    JOptionPane.showMessageDialog(this, "Reporte realizado correctamente");
                }catch (Exception r){
                    JOptionPane.showMessageDialog(this, "Error");
                }
                
            });
            
            btnEsta.addActionListener(e ->{
                
                JFrame ventana = new JFrame("Seleccionar Fecha y Opciones");
                ventana.setSize(400, 300);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventana.setLayout(null);

                // Campo de fecha
                JLabel lblFecha = new JLabel("Fecha (dd/MM/yyyy):");
                lblFecha.setBounds(20, 20, 150, 25);
                ventana.add(lblFecha);

                JTextField txtFecha = new JTextField();
                txtFecha.setBounds(180, 20, 150, 25);
                ventana.add(txtFecha);

                // Radio buttons
                JRadioButton rbTodos = new JRadioButton("Todos");
                rbTodos.setBounds(20, 60, 150, 25);
                ventana.add(rbTodos);

                JRadioButton rbCurso = new JRadioButton("Curso específico");
                rbCurso.setBounds(20, 90, 150, 25);
                ventana.add(rbCurso);

                JRadioButton rbGrupo = new JRadioButton("Grupo específico");
                rbGrupo.setBounds(20, 120, 150, 25);
                ventana.add(rbGrupo);

                // Agrupar para que solo se pueda seleccionar uno
                ButtonGroup grupoOpciones = new ButtonGroup();
                grupoOpciones.add(rbTodos);
                grupoOpciones.add(rbCurso);
                grupoOpciones.add(rbGrupo);

                // Campos para identificaciones
                JLabel lblCurso = new JLabel("ID Curso:");
                lblCurso.setBounds(200, 90, 100, 25);
                ventana.add(lblCurso);
                JTextField txtCurso = new JTextField();
                txtCurso.setBounds(260, 90, 100, 25);
                ventana.add(txtCurso);

                JLabel lblGrupo = new JLabel("ID Grupo:");
                lblGrupo.setBounds(200, 120, 100, 25);
                ventana.add(lblGrupo);
                JTextField txtGrupo = new JTextField();
                txtGrupo.setBounds(260, 120, 100, 25);
                ventana.add(txtGrupo);

                // Inicialmente deshabilitados
                txtCurso.setEnabled(false);
                txtGrupo.setEnabled(false);
                lblCurso.setEnabled(false);
                lblGrupo.setEnabled(false);

                // Listeners para habilitar campos según radio button
                rbTodos.addActionListener(w -> {
                    txtCurso.setEnabled(false);
                    txtGrupo.setEnabled(false);
                    lblCurso.setEnabled(false);
                    lblGrupo.setEnabled(false);
                });

                rbCurso.addActionListener(w -> {
                    txtCurso.setEnabled(true);
                    lblCurso.setEnabled(true);
                    txtGrupo.setEnabled(false);
                    lblGrupo.setEnabled(false);
                });

                rbGrupo.addActionListener(w -> {
                    txtCurso.setEnabled(true);
                    lblCurso.setEnabled(true);
                    txtGrupo.setEnabled(true);
                    lblGrupo.setEnabled(true);
                });

                // Botón de acción
                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setBounds(150, 200, 100, 30);
                ventana.add(btnAceptar);
                
                
                ventana.setVisible(true);
                btnAceptar.addActionListener(w -> {
                    String fechaStr = txtFecha.getText();
                    
                    
                    try {
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaIni = LocalDate.parse(fechaStr, formato);
                        if (rbTodos.isSelected()) {
                            sistema.ReporteEstadistica(1,null,null,fechaIni);
                            JOptionPane.showMessageDialog(ventana, "Reporte realizado correctamente");
                        } else if (rbCurso.isSelected()) {
                            String idCurso = txtCurso.getText();
                            sistema.ReporteEstadistica(2,idCurso,null,fechaIni);
                            JOptionPane.showMessageDialog(ventana, "Reporte realizado correctamente");
                        } else if (rbGrupo.isSelected()) {
                            String idCurso = txtCurso.getText();
                            String idGrupo = txtGrupo.getText();
                            sistema.ReporteEstadistica(3,idCurso,idGrupo,fechaIni);
                            JOptionPane.showMessageDialog(ventana, "Reporte realizado correctamente");
                        } else {
                            JOptionPane.showMessageDialog(ventana, "Seleccione una opción");
                        }
                        ventana.dispose();
                    } catch (DateTimeParseException ex) {
                        
                        JOptionPane.showMessageDialog(ventana, "Fecha inválida");
                        
                    }

                    
                });

                ventana.setVisible(true);
            });
            

            
            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministrador(ventanaPrincipal,tipoUsuario);
            });
        }
    }
    
    
    
    
    
    
    
    
    
    
    public static void correoModificacion(String correo, VentanaModificarEstudiante VentanaModificarEstudiante, VentanaModificarProfesor VentanaModificarProfesor, String dato){
        if(VentanaModificarProfesor==null){
            try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Modificacion de usuario ("+dato+")",
                                "Se han modificado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaModificarEstudiante,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaModificarEstudiante,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }else{
            try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Modificacion de usuario ("+dato+")",
                                "Se han modificado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaModificarProfesor,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaModificarProfesor,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }
        
    }
    
    public static void correoConsulta(String correo, VentanaMostrarEstudiante VentanaMostrarEstudiante, VentanaMostrarProfesor VentanaMostrarProfesor, String dato){
        if(VentanaMostrarProfesor==null){
            try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Consulta de usuario ("+dato+")",
                                "Se han consultado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaMostrarEstudiante,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaMostrarEstudiante,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }else{
            try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Consulta de usuario ("+dato+")",
                                "Se han consultado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaMostrarProfesor,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaMostrarProfesor,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }
        
    }
    
    public static void correoEliminar(String correo, VentanaEliminarEstudiante VentanaEliminarEstudiante, VentanaEliminarProfesor VentanaEliminarProfesor){
        if(VentanaEliminarProfesor==null){
                    try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Eliminacion de usuario",
                                "Se ah eliminado el usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaEliminarEstudiante,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaEliminarEstudiante,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }else{
            try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Eliminacion de usuario",
                                "Se ah eliminado el usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaEliminarProfesor,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaEliminarProfesor,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }

    }

    public String generarContrasenaTemporal() {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        }

    public void correoRestablecer(String correo, VentanaLogin VentanaLogin,String tempPassword){
        
        
        try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Restablecimiento de contraseña",
                                "Tu nueva contraseña temporal es: " + tempPassword
                        );

                        JOptionPane.showMessageDialog(this,
                                "Se ha enviado un correo a " + correo +
                                        "\ncon tu nueva contraseña temporal.");

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
    }
    
    
    
    private void prueba(ProjectoPOO ventanaPrincipal,String tipoUsuario) {
        new VentanaPrueba(ventanaPrincipal,tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }
    private class VentanaPrueba extends JFrame{
        private ProjectoPOO ventanaPrincipal;
        public VentanaPrueba(ProjectoPOO ventanaPrincipal,String tipoUsuario){
            this.ventanaPrincipal = ventanaPrincipal;
            setTitle("Usuario: - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 850);
            setLocationRelativeTo(null);
            
            setLayout(new GridLayout(3, 1, 5, 5));

            
            addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                //guardarInformacionAlCerrar();

                // 🔹 Mostramos confirmación opcional
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea salir del programa?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                    
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    sistema.guardar();
                    System.out.println("Cerrando aplicación...");
                    System.exit(0); // 🔹 Cierra completamente el programa
                }
            }
        });
            
            /*this.identificacion = identificacion;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.objetivos = objetivos;
        
        this.duracion = duracion;
        this.pregAl = pregAl;
        this.resAl = resAl;
        this.ejercicios = ejercicios;
        */
            ArrayList<String> obj = new ArrayList<>(Arrays.asList(
    "Manzanahrthsrhr",
    "Banasrthsrthsrno",
    "Pesrthsrthsrtra",
    "Sansrthsrthsrsrtdía"
));
            ArrayList<Ejercicios> ejercicios = new ArrayList<>();

        // Ejercicio 1: Opción Única
        ArrayList<String> opciones1 = new ArrayList<>(Arrays.asList(
            "Un archivo fuente de Java se guarda con extensión .jav",
            "Un archivo fuente de Java se guarda con extensión .java",
            "Un archivo fuente de Java se guarda con extensión .jar",
            "Un archivo fuente de Java se guarda con extensión .class"
        ));
        OpcionUnicaPanel e1 = new OpcionUnicaPanel(
            "¿Cuál es la extensión correcta de un archivo fuente de Java?",
            opciones1,
            1, // índice correcto
            10 // puntaje
        );

        // Ejercicio 2: Opción Múltiple
        ArrayList<String> opciones2 = new ArrayList<>(Arrays.asList(
            "for",
            "switch",
            "if",
            "repeat"
        ));
        Set<Integer> correctas2 = new HashSet<>(Arrays.asList(0, 1, 2)); // correctas
        OpcionMultiplePanel e2 = new OpcionMultiplePanel(
            "Selecciona todas las estructuras de control válidas en Java:",
            opciones2,
            correctas2,
            15
        );

        // Ejercicio 3: Pareo
        LinkedHashMap<String, String> pares = new LinkedHashMap<>();
        pares.put("int", "Número entero");
        pares.put("double", "Número decimal");
        pares.put("boolean", "Verdadero o falso");
        PareoPanel e3 = new PareoPanel(
            "Relaciona cada tipo de dato con su descripción:",
            pares,
            20
        );
        
        Map<String, String> palabras = new LinkedHashMap<>();
        palabras.put("POLIFORMISMOS", "Poliformismos");
        palabras.put("ADIOS", "Adios");
        palabras.put("MESAS", "Mesas");
        palabras.put("SILLAS", "Sillas");
        palabras.put("ARBOL", "Arbol");
        palabras.put("USTED", "Usted");
        palabras.put("FAMILIA", "Familia");
        palabras.put("FUEGO", "Fuego");
        palabras.put("FINAL", "Final");
        palabras.put("SOPA", "Sopa");

        SopaDeLetrasPanel e4 = new SopaDeLetrasPanel(
            "Encuentra las palabras relacionadas con informática:",
            palabras, // tablero aleatorio
            15
        );
        
        
        
        // Agregar todos los ejercicios
        ejercicios.add(e1);
        ejercicios.add(e2);
        ejercicios.add(e3);
        ejercicios.add(e4);
            Evaluaciones eva1 = new Evaluaciones(1,"Patos","Hacer todas las evaluaciones",obj,120,true,true);
            for (Ejercicios ej : ejercicios){
                eva1.agregarEjercicio(ej);
            }
            LocalDate fecha1 = LocalDate.of(2025, 10, 30);

            // Fecha 2: 1 de noviembre de 2025
            LocalDate fecha2 = LocalDate.of(2025, 11, 1);
            Grupos grupo = new Grupos(1,fecha1,fecha2);
            grupo.asignarEvaluacion(eva1);
            eva1.agregarGrupo(grupo);
            eva1.setNombreEst("Andres");
            
            eva1.iniciar();
            
            
            
            
            
            
            
            
        
        
        }
        
    }
    
    
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectoPOO().setVisible(true));
       
    }
}


