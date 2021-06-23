package src.environment;


import src.utils.FileUtils;
import src.utils.StringUtilsNinjaAutomatedTest;
import src.utils.XmlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Environment {

     public enum Databases {
//
        CONF_DK_AT ("neo-dk-at"),
        CONF_DK_STB ("neo-dk-st-b"),
        CONF_DK_STA ("neo-dk-st-a");

         public final String dbName;


         private Databases(String dbName) {
             this.dbName = dbName;
         }
    }

    public enum Projects {



    //    A("ww","rr",new String[]{"Foo","Bar","Baz"});


        PROJECT_DK_NSL ("dk_nsl","Nsl",new String[]{"GetAvailableTelephoneNumbers"} ),
        PROJECT_DK_GENERIC ( "dk_generic","Generic",new String[]{"CheckConnections","GetAvailableMsisdns"}),
        PROJECT_DK_SP ("dk_sp","Sp",new String[]{"CheckConnections"}),
        PROJECT_DK_MDWC ("dk_mdwc","Mdwc",new String[]{"CheckConnections"});


        public final String projName;
        public final String url;
        public final String[] requestList;



        private Projects(String projName, String url,String[] requestList) {
            this.projName = projName;
            this.url = url;
            this.requestList = requestList;
        }
    }




    static private Environment environment;

    public static String getProject() {
        return project;
    }

    public void setProject(String project) {
        Environment.project = project;
    }

    static private String project;

    //static NinjaConfigRegistry ninjaConfigRegistry;

    static private String httpServerUrl;


    private static HashMap<String, Object> xmlMap;

    public static String getHttpServerUrl() {
        return httpServerUrl;
    }

    private Environment() {
    }

    public static final Environment getInstance() {

        if (environment != null) {
            return environment;
        }

        environment = new Environment();

        return environment;
    }

    public final void setEnvironment(String envName) {
        setEnvironment(envName,null);
    }

    public final void setEnvironment(String envName, String projectUrlConfiguration) {


        if (environment == null) {
            environment = new Environment();
        }

        //    String projName         = "dk3";
        //    String projName         = "dk.at";
        //    String projName         = "dk.st";
        //    String projName         = "at-teliadk";
        //    String projName         = "dk.pt";
        //    String projName         = "dk.prod";
        //    String projName         = "no.env1.netcom";
        //    String projName         = "no.at.netcom";
        //    String projName         = "no.st.netcom";
        //    String projName         = "no.st2.netcom";
        //    String projName         = "pt-netcom";
        //    String projName         = "no.pt2.netcom";
        //    String projName         = "no.pt.netcom";

        String confRootDir = System.getProperty("user.dir", ".");

        //String configFile = confRootDir + "/conf/ninja/" + projName + "/ninja.properties";

        String configFile = confRootDir + "/conf/ninja/" + envName + "/ninja.xml";

        configFile = StringUtilsNinjaAutomatedTest.strTranslateFileSeperator(configFile);

        String propertyName = "ninja.config";

        System.setProperty(propertyName, configFile);


        //ninjaConfigRegistry = NinjaConfigRegistry.getInstance();

        setHttpServerUrl(configFile, projectUrlConfiguration);


        setWSDLs(configFile);

    }


    static {
        //    String projName         = "dk3";
        //    String projName         = "dk.at";
        //    String projName         = "dk.st";
        //    String projName         = "at-teliadk";
        //    String projName         = "dk.pt";
        //    String projName         = "dk.prod";
        //    String projName         = "no.env1.netcom";
        String envName = "no.at.netcom";
        //    String projName         = "no.st.netcom";
        //    String projName         = "no.st2.netcom";
        //    String projName         = "pt-netcom";
        //    String projName         = "no.pt2.netcom";
        //    String projName         = "no.pt.netcom";
        //String projName = "no.prod";


        String confRootDir = "C:";
        confRootDir = System.getProperty("user.dir", ".");

        //String configFile = confRootDir + "/conf/ninja/" + projName + "/ninja.properties";

        String configFile = confRootDir + "/conf/ninja/" + envName + "/ninja.xml";


        String propertyName = "ninja.config";

        System.setProperty(propertyName, configFile);

        //System.out.println("System.setProperty('" + propertyName + "' , '" + configFile + "')");

        //ninjaConfigRegistry = NinjaConfigRegistry.getInstance();

        setHttpServerUrl(configFile,null);

    }

    static public boolean isLocalHost() {
        boolean locaHost = false;



        System.out.println("isLocalHost : " + locaHost);

        return locaHost;
    }


    private static void setHttpServerUrl(String configFile, String projectUrlConfiguration) {

        String strXmlNinjaConfigFile = FileUtils.loadFileToString(configFile);

        String tagSearch = "NinjaAutomatedTest->url";

        if (StringUtilsNinjaAutomatedTest.isNotNullOrEmpty(projectUrlConfiguration)) {
            tagSearch = "NinjaAutomatedTest->Urls->" + projectUrlConfiguration;
        }

        httpServerUrl = StringUtilsNinjaAutomatedTest.strGetXmlTagValue(strXmlNinjaConfigFile, tagSearch);

        //System.out.println("setHttpServerUrl : httpServerUrl : " + httpServerUrl);

    }


    private static void setWSDLs(String configFile) {

        String strXmlNinjaConfigFile = FileUtils.loadFileToString(configFile);

        //String tagSearch = "NinjaAutomatedTest->WSDLs";

        //String strXmlWSDLs = StringUtilsNinjaAutomatedTest.strGetXmlTagValue(strXmlNinjaConfigFile, tagSearch);

        List<String> nodes = new ArrayList<>();
        //nodes.add("wsdl");

        List<Object> wsdls = XmlUtils.getXmlResultSet(strXmlNinjaConfigFile, "wsdl",nodes);

        for (Object o : wsdls){
            try {
                HashMap<String,Object> map = (HashMap<String, Object>) o;
                String url = (String) map.get("wsdl");
                //xmlMap = SoapUI.loadWsdl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //System.out.println("setHttpServerUrl : httpServerUrl : " + httpServerUrl);

    }


    public String getXml(String requestName){
        String ans = (String) xmlMap.get(requestName);
        return ans;
    }

}
