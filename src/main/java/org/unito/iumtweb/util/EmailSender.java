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
    private String api_key;

    public EmailSender(String api_key) {
        this.api_key = api_key;
    }


    public void bookedRepetition(Repetition r) {
        HttpResponse<JsonNode> request = null;
        try {
            request = Unirest.post("https://api.mailgun.net/v3/sandboxb2b2987128494c56a1b317a59ba2d671/messages")
                    .basicAuth("api", api_key)
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
