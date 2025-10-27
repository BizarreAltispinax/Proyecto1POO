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
public class Profesores extends Usuarios implements Serializable{
    private ArrayList<String> titulosObt;
    private ArrayList<String> Certificaciones;

    public Profesores(String nombre, String apellido1, String apellido2, String identificacion,
                   String telefono, String correoElectronico, String direccion,String contrasena,ArrayList<String> titulosObt,ArrayList<String> Certificaciones){
        super(nombre,apellido1,apellido2,identificacion,telefono,correoElectronico,direccion,contrasena);
        this.titulosObt = titulosObt;
        this.Certificaciones = Certificaciones;
    }
    public String getTitulos() {
        String titulos="";
        for (String titulo: titulosObt){
            titulos +=titulo+", ";
        }
        return titulos;
    }
    public String getCertificaciones() {
        String certificaciones="";
        for (String certificacion: Certificaciones){
            certificaciones+=certificacion+", ";
        }
        return certificaciones;
    }
    public void setTitulos(ArrayList<String> titulosObt) { this.titulosObt = titulosObt; }
    public void setCertificaciones(ArrayList<String> Certificaciones) { this.Certificaciones = Certificaciones; }
    public String toString(){
        return super.toString()+"Titulos Obtenidos: "+this.getTitulos()+"/n"+"Certificaciones"+this.getCertificaciones();
    }
}
