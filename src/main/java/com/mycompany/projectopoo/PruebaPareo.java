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
import java.util.*;
public class PruebaPareo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Ejercicio de Pareo");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(500, 300);
            ventana.setLayout(new BorderLayout());

            // 🔹 Crear las parejas
            Map<String, String> paisesCapitales = new LinkedHashMap<>();
            paisesCapitales.put("España", "Madrid");
            paisesCapitales.put("Italia", "Roma");
            paisesCapitales.put("Francia", "París");
            paisesCapitales.put("Alemania", "Berlín");

            // 🔹 Crear el ejercicio
            PareoPanel ejercicio = new PareoPanel(
                    "Relaciona cada país con su capital:",
                    10,
                    paisesCapitales
            );

            // 🔹 Botón para verificar respuestas
            JButton btnVerificar = new JButton("Verificar respuestas");
            btnVerificar.addActionListener(e -> {
                boolean correcto = ejercicio.verificarRespuesta();
                JOptionPane.showMessageDialog(
                        ventana,
                        correcto ? "✅ ¡Todas las respuestas son correctas!" : "❌ Hay errores, intenta de nuevo."
                );
            });

            // 🔹 Agregar a la ventana
            ventana.add(ejercicio, BorderLayout.CENTER);
            ventana.add(btnVerificar, BorderLayout.SOUTH);
            ventana.setVisible(true);
            
        });
    }
}
