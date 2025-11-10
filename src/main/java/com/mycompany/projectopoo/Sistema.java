/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
/**
 *
 * @author Usuario
 */
/**
 * Clase Sistema
 * 
 * Esta clase actúa como el núcleo principal del sistema, manejando todas las operaciones 
 * relacionadas con estudiantes, profesores, cursos, grupos y evaluaciones. 
 * Permite agregar, eliminar, buscar, modificar y generar reportes en formato PDF.
 * 
 * Además, implementa un patrón de diseño Singleton para garantizar que solo exista 
 * una instancia del sistema en ejecución, la cual puede ser guardada y cargada desde archivo.
 * 
 * Autor: (Tu nombre)
 * Versión: 1.0
 */

public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Ruta donde se guarda el archivo serializado del sistema */
    private static final String RUTA_ARCHIVO = "datos/matriculaycalificaciones/sistema.dat";

    /** Listas principales que almacenan los objetos del sistema */
    private ArrayList<Estudiantes> estudiantes;
    private ArrayList<Profesores> profesores;
    private ArrayList<Grupos> grupos;
    private ArrayList<Cursos> cursos;
    private ArrayList<Evaluaciones> evaluaciones;

    /** Instancia única del sistema (Singleton) */
    private static Sistema instancia;

    /** Contador general para asignar identificadores de evaluaciones */
    private int cont;

    /**
     * Constructor que inicializa todas las listas del sistema.
     */
    public Sistema() {
        this.estudiantes = new ArrayList<>();
        this.profesores = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    /** Agrega un nuevo estudiante al sistema */
    public void agregarEstudiantes(Estudiantes estudiante) {
        estudiantes.add(estudiante);
    }

    /** Agrega un nuevo profesor al sistema */
    public void agregarProfesores(Profesores profesor) {
        profesores.add(profesor);
    }

    /** Agrega un nuevo curso al sistema */
    public void agregarCursos(Cursos curso) {
        cursos.add(curso);
    }

    /** Agrega una nueva evaluación al sistema */
    public void agregarEvaluacion(Evaluaciones evaluacion) {
        evaluaciones.add(evaluacion);
    }

    /*
    public void agregarGrupos(Grupos grupo){
        estudiantes.add(grupo);
    }
    */

    /**
     * Obtiene la instancia única del sistema.
     * Si no existe, intenta cargarla desde el archivo o crea una nueva.
     */
    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = cargar();
        }
        return instancia;
    }

    /**
     * Guarda la instancia actual del sistema en el archivo "sistema.dat".
     */
    public void guardar() {
        try {
            // Crear el directorio si no existe
            File directorio = new File("datos/matriculaycalificaciones");
            if (!directorio.exists()) {
                directorio.mkdirs(); // crea todos los directorios necesarios
            }

            // Guardar el objeto
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(RUTA_ARCHIVO))) {
                oos.writeObject(this);
                System.out.println("✅ Sistema guardado correctamente en " + RUTA_ARCHIVO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga la instancia del sistema desde el archivo serializado.
     * Si el archivo no existe, se crea un nuevo sistema vacío.
     */
    private static Sistema cargar() {
        File archivo = new File(RUTA_ARCHIVO);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                Sistema sistemaCargado = (Sistema) ois.readObject();
                System.out.println("📂 Sistema cargado correctamente.");
                return sistemaCargado;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("⚠️ No se encontró archivo, creando nuevo sistema.");
        return new Sistema();
    }

    /**
     * Busca un estudiante por su identificación y devuelve un atributo específico según la opción.
     * 
     * @param opcion número que indica qué dato devolver
     * @param identificacion cédula o ID del estudiante
     * @return el atributo solicitado o un mensaje si no se encuentra
     */
    public String encontrarEstudiante(int opcion, String identificacion) {
        for (Estudiantes e : estudiantes) {
            if (e.getIdentificacion().equals(identificacion)) {
                switch (opcion) {
                    case 1: return e.getNombre();
                    case 2: return e.getApellido1();
                    case 3: return e.getApellido2();
                    case 4: return e.getTelefono();
                    case 5: return e.getCorreoElectronico();
                    case 6: return e.getDireccion();
                    case 7: return e.getOrganizacion();
                    case 8: return e.getTemasInteres();
                    default: return "Opción no válida";
                }
            }
        }
        return "No se encontro al estudiante";
    }

    /**
     * Busca un profesor por su identificación y devuelve un atributo específico según la opción.
     */
    public String encontrarProfesor(int opcion, String identificacion) {
        for (Profesores e : profesores) {
            if (e.getIdentificacion().equals(identificacion)) {
                switch (opcion) {
                    case 1: return e.getNombre();
                    case 2: return e.getApellido1();
                    case 3: return e.getApellido2();
                    case 4: return e.getTelefono();
                    case 5: return e.getCorreoElectronico();
                    case 6: return e.getDireccion();
                    case 7: return e.getTitulos();
                    case 8: return e.getCertificaciones();
                    default: return "Opción no válida";
                }
            }
        }
        return "No se encontro al profesor";
    }

    /** Verifica si una identificación ya está registrada en los estudiantes */
    public boolean todasIdentificaciones(String identificacion) {
        for (Estudiantes estudiante : estudiantes) {
            if (estudiante.getIdentificacion().equals(identificacion)) {
                return true;
            }
        }
        return false;
    }

    /** Verifica si una identificación ya está registrada en los profesores */
    public boolean todasIdentificacionesProfe(String identificacion) {
        for (Profesores profe : profesores) {
            if (profe.getIdentificacion().equals(identificacion)) {
                return true;
            }
        }
        return false;
    }

    /** Verifica si un correo ya está en uso por un estudiante */
    public boolean todosCorreos(String correo) {
        for (Estudiantes estudiante : estudiantes) {
            if (estudiante.getCorreoElectronico().equals(correo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Modifica los atributos de un estudiante según la opción seleccionada.
     */
    public boolean devolverEstudiante(int opcion, String identificacion, String atributo, ArrayList<String> temas) {
        for (Estudiantes e : estudiantes) {
            if (e.getIdentificacion().equals(identificacion)) {
                switch (opcion) {
                    case 1: e.setNombre(atributo); return true;
                    case 2: e.setApellido1(atributo); return true;
                    case 3: e.setApellido2(atributo); return true;
                    case 4: e.setTelefono(atributo); return true;
                    case 5: e.setCorreoElectronico(atributo); return true;
                    case 6: e.setDireccion(atributo); return true;
                    case 7: e.setOrganizacion(atributo); return true;
                    case 8: e.setTemasInteres(temas); return true;
                    case 9: e.setContraseña(atributo); return true;
                    case 10: e.setIdentificacion(atributo); return true;
                    default: return false;
                }
            }
        }
        return false;
    }

    /**
     * Modifica los atributos de un profesor según la opción seleccionada.
     */
    public boolean devolverProfesor(int opcion, String identificacion, String atributo, ArrayList<String> titulos, ArrayList<String> certificaciones) {
        for (Profesores e : profesores) {
            if (e.getIdentificacion().equals(identificacion)) {
                switch (opcion) {
                    case 1: e.setNombre(atributo); return true;
                    case 2: e.setApellido1(atributo); return true;
                    case 3: e.setApellido2(atributo); return true;
                    case 4: e.setTelefono(atributo); return true;
                    case 5: e.setCorreoElectronico(atributo); return true;
                    case 6: e.setDireccion(atributo); return true;
                    case 7: e.setTitulos(titulos); return true;
                    case 8: e.setCertificaciones(certificaciones); return true;
                    case 9: e.setContraseña(atributo); return true;
                    case 10: e.setIdentificacion(atributo); return true;
                    default: return false;
                }
            }
        }
        return false;
    }

    /**
     * Modifica los atributos de un curso según la opción seleccionada.
     */
    public boolean devolverCurso(int opcion, String identificacion, String atributo, int atributoInt) {
        for (Cursos e : cursos) {
            if (e.getIdentificacion().equals(identificacion)) {
                switch (opcion) {
                    case 1: e.setIdentificacion(atributo); return true;
                    case 2: e.setNombre(atributo); return true;
                    case 3: e.setDescripcion(atributo); return true;
                    case 4: e.setHorasPorDia(atributoInt); return true;
                    case 5: e.setModalidad(atributo); return true;
                    case 6: e.setMinEstudiantes(atributoInt); return true;
                    case 7: e.setMaxEstudiantes(atributoInt); return true;
                    case 8: e.setTipoCurso(atributo); return true;
                    case 9: e.setCalificacionMinima(atributoInt); return true;
                    default: return false;
                }
            }
        }
        return false;
    }

    /** Retorna la lista de evaluaciones */
    public ArrayList<Evaluaciones> getEvaluaciones() {
        return evaluaciones;
    }

    /** Elimina un estudiante del sistema y de todos los grupos donde aparezca */
    public void eliminarEstudiantes(String identificacion) {
        estudiantes.removeIf(est -> est.getIdentificacion().equals(identificacion));
        for (Cursos c : cursos) {
            for (Grupos g : c.getGrupos()) {
                g.getEstudiantes().removeIf(e -> e.getIdentificacion().equals(identificacion));
            }
        }
    }

    /** Elimina un profesor y lo desasigna de los grupos donde esté asignado */
    public void eliminarProfesor(String identificacion) {
        profesores.removeIf(prf -> prf.getIdentificacion().equals(identificacion));
        for (Cursos c : cursos) {
            for (Grupos g : c.getGrupos()) {
                Profesores prof = g.getProfesor();
                if (prof != null && prof.getIdentificacion().equals(identificacion)) {
                    g.asignarProfesor(null); // se elimina la referencia
                }
            }
        }
    }

    /** Elimina un curso del sistema */
    public void eliminarCursos(String identificacion) {
        cursos.removeIf(est -> est.getIdentificacion().equals(identificacion));
    }

    /** Devuelve un estudiante específico por su identificación */
    public Estudiantes devEstudiante(String identificacion) {
        for (Estudiantes est : estudiantes) {
            if (est.getIdentificacion().equals(identificacion)) {
                return est;
            }
        }
        return null;
    }

    /** Devuelve un profesor específico por su identificación */
    public Profesores devProfesor(String identificacion) {
        for (Profesores prof : profesores) {
            if (prof.getIdentificacion().equals(identificacion)) {
                return prof;
            }
        }
        return null;
    }

    /** Devuelve un curso específico según su ID */
    public Cursos devCursos(String idCurso) {
        for (Cursos c : cursos) {
            if (c.getIdentificacion().equals(idCurso)) {
                return c;
            }
        }
        return null;
    }

    /** Devuelve una evaluación específica según su ID numérico */
    public Evaluaciones devEva(int IdEva) {
        for (Evaluaciones e : evaluaciones) {
            if (e.getIdentificacion() == IdEva) {
                return e;
            }
        }
        return null;
    }

    /**
     * Genera un reporte PDF con la lista de estudiantes activos en los cursos y grupos.
     */
    public void ReporteEstudiantesCurso() {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("datos/matriculaycalificaciones/ListaEstudiantes.pdf"));
            documento.open();
            documento.add(new Paragraph("Lista de estudiantes:"));
            for (Cursos c : cursos) {
                documento.add(new Paragraph("Lista de estudiantes del curso " + c.getIdentificacion()));
                for (Grupos g : c.getGrupos()) {
                    if (g.getFechaFin().isAfter(LocalDate.now()) && g.getFechaInicio().isBefore(LocalDate.now())) {
                        documento.add(new Paragraph("Lista de estudiantes del grupo " + g.getIdGrupo()));
                        ArrayList<Estudiantes> copia = new ArrayList<>(g.getEstudiantes());
                        // Se ordena alfabéticamente
                        copia.sort(Comparator
                            .comparing(Estudiantes::getApellido1, String.CASE_INSENSITIVE_ORDER)
                            .thenComparing(Estudiantes::getApellido2, String.CASE_INSENSITIVE_ORDER)
                            .thenComparing(Estudiantes::getNombre, String.CASE_INSENSITIVE_ORDER)
                        );
                        for (Estudiantes est : copia) {
                            documento.add(new Paragraph(est.toString()));
                        }
                    }
                }
            }
            documento.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un reporte PDF con los detalles de una evaluación específica.
     */
    public void ReporteEvaluacionesDetalle(int idEvaluacion) {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("datos/matriculaycalificaciones/DetalleEvaluacion.pdf"));
            documento.open();
            documento.add(new Paragraph("Detalle de la evaluacion: "));
            for (Evaluaciones e : evaluaciones) {
                if (e.getIdentificacion() == idEvaluacion) {
                    documento.add(new Paragraph(e.toString()));
                }
            }
            documento.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un reporte PDF con estadísticas de matrícula según la opción seleccionada.
     * 
     * @param opcion 1: todos los cursos, 2: curso específico, 3: grupo específico
     */
    public void ReporteEstadistica(int opcion, String idCurso, String idGrupo, LocalDate fechavigencia) {
        Document documento = new Document();
        int contador = 0;
        switch (opcion) {
            case 1:
                try {
                    PdfWriter.getInstance(documento, new FileOutputStream("datos/matriculaycalificaciones/EstadisticaMatricula.pdf"));
                    documento.open();
                    for (Cursos c : cursos) {
                        for (Grupos g : c.getGrupos()) {
                            if (fechavigencia.isAfter(g.getFechaInicio()) && fechavigencia.isBefore(g.getFechaFin())) {
                                contador += g.getCantidadEstudiantes();
                            }
                        }
                    }
                    documento.add(new Paragraph("El total de estudiantes por todos los cursos y grupos es de: " + contador));
                    documento.close();
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;
            case 2:
                try {
                    PdfWriter.getInstance(documento, new FileOutputStream("datos/matriculaycalificaciones/EstadisticaMatricula.pdf"));
                    documento.open();
                    for (Cursos c : cursos) {
                        if (c.getIdentificacion().equals(idCurso)) {
                            for (Grupos g : c.getGrupos()) {
                                contador += g.getCantidadEstudiantes();
                            }
                        }
                    }
                    documento.add(new Paragraph("El total de estudiantes en el curso " + idCurso + " es: " + contador));
                    documento.close();
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;
            case 3:
                int idG = Integer.parseInt(idGrupo);
                try {
                    PdfWriter.getInstance(documento, new FileOutputStream("datos/matriculaycalificaciones/EstadisticaMatricula.pdf"));
                    documento.open();
                    for (Cursos c : cursos) {
                        if (c.getIdentificacion().equals(idCurso)) {
                            for (Grupos g : c.getGrupos()) {
                                if (g.getIdGrupo() == idG) {
                                    contador += g.getCantidadEstudiantes();
                                }
                            }
                        }
                    }
                    documento.add(new Paragraph("El total de estudiantes para el curso " + idCurso + " y el grupo " + idG + " es: " + contador));
                    documento.close();
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;
        }
    }

    /**
     * Verifica si un estudiante ya pertenece a algún grupo del curso especificado.
     */
    public boolean verificarEstudiante(String idCurso, Estudiantes est) {
        for (Grupos g : devCursos(idCurso).getGrupos()) {
            for (Estudiantes e : g.getEstudiantes()) {
                if (e.equals(est)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Retorna la lista completa de cursos */
    public ArrayList<Cursos> getCursos() {
        return cursos;
    }

    /** Genera y devuelve un nuevo ID de evaluación incremental */
    public int idEvaluacion() {
        cont = cont + 1;
        return cont;
    }
}
