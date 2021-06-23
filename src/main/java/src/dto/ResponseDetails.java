package src.dto;

import lombok.Data;

@Data
public class ResponseDetails {
    String dbName;
    String projName;
    String requestName;
    String message;
    String responseXML;

    @Override
    public String toString() {
        return
                "dbName='" + dbName + '\'' + "\n"+
                ", projName='" + projName + '\'' + "\n"+
                ", message='" + message + '\'' + "\n" +
                "---------------\n"
                ;
    }

    public String toStringHTML() {
        return "<tr>" +
                "<th>" + dbName + "</th>" +
                "<th>" +  projName + "</th>" +
                "<th>" +  requestName + "</th>" +
                "<th>" + message + "</th>" +
                "<th>"+ responseXML + "</th>" +
                "</tr>";
    }
}
