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

/**
 * Representa un curso dentro del sistema académico, incluyendo su información básica,
 * modalidad, límites de estudiantes, y los grupos asociados.
 * Implementa {@link Serializable} para permitir su persistencia.
 * 
 * <p>Incluye validaciones en el constructor y setters para asegurar que los datos
 * ingresados sean consistentes.</p>
 */
public class Cursos implements Serializable {

    /**
     * Enum que define las diferentes modalidades posibles de un curso.
     */
    public enum Modalidad {
        PRESENCIAL("Presencial"),
        VIRTUAL_SINCRONICO("Virtual sincrónico"),
        VIRTUAL_ASINCRONICO("Virtual asincrónico"),
        VIRTUAL_HIBRIDO("Virtual híbrido"),
        SEMIPRESENCIAL("Semipresencial");

        private final String texto;

        /**
         * Constructor del enum Modalidad.
         * @param texto descripción textual de la modalidad.
         */
        Modalidad(String texto) {
            this.texto = texto;
        }

        /**
         * Obtiene el texto asociado a la modalidad.
         * @return texto descriptivo.
         */
        public String getTexto() {
            return texto;
        }

        /**
         * Convierte una cadena en una modalidad, si existe una coincidencia.
         * @param texto texto a comparar con las modalidades existentes.
         * @return modalidad correspondiente o {@code null} si no coincide ninguna.
         */
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

    // Lista de grupos asociados a este curso
    private ArrayList<Grupos> grupos;

    /**
     * Constructor principal de la clase Cursos.
     *
     * @param identificacion código único de 6 caracteres.
     * @param nombre nombre del curso (5 a 40 caracteres).
     * @param descripcion descripción del curso (5 a 400 caracteres).
     * @param horasPorDia cantidad de horas impartidas por día (1 a 8).
     * @param modalidad texto de la modalidad (debe coincidir con una modalidad válida).
     * @param minEstudiantes cantidad mínima de estudiantes (1 a 5).
     * @param maxEstudiantes cantidad máxima de estudiantes (entre mínima y 20).
     * @param tipoCurso tipo o categoría del curso.
     * @param calificacionMinima nota mínima aprobatoria (0 a 100).
     * @throws IllegalArgumentException si alguno de los parámetros no cumple las validaciones.
     */
    public Cursos(String identificacion, String nombre, String descripcion, int horasPorDia,
                 String modalidad, int minEstudiantes, int maxEstudiantes,
                 String tipoCurso, int calificacionMinima) {

        this.modalidad = Modalidad.desdeTexto(modalidad);
        if (this.modalidad == null)
            throw new IllegalArgumentException("Modalidad no válida: " + modalidad);

        // Validaciones básicas de datos
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

        // Asignaciones
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

    /** @return la identificación del curso. */
    public String getIdentificacion() {
        return identificacion;
    }

    /** @return el nombre del curso. */
    public String getNombre() {
        return nombre;
    }

    /** @return lista de grupos asociados al curso. */
    public ArrayList<Grupos> getGrupos() {
        return grupos;
    }

    /**
     * Devuelve un grupo específico según su identificador.
     * @param idGrupo id del grupo buscado.
     * @return el grupo correspondiente o {@code null} si no existe.
     */
    public Grupos devGrupos(int idGrupo){
        for (Grupos g : grupos){
            if(g.getIdGrupo()==idGrupo){
                return g; // Retorna el grupo encontrado
            }
        }
        return null;
    }

    /** @return descripción del curso. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @return horas por día del curso. */
    public int getHorasPorDia() {
        return horasPorDia;
    }

    /** @return modalidad del curso. */
    public Modalidad getModalidad() {
        return modalidad;
    }

    /** @return número mínimo de estudiantes permitidos. */
    public int getMinEstudiantes() {
        return minEstudiantes;
    }

    /** @return número máximo de estudiantes permitidos. */
    public int getMaxEstudiantes() {
        return maxEstudiantes;
    }

    /** @return tipo o categoría del curso. */
    public String getTipoCurso() {
        return tipoCurso;
    }

    /** @return calificación mínima aprobatoria. */
    public double getCalificacionMinima() {
        return calificacionMinima;
    }

    // --- Setters con validaciones ---

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
        if (nueva == null)
            throw new IllegalArgumentException("Modalidad no válida: " + modalidadTexto);
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
        // No hay restricciones específicas, pero se deja el método para futura validación
        this.tipoCurso = tipoCurso;
    }

    public void setCalificacionMinima(int calificacionMinima) {
        if (calificacionMinima < 0 || calificacionMinima > 100)
            throw new IllegalArgumentException("La calificación mínima debe estar entre 0 y 100.");
        this.calificacionMinima = calificacionMinima;
    }

    /**
     * Crea un nuevo grupo vacío y lo asocia al curso actual.
     * @param inicio fecha de inicio del grupo.
     * @param fin fecha de finalización del grupo.
     * @param curso curso al que se asocia el grupo.
     */
    public void crearGrupo(LocalDate inicio, LocalDate fin, Cursos curso) {
        int nuevoId = grupos.size() + 1; // Calcula un nuevo ID incremental
        Grupos nuevo = new Grupos(nuevoId, inicio, fin);
        nuevo.asignarCurso(curso); // Asocia el grupo al curso actual
        grupos.add(nuevo); // Agrega el grupo a la lista
    }

    /**
     * Verifica si un grupo cumple con los límites de estudiantes establecidos.
     * @param idGrupo id del grupo a verificar.
     * @return {@code true} si el grupo está dentro del rango permitido, {@code false} en caso contrario.
     */
    public boolean verificarGrupo(int idGrupo){
        for(Grupos grupo : grupos){
            if (grupo.getIdGrupo() == idGrupo){
                if (grupo.getCantidadEstudiantes() <= maxEstudiantes && grupo.getCantidadEstudiantes() >= minEstudiantes) {
                    return true; // Cumple con los límites
                }
            }
        }
        return false;
    }

    /**
     * Matricula un estudiante en un grupo específico si cumple las condiciones.
     * @param idGrupo id del grupo donde se desea matricular al estudiante.
     * @param est objeto del estudiante a matricular.
     * @return {@code true} si la matrícula fue exitosa, {@code false} si no se pudo realizar.
     */
    public boolean matricularEstudianteEnGrupo(int idGrupo, Estudiantes est) {
        if(verificarGrupo(idGrupo)){
            buscarGrupo(idGrupo).agregarEstudiantes(est); // Agrega el estudiante al grupo
            return true;
        }
        return false;
    }

    /**
     * Busca un grupo por su identificador.
     * @param idGrupo id del grupo a buscar.
     * @return el grupo encontrado o {@code null} si no existe.
     */
    private Grupos buscarGrupo(int idGrupo) {
        for (Grupos g : grupos)
            if (g.getIdGrupo() == idGrupo) return g;
        return null;
    }

    /**
     * Devuelve una representación textual del curso, incluyendo datos generales y cantidad de grupos.
     * @return cadena descriptiva del curso.
     */
    @Override
    public String toString() {
        return "Curso: " + nombre + " (" + identificacion + ")\n" +
               "Tipo: " + tipoCurso + " | Modalidad: " + modalidad + "\n" +
               "Horas por día: " + horasPorDia + "\n" +
               "Estudiantes: " + minEstudiantes + " - " + maxEstudiantes + "\n" +
               "Grupos : " + grupos.size();
    }
}