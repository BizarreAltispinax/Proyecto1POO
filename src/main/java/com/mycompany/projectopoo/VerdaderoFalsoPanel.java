/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa un panel de ejercicio tipo Verdadero/Falso.
 * Extiende de {@link Ejercicios} e implementa {@link Serializable}.
 * Permite construir una interfaz con dos opciones (Verdadero o Falso),
 * verificar la respuesta seleccionada y calcular el puntaje obtenido.
 * 
 * @author  
 */
public class VerdaderoFalsoPanel extends Ejercicios implements Serializable {
    
    /** Lista con las dos opciones posibles: Verdadero y Falso. */
    private java.util.ArrayList<String> opciones;
    
    /** Índice de la opción correcta (0 = Verdadero, 1 = Falso). */
    private int correcta;
    
    /** Índice de la opción seleccionada por el usuario. -1 si no se ha seleccionado. */
    private int seleccion = -1;
    
    /** Indica si la respuesta correcta es Verdadero. */
    private boolean esVerdaderoE;
    
    /** Grupo de botones para asegurar selección única. */
    private ButtonGroup grupo;

    /**
     * Constructor del panel Verdadero/Falso.
     * 
     * @param enunciado Texto del enunciado de la pregunta.
     * @param esVerdadero Indica si la respuesta correcta es Verdadero.
     * @param puntaje Puntaje asignado al ejercicio.
     */
    public VerdaderoFalsoPanel(String enunciado, boolean esVerdadero, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = new ArrayList<>(Arrays.asList("Verdadero", "Falso"));
        this.correcta = esVerdadero ? 0 : 1;
        this.esVerdaderoE = esVerdadero;
    }

    /**
     * Asigna la respuesta correcta según el valor booleano recibido.
     * 
     * @param correcta true si la respuesta correcta es Verdadero, false si es Falso.
     */
    public void setCorrecta(boolean correcta) {
        if (correcta == true) {
            this.correcta = 0;
        } else {
            this.correcta = 1;
        }
    }

    /**
     * Construye el panel visual del ejercicio, agregando las opciones
     * Verdadero y Falso como botones de selección.
     */
    @Override
    public void construirPanel() {
        removeAll(); // Limpia componentes previos
        seleccion = -1;
        grupo = new ButtonGroup();
        JPanel panelOpciones = new JPanel(new GridLayout(opciones.size(), 1));
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        // Agrega cada opción con su listener
        for (int i = 0; i < opciones.size(); i++) {
            JRadioButton rb = new JRadioButton(opciones.get(i));
            int idx = i;
            rb.addActionListener(e -> {
                seleccion = idx;
                verificar();
            });
            grupo.add(rb);
            panelOpciones.add(rb);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Aplica aleatoriedad al orden de las opciones y actualiza
     * el índice de la respuesta correcta.
     * 
     * @param rand Instancia de Random utilizada para el barajado.
     */
    @Override
    public void aplicarRandom(Random rand) {
        Collections.shuffle(opciones, rand);
        System.out.println(opciones);
        // Recalcula el índice de la opción correcta
        correcta = opciones.indexOf(correcta == 0 ? "Verdadero" : "Falso");
        System.out.println(correcta);
        construirPanel();
    }

    /**
     * Verifica la respuesta seleccionada y asigna el puntaje obtenido.
     */
    @Override
    public void verificar() {
        puntajeObtenido = (seleccion == correcta) ? puntaje : 0;
    }

    /**
     * Devuelve una cadena con los detalles del ejercicio,
     * incluyendo las opciones, la marcada y el puntaje obtenido.
     * 
     * @return Descripción detallada del ejercicio.
     */
    @Override
    public String detalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(enunciado).append("\n");
        for (int i = 0; i < opciones.size(); i++) {
            sb.append((i == correcta ? "✓ " : "✗ ") + opciones.get(i));
            if (i == seleccion) sb.append("  (Marcada)");
            sb.append("\n");
        }
        sb.append("Puntaje obtenido: ").append(puntajeObtenido).append("/").append(puntaje).append("\n");
        return sb.toString();
    }

    /**
     * Asigna el índice de la opción seleccionada.
     * 
     * @param sele Índice de la opción elegida.
     */
    public void setSeleccion(int sele) {
        this.seleccion = sele;
    }

    /**
     * Borra la selección actual.
     */
    public void borrarSeleccion() {
        this.seleccion = -1;
    }

    /**
     * Establece una nueva lista de opciones.
     * 
     * @param opciones Lista de nuevas opciones Verdadero/Falso.
     */
    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = new ArrayList<>(opciones);
    }
    
    
    public void setEsVerdad(){
        this.correcta = esVerdaderoE ? 0 : 1;
    }
    
    
    
    /**
     * Crea una copia del ejercicio Verdadero/Falso actual,
     * conservando los valores del enunciado, puntaje y selección.
     * 
     * @return Objeto {@link Ejercicios} con los mismos valores.
     */
    public Ejercicios copiar() {
        VerdaderoFalsoPanel copia = new VerdaderoFalsoPanel(enunciado, correcta == 0, puntaje);
        copia.setSeleccion(this.seleccion);
        copia.setOpciones(opciones);
        this.seleccion = -1;
        this.opciones = new ArrayList<>(Arrays.asList("Verdadero", "Falso"));
        this.correcta = esVerdaderoE ? 0 : 1;
        return copia;
    }
}
