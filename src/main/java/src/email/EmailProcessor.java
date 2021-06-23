package src.email;


import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

import static src.email.EmailUtils.REPORT_MASSAGE;
import static src.email.EmailUtils.REPORT_MSG_HTML;

@Log
public  class EmailProcessor {


    protected SMTPEmailService emailService = null;

    private File attachment;
    protected String emailSubject;


    public EmailProcessor() {

        emailService = SMTPEmailService.createEmailService();
        log.log(Level.INFO, "XXXXXX EmailProcessor opened");


    }

    public void withEmailSubject(String subject) {
        emailSubject = subject;
    }

    public void setAttachment(File file) {
        this.attachment = file;
    }

    public void setRecipients(String recipients) {

            String recipientsArray[] = recipients.split("\\;");
            emailService.withReceipients(recipientsArray);
    }



    public boolean sendEmail(String recipients, String emailSubject,Object msgObject) {


        if(recipients == null || recipients.isEmpty()){
            return false;
        }

        withEmailSubject(emailSubject);
        setRecipients( recipients);

        String emailContent = populateEmailContent( REPORT_MASSAGE,msgObject.toString());
        String htmlEmailContent = populateEmailContent( REPORT_MSG_HTML,msgObject.toString());

        return emailService.sendHtmlEMail( emailService.getReceipients(), emailSubject, emailContent, attachment, htmlEmailContent);


    }



    public String populateEmailContent(  String emailTemplate, String emailText){

        String emailContent = emailTemplate;

        emailContent = emailContent.replace("#MsgText", emailText);

        return emailContent;
    }

}
