package org.unito.iumtweb.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.unito.iumtweb.model.Repetition;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
//    public static void bookedRepetition(Repetition r) {
//        final String username = "postmaster@sandboxb2b2987128494c56a1b317a59ba2d671.mailgun.org";
//        final String password = "a2982e2713cd0c226f327995e08be112-75cd784d-2b31081f";
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host", "smtp.mailgun.org");
//        prop.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("sandboxb2b2987128494c56a1b317a59ba2d671.mailgun.org"));
//            message.setRecipients(
//                    Message.RecipientType.TO,
//                    InternetAddress.parse(r.getUser().getEmail())
//            );
//            message.setSubject("Ripetizione prenotata con successo");
//            message.setText("La ripetizione di " + r.getTeaching().getCourse().getTitle() + " con il/la professore/ssa " + r.getTeaching().getProfessor().getName() + " " + r.getTeaching().getProfessor().getSurname() + " del giorno " + r.getDate().toString() + " alle ore " + r.getTime().toString() + " è stat prenotata con successo!");
//
//            Transport.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    public static void bookedRepetition(Repetition r) {
        HttpResponse<JsonNode> request = null;
        try {
            request = Unirest.post("https://api.mailgun.net/v3/sandboxb2b2987128494c56a1b317a59ba2d671/messages")
                    .basicAuth("api", "da22bb5bcf415bef134b85ba45367605-75cd784d-2b09bb6f")
                    .queryString("from", "mailgun@sandboxb2b2987128494c56a1b317a59ba2d671.org")
                    .queryString("to", r.getUser().getEmail())
                    .queryString("subject", "hello")
                    .queryString("text", "testing")
                    .asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(request.getBody());
    }
}
