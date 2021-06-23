package src.utils;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class XmlUtils {

    public static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






    public static void printTags(Node nodes){
        if(nodes.hasChildNodes()  || nodes.getNodeType()!=3){
            System.out.println(nodes.getNodeName()+" : "+nodes.getTextContent());
            NodeList nl=nodes.getChildNodes();
            for(int j=0;j<nl.getLength();j++)printTags(nl.item(j));
        }
    }


    public static final List<Object> getXmlResultSet(String strXml, String tagName, List<String> listNodesNames) {

        HashMap<String, Object> xmlElementRecord =null;
        List<Object> xmlResultSet = new LinkedList<>();

        String strTemp = StringUtilsNinjaAutomatedTest.strFilterXmlRemarks(strXml);

        String strRealTagName = StringUtilsNinjaAutomatedTest.strFindXmlRealTagName(strTemp, tagName);


        String tagStartInXml = StringUtilsNinjaAutomatedTest.strFindXmlTag(strTemp, tagName);

        String tagEndInXml = StringUtilsNinjaAutomatedTest.strFindXmlEndTag(strTemp, strRealTagName);


        while (tagStartInXml != null ){

            String tagValue = StringUtilsNinjaAutomatedTest.strBetweenStr(strTemp, tagStartInXml, tagEndInXml);
            //PrintUtils.printLine(tagName);
            //PrintUtils.printLine(tagValue);

            //Go through nodes list
            xmlElementRecord = new HashMap<>();

            if (listNodesNames.isEmpty()) {
                xmlElementRecord.put(tagName,tagValue);
            } else {

                for (String nodeName : listNodesNames) {
                    String strRealNodeName = StringUtilsNinjaAutomatedTest.strFindXmlRealTagName(tagValue, nodeName);
                    if (strRealNodeName == null) {
                        continue;
                    }
                    String strNodeStartInXml = StringUtilsNinjaAutomatedTest.strFindXmlTag(tagValue, nodeName);
                    String strNodeEndInXml = StringUtilsNinjaAutomatedTest.strFindXmlEndTag(tagValue, strRealNodeName);
                    String nodeValue = StringUtilsNinjaAutomatedTest.strBetweenStr(tagValue, strNodeStartInXml, strNodeEndInXml);
                    xmlElementRecord.put(nodeName, nodeValue);
                    //PrintUtils.printLine(nodeName+ " : " + nodeValue);
                }
            }

            xmlResultSet.add(xmlElementRecord);

            // Get next tag in the buffer
            //PrintUtils.printLine("");
            strTemp = StringUtilsNinjaAutomatedTest.strAfterStr(strTemp, tagEndInXml);
            tagStartInXml = StringUtilsNinjaAutomatedTest.strFindXmlTag(strTemp, tagName);
            tagEndInXml = StringUtilsNinjaAutomatedTest.strFindXmlEndTag(strTemp, tagName);

        }

        return xmlResultSet;
    }


    public static final List<String> getTagNodes(String strXml, String tagName) {

        List<String> nodesList = new LinkedList<>();

        String tagValue = StringUtilsNinjaAutomatedTest.strGetXmlTagValue(strXml,tagName);

        String strTemp = tagValue;


        while ( strTemp != null && strTemp.contains("<")) {
            String strTag = strTag = "<" + StringUtilsNinjaAutomatedTest.strBetweenStr(strTemp, "<", ">") + ">";

            if (strTag.startsWith("</")) {
                // End of ellement , get next tag name
                strTemp = StringUtilsNinjaAutomatedTest.strAfterStr(strTemp, strTag);
                continue;
            }

            String strCurrentTagName = StringUtilsNinjaAutomatedTest.strBetweenStr(strTag, "<", ">");

            if (strCurrentTagName.contains(" ")) {
                strCurrentTagName = StringUtilsNinjaAutomatedTest.strBeforeStr(strCurrentTagName, " ");
            }

            if (strCurrentTagName.contains(":")) {
                strCurrentTagName = StringUtilsNinjaAutomatedTest.strAfterStr(strCurrentTagName, ":");
            }

            nodesList.add(strCurrentTagName);

            // Get next tag name
            strTemp = StringUtilsNinjaAutomatedTest.strAfterStr(strTemp, strTag);
        }


        return nodesList;
    }

//    public static String xmlToJson(String strXml) {
//        String jsonPrettyPrintString=null;
//        Integer PRETTY_PRINT_INDENT_FACTOR = 4;
////        public static String TEST_XML_STRING =                "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";
//
//            try {
//                JSONObject xmlJSONObj = XML.toJSONObject(strXml);
//                jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
//                //System.out.println(jsonPrettyPrintString);
//            } catch (JSONException je) {
//                System.out.println(je.toString());
//            }
//
//        PrintUtils.printLine(jsonPrettyPrintString);
//
//        return jsonPrettyPrintString;
//    }


}


