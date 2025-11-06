/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.time.*;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class Evaluaciones implements Serializable{
    private int identificacion;
    private String nombre;
    private String instrucciones;
    private ArrayList<String> objetivos;
    private int duracion;
    private boolean pregAl;
    private boolean resAl;
    private ArrayList<Ejercicios> ejercicios;
    private Grupos grupo;
    private JFrame frame;
    private JPanel panelCentral;
    private long inicio;
    private long fin;
    private int indiceActual = 0;
    private double puntajeTotalObt=0;
    private double puntajeTotal=0;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin; ;
    private JLabel lblTiempoRestante;
    private JLabel lblPreguntaActual;
    private javax.swing.Timer temporizador;
    private String nombreEst;
    private LocalDateTime fechaInicioEv;
    private LocalDateTime fechaFinEv;
    private int i;
    
    
    public Evaluaciones(int identificacion, String nombre, String instrucciones, ArrayList<String> objetivos,
                 int duracion, boolean pregAl,
                 boolean resAl) {
        
        
        
        if (nombre == null || nombre.length() < 5 || nombre.length() > 20)
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 40 caracteres.");
        if (instrucciones == null || instrucciones.length() < 5 || instrucciones.length() > 400)
            throw new IllegalArgumentException("Las instrucciones deben tener entre 5 y 400 caracteres.");
        for (String st : objetivos){
            if (st.length() < 10 || st.length() > 40)
            throw new IllegalArgumentException("Cada objetivo debe de tener entre 10 y 40 caracteres");
        }
        
        if (duracion < 1)
            throw new IllegalArgumentException("La duracion debe ser de minimo 1 minuto");


        this.identificacion = identificacion;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.objetivos = objetivos;
        
        this.duracion = duracion;
        this.pregAl = pregAl;
        this.resAl = resAl;
        this.ejercicios = new ArrayList<>();
        
        

    }
    
    
    
    
    
    // Setter para nombre
    public void setNombre(String nombre) {
        if (nombre == null || nombre.length() < 5 || nombre.length() > 20) {
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 20 caracteres.");
        }
        this.nombre = nombre;
    }

    // Setter para instrucciones
    public void setInstrucciones(String instrucciones) {
        if (instrucciones == null || instrucciones.length() < 5 || instrucciones.length() > 400) {
            throw new IllegalArgumentException("Las instrucciones deben tener entre 5 y 400 caracteres.");
        }
        this.instrucciones = instrucciones;
    }

    // Setter para objetivos
    public void setObjetivos(ArrayList<String> objetivos) {
        if (objetivos == null) {
            throw new IllegalArgumentException("La lista de objetivos no puede ser nula.");
        }
        for (String st : objetivos) {
            if (st == null || st.length() < 10 || st.length() > 40) {
                throw new IllegalArgumentException("Cada objetivo debe tener entre 10 y 40 caracteres.");
            }
        }
        this.objetivos = objetivos;
    }

    // Setter para duracion
    public void setDuracion(int duracion) {
        if (duracion < 1) {
            throw new IllegalArgumentException("La duración debe ser de mínimo 1 minuto.");
        }
        this.duracion = duracion;
    }
    
    
    public void setPregAl(boolean pregAl){
        this.pregAl=pregAl;
    }
    
    public void setResAl(boolean resAl){
        this.resAl=resAl;
    }
    
    
    
    public Grupos getGrupo(){
        return grupo;
    }
    public String getNombre(){
        return nombre;
    }
    
    
    public void setHoraDeInicio(LocalDateTime horaInicio){
        this.fechaInicio=horaInicio;
    }
    public void setHoraDeFinal(LocalDateTime horaFinal){
        this.fechaFin=horaFinal;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    
    
    
    public void setHoraDeInicioEv(LocalDateTime horaInicio){
        this.fechaInicioEv=horaInicio;
    }
    public void setHoraDeFinalEv(LocalDateTime horaFinal){
        this.fechaFinEv=horaFinal;
    }
    
    public void agregarGrupo(Grupos g){
        this.grupo=g;

    }
    public void setNombreEst(String nombre){
        this.nombreEst=nombre;
    }
    public String getObjetivos() {
        String objs="";
        for (String obj: objetivos){
            objs+=obj+", ";
        }
        return objs;
    }
    
    public String getNombreEst(){
        return nombreEst;
    }
    
    public double getPuntajeTotal() {
        puntajeTotal=0;
        for (Ejercicios e: ejercicios){
            
            puntajeTotal=puntajeTotal+e.getPuntos();
            
        }
        return puntajeTotal;
    }

    public double getPuntajeTotalObtenido() {
        puntajeTotalObt=0;
        for (Ejercicios e: ejercicios){
            puntajeTotalObt=puntajeTotalObt+e.getPuntajeObtenido();
        }
        return puntajeTotalObt;
    }

    public int getNota() {
        for (Ejercicios ej :ejercicios){
        ej.verificar();
        }
        return (int) Math.round(getPuntajeTotalObtenido() * 100.0 / getPuntajeTotal());
    }
    
    public void mezclarEjercicios() {
        Collections.shuffle(ejercicios);
    }
    
    
    public ArrayList<Ejercicios> getEjercicios(){
        return ejercicios;
    }
    
    
    public void agregarEjercicio(Ejercicios ejercicio){
        ejercicios.add(ejercicio);
    }
    public void eliminarEjercicio (Ejercicios ejercicio){
        ejercicios.remove(ejercicio);
    }
    public int getIdentificacion(){
        return identificacion;
    }
    public String toString(){
        return "Identificacion: "+identificacion+"\n"+"Nombre: "+nombre+"\n"+"Objetivos: "+this.getObjetivos()+"\n"+"Duracion: "+duracion+"\n"+"Aleatoriedad de preguntas: "+pregAl+"\n"+"Aleatoriedad de respuestas: "+resAl+"\n"+"Cantidad de preguntas: "+ejercicios.size()+"\n";
    }  
    
    
    public String imprimir() {
    StringBuilder todo = new StringBuilder();
    for (Ejercicios ej :ejercicios){
        ej.verificar();
    }
    // Información general de la evaluación
    todo.append("Evaluación: ").append(nombre).append("\n");
    todo.append("Estudiante: ").append(nombreEst).append("\n");
    todo.append("Hora de inicio: ").append(fechaInicio).append("\n");
    todo.append("Hora de finalizacion: ").append(fechaFin).append("\n");
    todo.append("Hora de inicio estudiante: ").append(fechaInicioEv).append("\n");
    todo.append("Hora de finalizacion estudiante: ").append(fechaFinEv).append("\n");
    todo.append("Nota: ").append(getNota())
        .append(" (").append(getPuntajeTotalObtenido())
        .append("/").append(getPuntajeTotal()).append(")\n");
    todo.append("Duración: ").append(getDuracion()).append("\n");
    todo.append("==================================================\n");

    // Detalle de cada ejercicio
    int n = 1;
    for (Ejercicios e : ejercicios) {
        e.verificar();
        todo.append("Pregunta ").append(n++).append("\n");
        todo.append(e.detalle()).append("\n");
        todo.append("--------------------------------------------------\n");
    }

    return todo.toString();
}
    
    public int getDur(){
        return duracion;
    }
    
    
    public double getDuracion(){
        return (fin-inicio)/1000;
    }
    
    public void iniciar(){
        i = 0;
        this.setHoraDeInicioEv(LocalDateTime.now());
        frame = new JFrame("Evaluación: "+nombre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        lblTiempoRestante = new JLabel("Tiempo restante: 00:00", SwingConstants.CENTER);
        lblTiempoRestante.setFont(new Font("Arial", Font.BOLD, 24));
        lblTiempoRestante.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Nuevo label para la pregunta
        lblPreguntaActual = new JLabel("Pregunta 1", SwingConstants.CENTER);
        lblPreguntaActual.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPreguntaActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSuperior.add(lblTiempoRestante);
        panelSuperior.add(lblPreguntaActual);

        frame.add(panelSuperior, BorderLayout.NORTH);

        panelCentral = new JPanel(new CardLayout());
        if (pregAl==true){
            mezclarEjercicios();
        }
        for(Ejercicios e: ejercicios){
            if(resAl){
                e.aplicarRandom(new Random());
            }else{
                e.construirPanel();
            } 
                
            panelCentral.add(e,enunciado(e));
            e.setVisible(false);
        }

        frame.add(panelCentral,BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAnterior = new JButton("Anterior");
        JButton btnSiguiente = new JButton("Siguiente");
        JButton btnFinalizar = new JButton("Finalizar");

        btnAnterior.addActionListener(ev -> mostrarAnterior());
        btnSiguiente.addActionListener(ev -> mostrarSiguiente());
        btnFinalizar.addActionListener(ev -> {
            i=2;
            finalizar();
            ;
        });

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnFinalizar);

        frame.add(panelBotones,BorderLayout.SOUTH);

        inicio = System.currentTimeMillis();
        temporizador = new Timer(1000, ev -> actualizarTiempo(i));
        temporizador.start();

        frame.setVisible(true);
        mostrarEjercicio(0);
    }
    
    
    
    public void iniciarProfesor(){
        i =1;
        frame = new JFrame("Evaluación: "+nombre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        lblTiempoRestante = new JLabel("Tiempo restante: 00:00", SwingConstants.CENTER);
        lblTiempoRestante.setFont(new Font("Arial", Font.BOLD, 24));
        lblTiempoRestante.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Nuevo label para la pregunta
        lblPreguntaActual = new JLabel("Pregunta 1", SwingConstants.CENTER);
        lblPreguntaActual.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPreguntaActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSuperior.add(lblTiempoRestante);
        panelSuperior.add(lblPreguntaActual);

        frame.add(panelSuperior, BorderLayout.NORTH);

        
        panelCentral = new JPanel(new CardLayout());
        if (pregAl==true){
            mezclarEjercicios();
        }
        for(Ejercicios e: ejercicios){
            if(resAl){
                e.aplicarRandom(new Random());
            }else{
                e.construirPanel();
            } 
                
            panelCentral.add(e,enunciado(e));
            e.setVisible(false);
        }

        frame.add(panelCentral,BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAnterior = new JButton("Anterior");
        JButton btnSiguiente = new JButton("Siguiente");
        JButton btnFinalizar = new JButton("Finalizar");

        btnAnterior.addActionListener(ev -> mostrarAnterior());
        btnSiguiente.addActionListener(ev -> mostrarSiguiente());
        btnFinalizar.addActionListener(ev -> {
            i=2;
            finalizarProfesor();
        });

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnFinalizar);

        frame.add(panelBotones,BorderLayout.SOUTH);

        inicio = System.currentTimeMillis();
        temporizador = new Timer(1000, ev -> actualizarTiempo(i));
        temporizador.start();

        frame.setVisible(true);
        mostrarEjercicio(0);
    }
    
    
    
    
    private void actualizarTiempo(int i) {
        LocalDateTime ahora = LocalDateTime.now();

        if (ahora.isBefore(fechaFin)) {
            Duration restante = Duration.between(ahora, fechaFin);
            long totalSegundos = restante.getSeconds();

            long minutos = totalSegundos / 60;
            long segundos = totalSegundos % 60;

            lblTiempoRestante.setText(
                String.format("Tiempo restante: %02d:%02d", minutos, segundos)
            );
        } else {
            lblTiempoRestante.setText("Tiempo terminado");
            temporizador.stop();
            if (i==1){
                finalizarProfesor();
            }else{
                if (i==0){
                    finalizar();
                }else{
                    
                }
                
            }
            
            
            
            
        }
    }
    
    
    

    private String enunciado(Ejercicios e){
        return e.enunciado;
    }

    private void mostrarEjercicio(int idx){
        if(idx<0 || idx>=ejercicios.size()) return;
        for(int i=0;i<ejercicios.size();i++) ejercicios.get(i).setVisible(i==idx);
        indiceActual = idx;
            if (lblPreguntaActual != null) {
            lblPreguntaActual.setText("Pregunta " + (idx + 1));
        }
    }

    private void mostrarSiguiente(){ mostrarEjercicio(indiceActual+1); }
    private void mostrarAnterior(){ mostrarEjercicio(indiceActual-1); }

    private void finalizar(){
        this.setHoraDeFinalEv(LocalDateTime.now());
        
        fin = System.currentTimeMillis();
        JOptionPane.showMessageDialog(frame,"Evaluación finalizada en "+((fin-inicio)/1000)+" segEstudiantes");
        

        grupo.guardarEvaluacion(nombreEst,this); 
        //grupo.obtenerEvaluacion(nombreEst,this.getIdentificacion()).imprimirReporte();
        for (Ejercicios e :ejercicios){
            e.construirPanel();
        }
        
        
        frame.dispose();
    }
    
    private void finalizarProfesor(){
        
        fin = System.currentTimeMillis();
        JOptionPane.showMessageDialog(frame,"Evaluación finalizada en "+((fin-inicio)/1000)+" segProfesores");

        frame.dispose();
    }
    
    
    
    public Evaluaciones copiarEvaluacion(){
        Evaluaciones copia = new Evaluaciones(identificacion,nombre,instrucciones,objetivos,duracion,pregAl,resAl);
        copia.inicio = inicio;
        copia.fin = fin;
        copia.fechaInicio = fechaInicio;
        copia.fechaFin = fechaFin;
        copia.grupo=grupo;
        copia.nombreEst=nombreEst;
        copia.fechaInicioEv=fechaInicioEv;
        copia.fechaFinEv=fechaFinEv;
        
                
        this.inicio=0;
        this.fin=0;
        this.puntajeTotalObt=0;
        this.puntajeTotal=0;
        this.nombreEst="";
        this.fechaInicioEv=null;
        this.fechaFinEv=null;
        for(Ejercicios e: ejercicios){
            copia.agregarEjercicio(e.copiar());
        }
        for (Ejercicios e :ejercicios){
            e.borrarSeleccion();
        }
        return copia;
    }
    
    public void imprimirReporte(){
        JFrame ventana = new JFrame("Información general");
            ventana.setSize(400, 300);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLayout(null);

            // 🟩 JTextArea con texto
            JTextArea lblFecha = new JTextArea(10, 50);
            lblFecha.setText(this.imprimir());
            lblFecha.setEditable(false);
            lblFecha.setLineWrap(true);
            lblFecha.setWrapStyleWord(true);
            lblFecha.setBackground(ventana.getBackground());

            // 🟦 ScrollPane que contiene el JTextArea
            JScrollPane scroll = new JScrollPane(lblFecha);
            scroll.setBounds(20, 20, 350, 170); // coordenadas y tamaño
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // ✅ Agregamos el JScrollPane en lugar del JTextArea
            ventana.add(scroll);

            // 🟨 Botón para cerrar la ventana
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setBounds(150, 210, 100, 30);
            btnCerrar.addActionListener(w -> ventana.dispose());
            ventana.add(btnCerrar);

            ventana.setVisible(true);
    }
    
    
    
    
}
