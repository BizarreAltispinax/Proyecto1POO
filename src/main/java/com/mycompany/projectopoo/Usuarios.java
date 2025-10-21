/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.time.*;


/**
 *
 * @author Usuario
 */
public abstract class Usuarios {
    private String nombre;                   // 2 a 20 caracteres
    private String apellido1;                // 2 a 20 caracteres
    private String apellido2;                // 2 a 20 caracteres
    private String identificacion;           // 9+ caracteres, único
    private String telefono;                 // 8+ caracteres
    private String correoElectronico;        // formato parte1@parte2, único
    private String direccion;                // 5 a 60 caracteres
    /*
    private String organizacion;             // hasta 40 caracteres
    private ArrayList<String> temasInteres;       // lista de strings (5 a 30 caracteres cada uno)
    */
    private LocalDate fechaRegistro;         // asignada automáticamente
    private String contrasena;               // contraseña oculta (mínimo 8 caracteres, debe incluir mayúscula, minúscula, número y símbolo)

    // Constructor
    public Usuarios(String nombre, String apellido1, String apellido2, String identificacion,
                   String telefono, String correoElectronico, String direccion,
                   /*String organizacion, ArrayList<String> temasInteres*/ String contrasena) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        /*
        this.organizacion = organizacion;
        this.temasInteres = temasInteres;
        */
        this.fechaRegistro = LocalDate.now(); // Toma la fecha actual del sistema
        this.contrasena = contrasena;
    }

    // Getters (sin incluir getContrasena para mantener privacidad)
    public String getNombre() { return nombre; }
    public String getApellido1() { return apellido1; }
    public String getApellido2() { return apellido2; }
    public String getIdentificacion() { return identificacion; }
    public String getTelefono() { return telefono; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getDireccion() { return direccion; }
    /*
    public String getOrganizacion() { return organizacion; }
    public List<String> getTemasInteres() { return temasInteres; }
    */
    public LocalDate getFechaRegistro() { return fechaRegistro; }

    // Setters (solo si es necesario, excepto para fechaRegistro y contraseña)
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    /*
    public void setOrganizacion(String organizacion) { this.organizacion = organizacion; }
    public void setTemasInteres(ArrayList<String> temasInteres) { this.temasInteres = temasInteres; }
    */
}
