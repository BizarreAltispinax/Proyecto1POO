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

/**
 * Ventana de inicio de sesión con opción de restablecer contraseña.
 * Envía un correo real usando Outlook (SMTP).
 */
public class ProjectoPOO extends JFrame {
    Sistema sistema = new Sistema();
    /**
     * Constructor de la clase VentanaPrincipal.
     * <p>
     * Configura los componentes gráficos iniciales: botones para elegir
     * el tipo de usuario y un título.
     * </p>
     */
    public ProjectoPOO() {
        setTitle("Sistema de Inicio");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
                abrirAdministrador(this,"Administrador");
        
        });

        btnEstudiante.addActionListener(e -> {
            
            abrirLogin("Estudiante");
            
                });
        btnProfesor.addActionListener(e -> {
            
            abrirLogin("Profesor");
            
                });
        
        
        
    }

    /**
     * Abre la ventana de inicio de sesión correspondiente al tipo de usuario indicado.
     *
     * @param tipoUsuario Tipo de usuario que intenta iniciar sesión ("Estudiante" o "Profesor").
     */
    private void abrirLogin(String tipoUsuario) {
        new VentanaLogin(tipoUsuario).setVisible(true);
        this.dispose(); // Cierra la ventana principal
    }

    /**
     * Clase interna que representa la ventana de inicio de sesión.
     * <p>
     * Contiene campos para ingresar usuario y contraseña, y un botón para validar la entrada.
     * </p>
     */
    private class VentanaLogin extends JFrame {

        public VentanaLogin(String tipoUsuario) {
            setTitle("Inicio de sesión - "+tipoUsuario);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(350, 250);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(6, 1, 5, 5));

            JLabel lblUsuario = new JLabel("Usuario:");
            JTextField txtUsuario = new JTextField();
            JLabel lblContrasena = new JLabel("Contraseña:");
            JPasswordField txtContrasena = new JPasswordField();
            JButton btnIngresar = new JButton("Ingresar");
            JButton btnRestablecer = new JButton("Restablecer contraseña");

            add(lblUsuario);
            add(txtUsuario);
            add(lblContrasena);
            add(txtContrasena);
            add(btnIngresar);
            add(btnRestablecer);
            String tempPassword = generarContrasenaTemporal();
            // Acción para ingresar
            btnIngresar.addActionListener(e -> {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                if (!usuario.isEmpty() && (!contrasena.isEmpty()||contrasena.equals(tempPassword))) {
                    JOptionPane.showMessageDialog(this, "Bienvenido: " + usuario);

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe ingresar usuario y contraseña.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Acción para restablecer contraseña
            btnRestablecer.addActionListener(e -> {
                String correo = JOptionPane.showInputDialog(this,
                        "Ingrese su correo Outlook para restablecer la contraseña:");

                if (correo != null && !correo.isEmpty()) {


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
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Debe ingresar un correo válido.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        /**
         * Genera una contraseña temporal aleatoria de 8 caracteres.
         *
         * @return Contraseña generada
         */
        private String generarContrasenaTemporal() {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
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
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            JLabel lblHora = new JLabel("Administrador accedió en: " + ahora.format(formato));
            
            add(lblHora);
            
            JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));
            JButton btnEstudiante = new JButton("Estudiante");
            JButton btnProfesor = new JButton("Profesor");
            JButton btnCurso = new JButton("Curso");
            JButton btnGrupo = new JButton("Grupo");
            JButton btnSalir = new JButton("Salir");
            
            
            panelBotones.add(btnEstudiante);
            panelBotones.add(btnProfesor);
            panelBotones.add(btnCurso);
            panelBotones.add(btnGrupo);
            panelBotones.add(btnSalir);

            add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btnEstudiante.addActionListener(e -> {
            this.dispose();
            abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
                });
        
        
        btnCurso.addActionListener(e -> {
            this.dispose();
            abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
                });
        
        btnGrupo.addActionListener(e -> {
            this.dispose();
            abrirAsociarCursoGrupo(ventanaPrincipal,tipoUsuario);
                });
        
        
          
        //btnProfesor.addActionListener(e ->);    
        btnSalir.addActionListener(e -> {
            this.dispose();
            ventanaPrincipal.setVisible(true); // Muestra ventana principal
                });
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
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres1");
                }
                
                String apellido1=txtPrimerapellido.getText();
                if (apellido1.length() < 2 || apellido1.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres2");
                }
                
                String apellido2=txtSegundoapellido.getText();
                if (apellido2.length() < 2 || apellido2.length() > 20) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres3");
                }
                
                String identificacion=txtIdentificacion.getText();
                
                if (identificacion.length() < 9 || sistema.todasIdentificaciones(identificacion)) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres4");
                }
                
                String telefono=txtTelefono.getText();
                if (telefono.length() <8) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres5");
                }
                
                String correo=txtCorreoElectronico.getText();
                String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
                if (!correo.matches(regex) || sistema.todosCorreos(correo)==false) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres6");
                }
                
                String direccion=txtDireccion.getText();
                if (direccion.length() < 5 || direccion.length() > 60) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres7");
                }
                
                String contraseña=txtContraseña.getText();
                boolean tieneMayuscula = contraseña.matches(".*[A-Z].*");
                boolean tieneNumero    = contraseña.matches(".*[0-9].*");
                boolean tieneEspecial  = contraseña.matches(".*[^a-zA-Z0-9].*");
                if (contraseña.length() < 8 && !tieneMayuscula && !tieneNumero && !tieneEspecial) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres8");
                }
                
                String organizacion=txtOrganizacion.getText();
                if (organizacion.length() >= 40) {
                    verificador=false;
                    JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres9");
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
                            JOptionPane.showMessageDialog(this, "Debe ingresar al menos 3 caracteres");
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
                    correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Nombre");
                }
                
            });
            
            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(2,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Primer apellido");
                }
            });
            
            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(3,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Segundo apellido");
                }
            });
            
            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(4,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Telefono");
                }
            });
            
            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(5,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Correo");
                }
            });
            
            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(6,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Direccion");
                }
            });
            
            btnOrganizacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(7,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Organizacion");
                }
            });
            
            btnTemasInteres.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(8,identificacion));
                if (sistema.todasIdentificaciones(identificacion)){
                correoConsulta(sistema.encontrarEstudiante(5,identificacion),this,"Temas de interes");
                }
            });
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Nombre");
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
                    correoModificacion(sistema.encontrarEstudiante(5,Nidentificacion),this,"Identificacion");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Primer Apellido");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Segundo Apellido");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Telefono");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Correo");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Direccion");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Organizacion");
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
                            correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Temas de interes");
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
                    correoModificacion(sistema.encontrarEstudiante(5,identificacion),this,"Contraseña");
                    JOptionPane.showMessageDialog(this, "Modificacion exitosa");
                }else{
                    JOptionPane.showMessageDialog(this, "Modificacion erronea");
                }
            });
            
            
            
            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
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
                    correoEliminar(sistema.encontrarEstudiante(5,identificacion),this);
                }else{
                    JOptionPane.showMessageDialog(this, "No se encontro al estudiante");
                }
                
            });
            

            
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
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
                int horas=Integer.parseInt(txtHorasporDia.getText());
                int min=Integer.parseInt(txtminEstudiantes.getText());
                int max=Integer.parseInt(txtmaxEstudiantes.getText());
                int cal=Integer.parseInt(txtcalMinima.getText());
                String nombre = txtNombre.getText();
                String identificacion = txtIdentificacion.getText();
                String descripcion = txtDescripcion.getText();
                String modalidad = txtModalidad.getText();
                String tipoCurso = txtTipoCurso.getText();
                
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
                abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
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
                abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
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
                abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
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
                String identificacionCurso=lblIdentificacionCurso.getText();
                String fechaInicio = lblFechaInicio.getText();
                String fechaFinal = lblFechaFinal.getText();
                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaIni = LocalDate.parse(fechaInicio, formato);
                    LocalDate fechaFin = LocalDate.parse(fechaFinal, formato);
                    if (sistema.devCursos(identificacionCurso)!=null && fechaIni.isBefore(fechaFin)){
                        sistema.devCursos(identificacionCurso).crearGrupo(fechaIni, fechaFin);
                        JOptionPane.showMessageDialog(this, "Asociacion exitosa");
                        abrirAdministradorCurso(ventanaPrincipal,tipoUsuario);
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
    
    
    
    
    
    
    
    
    
    
    
    
    public static void correoModificacion(String correo, VentanaModificarEstudiante VentanaModificarEstudiante, String dato){
        try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Modificacion de usuario ("+"dato"+")",
                                "Se han modificado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaModificarEstudiante,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaModificarEstudiante,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
    }
    
    public static void correoConsulta(String correo, VentanaMostrarEstudiante VentanaMostrarEstudiante, String dato){
        try {
                        // Enviar correo real con Outlook
                        EnviadorCorreo.enviarCorreoOutlook(
                                "andrehiva6@gmail.com",     // tu correo Outlook
                                "iang gigc rlvr uimy",    // no pongas tu contraseña normal
                                correo,
                                "Consulta de usuario ("+"dato"+")",
                                "Se han consultado datos del usuario con correo " + correo
                        );

                        JOptionPane.showMessageDialog(VentanaMostrarEstudiante,
                                "Se ha enviado un correo a " + correo);

                    } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(VentanaMostrarEstudiante,
                                "Error al enviar el correo:\n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
    }
    
    public static void correoEliminar(String correo, VentanaEliminarEstudiante VentanaEliminarEstudiante){
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
    }
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectoPOO().setVisible(true));
       
    }
}


