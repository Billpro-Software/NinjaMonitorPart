package src.email;

public class EmailUtils {

    public final static String SENDER_NAME = "NinjaTeam";
    public final static String REPORT_MASSAGE = "" +
            "==========================================================\\r\\n\" +\n" +
            "Hi, \r\n" +
            "Please see massage\r\n " +
            "#MsgText\r\n " +
            "Best regards,\r\n" +
            "Ninja team" +
            "";

    public final static String REPORT_MSG_HTML = "" +
            "<html>" +
            "<body>" +
            "<style>" +
            "table {background-color:  #f5fcf9; font-size: 16px; border-collapse: collapse;}" +
            "th  {color: #393939; border: 1px solid #dde4e0; min-width:200px; padding:4px; text-align: left; font-weight: 500}" +
            "td  {color: #5f2626;border: 1px solid #dde4e0; width:80%; padding:4px;}" +
            "</style>" +
            "<p>Hi," +
            "<br/>Status details:" +
            "<table>"+
            "<p>#MsgText </p>" +
            "</table>"+
            "<p>Best Regards," +
            "<br/>Ninja team " +
            "</body>" +
            "</html>";

    public final static String EMAIL_SUBJECT = "Test";

    public final static String SENDER_MAIL= "infraweb@billpro-software.com";
    public final static String PASSWORD = "billpro2000";

}
