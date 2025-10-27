/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.util.ArrayList;
import java.io.*;
/**
 *
 * @author Usuario
 */
public class Estudiantes extends Usuarios implements Serializable{
    private String organizacion;             // hasta 40 caracteres
    private ArrayList<String> temasInteres;       // lista de strings (5 a 30 caracteres cada uno)
    
    public Estudiantes(String nombre, String apellido1, String apellido2, String identificacion,
                   String telefono, String correoElectronico, String direccion,String organizacion, ArrayList<String> temasInteres,String contrasena){
        super(nombre,apellido1,apellido2,identificacion,telefono,correoElectronico,direccion,contrasena);
        this.organizacion=organizacion;
        this.temasInteres = temasInteres;
    }
    public String getOrganizacion() { return organizacion; }
    public String getTemasInteres() {
        String temas="";
        for (String tema: temasInteres){
            temas+=tema+", ";
        }
        return temas;
    }
    
    public void setOrganizacion(String organizacion) { this.organizacion = organizacion; }
    public void setTemasInteres(ArrayList<String> temasInteres) { this.temasInteres = temasInteres; }
    public String toString(){
        return super.toString()+"Organizacion: "+organizacion+"\n"+"Temas de interes: "+this.getTemasInteres();
    }
}
