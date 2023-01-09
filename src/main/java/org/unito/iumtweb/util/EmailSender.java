package org.unito.iumtweb.util;

import org.unito.iumtweb.model.Repetition;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void bookedRepetition(Repetition r) {
        final String username = "ripetizioniunito@gmail.com";
        final String password = "bwamukeiutrcxucm";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ripetizioniunito@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(r.getUser().getEmail())
            );
            message.setSubject("Ripetizione prenotata con successo");
            message.setText("La ripetizione di " + r.getTeaching().getCourse().getTitle() + " con il/la professore/ssa " + r.getTeaching().getProfessor().getName() + " " + r.getTeaching().getProfessor().getSurname() + " del giorno " + r.getDate().toString() + " alle ore " + r.getTime().toString() + " è stat prenotata con successo!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
