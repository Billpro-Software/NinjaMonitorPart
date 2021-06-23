package src.xml;

import src.dto.ResponseDetails;

import java.util.List;

public class ResponseHandler {

    public final static String SUCCESS = "SUCCESS";
    public final static String FAIL = "FAIL";


    public static boolean parseCheckConnResponse(String respXML, String requestName, ResponseDetails responseDetails){

        String result = requestName + ":";
        boolean isSucceed = true;
        String msg = "";


        if(requestName.contains("CheckConnections")) {
            List<String> resList = ReadParseXml.findParametersByValue(respXML, FAIL);
            if(resList == null){
                msg = "\nCheckConnections request failed";
                isSucceed = false;
            }
            else if (!resList.isEmpty()){
                msg = "\nDatabases: " + String.join(",", resList) + " failed";
                isSucceed = false;
            }
        }
        else if(requestName.contains("GetAvailableMsisdns")){
            String res = ReadParseXml.findParameter(respXML, "Msisdn");
            if (res == null){
                isSucceed = false;
                msg = "\nMassage: GetAvailableMsisdns failed";
            }
        }
        else if(requestName.contains("GetAvailableTelephoneNumbers")){
            String res = ReadParseXml.findAttribute(respXML, "LocalNumber");
            if (res == null){
                isSucceed = false;
                 msg = "\nGetAvailableTelephoneNumbersRequest failed";
            }
        }

        if(!isSucceed){
            responseDetails.setMessage(msg);
            responseDetails.setResponseXML("<xmp>" + respXML + "</xmp>");
        }


        return isSucceed;
    }
}
