/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.util.ArrayList;
import java.io.*;


/**
 * Representa a un profesor dentro del sistema. Extiende la clase {@link Usuarios}
 * y añade información académica como títulos obtenidos y certificaciones.
 * 
 * @author Andres
 * @version 1.0
 */
public class Profesores extends Usuarios implements Serializable {
    private ArrayList<String> titulosObt;         // Lista de títulos obtenidos por el profesor
    private ArrayList<String> Certificaciones;    // Lista de certificaciones del profesor

    /**
     * Constructor que inicializa un objeto Profesor con todos sus datos personales y académicos.
     * 
     * @param nombre              Nombre del profesor
     * @param apellido1           Primer apellido
     * @param apellido2           Segundo apellido
     * @param identificacion      Identificación única del profesor
     * @param telefono            Número de teléfono
     * @param correoElectronico   Correo electrónico
     * @param direccion           Dirección física
     * @param contrasena          Contraseña de acceso
     * @param titulosObt          Lista de títulos obtenidos
     * @param Certificaciones     Lista de certificaciones
     */
    public Profesores(String nombre, String apellido1, String apellido2, String identificacion,
                   String telefono, String correoElectronico, String direccion, String contrasena,
                   ArrayList<String> titulosObt, ArrayList<String> Certificaciones) {
        super(nombre, apellido1, apellido2, identificacion, telefono, correoElectronico, direccion, contrasena);
        this.titulosObt = titulosObt;
        this.Certificaciones = Certificaciones;
    }

    /**
     * Devuelve los títulos obtenidos por el profesor en formato de texto.
     * 
     * @return Cadena con todos los títulos separados por comas.
     */
    public String getTitulos() {
        String titulos = "";
        for (String titulo : titulosObt) {
            titulos += titulo + ", "; // Concatena cada título con coma
        }
        return titulos;
    }

    /**
     * Devuelve las certificaciones del profesor en formato de texto.
     * 
     * @return Cadena con todas las certificaciones separadas por comas.
     */
    public String getCertificaciones() {
        String certificaciones = "";
        for (String certificacion : Certificaciones) {
            certificaciones += certificacion + ", "; // Concatena cada certificación con coma
        }
        return certificaciones;
    }

    /**
     * Asigna una nueva lista de títulos obtenidos.
     * 
     * @param titulosObt Lista de títulos a establecer.
     */
    public void setTitulos(ArrayList<String> titulosObt) { 
        this.titulosObt = titulosObt; 
    }

    /**
     * Asigna una nueva lista de certificaciones.
     * 
     * @param Certificaciones Lista de certificaciones a establecer.
     */
    public void setCertificaciones(ArrayList<String> Certificaciones) { 
        this.Certificaciones = Certificaciones; 
    }

    /**
     * Devuelve una representación en texto del profesor, incluyendo datos personales
     * y académicos.
     * 
     * @return Cadena con la información completa del profesor.
     */
    @Override
    public String toString() {
        return super.toString() + "Titulos Obtenidos: " + this.getTitulos() + "\n" +
               "Certificaciones: " + this.getCertificaciones();
    }
}

