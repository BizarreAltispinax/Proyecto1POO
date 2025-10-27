/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.io.*;

public class Cursos implements Serializable{
    public enum Modalidad {
        PRESENCIAL("Presencial"),
        VIRTUAL_SINCRONICO("Virtual sincrónico"),
        VIRTUAL_ASINCRONICO("Virtual asincrónico"),
        VIRTUAL_HIBRIDO("Virtual híbrido"),
        SEMIPRESENCIAL("Semipresencial");

        private final String texto;

        Modalidad(String texto) {
            this.texto = texto;
        }

        public String getTexto() {
            return texto;
        }

        public static Modalidad desdeTexto(String texto) {
            for (Modalidad m : Modalidad.values()) {
                if (m.texto.equalsIgnoreCase(texto)) {
                    return m;
                }
            }
            return null;
        }
    }
    private String identificacion;
    private String nombre;
    private String descripcion;
    private int horasPorDia;
    private Modalidad modalidad;
    private int minEstudiantes;
    private int maxEstudiantes;
    private String tipoCurso;
    private int calificacionMinima;

    // Listas separadas
    private ArrayList<Grupos> grupos; // aún no cumplen mínimo


    public Cursos(String identificacion, String nombre, String descripcion, int horasPorDia,
                 String modalidad, int minEstudiantes, int maxEstudiantes,
                 String tipoCurso, int calificacionMinima) {
        this.modalidad = Modalidad.desdeTexto(modalidad);
        if (this.modalidad == null) {
            throw new IllegalArgumentException("Modalidad no válida: " + modalidad);
        }
        
        if (identificacion == null || identificacion.length() != 6)
            throw new IllegalArgumentException("La identificación debe tener exactamente 6 caracteres.");
        if (nombre == null || nombre.length() < 5 || nombre.length() > 40)
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 40 caracteres.");
        if (descripcion == null || descripcion.length() < 5 || descripcion.length() > 400)
            throw new IllegalArgumentException("La descripción debe tener entre 5 y 400 caracteres.");
        if (horasPorDia < 1 || horasPorDia > 8)
            throw new IllegalArgumentException("Las horas por día deben estar entre 1 y 8.");
        if (minEstudiantes < 1 || minEstudiantes > 5)
            throw new IllegalArgumentException("La cantidad mínima de estudiantes debe estar entre 1 y 5.");
        if (maxEstudiantes < minEstudiantes || maxEstudiantes > 20)
            throw new IllegalArgumentException("La cantidad máxima debe estar entre la mínima y 20.");
        if (calificacionMinima < 0 || calificacionMinima > 100)
            throw new IllegalArgumentException("La calificación mínima debe estar entre 0 y 100.");

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horasPorDia = horasPorDia;
        
        this.minEstudiantes = minEstudiantes;
        this.maxEstudiantes = maxEstudiantes;
        this.tipoCurso = tipoCurso;
        this.calificacionMinima = calificacionMinima;
        this.grupos = new ArrayList<>();

    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Grupos> getGrupos() {
        return grupos;
    }


    public Grupos devGrupos(int idGrupo){
        for (Grupos g : grupos){
            if(g.getIdGrupo()==idGrupo){
                return g;
            }
        }
        return null;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getHorasPorDia() {
        return horasPorDia;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public int getMinEstudiantes() {
        return minEstudiantes;
    }

    public int getMaxEstudiantes() {
        return maxEstudiantes;
    }

    public String getTipoCurso() {
        return tipoCurso;
    }

    public double getCalificacionMinima() {
        return calificacionMinima;
    }

    public void setIdentificacion(String identificacion) {
    if (identificacion == null || identificacion.length() != 6)
        throw new IllegalArgumentException("La identificación debe tener exactamente 6 caracteres.");
    this.identificacion = identificacion;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.length() < 5 || nombre.length() > 40)
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 40 caracteres.");
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.length() < 5 || descripcion.length() > 400)
            throw new IllegalArgumentException("La descripción debe tener entre 5 y 400 caracteres.");
        this.descripcion = descripcion;
    }

    public void setHorasPorDia(int horasPorDia) {
        if (horasPorDia < 1 || horasPorDia > 8)
            throw new IllegalArgumentException("Las horas por día deben estar entre 1 y 8.");
        this.horasPorDia = horasPorDia;
    }

    public void setModalidad(String modalidadTexto) {
        Modalidad nueva = Modalidad.desdeTexto(modalidadTexto);
        if (nueva == null) {
            throw new IllegalArgumentException("Modalidad no válida: " + modalidadTexto);
        }
        this.modalidad = nueva;
    }

    public void setMinEstudiantes(int minEstudiantes) {
        if (minEstudiantes < 1 || minEstudiantes > 5)
            throw new IllegalArgumentException("La cantidad mínima de estudiantes debe estar entre 1 y 5.");
        this.minEstudiantes = minEstudiantes;
    }

    public void setMaxEstudiantes(int maxEstudiantes) {
        if (maxEstudiantes < this.minEstudiantes || maxEstudiantes > 20)
            throw new IllegalArgumentException("La cantidad máxima debe estar entre la mínima y 20.");
        this.maxEstudiantes = maxEstudiantes;
    }

    public void setTipoCurso(String tipoCurso) {
        // Sin restricciones dadas, pero podrías agregar validaciones personalizadas si aplica.
        this.tipoCurso = tipoCurso;
    }

    public void setCalificacionMinima(int calificacionMinima) {
        if (calificacionMinima < 0 || calificacionMinima > 100)
            throw new IllegalArgumentException("La calificación mínima debe estar entre 0 y 100.");
        this.calificacionMinima = calificacionMinima;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // --- Crear un nuevo grupo vacío ---
    public void crearGrupo(LocalDate inicio, LocalDate fin,Cursos curso) {
        int nuevoId = grupos.size() + 1;
        Grupos nuevo = new Grupos(nuevoId, inicio, fin);
        nuevo.asignarCurso(curso);
        grupos.add(nuevo);
    }
    
    
    public boolean verificarGrupo(int idGrupo){
        for(Grupos grupo : grupos){
            if (grupo.getIdGrupo() == idGrupo){
                if (grupo.getCantidadEstudiantes() <= maxEstudiantes && grupo.getCantidadEstudiantes() >= minEstudiantes) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    
    // --- Matricular estudiante en un grupo específico ---
    public boolean matricularEstudianteEnGrupo(int idGrupo,Estudiantes est) {
        
        if(verificarGrupo(idGrupo)){
            buscarGrupo(idGrupo).agregarEstudiantes(est);
            return true;
        }
        return false;
    }

    // --- Buscar grupo por id en ambas listas ---
    private Grupos buscarGrupo(int idGrupo) {
        for (Grupos g : grupos)
            if (g.getIdGrupo() == idGrupo) return g;
        return null;
    }
    
    
    
    
    
    
    

    @Override
    public String toString() {
        return "Curso: " + nombre + " (" + identificacion + ")\n" +
               "Tipo: " + tipoCurso + " | Modalidad: " + modalidad + "\n" +
               "Horas por día: " + horasPorDia + "\n" +
               "Estudiantes: " + minEstudiantes + " - " + maxEstudiantes + "\n" +
               "Grupos : " + grupos.size();
    }
}
