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

public class VerdaderoFalsoPanel extends Ejercicios implements Serializable{
    private java.util.List<String> opciones;
    private int correcta;  // 0 = Verdadero, 1 = Falso
    private int seleccion = -1;
    private ButtonGroup grupo;

    public VerdaderoFalsoPanel(String enunciado, boolean esVerdadero, int puntaje) {
        super(enunciado, puntaje);
        this.opciones = Arrays.asList("Verdadero", "Falso");
        this.correcta = esVerdadero ? 0 : 1;
    }
    public void setCorrecta(boolean correcta){
        if (correcta==true){
            this.correcta=0;
        }else{
            this.correcta=1;
        }
        
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
        // Opciones fijas, pero si quisieras mezclar Verdadero/Falso:
        Collections.shuffle(opciones, rand);
        // Recalculamos el índice de la opción correcta
        correcta = opciones.indexOf(correcta == 0 ? "Verdadero" : "Falso");
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
        this.seleccion = sele;
    }

    public Ejercicios copiar() {
        VerdaderoFalsoPanel copia = new VerdaderoFalsoPanel(enunciado, correcta == 0, puntaje);
        copia.setSeleccion(this.seleccion);
        return copia;
    }
}
