/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.time.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;



/**
 * Clase abstracta que representa un usuario general del sistema.
 * Contiene información común como nombre, identificación, contacto y dirección.
 * Además, gestiona de forma segura el almacenamiento de contraseñas mediante hash y salt.
 * 
 * <p>Los usuarios concretos (como profesores o estudiantes) deben extender esta clase.</p>
 * 
 * @author  
 * @version 1.0
 */
public abstract class Usuarios implements Serializable {

    // -------------------- Atributos --------------------
    private String nombre;                   // 2 a 20 caracteres
    private String apellido1;                // 2 a 20 caracteres
    private String apellido2;                // 2 a 20 caracteres
    private String identificacion;           // 9+ caracteres, único
    private String telefono;                 // 8+ caracteres
    private String correoElectronico;        // formato parte1@parte2, único
    private String direccion;                // 5 a 60 caracteres
    /*
    private String organizacion;             // hasta 40 caracteres
    private ArrayList<String> temasInteres;  // lista de strings (5 a 30 caracteres cada uno)
    */
    private LocalDate fechaRegistro;         // Asignada automáticamente al crear el usuario
    private String contrasena;               // Contraseña original (no se almacena directamente)
    private String hashContraseña;           // Hash seguro de la contraseña
    private String salt;                     // Salt usado para generar el hash

    private static final int ITERACIONES = 65536;    // Iteraciones del algoritmo PBKDF2
    private static final int LONGITUD_HASH = 512;    // Longitud del hash resultante

    // -------------------- Constructor --------------------
    /**
     * Crea un nuevo usuario con los datos personales y credenciales indicadas.
     * El hash y el salt se generan automáticamente a partir de la contraseña.
     *
     * @param nombre Nombre del usuario
     * @param apellido1 Primer apellido
     * @param apellido2 Segundo apellido
     * @param identificacion Identificación única del usuario
     * @param telefono Número de teléfono
     * @param correoElectronico Correo electrónico (único)
     * @param direccion Dirección física del usuario
     * @param contrasena Contraseña original en texto plano
     */
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
        this.fechaRegistro = LocalDate.now(); // Fecha actual del sistema
        setContraseña(contrasena);
    }

    // -------------------- Getters --------------------
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

    // -------------------- Métodos de seguridad --------------------
    /**
     * Genera un valor aleatorio de salt para proteger la contraseña.
     * @return Salt codificado en Base64
     */
    private static String generarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Genera un hash seguro utilizando PBKDF2 con HMAC-SHA512.
     *
     * @param contraseña Contraseña en texto plano
     * @param salt Valor salt usado en la derivación
     * @return Hash codificado en Base64
     */
    private static String generarHash(String contraseña, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            KeySpec spec = new PBEKeySpec(contraseña.toCharArray(), saltBytes, ITERACIONES, LONGITUD_HASH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }

    // -------------------- Setters --------------------
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    /*
    public void setOrganizacion(String organizacion) { this.organizacion = organizacion; }
    public void setTemasInteres(ArrayList<String> temasInteres) { this.temasInteres = temasInteres; }
    */

    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Asigna una nueva contraseña generando automáticamente su salt y hash.
     * @param contraseña Contraseña en texto plano
     */
    public void setContraseña(String contraseña) {
        this.salt = generarSalt();
        this.hashContraseña = generarHash(contraseña, this.salt);
    }

    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // -------------------- Verificación de contraseña --------------------
    /**
     * Verifica si la contraseña ingresada coincide con el hash almacenado.
     *
     * @param contraseñaIngresada Contraseña introducida por el usuario
     * @return true si es correcta, false si no coincide
     */
    public boolean verificarContraseña(String contraseñaIngresada) {
        String hashVerificado = generarHash(contraseñaIngresada, this.salt);
        return hashVerificado.equals(this.hashContraseña);
    }

    public String getHashContraseña() { return hashContraseña; }
    public String getSalt() { return salt; }

    // -------------------- Representación en texto --------------------
    /**
     * Devuelve una representación textual legible de los datos del usuario.
     * @return Cadena con información personal formateada
     */
    public String toString() {
        return "Nombre: " + nombre + " " + apellido1 + " " + apellido2 + " " +
               "\nIdentificaion: " + identificacion + "\n" +
               "Telefono: " + telefono + "\n" +
               "Correo electronico: " + correoElectronico + "\n" +
               "Direccion: " + direccion + "\n";
    }
}
