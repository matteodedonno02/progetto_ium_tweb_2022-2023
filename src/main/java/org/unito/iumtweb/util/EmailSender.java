package org.unito.iumtweb.util;


import org.unito.iumtweb.model.Repetition;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static Session getSession() {
        final String username = "unitoripetizione@gmail.com";
        final String password = "jcsdorelzwfdzbgx";

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

        return session;
    }

    private static void sendMessage(Repetition r, int type) {
        Session session = getSession();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("unitoripetizione@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(r.getUser().getEmail())
            );
            message.setSubject("Ripetizione " + (type > 0 ? (type > 1 ? "eliminata" : "confermata") : "prenotata") + " con successo");

            String title = r.getTeaching().getCourse().getTitle();
            String professor = r.getTeaching().getProfessor().getName() + " " + r.getTeaching().getProfessor().getSurname();
            String date = DateAndTimeManipulator.formatLocalDate(r.getDate().toLocalDate());
            String time = DateAndTimeManipulator.formatTime(r.getTime().toString());
            message.setText("La ripetizione di " + title + " con il/la professore/ssa " + professor + " del giorno " + date + " alle ore " + time + " è stata " + (type > 0 ? (type > 1 ? "eliminata" : "confermata") : "prenotata") + " con successo!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void recoveryEmails(String OTP, String to) {
        Session session = getSession();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("unitoripetizione@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("Email di recupero password");

            message.setText("Ecco a te il codice segreto per impostare una nuova password! " + OTP);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void confirmedRepetition(Repetition r) {
        sendMessage(r, 1);
    }

    public static void deletedRepetition(Repetition r) {
        sendMessage(r, 2);
    }

    public static void bookedRepetition(Repetition r) {
        sendMessage(r, 0);
    }
}
