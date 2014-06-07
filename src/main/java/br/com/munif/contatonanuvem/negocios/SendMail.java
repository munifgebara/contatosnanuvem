/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.contatonanuvem.negocios;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public final static String username = "enviador@munif.com.br";
    public final static String password = "qwe123qwe";

    private static String assuntoMail;
    private static String emailPara;
    private static String conteudoMail;
    private static String tituloConteudo;

    public static void setAssuntoMail(String assuntoMail) {
        SendMail.assuntoMail = assuntoMail;
    }

    public static void setEmailPara(String emailPara) {
        SendMail.emailPara = emailPara;
    }

    public static void setConteudoMail(String conteudoMail) {
        SendMail.conteudoMail = conteudoMail;
    }

    public static void setTituloConteudo(String tituloConteudo) {
        SendMail.tituloConteudo = tituloConteudo;
    }

    public static void enviar() {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("proxySet", "true");
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            // Cria nova mensagem
            Message msg = new MimeMessage(session);

            //Email Origem
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    //Email para destinátario
                    InternetAddress.parse(emailPara, false));
            msg.setSubject(assuntoMail);
            msg.setText(conteudoMail);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        setAssuntoMail("Teste "+System.currentTimeMillis());
        setConteudoMail("Bom dia, tudo bem?");
        setEmailPara("munifgebara@gmail.com");
        setTituloConteudo("Teste de envio");
        enviar();
    }
}
