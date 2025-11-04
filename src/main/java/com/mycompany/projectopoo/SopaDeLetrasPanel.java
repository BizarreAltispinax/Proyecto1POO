/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;

public class SopaDeLetrasPanel extends Ejercicios implements Serializable{
    private char[][] tablero;
    private int filas, columnas;
    private Map<String, String> palabras; // palabra → definición
    private Set<String> encontradas = new HashSet<>();
    private JPanel panelTablero;
    private JTextArea areaDefiniciones;
    private JLabel lblTitulo;
    private boolean aleatoria;
    private Random random = new Random(1);
    private String llaveMasLarga = null;
    private boolean v = false;

    private static final int[][] DIRECCIONES = {
        {-1, 0},  // arriba
        {1, 0},   // abajo
        {0, -1},  // izquierda
        {0, 1},   // derecha
        {-1, -1}, // diagonal arriba-izquierda
        {-1, 1},  // diagonal arriba-derecha
        {1, -1},  // diagonal abajo-izquierda
        {1, 1}    // diagonal abajo-derecha
    };

    public SopaDeLetrasPanel(String enunciado, Map<String, String> palabras, int puntaje) {
        super(enunciado, puntaje);
        this.palabras = new LinkedHashMap<>(palabras);
        this.aleatoria = false;
        for (String llave : palabras.keySet()) {
            if (llaveMasLarga == null || llave.length() > llaveMasLarga.length()) {
                llaveMasLarga = llave;
            }
        }
        this.filas = llaveMasLarga.length();
        this.columnas = llaveMasLarga.length();
        
    }
    public void setPalabras(Map<String, String> palabras){
        this.palabras=palabras;
    }

    // ---------------------------------------------------
    // 🔹 Generación del tablero
    // ---------------------------------------------------
    private void generarTablero() {
        tablero = new char[filas][columnas];
        for (int i = 0; i < filas; i++)
            Arrays.fill(tablero[i], ' ');

        ArrayList<String> listaPalabras = new ArrayList<>(palabras.keySet());
        

        colocarTodasLasPalabras(listaPalabras);
        llenarVacios();
    }

    
    private void colocarTodasLasPalabras(ArrayList<String> palabras) {
        

        // 1️⃣ Colocar las primeras 8, una en cada dirección
        int limite = Math.min(DIRECCIONES.length, palabras.size());
        for (int i = 0; i < limite; i++) {
            String palabra = palabras.get(i);
            int[] dir = DIRECCIONES[i];
            colocarPalabraEnDireccion(palabra, dir);
        }

        // 2️⃣ Colocar el resto aleatoriamente
        for (int i = DIRECCIONES.length; i < palabras.size(); i++) {
            colocarPalabra(palabras.get(i));
        }
    }
    private void colocarPalabraEnDireccion(String palabra, int[] dir) {
        palabra = palabra.toUpperCase();
        int intentos = 100;
        while (intentos-- > 0) {
            int fila = random.nextInt(filas);
            int col = random.nextInt(columnas);
            if (puedeColocar(palabra, fila, col, dir[0], dir[1])) {
                for (int i = 0; i < palabra.length(); i++) {
                    tablero[fila + i * dir[0]][col + i * dir[1]] = palabra.charAt(i);
                }
                return;
            }
        }
    }
    
    
    
    
    
    
    
    private void colocarPalabra(String palabra) {
        palabra = palabra.toUpperCase();
        int intentos = 100;
        while (intentos-- > 0) {
            int fila = random.nextInt(filas);
            int col = random.nextInt(columnas);
            int[] dir = DIRECCIONES[random.nextInt(DIRECCIONES.length)];
            if (puedeColocar(palabra, fila, col, dir[0], dir[1])) {
                for (int i = 0; i < palabra.length(); i++)
                    tablero[fila + i * dir[0]][col + i * dir[1]] = palabra.charAt(i);
                return;
            }
        }
    }

    private boolean puedeColocar(String palabra, int f, int c, int df, int dc) {
        for (int i = 0; i < palabra.length(); i++) {
            int nf = f + i * df, nc = c + i * dc;
            if (nf < 0 || nf >= filas || nc < 0 || nc >= columnas) return false;
            char ch = tablero[nf][nc];
            if (ch != ' ' && ch != palabra.charAt(i)) return false;
        }
        return true;
    }

    private void llenarVacios() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                if (tablero[i][j] == ' ')
                    tablero[i][j] = (char) ('A' + random.nextInt(26));
    }

    // ---------------------------------------------------
    // 🧩 Construcción del panel
    // ---------------------------------------------------
    @Override
    public void construirPanel() {
        encontradas.clear();
        removeAll();
        if (v==false){
            random = new Random(1);
        }
        generarTablero();

        lblTitulo = new JLabel("<html><b>" + enunciado + "</b></html>", SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        panelTablero = new JPanel(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton btn = new JButton(String.valueOf(tablero[i][j]));
                btn.setFont(new Font("Monospaced", Font.BOLD, 16));
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setFocusPainted(false);
                int fi = i, co = j;
                btn.addActionListener(e -> {seleccionarLetra(fi, co);
                    });
                panelTablero.add(btn);
            }
        }

        areaDefiniciones = new JTextArea(10, 20);
        areaDefiniciones.setEditable(false);
        actualizarDefiniciones();

        JScrollPane scrollDef = new JScrollPane(areaDefiniciones);
        JPanel panelDerecha = new JPanel(new BorderLayout());
        panelDerecha.add(new JLabel("Definiciones:"), BorderLayout.NORTH);
        panelDerecha.add(scrollDef, BorderLayout.CENTER);

        add(panelTablero, BorderLayout.CENTER);
        add(panelDerecha, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    // ---------------------------------------------------
    // 🧩 Interacción con el tablero
    // ---------------------------------------------------
    private java.util.List<Point> seleccionActual = new ArrayList<>();

    private void seleccionarLetra(int fila, int col) {
        
        seleccionActual.add(new Point(fila, col));
        if (seleccionActual.size() >= 2) {
            verificarSeleccion();
            seleccionActual.clear();
        }
    }

    private void verificarSeleccion() {
        
        if (seleccionActual.size() < 2) return;

        Point inicio = seleccionActual.get(0);
        Point fin = seleccionActual.get(seleccionActual.size() - 1);
        String palabra = obtenerPalabra(inicio, fin);
        
        
        
        
        if (palabra != null && palabras.containsKey(palabra.toUpperCase())) {
            
            encontradas.add(palabra.toUpperCase());
            actualizarDefiniciones();
            resaltarPalabra(inicio, fin);
            verificar();
        }
    }

    private String obtenerPalabra(Point inicio, Point fin) {
        
        int df = Integer.compare(fin.x, inicio.x);
        int dc = Integer.compare(fin.y, inicio.y);
        StringBuilder sb = new StringBuilder();

        int f = inicio.x, c = inicio.y;
        while (true) {
            
            if (f < 0 || f >= filas || c < 0 || c >= columnas) break;
            sb.append(tablero[f][c]);
            if (f == fin.x && c == fin.y) break;
            f += df;
            c += dc;
        }
        
        return sb.toString();
    }

    private void resaltarPalabra(Point inicio, Point fin) {
        int df = Integer.compare(fin.x, inicio.x);
        int dc = Integer.compare(fin.y, inicio.y);
        int f = inicio.x, c = inicio.y;
        Component[] comps = panelTablero.getComponents();

        while (true) {
            int idx = f * columnas + c;
            if (idx >= 0 && idx < comps.length) {
                comps[idx].setBackground(Color.GREEN);
            }
            if (f == fin.x && c == fin.y) break;
            f += df;
            c += dc;
        }
    }

    // ---------------------------------------------------
    // 🧩 Actualizar definiciones y puntaje
    // ---------------------------------------------------
    private void actualizarDefiniciones() {
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras.keySet()) {
            if (!encontradas.contains(palabra.toUpperCase())) {
                sb.append("- ").append(palabras.get(palabra)).append("\n");
            }
        }
        areaDefiniciones.setText(sb.toString());
    }

    @Override
    public void verificar() {
        int total = palabras.size();
        int correctas = encontradas.size();
        double proporcion = (double) correctas / total;
        puntajeObtenido = (int) (proporcion * puntaje);
    }

    @Override
    public void aplicarRandom(Random r) {
        this.random = r;
        this.aleatoria = true;
        v=true;
        construirPanel();
    }

    @Override
    public String detalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pregunta: ").append(enunciado).append("\n");
        sb.append("Palabras encontradas: ").append(encontradas.size()).append("/").append(palabras.size()).append("\n");
        for (String palabra : palabras.keySet()) {
            sb.append(palabra.toUpperCase())
              .append(" → ").append(palabras.get(palabra));
            if (encontradas.contains(palabra.toUpperCase()))
                sb.append(" ✓");
            else
                sb.append(" ✗");
            sb.append("\n");
        }
        sb.append("Puntaje obtenido: ").append(puntajeObtenido).append("/").append(puntaje).append("\n");
        return sb.toString();
    }
    public void setAleatorio(boolean aleatorio){
        this.aleatoria=aleatorio;
    }
    public Ejercicios copiar(){
        SopaDeLetrasPanel copia = new SopaDeLetrasPanel(enunciado, new LinkedHashMap<>(palabras),puntaje);
        copia.encontradas.addAll(encontradas);
        copia.setAleatorio(aleatoria);
        return copia;
    }
}

