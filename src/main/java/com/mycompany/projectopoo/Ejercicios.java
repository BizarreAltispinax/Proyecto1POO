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
 * Clase abstracta base para todos los ejercicios del sistema.
 * Define propiedades comunes como enunciado, puntaje y métodos abstractos
 * que deben implementar las subclases.
 */
abstract class Ejercicios extends JPanel implements Serializable {

    protected String enunciado;
    protected int puntaje;
    protected double puntajeObtenido;

    /**
     * Constructor que inicializa el enunciado y el puntaje del ejercicio.
     * @param enunciado texto que describe el ejercicio.
     * @param puntaje valor numérico asignado al ejercicio (debe ser ≥ 1).
     * @throws IllegalArgumentException si el puntaje es menor que 1.
     */
    public Ejercicios(String enunciado, int puntaje) {
        this.enunciado = enunciado;
        if (puntaje == 0 || puntaje < 1)
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a 1.");
        this.puntaje = puntaje;
        this.puntajeObtenido = 0;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes del panel
    }

    /** Construye visualmente el panel del ejercicio. */
    protected abstract void construirPanel();

    /** Verifica las respuestas o resultados del ejercicio. */
    public abstract void verificar();

    /** Aplica una variación aleatoria al ejercicio. */
    public abstract void aplicarRandom(Random r);

    /** Devuelve un texto con el detalle del ejercicio y su estado. */
    public abstract String detalle();

    /** Crea una copia independiente del ejercicio. */
    public abstract Ejercicios copiar();

    /** @return el puntaje total asignado al ejercicio. */
    public int getPuntos() {
        return puntaje;
    }

    /** @return el enunciado del ejercicio. */
    public String getEnunciado() {
        return enunciado;
    }

    /** @return el puntaje obtenido por el estudiante. */
    public double getPuntajeObtenido() {
        return puntajeObtenido;
    }

    /** Asigna un nuevo enunciado al ejercicio. */
    public void setEnunciado(String enunciados) {
        this.enunciado = enunciados;
    }

    /**
     * Establece un nuevo puntaje para el ejercicio.
     * @throws IllegalArgumentException si el valor es menor que 1.
     */
    public void setPuntaje(int puntaje) {
        if (puntaje == 0 || puntaje < 1)
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a 1.");
        this.puntaje = puntaje;
    }

    /** Elimina cualquier selección o respuesta hecha por el usuario. */
    public abstract void borrarSeleccion();
}