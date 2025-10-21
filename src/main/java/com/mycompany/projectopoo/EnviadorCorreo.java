/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectopoo;

/**
 *
 * @author Usuario
 */
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * Clase encargada de enviar correos mediante Outlook usando Jakarta Mail.
 */
public class EnviadorCorreo {

    /**
     * Envía un correo electrónico con Outlook (SMTP).
     *
     * @param remitente   Correo del remitente (tu correo Outlook)
     * @param contrasena  Contraseña o clave de aplicación
     * @param destinatario Correo del destinatario
     * @param asunto      Asunto del correo
     * @param cuerpo      Contenido del correo
     * @throws MessagingException Si ocurre un error en el envío
     */
    public static void enviarCorreoOutlook(String remitente, String contrasena,
                                           String destinatario, String asunto,
                                           String cuerpo) throws MessagingException {

        // Propiedades específicas del servidor SMTP de Outlook
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Crear sesión autenticada
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasena);
            }
        });

        // Crear mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(cuerpo);

        // Enviar
        Transport.send(message);
        System.out.println("Correo enviado correctamente a " + destinatario);
    }
}
