package src.xml;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/*import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriBuilder;*/
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static src.xml.ResponseHandler.FAIL;

/**
 * Created by vmb5390 on 22.11.2018.
 */
public class ReadParseXml {
     String TMDXmlRequest ="<TMDXmlRequest";
     final static  String parameterName = "<parameter name";
     final static String secondparameterName = "</parameter>";
     String JDBC = "jdbc";
     String urlXml_byDefault = "http://ninjadk-testsvr.han.telia.se:7042/NinjaGenericInterface";
     String filePath ;
     String directory;
     String environment;
     String projectName;
    // Logger logger;

    public ReadParseXml(String filePath,String directory,String environment,String projectName) {
       this.filePath = filePath;
       this.directory = directory;
       this.environment =environment;
       this.projectName = projectName;
       //logger = LogManager.getLogger(this.getClass());
    }


// found some parameter in xml file(value of nodes)
   static public  String findParameter( String response,String parameter){
        String result = null;
        parameter = parameterName + "="+'"'+parameter+'"';
       // try {
          //  BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
          //  StringBuffer buffer = new StringBuffer();


          //  while ((result= response..readLine())!=null) {
       Integer indParam = response.indexOf(parameter);
        if(indParam > 0) {
          //  bufferedReader.close();
            String respSubString = response.substring(indParam);

            Integer nameStart = respSubString.indexOf(">");
            Integer nameEnd = respSubString.indexOf("</");
            result = respSubString.substring(nameStart+1,nameEnd);
           // return result;
        }
           // }
//        }
//        catch (IOException e) {
//            //logger.info("File was not found check path please");
//            System.out.println("File was not found check path please");
//            e.printStackTrace();
//        }
        return result;
    }

    // found some parameter in xml file(value of nodes)
    static public  List<String> findParametersByValue(String response, String reqValue){
        String parameter = parameterName + "="+'"';
        // try {
        //  BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
        String respSubString = response;

      //  String result = "";
        List<String> resList = new ArrayList<>();

        Integer indParam = respSubString.indexOf(parameter);

        if(indParam < 0){
            return null;
        }


        while (indParam >= 0 && (respSubString.length() > parameter.length())) {

                //  bufferedReader.close();
                respSubString = respSubString.substring(indParam);
                respSubString.indexOf(parameter);
                Integer nameStart = respSubString.indexOf(">");
                Integer nameEnd = respSubString.indexOf("</");

                String name = respSubString.substring(parameter.length(), nameStart).replace("\"", "");
                String value = respSubString.substring(nameStart + 1, nameEnd);
                System.out.println("name" + name + " value" + value);
                respSubString = respSubString.substring(nameEnd + secondparameterName.length() + 2);

                indParam = respSubString.indexOf(parameter);
                if (value.equals(reqValue)) {
                    resList.add(name);
                    //result += (result.isEmpty() ? "" : ",") + name;
                }

        }





//        while ((respSubString.length() > parameter.length())) {
//
//            if((respSubString.contains(parameter))) {
//                //  bufferedReader.close();
//                Integer nameStart = respSubString.indexOf(">");
//                Integer nameEnd = respSubString.indexOf("</");
//                String value = respSubString.substring(nameStart+1,nameEnd);
////                String name = respSubString.substring(nameStart + 1,)
////                if(value.equals(reqValue)){
////                    result += "," + ;
////                }
//                respSubString = respSubString.substring(nameEnd + secondparameterName.length() + 2);
//                // return result;
//            }
//         }
//        }
//        catch (IOException e) {
//            //logger.info("File was not found check path please");
//            System.out.println("File was not found check path please");
//            e.printStackTrace();
//        }
        return resList;
    }

    // found some parameter in xml file(value of nodes)
    static public  String findAttribute(String response,String attribute ){
        // try {
        //  BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
        String respSubString = response;

        String result = null;

        Integer indParam = respSubString.indexOf(attribute);

        if(indParam > 0) {
            respSubString = response.substring(indParam);
            Integer nameStart = respSubString.indexOf(">");
            Integer nameEnd = respSubString.indexOf("</");
            result = respSubString.substring(nameStart+1,nameEnd);
        }

        return result;
    }



    private static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return  dateFormat.format(cal.getTime());
    }

}
