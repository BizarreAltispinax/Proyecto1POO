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
    private List<Estudiantes> estudiantes;
    private ArrayList<Profesores> profesores;
    /*
    private ArrayList<Grupos> grupos;
    private ArrayList<Cursos> cursos;
    private ArrayList<Evaluaciones> evaluaciones;
    */
    
    
    
    public Sistema(){
        this.estudiantes=new ArrayList<>();
    }
    public void agregarEstudiantes(Estudiantes estudiante){
        estudiantes.add(estudiante);
    }
    public void agregarProfesores(Profesores profesor){
        profesores.add(profesor);
    }
    /*
    public void agregarGrupos(Grupos grupo){
        estudiantes.add(grupo);
    }
    public void agregarCursos(Cursos curso){
        profesores.add(curso);
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
    
    
}
