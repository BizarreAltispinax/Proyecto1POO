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
 * Representa una evaluación compuesta por múltiples ejercicios.
 * Contiene la configuración de la evaluación (nombre, instrucciones, objetivos, duración),
 * la lista de ejercicios, enlaces a GUI (JFrame, JLabels) y mecanismos para iniciar,
 * controlar y finalizar la evaluación tanto para estudiantes como para profesores.
 *
 * <p>Implementa {@link Serializable} para permitir su persistencia si es necesario.</p>
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
    
    
    /**
     * Constructor principal de Evaluaciones.
     *
     * @param identificacion identificador de la evaluación.
     * @param nombre nombre de la evaluación (5 a 20 caracteres).
     * @param instrucciones instrucciones de la evaluación (5 a 400 caracteres).
     * @param objetivos lista de objetivos (cada objetivo entre 10 y 40 caracteres).
     * @param duracion duración en minutos (mínimo 1).
     * @param pregAl indica si las preguntas se aleatorizan.
     * @param resAl indica si las respuestas se aleatorizan.
     * @throws IllegalArgumentException si alguno de los parámetros no cumple las validaciones.
     */
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
    
    
    
    /**
     * Establece el nombre de la evaluación.
     * @param nombre nuevo nombre (5 a 20 caracteres).
     * @throws IllegalArgumentException si el nombre no cumple las restricciones.
     */
    // Setter para nombre
    public void setNombre(String nombre) {
        if (nombre == null || nombre.length() < 5 || nombre.length() > 20) {
            throw new IllegalArgumentException("El nombre debe tener entre 5 y 20 caracteres.");
        }
        this.nombre = nombre;
    }

    /**
     * Establece las instrucciones de la evaluación.
     * @param instrucciones texto de instrucciones (5 a 400 caracteres).
     * @throws IllegalArgumentException si las instrucciones no cumplen las restricciones.
     */
    // Setter para instrucciones
    public void setInstrucciones(String instrucciones) {
        if (instrucciones == null || instrucciones.length() < 5 || instrucciones.length() > 400) {
            throw new IllegalArgumentException("Las instrucciones deben tener entre 5 y 400 caracteres.");
        }
        this.instrucciones = instrucciones;
    }

    /**
     * Establece la lista de objetivos.
     * @param objetivos lista de objetivos; no puede ser {@code null} y cada objetivo debe tener 10-40 caracteres.
     * @throws IllegalArgumentException si la lista o algún objetivo es inválido.
     */
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

    /**
     * Establece la duración de la evaluación en minutos.
     * @param duracion duración en minutos (mínimo 1).
     * @throws IllegalArgumentException si la duración es menor que 1.
     */
    // Setter para duracion
    public void setDuracion(int duracion) {
        if (duracion < 1) {
            throw new IllegalArgumentException("La duración debe ser de mínimo 1 minuto.");
        }
        this.duracion = duracion;
    }
    
    
    /**
     * Establece si las preguntas se deben aleatorizar.
     * @param pregAl {@code true} para aleatorizar preguntas.
     */
    public void setPregAl(boolean pregAl){
        this.pregAl=pregAl;
    }
    
    /**
     * Establece si las respuestas se deben aleatorizar.
     * @param resAl {@code true} para aleatorizar respuestas.
     */
    public void setResAl(boolean resAl){
        this.resAl=resAl;
    }
    
    
    
    /** @return el grupo asociado a la evaluación. */
    public Grupos getGrupo(){
        return grupo;
    }
    /** @return el nombre de la evaluación. */
    public String getNombre(){
        return nombre;
    }
    
    
    /**
     * Fija la hora de inicio (global) de la evaluación.
     * @param horaInicio fecha y hora de inicio.
     */
    public void setHoraDeInicio(LocalDateTime horaInicio){
        this.fechaInicio=horaInicio;
    }
    /**
     * Fija la hora de finalización (global) de la evaluación.
     * @param horaFinal fecha y hora de finalización.
     */
    public void setHoraDeFinal(LocalDateTime horaFinal){
        this.fechaFin=horaFinal;
    }
    
    /** @return la fecha y hora de inicio (global). */
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    /** @return la fecha y hora de fin (global). */
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    
    
    
    /**
     * Establece la hora de inicio específica del estudiante.
     * @param horaInicio fecha y hora de inicio del estudiante.
     */
    public void setHoraDeInicioEv(LocalDateTime horaInicio){
        this.fechaInicioEv=horaInicio;
    }
    /**
     * Establece la hora de finalización específica del estudiante.
     * @param horaFinal fecha y hora de finalización del estudiante.
     */
    public void setHoraDeFinalEv(LocalDateTime horaFinal){
        this.fechaFinEv=horaFinal;
    }
    
    /**
     * Asocia un grupo a esta evaluación.
     * @param g grupo a asociar.
     */
    public void agregarGrupo(Grupos g){
        this.grupo=g;

    }
    /**
     * Establece el nombre del estudiante que realiza la evaluación.
     * @param nombre nombre del estudiante.
     */
    public void setNombreEst(String nombre){
        this.nombreEst=nombre;
    }
    /**
     * Devuelve los objetivos concatenados en una cadena separada por comas.
     * @return cadena con los objetivos.
     */
    public String getObjetivos() {
        String objs="";
        for (String obj: objetivos){
            objs+=obj+", ";
        }
        return objs;
    }
    
    /** @return nombre del estudiante asociado (si lo hay). */
    public String getNombreEst(){
        return nombreEst;
    }
    
    /**
     * Calcula la suma total de puntos de todos los ejercicios.
     * @return puntaje total de la evaluación.
     */
    public double getPuntajeTotal() {
        puntajeTotal=0;
        for (Ejercicios e: ejercicios){
            
            puntajeTotal=puntajeTotal+e.getPuntos();
            
        }
        return puntajeTotal;
    }

    /**
     * Calcula la suma de los puntajes obtenidos por el estudiante en todos los ejercicios.
     * @return puntaje total obtenido.
     */
    public double getPuntajeTotalObtenido() {
        puntajeTotalObt=0;
        for (Ejercicios e: ejercicios){
            puntajeTotalObt=puntajeTotalObt+e.getPuntajeObtenido();
        }
        return puntajeTotalObt;
    }

    /**
     * Calcula la nota final (porcentaje redondeado) tras verificar cada ejercicio.
     * @return nota final como entero (0-100).
     */
    public int getNota() {
        for (Ejercicios ej :ejercicios){
        ej.verificar(); // Verifica cada ejercicio antes de calcular la nota
        }
        return (int) Math.round(getPuntajeTotalObtenido() * 100.0 / getPuntajeTotal());
    }
    
    /** Mezcla aleatoriamente el orden de los ejercicios. */
    public void mezclarEjercicios() {
        Collections.shuffle(ejercicios);
    }
    
    
    /** @return lista de ejercicios de la evaluación. */
    public ArrayList<Ejercicios> getEjercicios(){
        return ejercicios;
    }
    
    
    /**
     * Agrega un ejercicio a la evaluación.
     * @param ejercicio ejercicio a agregar.
     */
    public void agregarEjercicio(Ejercicios ejercicio){
        ejercicios.add(ejercicio);
    }
    /**
     * Elimina un ejercicio de la evaluación.
     * @param ejercicio ejercicio a eliminar.
     */
    public void eliminarEjercicio (Ejercicios ejercicio){
        ejercicios.remove(ejercicio);
    }
    /** @return la identificación de la evaluación. */
    public int getIdentificacion(){
        return identificacion;
    }
    /**
     * Representación textual resumida de la evaluación.
     * @return cadena descriptiva.
     */
    public String toString(){
        return "Identificacion: "+identificacion+"\n"+"Nombre: "+nombre+"\n"+"Objetivos: "+this.getObjetivos()+"\n"+"Duracion: "+duracion+"\n"+"Aleatoriedad de preguntas: "+pregAl+"\n"+"Aleatoriedad de respuestas: "+resAl+"\n"+"Cantidad de preguntas: "+ejercicios.size()+"\n";
    }  
    
    
    /**
     * Construye un texto completo con los detalles de la evaluación y cada ejercicio.
     * @return texto completo de la evaluación listo para mostrar o guardar.
     */
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
    
    /** @return valor del campo duracion. */
    public int getDur(){
        return duracion;
    }
    
    
    /** @return duración en segundos calculada a partir de timestamps. */
    public double getDuracion(){
        return (fin-inicio)/1000;
    }
    
    /**
     * Inicia la evaluación para el estudiante: configura ventana, temporizador y paneles.
     * Muestra la primera pregunta al final del proceso.
     */
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
            mezclarEjercicios(); // Si corresponde, aleatoriza preguntas
        }
        for(Ejercicios e: ejercicios){
            if(resAl){
                e.aplicarRandom(new Random()); // Aplica aleatoriedad en respuestas si está activada
            }else{
                e.construirPanel(); // Construye el panel del ejercicio tal cual
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
    
    
    
    /**
     * Inicia la evaluación en modo profesor (similar a iniciar pero con diferencias en control).
     */
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
    
    
    
    
    /**
     * Actualiza la etiqueta de tiempo restante cada segundo.
     * Si el tiempo ha terminado, detiene el temporizador y finaliza según el modo.
     * @param i indicador de modo (0 estudiante, 1 profesor, etc.).
     */
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
    
    
    
    /** Devuelve el enunciado de un ejercicio (acceso directo al campo protegido). */
    private String enunciado(Ejercicios e){
        return e.enunciado;
    }

    /**
     * Muestra el ejercicio en la posición indicada; oculta los demás.
     * @param idx índice del ejercicio a mostrar.
     */
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

    /**
     * Finaliza la evaluación en modo estudiante: registra tiempos, guarda evaluación en el grupo
     * y reconstruye paneles si es necesario.
     */
    private void finalizar(){
        i=2;
        this.setHoraDeFinalEv(LocalDateTime.now());
        
        fin = System.currentTimeMillis();
        JOptionPane.showMessageDialog(frame,"Evaluación finalizada en "+((fin-inicio)/1000)+" seg");
        

        grupo.guardarEvaluacion(nombreEst,this); 
        //grupo.obtenerEvaluacion(nombreEst,this.getIdentificacion()).imprimirReporte();
        for (Ejercicios e :ejercicios){
            e.construirPanel();
        }
        
        
        frame.dispose();
    }
    
    /**
     * Finaliza la evaluación en modo profesor: borra selecciones y cierra la ventana.
     */
    private void finalizarProfesor(){
        i=2;
        fin = System.currentTimeMillis();
        JOptionPane.showMessageDialog(frame,"Evaluación finalizada en "+((fin-inicio)/1000)+" seg");
        for (Ejercicios e :ejercicios){
            e.borrarSeleccion();
            if (e instanceof VerdaderoFalsoPanel) {
                System.out.println("Es un ejercicio de VF");
                ((VerdaderoFalsoPanel) e).setOpciones(new ArrayList<>(Arrays.asList("Verdadero", "Falso")));
                ((VerdaderoFalsoPanel) e).setEsVerdad();
            }
        }
        
        frame.dispose();
    }
    
    
    
    /**
     * Crea y devuelve una copia profunda (en la medida de los ejercicios) de la evaluación.
     * La copia contiene copias de cada ejercicio mediante {@code e.copiar()}.
     * @return nueva instancia de Evaluaciones con los ejercicios copiados.
     */
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
            copia.agregarEjercicio(e.copiar()); // Agrega una copia de cada ejercicio
        }
        for (Ejercicios e :ejercicios){
            e.borrarSeleccion(); // Limpia selecciones en la evaluación original
        }
        return copia;
    }
    
    /**
     * Muestra un reporte en una ventana con la información de la evaluación.
     * Construye un JTextArea dentro de un JScrollPane y un botón Cerrar.
     */
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
