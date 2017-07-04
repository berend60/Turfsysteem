package com.tolsma.pieter.turf.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by pietertolsma on 6/4/17.
 */
public class MailService {

    private String mail, server, password;
    private Session session;

    public MailService(String mail, String server, String user, String password) {
        this.mail = mail;
        this.server = server;
        this.password = password;

        Properties properties = System.getProperties();


        properties.setProperty("mail.smtp.host", server);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        session = Session.getInstance(properties, new GMailAuthenticator(mail, password));

    }


    public boolean send(String[] recipients, String subject, String content, List<File> files) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", server);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        try {
            Transport t = Session.getDefaultInstance(properties, null).getTransport("smtps");
            System.out.println("Connecting...");

            t.connect(server, 465, mail, password);
            System.out.println("Connected!");



            MimeBodyPart body = new MimeBodyPart();
            body.setContent(content, "text/html");

            Multipart bodyMulty = new MimeMultipart();
            bodyMulty.addBodyPart(body);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mail));
            for (int i = 0; i < recipients.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients[i]));
            }

            for (File file : files ) {
                if (file != null) {
                    MimeBodyPart attach = new MimeBodyPart();
                    try {
                        attach.attachFile(file);
                    } catch ( IOException e) {
                        e.printStackTrace();
                    }
                    bodyMulty.addBodyPart(attach);
                }
            }

            msg.setSubject(subject);
            msg.setContent(bodyMulty);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            System.out.println("Message sent!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static class GMailAuthenticator extends Authenticator {
        String user;
        String pw;
        public GMailAuthenticator (String username, String password)
        {
            super();
            this.user = username;
            this.pw = password;
        }
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(user, pw);
        }
    }
}
