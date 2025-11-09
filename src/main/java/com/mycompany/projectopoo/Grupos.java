/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Representa un grupo académico con sus fechas, estudiantes, profesor,
 * curso y evaluaciones asociadas. Permite gestionar evaluaciones,
 * registrar respuestas y obtener resultados por estudiante.
 * 
 * @author  
 * @version 1.0
 */
public class Grupos implements Serializable {

    private int idGrupo;                                         // Identificador único del grupo
    private LocalDate fechaInicio;                               // Fecha de inicio del grupo
    private LocalDate fechaFin;                                  // Fecha de finalización del grupo
    private ArrayList<Estudiantes> estudiantes;                  // Lista de estudiantes del grupo
    private Profesores profesor;                                 // Profesor asignado
    private Cursos curso;                                        // Curso asociado al grupo
    private Map<String, ArrayList<Evaluaciones>> respuestasAlumnos; // Respuestas de alumnos (nombre → lista de evaluaciones)
    private ArrayList<Evaluaciones> evaluaciones;                // Evaluaciones asignadas al grupo

    /**
     * Constructor del grupo.
     * 
     * @param idGrupo      identificador del grupo
     * @param fechaInicio  fecha de inicio del grupo
     * @param fechaFin     fecha de finalización del grupo
     * @throws IllegalArgumentException si las fechas son inválidas
     */
    public Grupos(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio))
            throw new IllegalArgumentException("Las fechas son inválidas.");

        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estudiantes = new ArrayList<>();
        this.respuestasAlumnos = new LinkedHashMap<>();
        this.evaluaciones = new ArrayList<>();
    }

    /** @return identificador del grupo */
    public int getIdGrupo() { return idGrupo; }

    /** @return fecha de inicio del grupo */
    public LocalDate getFechaInicio() { return fechaInicio; }

    /** @return fecha de finalización del grupo */
    public LocalDate getFechaFin() { return fechaFin; }

    /** @return lista de estudiantes del grupo */
    public ArrayList<Estudiantes> getEstudiantes() { return estudiantes; }
    
    /** @return mapa con las respuestas de los alumnos */
    public Map<String, ArrayList<Evaluaciones>> getRespuestasAlumnos() { return respuestasAlumnos; }

    /** @return cantidad de estudiantes matriculados */
    public int getCantidadEstudiantes() { return estudiantes.size(); }

    /** @return profesor asignado al grupo */
    public Profesores getProfesor() { return profesor; }

    /**
     * Agrega un estudiante al grupo.
     * 
     * @param est estudiante a agregar
     */
    public void agregarEstudiantes(Estudiantes est) {
        if (est != null) {
            estudiantes.add(est); // Se agrega el estudiante a la lista
        }
    }
    
    /**
     * Asigna un profesor al grupo.
     * 
     * @param profe profesor a asignar
     */
    public void asignarProfesor(Profesores profe) {
        if (profe != null) {
            this.profesor = profe; // Se guarda la referencia del profesor
        }
    }
    
    /**
     * Devuelve una evaluación específica según su identificador.
     * 
     * @param id identificador de la evaluación
     * @return evaluación correspondiente o null si no se encuentra
     */
    public Evaluaciones devEvaluaciones(int id) {
        for (Evaluaciones e : evaluaciones) {
            if (e.getIdentificacion() == id) {
                return e; // Coincidencia encontrada
            }
        }
        return null; // No se encontró
    }

    /**
     * Asigna un curso al grupo.
     * 
     * @param curso curso a asignar
     */
    public void asignarCurso(Cursos curso) {
        this.curso = curso;
    }
    
    /**
     * Asigna una evaluación al grupo.
     * 
     * @param eva evaluación a agregar
     */
    public void asignarEvaluacion(Evaluaciones eva) {
        if (eva != null) {
            evaluaciones.add(eva); // Se agrega la evaluación
        }
    }
    
    /**
     * Elimina una evaluación del grupo.
     * 
     * @param eva evaluación a eliminar
     */
    public void eliminarEvaluacion(Evaluaciones eva) {
        if (eva != null) {
            evaluaciones.remove(eva); // Se elimina la evaluación
        }
    }
    
    /** @return lista de evaluaciones del grupo */
    public ArrayList<Evaluaciones> getEvaluaciones() { return evaluaciones; }
    
    /**
     * Guarda una copia de la evaluación realizada por un estudiante.
     * 
     * @param estudiante nombre del estudiante
     * @param e          evaluación completada
     */
    public void guardarEvaluacion(String estudiante, Evaluaciones e) {
        Evaluaciones copia = e.copiarEvaluacion(); // Se guarda una copia independiente
        respuestasAlumnos.putIfAbsent(estudiante, new ArrayList<>());
        respuestasAlumnos.get(estudiante).add(copia);
    }

    /**
     * Obtiene todas las evaluaciones de un estudiante.
     * 
     * @param estudiante nombre del estudiante
     * @return lista de evaluaciones asociadas
     */
    public List<Evaluaciones> obtenerEvaluaciones(String estudiante) {
        return respuestasAlumnos.getOrDefault(estudiante, new ArrayList<>());
    }

    /**
     * Obtiene una evaluación específica de un estudiante.
     * 
     * @param estudiante nombre del estudiante
     * @param id         identificador de la evaluación
     * @return evaluación encontrada o null si no existe
     */
    public Evaluaciones obtenerEvaluacion(String estudiante, int id) {
        List<Evaluaciones> lista = respuestasAlumnos.get(estudiante);
        if (lista == null) {
            System.out.println("Hola");
            return null; // No hay registros para ese estudiante
        }
        for (Evaluaciones ev : lista) {
            if (ev.getIdentificacion() == id) {
                return ev; // Evaluación encontrada
            }
        }
        System.out.println("Hola2");
        return null; // Evaluación no encontrada
    }

    /**
     * Devuelve una representación en texto del grupo.
     * 
     * @return texto con id y fechas del grupo
     */
    @Override
    public String toString() {
        return "Grupo " + idGrupo + ": " + fechaInicio + " → " + fechaFin;
    }
}