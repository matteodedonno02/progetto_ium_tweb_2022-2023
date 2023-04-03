package org.unito.iumtweb.util;

import com.mailgun.api.v3.*;
import com.mailgun.client.*;
import com.mailgun.model.message.Message;
import org.unito.iumtweb.model.Repetition;

import javax.mail.*;

public class EmailSender {
    private String api_key;
    private final String BASE_URL = "https://api.mailgun.net/v3/sandboxb2b2987128494c56a1b317a59ba2d671.mailgun.org/messages";
    private final String DOMAIN_NAME = "sandboxb2b2987128494c56a1b317a59ba2d671";
    public EmailSender(String api_key) {
        this.api_key = api_key;
    }

    public void bookedRepetition(Repetition r) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(api_key)
                .createApi(MailgunMessagesApi.class);


        Message message = Message.builder()
                .from("Excited User mailgun@sandboxb2b2987128494c56a1b317a59ba2d671.org")
                .to(r.getUser().getEmail())
                .subject("Hello")
                .text("Testing out some Mailgun awesomeness!")
                .build();

        System.out.println(mailgunMessagesApi.sendMessage(DOMAIN_NAME, message).getMessage());
    }
}
