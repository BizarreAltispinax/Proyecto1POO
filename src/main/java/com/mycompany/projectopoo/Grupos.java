/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */
import java.time.LocalDate;
import java.util.ArrayList;

public class Grupos {
    private int idGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<Estudiantes> estudiantes;
    private ArrayList<Profesores> profesores;

    public Grupos(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio))
            throw new IllegalArgumentException("Las fechas son inválidas.");

        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        
    }

    public int getIdGrupo() { return idGrupo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public ArrayList<Estudiantes> getEstudiantes() { return estudiantes; }
    public ArrayList<Profesores> getProfesores() { return profesores; }
    public int getCantidadEstudiantes() { return estudiantes.size(); }

    // Incrementa los estudiantes (por matrícula)
    public void agregarEstudiantes(Estudiantes est){
        if (est!=null){
            estudiantes.add(est);
        }
        
    }

    @Override
    public String toString() {
        return "Grupo " + idGrupo + ": " + fechaInicio + " → " + fechaFin;
    }
}
