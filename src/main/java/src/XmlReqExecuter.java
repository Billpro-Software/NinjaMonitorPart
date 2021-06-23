package src;

import src.dto.ResponseDetails;
import src.dto.StructureDTO;
import src.email.EmailProcessor;
import src.executer.XmlHandler;
import src.environment.Environment;
import src.xml.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class XmlReqExecuter {

    public static final String PHONE_RECIPIENTS_LIST = "04528270376";
    public static final String MAIL_RECIPIENTS_LIST = "michael.son@teliacompany.com";


    private static HashMap<String, Object> requestInput = new HashMap<>();


    public static void main(String[] args) throws Exception {

        List<ResponseDetails> responseDetailsList = new ArrayList<>();

        for(Environment.Databases db : Environment.Databases.values()){
         responseDetailsList.addAll( getStatusByDb(db));
        }

        if(!responseDetailsList.isEmpty()) {
         sendMail(responseDetailsList);

         sendMessageRequest(Environment.Databases.CONF_DK_AT, responseDetailsList);
        }

              //  System.exit(0);
    }

    public static void sendMessageRequest(Environment.Databases db, List<ResponseDetails> responseDetailsList) throws Exception {


        requestInput = new HashMap<>();


        requestInput.put("Environment", db.dbName);

        System.out.println("Db = " + db);

        requestInput.put("Project","dk_mdwc");
        requestInput.put("ProjectUrlConfiguration", "Mdwc");


        requestInput.put("XmlFileName", "MDWC_SendMessage");

        String smsMsg = "Sanity Status\n";

        for (ResponseDetails responseDetails :responseDetailsList) {
            smsMsg += responseDetails.toString();
        }

        XmlHandler xmlExecuter = new XmlHandler(requestInput);

        String [] phoneNumbers= PHONE_RECIPIENTS_LIST.split(";");
        for(String phoneNumber :phoneNumbers ) {
            String filledXMLReq = xmlExecuter.getXmlRequest().getXmlRequest()
                    .replace("#msisdn", phoneNumber)
                    .replace("#msg", smsMsg);
            xmlExecuter.getXmlRequest().setXmlRequest(filledXMLReq);
            String respXML = xmlExecuter.execute();
            System.out.println(respXML);
        }

    }

    public static List<StructureDTO> getStructureByDb(String db)  {
        return null;

    }

    public static List<ResponseDetails> getStatusByDb(String db) throws Exception {


        requestInput = new HashMap<>();

        List<StructureDTO> structureDTOList = getStructureByDb(db);


        requestInput.put("Environment", db);

        // String msgDb = "Db = " + db;
        List<ResponseDetails> responseDetailsList = new ArrayList<>();


        for (StructureDTO structureDTO : structureDTOList) {
            //  String msg = msgDb;
            System.out.println("Db = " + db);
            //msg += "<br/>Project = " + proj.projName;
            System.out.println("Project = " + structureDTO.getProject());

            requestInput.put("Project",structureDTO.getProject());
            requestInput.put("ProjectUrlConfiguration", structureDTO.getUrl());

            String [] requests  = structureDTO.getRequestList().split(";");

            for(String requestName:requests) {
                ResponseDetails responseDetails = new ResponseDetails();
                responseDetails.setDbName(db);
                responseDetails.setProjName(structureDTO.getProject());
                responseDetails.setRequestName(requestName);


                requestInput.put("XmlFileName", requestName);

                XmlHandler xmlExecuter = new XmlHandler(requestInput);
                String respXML = xmlExecuter.execute();
                System.out.println(respXML);
                boolean isSuccess  = ResponseHandler.parseCheckConnResponse(respXML,requestName,responseDetails);
                if(!isSuccess){
                    responseDetailsList.add(responseDetails);
                    // msg += "<br/>" +res;
                    // emailMsg += msg;
                }
                // System.out.println(res);
            }

        }
        return responseDetailsList;
    }

    public static List<ResponseDetails> getStatusByDb(Environment.Databases db) throws Exception {


            requestInput = new HashMap<>();


            requestInput.put("Environment", db.dbName);
            String emailMsg = "";

           // String msgDb = "Db = " + db;
            List<ResponseDetails> responseDetailsList = new ArrayList<>();


            for (Environment.Projects proj : Environment.Projects.values()) {
              //  String msg = msgDb;
                System.out.println("Db = " + db);
                //msg += "<br/>Project = " + proj.projName;
                System.out.println("Project = " + proj.projName);



                requestInput.put("Project", proj.projName);
                requestInput.put("ProjectUrlConfiguration", proj.url);

                for(String requestName:proj.requestList) {
                    ResponseDetails responseDetails = new ResponseDetails();
                    responseDetails.setDbName(db.toString());
                    responseDetails.setProjName(proj.projName);
                    responseDetails.setRequestName(requestName);


                    requestInput.put("XmlFileName", requestName);

                    XmlHandler xmlExecuter = new XmlHandler(requestInput);
                    String respXML = xmlExecuter.execute();
                    System.out.println(respXML);
                    boolean isSuccess  = ResponseHandler.parseCheckConnResponse(respXML,requestName,responseDetails);
                    if(!isSuccess){
                        responseDetailsList.add(responseDetails);
                       // msg += "<br/>" +res;
                       // emailMsg += msg;
                    }
                   // System.out.println(res);
                }

            }
            return responseDetailsList;
        }

        private static void sendMail(List<ResponseDetails> responseDetailsList){
            String emailMsg = "<table>"
                    + "<tr>"
                    +" <th>dbName</th>"
                    +" <th>projName</th>"
                    +" <th>requestName</th>"
                    +" <th>message</th>"
                    +" <th>responseXML</th>"
                    + "</tr>";

            for (ResponseDetails responseDetails :responseDetailsList) {
                emailMsg += responseDetails.toStringHTML();

            }

            emailMsg += "</table>";


            String recipients = MAIL_RECIPIENTS_LIST;

            EmailProcessor emailProcessor = new EmailProcessor();

            emailProcessor.sendEmail(recipients,"Sanity Status",emailMsg);


        }


  }



