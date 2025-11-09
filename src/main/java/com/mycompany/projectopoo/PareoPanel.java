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
 * Clase que representa un ejercicio tipo pareo.
 * El usuario debe asociar elementos de una columna con sus pares correctos.
 * Extiende la clase {@link Ejercicios}.
 * 
 * @author Andres
 * @version 1.0
 */
public class PareoPanel extends Ejercicios implements Serializable {

    private Map<String, String> paresCorrectos;              // Mapa de respuestas correctas (clave → valor)
    private Map<String, String> respuestas = new HashMap<>(); // Respuestas elegidas por el usuario
    private Map<String, JComboBox<String>> combos = new HashMap<>(); // Combos desplegables de selección
    private boolean v = false;                                // Controla si se mezclan los valores

    /**
     * Constructor que inicializa un ejercicio de pareo.
     * 
     * @param enunciado Texto del enunciado del ejercicio
     * @param paresCorrectos Mapa con los pares clave-valor correctos
     * @param puntaje Puntaje total asignado al ejercicio
     */
    public PareoPanel(String enunciado, Map<String, String> paresCorrectos, int puntaje) {
        super(enunciado, puntaje);
        this.paresCorrectos = new LinkedHashMap<>(paresCorrectos);
    }

    /**
     * Construye el panel gráfico con los pares a asociar.
     */
    @Override
    public void construirPanel() {
        removeAll(); // Limpia el contenido previo
        respuestas.clear(); // Reinicia las respuestas
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        java.util.List<String> valores = new ArrayList<>(paresCorrectos.values());
        if (v == true) {
            Collections.shuffle(valores); // Mezcla los valores si está activado
        }

        JPanel panel = new JPanel(new GridLayout(paresCorrectos.size(), 2, 5, 5));

        // Crea una fila por cada par
        for (String clave : paresCorrectos.keySet()) {
            JLabel lblClave = new JLabel(clave);
            JComboBox<String> combo = new JComboBox<>(valores.toArray(new String[0]));
            combo.addActionListener(e -> {
                respuestas.put(clave, (String) combo.getSelectedItem());
                verificar(); // Recalcula el puntaje
            });
            combos.put(clave, combo);
            panel.add(lblClave);
            panel.add(combo);
        }

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /** Define los pares correctos del ejercicio. */
    public void setPares(Map<String, String> paresCorrectos) {
        this.paresCorrectos = paresCorrectos;
    }

    /**
     * Aplica un orden aleatorio a los pares y reconstruye el panel.
     * 
     * @param rand Generador aleatorio para mezclar los pares
     */
    @Override
    public void aplicarRandom(Random rand) {
        v = true; // Activa la mezcla visual

        // Crea una lista de pares clave-valor
        ArrayList<Map.Entry<String, String>> entradas = new ArrayList<>(paresCorrectos.entrySet());

        // Mezcla los pares
        Collections.shuffle(entradas, rand);

        // Reconstruye un nuevo mapa con el nuevo orden
        LinkedHashMap<String, String> mezclado = new LinkedHashMap<>();
        for (Map.Entry<String, String> entrada : entradas) {
            mezclado.put(entrada.getKey(), entrada.getValue());
        }

        paresCorrectos = mezclado; // Actualiza el mapa original
        construirPanel(); // Redibuja el panel
    }

    /**
     * Verifica las respuestas del usuario comparándolas con los pares correctos.
     */
    @Override
    public void verificar() {
        int correctos = 0;
        for (String clave : paresCorrectos.keySet()) {
            String respuesta = respuestas.get(clave);
            if (respuesta != null && respuesta.equals(paresCorrectos.get(clave))) correctos++;
        }
        puntajeObtenido = (int) ((correctos / (double) paresCorrectos.size()) * puntaje);
    }

    /**
     * Devuelve un texto con los detalles del ejercicio, respuestas y puntaje.
     * 
     * @return Cadena con el detalle completo del ejercicio
     */
    @Override
    public String detalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(enunciado).append("\n");
        for (String clave : paresCorrectos.keySet()) {
            String correcto = paresCorrectos.get(clave);
            String resp = respuestas.get(clave);
            sb.append(clave + " → " + (resp == null ? "(sin respuesta)" : resp));
            if (correcto.equals(resp)) sb.append("  ✓");
            else sb.append("  ✗ (Correcto: " + correcto + ")");
            sb.append("\n");
        }
        sb.append("Puntaje obtenido: ").append(puntajeObtenido)
          .append("/").append(puntaje).append("\n");
        return sb.toString();
    }

    /** Define las respuestas actuales del usuario. */
    public void setRespuestas(Map<String, String> respuestas) {
        this.respuestas = respuestas;
    }

    /** Borra todas las respuestas seleccionadas. */
    public void borrarSeleccion() {
        this.respuestas.clear();
    }

    /**
     * Crea una copia del ejercicio conservando sus pares y respuestas.
     * 
     * @return Copia del objeto {@code PareoPanel}
     */
    public Ejercicios copiar() {
        PareoPanel copia = new PareoPanel(enunciado, new LinkedHashMap<>(paresCorrectos), puntaje);
        copia.setRespuestas(new HashMap<>(this.respuestas));
        this.respuestas.clear();
        return copia;
    }
}
