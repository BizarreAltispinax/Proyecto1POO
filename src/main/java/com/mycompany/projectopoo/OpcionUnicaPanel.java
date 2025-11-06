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

public class OpcionUnicaPanel extends Ejercicios implements Serializable{
    private java.util.List<String> opciones;
    private int correcta;
    private int seleccion = -1;
    private ButtonGroup grupo;

    public OpcionUnicaPanel(String enunciado, java.util.List<String> opciones, int correcta, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = new ArrayList<>(opciones);
        this.correcta = correcta;
        
    }
    
    public void setOpciones(ArrayList<String>opciones){
        this.opciones=opciones;
    }

    @Override
    public void construirPanel() {
        removeAll();
        seleccion=-1;
        grupo = new ButtonGroup();
        JPanel panelOpciones = new JPanel(new GridLayout(opciones.size(), 1));
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        for (int i = 0; i < opciones.size(); i++) {
            JRadioButton rb = new JRadioButton(opciones.get(i));
            int idx = i;
            rb.addActionListener(e -> {seleccion = idx;
                verificar();
                    });
            grupo.add(rb);
            panelOpciones.add(rb);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void aplicarRandom(Random rand) {
   
    // Guardamos el texto de la opción correcta antes de mezclar
    String opcionCorrecta = opciones.get(correcta);

    // Creamos y mezclamos la lista
    ArrayList<String> lista = new ArrayList<>(opciones);
    Collections.shuffle(lista, rand);

    // Actualizamos el índice correcto según la nueva posición del texto correcto
    this.correcta = lista.indexOf(opcionCorrecta);
    opciones=lista;
    // Reconstruimos el panel con las opciones mezcladas
    construirPanel();
}

    @Override
    public void verificar() {
        puntajeObtenido = (seleccion == correcta) ? puntaje : 0;
    }
    

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
    
    public void setSeleccion(int sele){
        this.seleccion=sele;
    }
    
    
    public void borrarSeleccion(){
        this.seleccion=-1;
    }
    
    
    public Ejercicios copiar() {
        OpcionUnicaPanel copia = new OpcionUnicaPanel(enunciado, new ArrayList<>(opciones), correcta, puntaje);

        copia.setSeleccion(this.seleccion);
        
        return copia;
    }
}
