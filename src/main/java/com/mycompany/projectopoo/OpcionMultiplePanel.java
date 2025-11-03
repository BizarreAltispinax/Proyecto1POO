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

public class OpcionMultiplePanel extends Ejercicios implements Serializable{
    private java.util.List<String> opciones;
    private Set<Integer> correctas;
    private Set<Integer> seleccionadas = new HashSet<>();

    public OpcionMultiplePanel(String enunciado, java.util.List<String> opciones, Set<Integer> correctas, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = new ArrayList<>(opciones);
        this.correctas = new HashSet<>(correctas);
        
    }
    
    public void setOpciones(ArrayList<String>opciones){
        this.opciones=opciones;
    }
    
    public void setCorrectas(Set<Integer>correctas){
        this.correctas=correctas;
    }

    @Override
    public void construirPanel() {
        removeAll();
        seleccionadas.clear();
        JPanel panelOpciones = new JPanel(new GridLayout(opciones.size(), 1));
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        for (int i = 0; i < opciones.size(); i++) {
            JCheckBox cb = new JCheckBox(opciones.get(i));
            int idx = i;
            cb.addActionListener(e -> {
                if (cb.isSelected()) seleccionadas.add(idx);
                else seleccionadas.remove(idx);
                verificar();
            });
            panelOpciones.add(cb);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void aplicarRandom(Random rand) {
        // Guardamos el texto de las opciones correctas
        ArrayList<String> opcionesCorrectas = new ArrayList<>();
        for (int i : correctas) {
            opcionesCorrectas.add(opciones.get(i));
        }

        // Mezclamos todas las opciones
        ArrayList<String> lista = new ArrayList<>(opciones);
        Collections.shuffle(lista, rand);

        // Recalculamos los nuevos índices correctos según el nuevo orden
        Set<Integer> nuevosIndices = new HashSet<>();
        for (String correcta : opcionesCorrectas) {
            nuevosIndices.add(lista.indexOf(correcta));
        }
        this.correctas = nuevosIndices;
        opciones=lista;
        // Reconstruimos el panel con el nuevo orden
        construirPanel();
    }

    @Override
    public void verificar() {
        int correctos = 0;
        for (int i : seleccionadas)
            if (correctas.contains(i)) correctos++;

        puntajeObtenido = (int) ((correctos / (double) correctas.size()) * puntaje);
    }

    @Override
    public String detalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(enunciado).append("\n");
        for (int i = 0; i < opciones.size(); i++) {
            boolean esCorrecta = correctas.contains(i);
            boolean fueMarcada = seleccionadas.contains(i);
            sb.append((esCorrecta ? "✓ " : "✗ ") + opciones.get(i));
            if (fueMarcada) sb.append("  (Marcada)");
            sb.append("\n");
        }
        sb.append("Puntaje obtenido: ").append(puntajeObtenido).append("/").append(puntaje).append("\n");
        return sb.toString();
    }
    public void setSeleccionadas(Set<Integer> seleccionadas){
        this.seleccionadas=seleccionadas;
    }
    public Ejercicios copiar() {
        OpcionMultiplePanel copia = new OpcionMultiplePanel(enunciado, new ArrayList<>(opciones), new HashSet<>(correctas), puntaje);
        copia.setSeleccionadas(new HashSet<>(this.seleccionadas));
        return copia;
    }
}
