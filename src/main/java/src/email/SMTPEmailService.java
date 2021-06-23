package src.email;


import org.apache.commons.mail.HtmlEmail;

import javax.mail.*;
import java.io.File;
import java.util.Properties;

import static src.email.EmailUtils.PASSWORD;
import static src.email.EmailUtils.SENDER_MAIL;
import static src.email.EmailUtils.SENDER_NAME;


public class SMTPEmailService{//} extends EmailService{

    private String host;
    private String sender;
    private String [] receipients;

    public String getHost() {
        return host;
    }

    public SMTPEmailService withHost(String host) {
        this.host = host;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public SMTPEmailService withSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String[] getReceipients() {
        return receipients;
    }

    public SMTPEmailService withReceipients(String[] receipients) {
        this.receipients = receipients;
        return this;
    }


    public boolean sendHtmlEMail( String[] recipientMails ,String mailSubject, String mailMessage, File attachment,String htmlMessage) {
        try{

            Session session = getSession();
            
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setMailSession(session);
//            email.setHostName(emailServerHost);
            for(String recipientMail:recipientMails){
                email.addTo(recipientMail);
            }
           email.setFrom(SENDER_MAIL,SENDER_NAME);
           email.setSubject(mailSubject);

            // set the html message
            email.setHtmlMsg(htmlMessage);

            // set the alternative message
            email.setTextMsg(mailMessage);
            if(attachment!=null) {
                email.attach(attachment);
            }
                // send the email
            email.send();


        }catch (Exception e){
            e.printStackTrace();
            //logger.error("error sending email: "+e.getMessage(),e);
            return false;
        }
        return true;
    }


    public static SMTPEmailService createEmailService(){
        return new SMTPEmailService();
    }

    private Session getSession() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_MAIL, PASSWORD);
                    }
                });

    }


}
