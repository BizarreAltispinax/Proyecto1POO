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
import java.util.*;

public class PareoPanel extends Ejercicios {
    private Map<String, String> paresCorrectos;
    private Map<String, String> respuestas = new HashMap<>();
    private Map<String, JComboBox<String>> combos = new HashMap<>();

    public PareoPanel(String enunciado, Map<String, String> paresCorrectos, int puntaje) {
        super(enunciado, puntaje);
        this.paresCorrectos = new LinkedHashMap<>(paresCorrectos);
        
    }

    @Override
    public void construirPanel() {
        removeAll();
        JLabel lbl = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lbl, BorderLayout.NORTH);

        java.util.List<String> valores = new ArrayList<>(paresCorrectos.values());
        Collections.shuffle(valores);
        JPanel panel = new JPanel(new GridLayout(paresCorrectos.size(), 2, 5, 5));

        for (String clave : paresCorrectos.keySet()) {
            JLabel lblClave = new JLabel(clave);
            JComboBox<String> combo = new JComboBox<>(valores.toArray(new String[0]));
            combo.addActionListener(e -> respuestas.put(clave, (String) combo.getSelectedItem()));
            combos.put(clave, combo);
            panel.add(lblClave);
            panel.add(combo);
        }
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void aplicarRandom(Random rand) {
        // Creamos una lista concreta de los pares completos (clave-valor)
        ArrayList<Map.Entry<String, String>> entradas = new ArrayList<>(paresCorrectos.entrySet());

        // Mezclamos la lista
        Collections.shuffle(entradas, rand);

        // Reconstruimos un LinkedHashMap con el nuevo orden
        LinkedHashMap<String, String> mezclado = new LinkedHashMap<>();
        for (Map.Entry<String, String> entrada : entradas) {
            mezclado.put(entrada.getKey(), entrada.getValue());
        }

        // Reemplazamos el mapa original
        paresCorrectos = mezclado;
    }


    @Override
    public void verificar() {
        int correctos = 0;
        for (String clave : paresCorrectos.keySet()) {
            String respuesta = respuestas.get(clave);
            if (respuesta != null && respuesta.equals(paresCorrectos.get(clave))) correctos++;
        }
        puntajeObtenido = (int) Math.round((correctos / (double) paresCorrectos.size()) * puntaje);
    }

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
        sb.append("Puntaje obtenido: ").append(puntajeObtenido).append("/").append(puntaje).append("\n");
        return sb.toString();
    }
    public void setRespuestas(Map<String, String> respuestas){
        this.respuestas=respuestas;
    }
    
    
    
    public Ejercicios copiar() {
            PareoPanel copia = new PareoPanel(enunciado, new LinkedHashMap<>(paresCorrectos), puntaje);
            copia.setRespuestas(new HashMap<>(this.respuestas));
            return copia;
    }
}
