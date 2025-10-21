/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class Sistema {
    private ArrayList<Estudiantes> estudiantes;
    private ArrayList<Profesores> profesores;
    
    private ArrayList<Grupos> grupos;
    private ArrayList<Cursos> cursos;
    /*
    private ArrayList<Evaluaciones> evaluaciones;
    */
    
    
    
    public Sistema(){
        this.estudiantes=new ArrayList<>();
        this.profesores=new ArrayList<>();
        this.grupos=new ArrayList<>();
        this.cursos=new ArrayList<>();
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
    /*
    public void agregarGrupos(Grupos grupo){
        estudiantes.add(grupo);
    }
    
    public void agregarEvaluaciones(Evaluaciones evaluacion){
        estudiantes.add(evaluacion);
    }
    */
    
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

    
    
    public boolean todasIdentificaciones(String identificacion){
        
        for(Estudiantes estudiante : estudiantes){
            if(estudiante.getIdentificacion().equals(identificacion)){
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
    public boolean devolverEstudiante(int opcion,String identificacion,String atributo,ArrayList<String> temas){
        
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
    
    public boolean devolverCurso(int opcion,String identificacion,String atributo,int atributoInt){
        /*            
            private String identificacion;
            private String nombre;
            private String descripcion;
            private int horasPorDia;
            private String modalidad;
            private int minEstudiantes;
            private int maxEstudiantes;
            private String tipoCurso;
            private int calificacionMinima;
            */
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
    
    
    public void eliminarEstudiantes(String identificacion){

        estudiantes.removeIf(est -> est.getIdentificacion().equals(identificacion));
        
        for (Cursos c : cursos) {
            for (Grupos g : c.getGrupos()) {
                g.getEstudiantes().removeIf(e -> e.getIdentificacion().equals(identificacion));
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
    public Cursos  devCursos(String idCurso){
        for (Cursos c : cursos){
            if(c.getIdentificacion().equals(idCurso)){
                return c;
            }
        }
        return null;
    }
    
}
