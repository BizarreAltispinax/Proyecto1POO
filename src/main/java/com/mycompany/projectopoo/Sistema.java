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
public class Sistema implements Serializable{
    private static final long serialVersionUID = 1L;


    private static final String RUTA_ARCHIVO = "sistema.dat";
    private ArrayList<Estudiantes> estudiantes;
    private ArrayList<Profesores> profesores;
    
    private ArrayList<Grupos> grupos;
    private ArrayList<Cursos> cursos;
    private ArrayList<Evaluaciones> evaluaciones;
    private static Sistema instancia;
    
    
    
    
    public Sistema(){
        this.estudiantes=new ArrayList<>();
        this.profesores=new ArrayList<>();
        this.grupos=new ArrayList<>();
        this.cursos=new ArrayList<>();
        this.evaluaciones=new ArrayList<>();
    }
    public void agregarEstudiantes(Estudiantes estudiante){
        estudiantes.add(estudiante);
    }
    public void agregarProfesores(Profesores profesor){
        profesores.add(profesor);
    }
    
    public void agregarCursos(Cursos curso){
        cursos.add(curso);
    }
    public void agregarEvaluacion(Evaluaciones evaluacion){
        evaluaciones.add(evaluacion);
    }
    
    /*
    public void agregarGrupos(Grupos grupo){
        estudiantes.add(grupo);
    }
    */
    
    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = cargar();
        }
        return instancia;
    }

    
    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(this);
            System.out.println("✅ Sistema guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
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
    
    
    
    public String encontrarEstudiante(int opcion,String identificacion){
        
        for (Estudiantes e : estudiantes) {
            if(e.getIdentificacion().equals(identificacion)){
                switch (opcion) {
                    case 1:
                        return e.getNombre();
                        
                    case 2:
                        return e.getApellido1();
                        
                    case 3:
                        return e.getApellido2();
                        
                    case 4:
                        return e.getTelefono();
                        
                    case 5:
                        return e.getCorreoElectronico();
                        
                    case 6:
                        return e.getDireccion();
                        
                    case 7:
                        return e.getOrganizacion();
                        
                    case 8:
                        return e.getTemasInteres();
                        
                    default:
                        return "Opción no válida";
                }    
            }
            
        }
        return "No se encontro al estudiante";
    }

    public String encontrarProfesor(int opcion,String identificacion){

        for (Profesores e : profesores) {
            if(e.getIdentificacion().equals(identificacion)){
                switch (opcion) {
                    case 1:
                        return e.getNombre();

                    case 2:
                        return e.getApellido1();

                    case 3:
                        return e.getApellido2();

                    case 4:
                        return e.getTelefono();

                    case 5:
                        return e.getCorreoElectronico();

                    case 6:
                        return e.getDireccion();

                    case 7:
                        return e.getTitulos();

                    case 8:
                        return e.getCertificaciones();

                    default:
                        return "Opción no válida";
                }
            }

        }
        return "No se encontro al profesor";
    }

    
    
    public boolean todasIdentificaciones(String identificacion){
        
        for(Estudiantes estudiante : estudiantes){
            if(estudiante.getIdentificacion().equals(identificacion)){
                return true;
            }
        }
        return false;
    }
    public boolean todasIdentificacionesProfe(String identificacion){
        
        for(Profesores profe : profesores){
            if(profe.getIdentificacion().equals(identificacion)){
                return true;
            }
        }
        return false;
    }
    
    
    public boolean todosCorreos(String correo){
        for(Estudiantes estudiante : estudiantes){
            if(estudiante.getCorreoElectronico().equals(correo)){
                return false;
            }
        }
        return true;
    }
    public boolean devolverEstudiante(int opcion,String identificacion,String atributo,ArrayList<String> temas) {
        
        for (Estudiantes e : estudiantes) {
            if(e.getIdentificacion().equals(identificacion)){
                switch (opcion) {
                    case 1:
                        e.setNombre(atributo);
                        return true;
                        
                    case 2:
                        e.setApellido1(atributo);
                        return true;
                        
                    case 3:
                        e.setApellido2(atributo);
                        return true;
                        
                    case 4:
                        e.setTelefono(atributo);
                        return true;
                        
                    case 5:
                        e.setCorreoElectronico(atributo);
                        return true;
                        
                    case 6:
                        e.setDireccion(atributo);
                        return true;
                        
                    case 7:
                        e.setOrganizacion(atributo);
                        return true;
                        
                    case 8:
                        e.setTemasInteres(temas);
                        return true;
                    case 9:
                        e.setContraseña(atributo);
                        return true;
                    case 10:
                        e.setIdentificacion(atributo);
                        return true;
                        
                    default:
                        return false;
                }    
            }
            
        }
        return false;
    }

    public boolean devolverProfesor(int opcion,String identificacion,String atributo,ArrayList<String> titulos,ArrayList<String> certificaciones){
        for (Profesores e : profesores) {
            if(e.getIdentificacion().equals(identificacion)){
                switch (opcion) {
                    case 1:
                        e.setNombre(atributo);
                        return true;

                    case 2:
                        e.setApellido1(atributo);
                        return true;

                    case 3:
                        e.setApellido2(atributo);
                        return true;

                    case 4:
                        e.setTelefono(atributo);
                        return true;

                    case 5:
                        e.setCorreoElectronico(atributo);
                        return true;

                    case 6:
                        e.setDireccion(atributo);
                        return true;

                    case 7:
                        e.setTitulos(titulos);
                        return true;

                    case 8:
                        e.setCertificaciones(certificaciones);
                        return true;

                    case 9:
                        e.setContraseña(atributo);
                        return true;

                    case 10:
                        e.setIdentificacion(atributo);
                        return true;

                    default:
                        return false;
                }
            }

        }
        return false;
    }
    
    public boolean devolverCurso(int opcion,String identificacion,String atributo,int atributoInt){
        
        for (Cursos e : cursos) {
            if(e.getIdentificacion().equals(identificacion)){
                switch (opcion) {
                    case 1:
                        e.setIdentificacion(atributo);
                        return true;
                        
                    case 2:
                        e.setNombre(atributo);
                        return true;
                        
                    case 3:
                        e.setDescripcion(atributo);
                        return true;
                        
                    case 4:
                        e.setHorasPorDia(atributoInt);
                        return true;
                        
                    case 5:
                        e.setModalidad(atributo);
                        return true;
                        
                    case 6:
                        e.setMinEstudiantes(atributoInt);
                        return true;
                        
                    case 7:
                        e.setMaxEstudiantes(atributoInt);
                        return true;
                        
                    case 8:
                        e.setTipoCurso(atributo);
                        return true;
                    case 9:
                        e.setCalificacionMinima(atributoInt);
                        return true;
                        
                    default:
                        return false;
                }    
            }
            
        }
        return false;
    }
    public ArrayList<Evaluaciones> getEvaluaciones() {
        return evaluaciones;
    }
    
    public void eliminarEstudiantes(String identificacion){

        estudiantes.removeIf(est -> est.getIdentificacion().equals(identificacion));
        
        for (Cursos c : cursos) {
            for (Grupos g : c.getGrupos()) {
                g.getEstudiantes().removeIf(e -> e.getIdentificacion().equals(identificacion));
            }
        }
    }


    public void eliminarProfesor(String identificacion){

        profesores.removeIf(prf -> prf.getIdentificacion().equals(identificacion));

        for (Cursos c : cursos) {
            for (Grupos g : c.getGrupos()) {
                Profesores prof = g.getProfesor();
                if (prof != null && prof.getIdentificacion().equals(identificacion)) {
                    g.asignarProfesor(null);
                }
            }
        }
    }
    
    public void eliminarCursos(String identificacion){

        cursos.removeIf(est -> est.getIdentificacion().equals(identificacion));
        
        
    }
    
    
    
    public Estudiantes devEstudiante(String identificacion){
        for (Estudiantes est : estudiantes){
            if(est.getIdentificacion().equals(identificacion)){
                return est;
            }
        }
        return null;
    }
    public Profesores devProfesor(String identificacion){
        for (Profesores prof : profesores){
            if(prof.getIdentificacion().equals(identificacion)){
                return prof;
            }
        }
        return null;
    }
    public Cursos  devCursos(String idCurso){
        for (Cursos c : cursos){
            if(c.getIdentificacion().equals(idCurso)){
                return c;
            }
        }
        return null;
    }
    
    public Evaluaciones devEva(int IdEva){
        for (Evaluaciones e :evaluaciones){
            if(e.getIdentificacion()==IdEva){
                return e;
            }
        }
        return null;
    }
    public void ReporteEstudiantesCurso(){
        
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream("ListaEstudiantes.pdf"));
            documento.open();
            documento.add(new Paragraph("Lista de estudiantes:"));
            for (Cursos c : cursos){
                documento.add(new Paragraph("Lista de estudiantes del curso "+c.getIdentificacion()));
                for (Grupos g : c.getGrupos()) {
                    if (g.getFechaFin().isAfter(LocalDate.now()) && g.getFechaInicio().isBefore(LocalDate.now())){
                        documento.add(new Paragraph("Lista de estudiantes del grupo "+g.getIdGrupo()));
                        ArrayList<Estudiantes> copia = new ArrayList<>(g.getEstudiantes());
                        copia.sort(Comparator
                            .comparing(Estudiantes::getApellido1, String.CASE_INSENSITIVE_ORDER)
                            .thenComparing(Estudiantes::getApellido2, String.CASE_INSENSITIVE_ORDER)
                            .thenComparing(Estudiantes::getNombre, String.CASE_INSENSITIVE_ORDER)
                        );
                        for (Estudiantes est : copia){
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
    
    public void ReporteEvaluacionesDetalle(int idEvaluacion){
        
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream("DetalleEvaluacion.pdf"));
            documento.open();
            documento.add(new Paragraph("Detalle de la evaluacion: "));
            for (Evaluaciones e : evaluaciones){
                if (e.getIdentificacion()==idEvaluacion){
                    documento.add(new Paragraph(e.toString()));
                }
            }
            documento.close();
            
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
    public void ReporteEstadistica(int opcion, String idCurso, String idGrupo,LocalDate fechavigencia){
        Document documento = new Document();
        int contador=0;
        switch (opcion) {
            case 1:
                try{
                    PdfWriter.getInstance(documento, new FileOutputStream("EstadisticaMatricula.pdf"));
                    documento.open();   
                    for (Cursos c:cursos){
                        for (Grupos g:grupos){
                            if (fechavigencia.isAfter(g.getFechaInicio()) && fechavigencia.isBefore(g.getFechaFin())){
                                contador=contador+g.getCantidadEstudiantes();
                            }
                            
                        }
                    }
                    documento.add(new Paragraph("El total de estudiantes por todos los cursos y grupos es de: "+contador));
                    documento.close();
                }catch(FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;     
            case 2:
                try{
                    PdfWriter.getInstance(documento, new FileOutputStream("EstadisticaMatricula.pdf"));
                    documento.open();
                    for (Cursos c:cursos){
                        if (c.getIdentificacion().equals(idCurso)){
                            for (Grupos g:grupos){
                                contador=contador+g.getCantidadEstudiantes();
                            }
                        }

                    }
                    documento.add(new Paragraph("El total de estudiantes en el curso "+idCurso+" es: "+contador));
                    documento.close();
                }catch(FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;
                        
            case 3:
                int idG=Integer.parseInt(idGrupo);
                try{
                    PdfWriter.getInstance(documento, new FileOutputStream("EstadisticaMatricula.pdf"));
                    documento.open();
                    for (Cursos c:cursos){
                        if (c.getIdentificacion().equals(idCurso)){
                            for (Grupos g:grupos){
                                if (g.getIdGrupo()==idG){
                                    contador=contador+g.getCantidadEstudiantes();
                                }

                            }
                        }

                    }
                    documento.add(new Paragraph("El total de estudiantes para el grupo "+idCurso+" y para el grupo "+idG+" es: "+contador));
                    documento.close();
                }catch(FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                return;
                
        }
    } 
    public boolean verificarEstudiante(String idCurso,Estudiantes est){
        
        for (Grupos g : devCursos(idCurso).getGrupos()){
            for (Estudiantes e : g.getEstudiantes()){
                if (e.equals(est)){
                    return false;
                }
            }
        }
        return true;
        
    }
    public ArrayList<Cursos> getCursos(){
        return cursos;
    }
}
