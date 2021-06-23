package src.conn;

//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by vmb5390 on 22.11.2018.
 */
public class BaseConnector {

    private static BaseConnector instance = null;
    private static int count = 0;

    private static   String s_sNinjaConfigFile     = "";
    static String  s_sEnvironment;// = "dk.at";


    static String driverBase;
    static String subProtocol;
    static String urlBase;
    static String userBase;
    static String passwordBase;
    public static String host;
    public static String header;
    final static String JDBC = "jdbc";

    static String ReqCallerId;
    static String ReqPassword;
    static String ReqChannel;
    static String ReqInvokingSystem;
   // static Logger logger;



    private BaseConnector(){}

    public static BaseConnector getInstance(){
        if(instance==null)
            instance = new BaseConnector();
        return instance;
    }



    public static void setup(String enviroment) {
        //PrintUtils.printHeader("BaseConnector : setup : " + enviroment);
        //s_sNinjaConfigFile     = System.getProperty("user.dir", ".") + "\\src\\Config\\FokusAppDataSource.xml";
        //System.setProperty(enviroment,s_sNinjaConfigFile);
    }

    public static void setupDatabase(String enviroment, String projectName){
        String file = System.getProperty(enviroment);
        File fXml = new File (file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       // logger = LogManager.getLogger(BaseConnector.class);

        try{
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(projectName);
            Node nNode = nList.item(0);

            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) nNode;
                driverBase = element.getElementsByTagName("driver").item(0).getTextContent();
                subProtocol =  element.getElementsByTagName("subprotocol").item(0).getTextContent();
                urlBase = element.getElementsByTagName("url").item(0).getTextContent();
                userBase = element.getElementsByTagName("username").item(0).getTextContent();
                passwordBase = element.getElementsByTagName("password").item(0).getTextContent();
                host = element.getElementsByTagName("serverhost").item(0).getTextContent();
                header = element.getElementsByTagName("header").item(0).getTextContent();
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection dbConn = null;

        try{
         dbConn = DriverManager.getConnection(JDBC + ":" + subProtocol + ":@" + urlBase, userBase, passwordBase);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        count++;
        return dbConn;
    }

    public String getDriverBese(){
        return driverBase;
    }

    public String getSubProtocol(){
        return subProtocol;
    }

    public String getUrlBase(){
        return urlBase;
    }

    public String getUserBase(){
        return userBase;
    }

    public String getPasswordBase(){
        return passwordBase;
    }

    public String getJDBC(){return JDBC;}

    public String getHost(){return host;}

    public int getCoun(){return  count;}
}

