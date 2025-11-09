/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa un ejercicio de opción múltiple dentro de una evaluación.
 * Permite definir varias opciones, marcar las correctas y evaluar las seleccionadas.
 * Extiende la clase {@link Ejercicios}.
 * 
 * @author Andres
 * @version 1.0
 */
public class OpcionMultiplePanel extends Ejercicios implements Serializable {

    private java.util.List<String> opciones;           // Lista de opciones posibles
    private Set<Integer> correctas;                    // Índices de las opciones correctas
    private Set<Integer> seleccionadas = new HashSet<>(); // Índices seleccionados por el usuario

    /**
     * Constructor que inicializa la pregunta, sus opciones y las respuestas correctas.
     * 
     * @param enunciado Texto del enunciado de la pregunta
     * @param opciones Lista de opciones disponibles
     * @param correctas Conjunto de índices de las opciones correctas
     * @param puntaje Puntaje total asignado al ejercicio
     */
    public OpcionMultiplePanel(String enunciado, java.util.List<String> opciones, Set<Integer> correctas, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = new ArrayList<>(opciones);
        this.correctas = new HashSet<>(correctas);
    }

    /** Establece la lista de opciones. */
    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = opciones;
    }

    /** Establece el conjunto de respuestas correctas. */
    public void setCorrectas(Set<Integer> correctas) {
        this.correctas = correctas;
    }

    /** 
     * Construye el panel gráfico con los componentes de la pregunta.
     * Crea los checkboxes según la cantidad de opciones.
     */
    @Override
    public void construirPanel() {
        removeAll(); // Limpia el contenido anterior
        seleccionadas.clear(); // Reinicia las selecciones

        JPanel panelOpciones = new JPanel(new GridLayout(opciones.size(), 1)); // Panel con una fila por opción
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        // Crea un checkbox por cada opción
        for (int i = 0; i < opciones.size(); i++) {
            JCheckBox cb = new JCheckBox(opciones.get(i));
            int idx = i;
            cb.addActionListener(e -> {
                if (cb.isSelected()) seleccionadas.add(idx);
                else seleccionadas.remove(idx);
                verificar(); // Recalcula puntaje al marcar/desmarcar
            });
            panelOpciones.add(cb);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Aplica un orden aleatorio a las opciones y actualiza los índices de las correctas.
     * 
     * @param rand Generador aleatorio utilizado para mezclar las opciones
     */
    @Override
    public void aplicarRandom(Random rand) {
        // Guarda los textos de las opciones correctas
        ArrayList<String> opcionesCorrectas = new ArrayList<>();
        for (int i : correctas) {
            opcionesCorrectas.add(opciones.get(i));
        }

        // Mezcla todas las opciones
        ArrayList<String> lista = new ArrayList<>(opciones);
        Collections.shuffle(lista, rand);

        // Recalcula los nuevos índices de las correctas según el nuevo orden
        Set<Integer> nuevosIndices = new HashSet<>();
        for (String correcta : opcionesCorrectas) {
            nuevosIndices.add(lista.indexOf(correcta));
        }

        this.correctas = nuevosIndices;
        opciones = lista;

        // Reconstruye el panel con el nuevo orden
        construirPanel();
    }

    /**
     * Verifica las opciones seleccionadas y calcula el puntaje obtenido.
     */
    @Override
    public void verificar() {
        int correctos = 0;
        for (int i : seleccionadas)
            if (correctas.contains(i)) correctos++;

        puntajeObtenido = (int) ((correctos / (double) correctas.size()) * puntaje);
    }

    /**
     * Genera un detalle textual del ejercicio, incluyendo respuestas y puntaje.
     * 
     * @return Descripción detallada del ejercicio con el resultado
     */
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

        sb.append("Puntaje obtenido: ").append(puntajeObtenido)
          .append("/").append(puntaje).append("\n");
        return sb.toString();
    }

    /** Define las opciones seleccionadas manualmente. */
    public void setSeleccionadas(Set<Integer> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    /** Elimina todas las selecciones actuales. */
    public void borrarSeleccion() {
        this.seleccionadas.clear();
    }

    /**
     * Crea una copia del ejercicio con sus mismas propiedades y selecciones.
     * 
     * @return Copia del objeto {@code OpcionMultiplePanel}
     */
    public Ejercicios copiar() {
        OpcionMultiplePanel copia = new OpcionMultiplePanel(
            enunciado, new ArrayList<>(opciones), new HashSet<>(correctas), puntaje
        );
        copia.setSeleccionadas(new HashSet<>(this.seleccionadas));
        return copia;
    }
}
