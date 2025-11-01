/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author User
 */
public class Evaluaciones implements Serializable{
    private int identificacion;
    private String nombre;
    private String instrucciones;
    private ArrayList<String> objetivos;
    private int duracion;
    private boolean pregAl;
    private boolean resAl;
    private ArrayList<Ejercicios> ejercicios;
    private Grupos grupo;
    private JFrame frame;
    private JPanel panelCentral;
    private long inicio;
    private long fin;
    private int indiceActual = 0;
    private double puntajeTotalObt=0;
    private double puntajeTotal=0;
    
    
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
    
    public double getPuntajeTotal() {
        puntajeTotal=0;
        for (Ejercicios e: ejercicios){
            
            puntajeTotal=puntajeTotal+e.getPuntos();
            
        }
        return puntajeTotal;
    }

    public double getPuntajeTotalObtenido() {
        puntajeTotalObt=0;
        for (Ejercicios e: ejercicios){
            puntajeTotalObt=puntajeTotalObt+e.getPuntajeObtenido();
        }
        return puntajeTotalObt;
    }

    public int getNota() {
        return (int) Math.round(getPuntajeTotalObtenido() * 100.0 / getPuntajeTotal());
    }
    
    
    
    public void agregarEjercicio(Ejercicios ejercicio){
        ejercicios.add(ejercicio);
    }
    public void eliminarEjercicio (Ejercicios ejercicio){
        ejercicios.remove(ejercicio);
    }
    public int getIdentificacion(){
        return identificacion;
    }
    public String toString(){
        return "Identificacion: "+identificacion+"\n"+"Nombre: "+nombre+"\n"+"Objetivos: "+this.getObjetivos()+"\n"+"Duracion: "+duracion+"\n"+"Aleatoriedad de preguntas: "+pregAl+"\n"+"Aleatoriedad de respuestas: "+resAl+"\n"+"Cantidad de preguntas: "+ejercicios.size()+"\n";
    }  
    
    public String imprimir() {
    StringBuilder todo = new StringBuilder();

    // Información general de la evaluación
    todo.append("Evaluación: ").append(nombre).append("\n");
    todo.append("Estudiante: ").append("Andres").append("\n");
    todo.append("Nota: ").append(getNota())
        .append(" (").append(getPuntajeTotalObtenido())
        .append("/").append(getPuntajeTotal()).append(")\n");
    todo.append("Duración: ").append(getDuracion()).append("\n");
    todo.append("==================================================\n");

    // Detalle de cada ejercicio
    int n = 1;
    for (Ejercicios e : ejercicios) {
        todo.append("Pregunta ").append(n++).append("\n");
        todo.append(e.detalle()).append("\n");
        todo.append("--------------------------------------------------\n");
    }

    return todo.toString();
}
    public double getDuracion(){
        return (fin-inicio)/1000;
    }
    
    public void iniciar(){
        
        frame = new JFrame("Evaluación: "+nombre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLayout(new BorderLayout());

        panelCentral = new JPanel(new CardLayout());
        for(Ejercicios e: ejercicios){
            if(resAl){
                e.aplicarRandom(new Random());
            }else{
                e.construirPanel();
            } 
                
            panelCentral.add(e,enunciado(e));
            e.setVisible(false);
        }

        frame.add(panelCentral,BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAnterior = new JButton("Anterior");
        JButton btnSiguiente = new JButton("Siguiente");
        JButton btnFinalizar = new JButton("Finalizar");

        btnAnterior.addActionListener(ev -> mostrarAnterior());
        btnSiguiente.addActionListener(ev -> mostrarSiguiente());
        btnFinalizar.addActionListener(ev -> finalizar());

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnFinalizar);

        frame.add(panelBotones,BorderLayout.SOUTH);

        inicio = System.currentTimeMillis();

        frame.setVisible(true);
        mostrarEjercicio(0);
    }

    private String enunciado(Ejercicios e){
        return e.enunciado;
    }

    private void mostrarEjercicio(int idx){
        if(idx<0 || idx>=ejercicios.size()) return;
        for(int i=0;i<ejercicios.size();i++) ejercicios.get(i).setVisible(i==idx);
        indiceActual = idx;
    }

    private void mostrarSiguiente(){ mostrarEjercicio(indiceActual+1); }
    private void mostrarAnterior(){ mostrarEjercicio(indiceActual-1); }

    private void finalizar(){
        fin = System.currentTimeMillis();
        JOptionPane.showMessageDialog(frame,"Evaluación finalizada en "+((fin-inicio)/1000)+" seg");
        // Aquí podrías calcular notas y guardar resultados
        JFrame ventana = new JFrame("Información general");
            ventana.setSize(400, 300);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLayout(null);

            // 🟩 JTextArea con texto
            JTextArea lblFecha = new JTextArea(10, 50);
            lblFecha.setText(this.imprimir());
            lblFecha.setEditable(false);
            lblFecha.setLineWrap(true);
            lblFecha.setWrapStyleWord(true);
            lblFecha.setBackground(ventana.getBackground());

            // 🟦 ScrollPane que contiene el JTextArea
            JScrollPane scroll = new JScrollPane(lblFecha);
            scroll.setBounds(20, 20, 350, 170); // coordenadas y tamaño
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // ✅ Agregamos el JScrollPane en lugar del JTextArea
            ventana.add(scroll);

            // 🟨 Botón para cerrar la ventana
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setBounds(150, 210, 100, 30);
            btnCerrar.addActionListener(w -> ventana.dispose());
            ventana.add(btnCerrar);

            ventana.setVisible(true);
        this.iniciar();
    }
    
    
    
    
}
