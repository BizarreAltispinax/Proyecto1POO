/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Evaluaciones {
    private int identificacion;
    private String nombre;
    private String instrucciones;
    private ArrayList<String> objetivos;
    private int duracion;
    private boolean pregAl;
    private boolean resAl;
    private ArrayList<Ejercicios> ejercicios;
    private Grupos grupo;
    
    
    public Evaluaciones(int identificacion, String nombre, String instrucciones, ArrayList<String> objetivos,
                 int duracion, boolean pregAl,
                 boolean resAl, ArrayList<Ejercicios> ejercicios) {
        
        
        
        if (nombre == null || nombre.length() < 5 || nombre.length() > 20)
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 40 caracteres.");
        if (instrucciones == null || instrucciones.length() < 5 || instrucciones.length() > 400)
            throw new IllegalArgumentException("Las instrucciones deben tener entre 5 y 400 caracteres.");
        for (String st : objetivos){
            if (st.length() < 10 || st.length() > 40)
            throw new IllegalArgumentException("Cada objetivo debe de tener entre 10 y 40 caracteres");
        }
        
        if (duracion < 1)
            throw new IllegalArgumentException("La duracion debe ser de minimo 1 minuto");


        this.identificacion = identificacion;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.objetivos = objetivos;
        
        this.duracion = duracion;
        this.pregAl = pregAl;
        this.resAl = resAl;
        this.ejercicios = ejercicios;
        

    }
    public String getObjetivos() {
        String objs="";
        for (String obj: objetivos){
            objs+=obj+", ";
        }
        return objs;
    }
    
    public void agregarEjercicio(Ejercicios ejercicio){
        ejercicios.add(ejercicio);
    }
    public void eliminarEjercicio (Ejercicios ejercicio){
        ejercicios.remove(ejercicio);
    }
    
    
}
