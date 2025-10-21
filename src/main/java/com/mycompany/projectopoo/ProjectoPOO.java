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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            JLabel lblHora = new JLabel("Administrador accedió en: " + ahora.format(formato));
            
            add(lblHora);
            
            JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton btnEstudiante = new JButton("Estudiante");
            JButton btnProfesor = new JButton("Profesor");
            JButton btnSalir = new JButton("Salir");
            
            
            panelBotones.add(btnEstudiante);
            panelBotones.add(btnProfesor);
            panelBotones.add(btnSalir);

            add(panelBotones, BorderLayout.CENTER);

        // Acción para cada botón
        btnEstudiante.addActionListener(e -> {
            this.dispose();
            abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
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
                if (nombre.length() < 3) {
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
            });
            
            btnPrimerApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(2,identificacion));
            });
            
            btnSegundoApellido.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(3,identificacion));
            });
            
            btnTelefono.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(4,identificacion));
            });
            
            btnCorreo.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(5,identificacion));
            });
            
            btnDireccion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(6,identificacion));
            });
            
            btnOrganizacion.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(7,identificacion));
            });
            
            btnTemasInteres.addActionListener(e ->{
                String identificacion=txtIdentificacion.getText();
                JOptionPane.showMessageDialog(this, sistema.encontrarEstudiante(8,identificacion));
            });
            btnSalir.addActionListener(e ->{
                this.dispose();
                abrirAdministradorEstudiante(ventanaPrincipal,tipoUsuario);
            });
            
        }
    }
    
    
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectoPOO().setVisible(true));
    }
}


