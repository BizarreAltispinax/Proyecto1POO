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
class PareoPanel extends Ejercicios {
    private Map<String, String> parejas;         // Pregunta → Respuesta correcta
    private JComboBox<String>[] combos;          // Combos donde el usuario elige
    private String[] opciones;                   // Respuestas posibles

    public PareoPanel(String enunciado, int puntaje, Map<String, String> parejas) {
        super(enunciado, puntaje);
        this.parejas = parejas;
        this.opciones = new ArrayList<>(parejas.values()).toArray(new String[0]);
        construirPanel();
    }

    @Override
    protected void construirPanel() {
        JLabel lblEnunciado = new JLabel("<html><b>" + enunciado + "</b></html>");
        add(lblEnunciado, BorderLayout.NORTH);

        JPanel panelPareo = new JPanel(new GridLayout(parejas.size(), 2, 10, 10));
        combos = new JComboBox[parejas.size()];

        int i = 0;
        for (String pregunta : parejas.keySet()) {
            JLabel lblPregunta = new JLabel(pregunta);
            lblPregunta.setHorizontalAlignment(SwingConstants.RIGHT);
            combos[i] = new JComboBox<>(opciones);
            panelPareo.add(lblPregunta);
            panelPareo.add(combos[i]);
            i++;
        }

        add(panelPareo, BorderLayout.CENTER);
    }

    @Override
    public boolean verificarRespuesta() {
        int i = 0;
        for (String pregunta : parejas.keySet()) {
            String seleccion = (String) combos[i].getSelectedItem();
            String correcta = parejas.get(pregunta);
            if (!Objects.equals(seleccion, correcta)) {
                return false;
            }
            i++;
        }
        return true;
    }
}
