/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.util.ArrayList;
import java.io.*;

/**
 * Representa a un estudiante dentro del sistema.
 * Hereda de {@link Usuarios} e incluye información adicional
 * como la organización y los temas de interés.
 */
public class Estudiantes extends Usuarios implements Serializable {

    private String organizacion;             // Nombre de la organización (máx. 40 caracteres)
    private ArrayList<String> temasInteres;  // Lista de temas de interés (cada uno de 5 a 30 caracteres)

    /**
     * Constructor que inicializa todos los datos del estudiante.
     * @param nombre nombre del estudiante.
     * @param apellido1 primer apellido.
     * @param apellido2 segundo apellido.
     * @param identificacion número de identificación.
     * @param telefono número telefónico.
     * @param correoElectronico correo del estudiante.
     * @param direccion dirección física.
     * @param organizacion institución u organización a la que pertenece.
     * @param temasInteres lista de temas de interés.
     * @param contrasena contraseña de acceso.
     */
    public Estudiantes(String nombre, String apellido1, String apellido2, String identificacion,
                       String telefono, String correoElectronico, String direccion,
                       String organizacion, ArrayList<String> temasInteres, String contrasena) {
        super(nombre, apellido1, apellido2, identificacion, telefono, correoElectronico, direccion, contrasena);
        this.organizacion = organizacion;
        this.temasInteres = temasInteres;
    }

    /** @return la organización del estudiante. */
    public String getOrganizacion() {
        return organizacion;
    }

    /**
     * Devuelve los temas de interés como una cadena separada por comas.
     * @return cadena con los temas de interés.
     */
    public String getTemasInteres() {
        String temas = "";
        for (String tema : temasInteres) {
            temas += tema + ", "; // Concatena cada tema con coma y espacio
        }
        return temas;
    }

    /** Asigna una nueva organización al estudiante. */
    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    /** Asigna una nueva lista de temas de interés. */
    public void setTemasInteres(ArrayList<String> temasInteres) {
        this.temasInteres = temasInteres;
    }

    /**
     * Devuelve una representación en texto del estudiante,
     * incluyendo su información heredada, organización y temas de interés.
     * @return cadena descriptiva del estudiante.
     */
    public String toString() {
        return super.toString() +
               "Organizacion: " + organizacion + "\n" +
               "Temas de interes: " + this.getTemasInteres();
    }
}
