/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Profesores extends Usuarios{
    private String titulosObtenidos;             // hasta 40 caracteres
    private ArrayList<String> certificacionesEstudios;       // lista de strings (5 a 30 caracteres cada uno)

    public Profesores(String nombre, String apellido1, String apellido2, String identificacion,
                       String telefono, String correoElectronico, String direccion,String titulosObtenidos, ArrayList<String> certificacionesEstudios,String contrasena){
        super(nombre,apellido1,apellido2,identificacion,telefono,correoElectronico,direccion,contrasena);
        this.titulosObtenidos=titulosObtenidos;
        this.certificacionesEstudios = certificacionesEstudios;
    }

    public String gettitulosObtenidos() {
        return titulosObtenidos;
    }

    public String getcertificacionesEstudios() {
        String temas = "";
        for (String tema: certificacionesEstudios){
            temas += tema + " ";
        }
        return temas;
    }

    public void settitulosObtenidos(String titulosObtenidos) {
        this.titulosObtenidos = titulosObtenidos;
    }

    public void setcertificacionesEstudios(ArrayList<String> certificacionesEstudios) {
        this.certificacionesEstudios = certificacionesEstudios;
    }
}
