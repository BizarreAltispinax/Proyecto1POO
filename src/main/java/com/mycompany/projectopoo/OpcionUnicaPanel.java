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
 * Clase que representa un ejercicio de opción única.
 * Permite al usuario seleccionar solo una respuesta de varias opciones.
 * Extiende la clase {@link Ejercicios}.
 * 
 * @author Andres
 * @version 1.0
 */
public class OpcionUnicaPanel extends Ejercicios implements Serializable {

    private java.util.List<String> opciones; // Lista de opciones disponibles
    private int correcta;                    // Índice de la opción correcta
    private int seleccion = -1;              // Índice seleccionado por el usuario (-1 si no ha elegido)
    private ButtonGroup grupo;               // Agrupa los botones para permitir solo una selección

    /**
     * Constructor que inicializa la pregunta de opción única.
     * 
     * @param enunciado Texto del enunciado de la pregunta
     * @param opciones Lista de opciones a mostrar
     * @param correcta Índice de la opción correcta
     * @param puntaje Puntaje total asignado al ejercicio
     */
    public OpcionUnicaPanel(String enunciado, java.util.List<String> opciones, int correcta, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = new ArrayList<>(opciones);
        this.correcta = correcta;
    }

    /** Define la lista de opciones. */
    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = opciones;
    }

    /**
     * Construye el panel gráfico del ejercicio con botones de selección única.
     */
    @Override
    public void construirPanel() {
        removeAll(); // Limpia los componentes previos
        seleccion = -1; // Reinicia la selección
        grupo = new ButtonGroup(); // Agrupa los botones para selección única
        JPanel panelOpciones = new JPanel(new GridLayout(opciones.size(), 1));
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        // Crea un botón por cada opción
        for (int i = 0; i < opciones.size(); i++) {
            JRadioButton rb = new JRadioButton(opciones.get(i));
            int idx = i;
            rb.addActionListener(e -> {
                seleccion = idx;
                verificar(); // Recalcula puntaje al seleccionar
            });
            grupo.add(rb);
            panelOpciones.add(rb);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Aplica un orden aleatorio a las opciones y actualiza el índice correcto.
     * 
     * @param rand Generador aleatorio para mezclar las opciones
     */
    @Override
    public void aplicarRandom(Random rand) {
        // Guarda la opción correcta antes de mezclar
        String opcionCorrecta = opciones.get(correcta);

        // Crea una nueva lista mezclada
        ArrayList<String> lista = new ArrayList<>(opciones);
        Collections.shuffle(lista, rand);

        // Actualiza el índice correcto según la nueva posición
        this.correcta = lista.indexOf(opcionCorrecta);
        opciones = lista;

        // Reconstruye el panel con el nuevo orden
        construirPanel();
    }

    /**
     * Verifica si la opción seleccionada es la correcta y asigna el puntaje.
     */
    @Override
    public void verificar() {
        puntajeObtenido = (seleccion == correcta) ? puntaje : 0;
    }

    /**
     * Genera una descripción detallada del ejercicio, mostrando respuestas y puntaje.
     * 
     * @return Texto con el detalle del ejercicio
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

        sb.append("Puntaje obtenido: ").append(puntajeObtenido)
          .append("/").append(puntaje).append("\n");

        return sb.toString();
    }

    /** Define manualmente la opción seleccionada. */
    public void setSeleccion(int sele) {
        this.seleccion = sele;
    }

    /** Borra la selección actual. */
    public void borrarSeleccion() {
        this.seleccion = -1;
    }

    /**
     * Crea una copia del ejercicio manteniendo sus propiedades y selección actual.
     * 
     * @return Copia del objeto {@code OpcionUnicaPanel}
     */
    public Ejercicios copiar() {
        OpcionUnicaPanel copia = new OpcionUnicaPanel(enunciado, new ArrayList<>(opciones), correcta, puntaje);
        copia.setSeleccion(this.seleccion);
        return copia;
    }
}
