package src.executer;


import src.environment.Environment;
import src.http.HttpRequestSender;
import src.utils.StringUtilsNinjaAutomatedTest;
import src.utils.XmlUtils;
import src.xml.XmlRequest;

import java.util.*;
import java.util.logging.Logger;



/**
 * Created by vmb5390 on 26.11.2018.
 */
public  class XmlHandler  {

    static private String XML_TYPE_RICHARD = "Richard";

    protected HashMap<String, Object> inputParameters;
    protected HashMap<String, Object> testStepOutput = new HashMap<>();

    protected XmlRequest xmlRequest;
    protected String xmlResponse;


    public String getResultSet(int i, String columnName) throws Exception {
        List<Object> list = (List<Object>) testStepOutput.get("ResultSet");
        if (list == null) {
            return null;
        }
        HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);
        return (String) map.get(columnName);
    }


    protected long startTime;
    protected Logger logger;

    public XmlHandler() {
    }

    public XmlHandler(HashMap<String, Object> parameters) throws Exception {
        setup(parameters);
    }

    public void setup(HashMap<String, Object> parameters) throws Exception {

        inputParameters = parameters;
        String project = (String) inputParameters.get("Project");

        String environmentName = (String) parameters.get("Environment");
        if (environmentName != null) {
            Environment environment = Environment.getInstance();
            String projectUrlConfiguration = (String) parameters.get("ProjectUrlConfiguration");
            environment.setEnvironment(environmentName,projectUrlConfiguration);
        }

        String xmlFileName = (String) inputParameters.get("XmlFileName");

        //PrintUtils.printHeader("XmlHandler : setup : " + className);

        /////////////////////////////////////////////////////////////////////////////////////////////////
        // Create xml request                                                                          //
        //   constructor will                                                                          //
        //    getTemplateFileName                                                                      //
        //    loadXmlTemplateFile                                                                      //
        //    extractPlaceHoldersFromXml                                                               //
        //    placeInputParamersIntoXml                                                                //
        xmlRequest = new XmlRequest(xmlFileName, project, inputParameters);
        startTime = System.currentTimeMillis();
        //                                                                                             //
        /////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public String execute()  {
        //PrintUtils.printHeader("XmlHandler : executeStep");

        return HttpRequestSender.sendRequest(xmlRequest.getXmlRequest(), Environment.getHttpServerUrl());

        //String strXmlNodesNames = (String) inputParameters.get("XmlOutput");

    }


    public void setXmlOutput(String strXmlNodesNames) throws Exception {
        StringTokenizer multiTokenizer = new StringTokenizer(strXmlNodesNames, ", ");
        while (multiTokenizer.hasMoreTokens()) {
            String nodeName = multiTokenizer.nextToken();
            String nodeValue = StringUtilsNinjaAutomatedTest.strGetXmlTagValue(xmlResponse, nodeName);

            while (nodeName.contains("->")) {
                nodeName = StringUtilsNinjaAutomatedTest.strAfterStr(nodeName, "->");
            }

            testStepOutput.put(nodeName, nodeValue);
        }
    }

    public void setXmlOutput(List<String> lstXmlNodesNames) throws Exception {
        for (String nodeName : lstXmlNodesNames) {
            nodeName = nodeName.replaceAll("\r", "").replaceAll("\n", "");
            String nodeValue = StringUtilsNinjaAutomatedTest.strGetXmlTagValue(xmlResponse, nodeName);
            testStepOutput.put(nodeName, nodeValue);
        }
    }



    public void setXmlResultSet(String strXmlResultSetParams) throws Exception {

        // "TelephoneNumber[CountryCode,LocalNumber]"

        String tagName = StringUtilsNinjaAutomatedTest.strBeforeStr(strXmlResultSetParams, "[");
        String nodeNames = StringUtilsNinjaAutomatedTest.strBetweenStr(strXmlResultSetParams, "[", "]");

        List<String> listNodesNames = new LinkedList<>();

        if (nodeNames.contains("*")) {
            listNodesNames = XmlUtils.getTagNodes(xmlResponse, tagName);
        } else {
            StringTokenizer multiTokenizer = new StringTokenizer(nodeNames, ", ");
            while (multiTokenizer.hasMoreTokens()) {
                String nodeName = multiTokenizer.nextToken();
                listNodesNames.add(nodeName);
            }
        }


        testStepOutput.put("ResultSet", XmlUtils.getXmlResultSet(xmlResponse, tagName, listNodesNames));
    }

    public XmlRequest getXmlRequest() {
        return xmlRequest;
    }
}
