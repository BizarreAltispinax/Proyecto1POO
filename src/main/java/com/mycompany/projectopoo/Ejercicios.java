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
abstract class Ejercicios extends JPanel {
    protected String enunciado;
    protected int puntaje;

    public Ejercicios(String enunciado, int puntaje) {
        this.enunciado = enunciado;
        this.puntaje = puntaje;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    protected abstract void construirPanel();
    public abstract boolean verificarRespuesta();
}