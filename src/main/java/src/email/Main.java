package src.email;


public class Main {



    public static void main(String[] args) {

        String recipients = "michael.son@teliacompany.com";

        EmailProcessor emailProcessor = new EmailProcessor();

        boolean emailSent = true; //emailProcessor.sendEmail(recipients,"TEST subj","Test MSG");

        System.out.println("Email sent " + emailSent); ;

    }
}
