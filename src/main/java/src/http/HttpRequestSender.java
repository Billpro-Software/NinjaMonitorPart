package src.http;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import src.conn.BaseConnector;

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

/*import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriBuilder;*/

/**
 * Created by vmb5390 on 22.11.2018.
 */
public class HttpRequestSender {
    String TMDXmlRequest = "<TMDXmlRequest";
    String parameterName = "<parameter name"; //
    String secondparameterName = "</parameter>";
    String JDBC = "jdbc";
    static String urlXml_byDefault = "http://ninjadk-testsvr.han.telia.se:7042/NinjaGenericInterface";
    String filePath;
    String directory;
    String environment;
    String projectName;
    // Logger logger;

    public HttpRequestSender(String filePath, String directory, String environment, String projectName) {
        this.filePath = filePath;
        this.directory = directory;
        this.environment = environment;
        this.projectName = projectName;
        //logger = LogManager.getLogger(this.getClass());
    }


    // found some parameter in xml file(value of nodes)
    public String findParameter(String parameter) {
        String result = null;
        parameter = parameterName + "=" + '"' + parameter;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
            StringBuffer buffer = new StringBuffer();


            while ((result = bufferedReader.readLine()) != null) {

                if ((result.contains(parameter))) {
                    bufferedReader.close();
                    Integer nameStart = result.indexOf(">");
                    Integer nameEnd = result.indexOf("</");
                    result = result.substring(nameStart + 1, nameEnd);
                    return result;
                }
            }
        } catch (IOException e) {
            //logger.info("File was not found check path please");
            System.out.println("File was not found check path please");
            e.printStackTrace();
        }
        return result;
    }

    //put values in xml request
    public void putValueOnHolder(HashMap<String, String> values) {
        try {
            //BufferedReader bufferedReader = new BufferedReader(new FileReader(setTempFile()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(setTempFile()), "UTF-8"));

            StringBuffer buffer = new StringBuffer();
            String str = null;
            String[] arr;
            boolean flag = false;

            while ((str = bufferedReader.readLine()) != null) {
                for (Map.Entry<String, String> e : values.entrySet()) {
                    str = str.replaceAll(e.getKey(), e.getValue());
                }
                buffer.append(str);
                buffer.append('\n');
            }

            BufferedWriter print = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(setTempFile()), "UTF-8"));
            print.write(buffer.toString());
            print.close();
        } catch (IOException e) {
            //logger.info("File was not found check path please");
            System.out.println("File was not found check path please");
            e.printStackTrace();
        }

    }

    //replace value of xml node : <NodeName>value</NodeName> -> <NodeName>#NodeName</NodeName>
    public void replasePlaceHolder() {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
            StringBuffer buffer = new StringBuffer();
            String str = null;

            while ((str = bufferedReader.readLine()) != null) {

                if ((str.contains(parameterName)) && (str.contains(secondparameterName))) {
                    str = changeString(str);
                    buffer.append(str);
                    buffer.append('\n');
                } else if (str.contains(TMDXmlRequest)) {
                    buffer.append('<');
                    buffer.append(BaseConnector.header);
                    buffer.append(this.filePath);
                    buffer.append('"');
                    buffer.append('>');
                    buffer.append('\n');
                } else {
                    buffer.append(str);
                    buffer.append('\n');
                }
            }
            bufferedReader.close();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(setTempFile()));

            bufferedWriter.write(buffer.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            //logger.info("File was not found check path please");
            System.out.println("File was not found check path please");
            e.printStackTrace();
        }

    }

    private void replaseHeader() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(setFilepath()));
            StringBuffer buffer = new StringBuffer();
            String str = null;

            while ((str = bufferedReader.readLine()) != null) {

            }
        } catch (IOException e) {
            //logger.info("File was not found check path please");
            System.out.println("File was not found check path please");
            e.printStackTrace();
        }
    }

    private static String changeString(String str) {

        //example how to change string
        //<parameter name="InvokingSystem">Test</parameter> old
        //<parameter name="InvokingSystem">#InvokingSystem</parameter> new

        Integer nameStart = str.indexOf("=\"");
        Integer nameEnd = str.indexOf("\">");
        String strValue = str.substring(nameStart + 2, nameEnd);
        String str1 = str.substring(0, str.indexOf(">") + 1);
        String str2 = str.substring(str.indexOf("</"), str.length());

        return str1 + "#" + strValue + str2;
    }

    //returm xml request as a string
    public String addRequest() {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(setTempFile()));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append('\n');
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //Create path to xml file
    private String setFilepath() {

        String userDir = System.getProperty("user.dir", ".") + "\\";

        String testCaseDir = "src\\main\\java\\com\\telia\\automatedtest\\project\\" + projectName + "\\xmlstep\\input\\xml\\";

        String xmlFileName = this.filePath + ".xml";

        String filePath = userDir + testCaseDir + xmlFileName;

//        String filePath     = userDir + "\\src\\XmlTests\\" +  this.directory +"\\"+this.filePath+".xml";
        return filePath;
    }

    //Create path to temporary file
    public String setTempFile() {

        String userDir = System.getProperty("user.dir", ".") + "\\";

        String testCaseDir = "src\\main\\java\\com\\telia\\automatedtest\\project\\" + projectName + "\\xmlstep\\input\\xml\\";

        String xmlFileName = this.filePath + ".xml";

        String filePath = userDir + testCaseDir + "\\temp\\" + xmlFileName;

        return filePath;

    }


    // send xml request via JAVA JERSEY
    static public String sendHttpRequest(String xml, String host) {


        System.out.println(xml);

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri(host).build());
        service.header("Content-Type", "text/soap+xml");
        ClientResponse response = service.post(ClientResponse.class, xml);
        return response.getEntity(String.class);
    }

    //send xml request to server return request as array string
    public String[] sendRequest_arrayResponse(String xml, String host) {
        String response = sendRequest(xml, host);
        String[] result = response.split("\n");
        return result;
    }

    static public String sendRequest(String xmlRequestBody, String requestUrl) {
        boolean isDebug = false;
        return sendRequest(xmlRequestBody, requestUrl, isDebug);
    }

    //send xml request to server return request as string
    static public String sendRequest(String xmlRequestBody, String requestUrl, boolean isDebug) {


        // ninjadk-testsvr
        //String URL = requestUrl; //" http://"+ path_send +".han.telia.se:7072/NinjaGenericInterface";


        StringBuffer response = new StringBuffer();
        if (requestUrl == null) {
            System.out.println("GET EVIRONMENT NUll put it by default :");
            System.out.println("http://ninjadk-testsvr.han.telia.se:7072/NinjaGenericInterface");
            //logger.info("GET EVIRONMENT NUll put it by default :");
            //logger.info("http://ninjadk-testsvr.han.telia.se:7072/NinjaGenericInterface");
            //  URL = urlXml_byDefault;
            return "";
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xmlRequestBody);
            wr.flush();
            wr.close();


            int responseCode = con.getResponseCode();
            BufferedReader in = null;

            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine = "";
            int i;
            while ((i = in.read()) != -1) {
                response = response.append(inputLine.valueOf((char) i));
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }



        //System.out.println(response);


        return response.toString();
    }

    //send simply request array result return
    private ArrayList<String> sendSimpleQuery_Array(String query, String environment) {
        ArrayList<String> result = new ArrayList<>();
        BaseConnector base = BaseConnector.getInstance();
        base.setup(environment);
        base.setupDatabase(environment, projectName);
        try {
            System.out.println("Try to connect ORACLE " + base.getUrlBase());
            //logger.info("Try to connect ORACLE " + base.getUrlBase());
            Connection dbConn = DriverManager.getConnection(base.getJDBC() + ":" + base.getSubProtocol() + ":@" + base.getUrlBase(), base.getUserBase(), base.getPasswordBase());
            Statement stmt = dbConn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The coonection " + base.getUrlBase() + " was closed");
        //logger.info("The coonection " +  base.getUrlBase()+" was closed");
        return result;
    }

    //check result of xml request
    public boolean validateRequest(String query, String columnLabel1, String columnLabel2, String firstParameter, String secondParameter, String enviriment) {

        BaseConnector connector = BaseConnector.getInstance();
        BaseConnector.setup(enviriment);
        BaseConnector.setupDatabase(enviriment, projectName);

        boolean result = true;
        Connection dbConn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        String checkParameter1 = "";
        String checkParameter2 = "";
        List<String> list = new ArrayList<String>();

        try {
            System.out.println("Try to connect ORACLE " + connector.getUrlBase());
            //logger.info("Try to connect ORACLE "+ connector.getUrlBase());
            dbConn = DriverManager.getConnection(JDBC + ":" + connector.getSubProtocol() + ":@" + connector.getUrlBase(), connector.getUserBase(), connector.getPasswordBase());
            stmt = dbConn.createStatement();
            resultSet = stmt.executeQuery(query);
            int i = 0;
            while (resultSet.next()) {
                if (i == 0) {
                    checkParameter1 = resultSet.getString(columnLabel1).toString();
                    checkParameter2 = resultSet.getString(columnLabel2).toString();
                    i++;
                } else {
                    i++;
                }
                if (!checkParameter1.contains(firstParameter) || !checkParameter2.contains(secondParameter)) {
                    result = false;
                }
            }
            System.out.println("Connection was saccessfuly closed");
            //logger.info("Connection was saccessfuly closed");
            dbConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    private static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}
