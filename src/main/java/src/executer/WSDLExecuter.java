package src.executer;


import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class WSDLExecuter {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://no-neo-pt-01.netcom.no:7001/Equipment/Equipment_v3_0?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");

        Service service = Service.create(url, qname);

        WSDLExecuter hello = service.getPort(WSDLExecuter.class);

        //System.out.println(hello.getHelloWorldAsString("mkyong"));

    }
}