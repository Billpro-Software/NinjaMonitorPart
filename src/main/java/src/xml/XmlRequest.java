package src.xml;


import src.utils.FileUtils;
import src.utils.StringUtilsNinjaAutomatedTest;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriBuilder;*/

/**
 * Created by vmb5390 on 22.11.2018.
 */
public class XmlRequest {

    private String xmlTemplateFileName;
    private String xmlRequest;
    private List<String> xmlPlaceHolders = new ArrayList<String>();

    HashMap<String, Object> xmlInputParameters;

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }

    // Constructor , load xml xml by testCaseClassName and projectName
    public XmlRequest(String xmlFileName, String projectName, HashMap<String, Object> inputParameters) {

        xmlTemplateFileName = this.getTemplateFileName(xmlFileName, projectName);

        loadXmlTemplateFile();

        xmlInputParameters = inputParameters;

    }

    private void loadXmlTemplateFile() {
        try {
            xmlRequest = FileUtils.loadFileToString(xmlTemplateFileName);
            //System.out.println(xmlRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public String getTemplateFileName(String className, String projectName) {

        String userDir = System.getProperty("user.dir", ".") + "\\";


        String testCaseDir = "src\\main\\java\\src\\requestXMLs\\"+projectName+"\\";

        String xmlFileName = className + ".xml";

        String xmlTemplateFileName = userDir + testCaseDir + xmlFileName;

        xmlTemplateFileName = StringUtilsNinjaAutomatedTest.strTranslateFileSeperator(xmlTemplateFileName);

        return xmlTemplateFileName;
    }

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 4);
    }

}
