///*
// * $Id: PsgInterface.java,v 1.3 2015/07/31 11:09:51 xrism00 Exp $
// *
// * Copyright (c) 2015 Telia Danmark
// * All rights reserved.
// */
//package src.sms;
//
//import dk.telia.ninja.mdwc.xmlinterface.MDWCInterfaceServlet;
//import dk.telia.ninja.parlayx.*;
//import no.netcom.ninja.core.inventory.CTN;
//import org.apache.log4j.Logger;
//
//import javax.xml.namespace.QName;
//import javax.xml.ws.BindingProvider;
//import java.net.URL;
//import java.util.Map;
//
///**
// * A client for the Psg Web Service using SAAJ.
// *
// * @author Richard Marriott
// */
//public class PsgInterface {
//	public static final String ident = "$Id: PsgInterface.java,v 1.3 2015/07/31 11:09:51 xrism00 Exp $";
//	//private static Logger logger = Logger.getLogger(PsgInterface.class);
//
//	protected static PsgParlayXSoap PsgServices = null;
//	// Connection properties read from .properties file on startup
//	private static String wsdlURL = null;
//	private static String namespaceURI = null;
//	private static String endpoint_address_property = null;
//	private static String username_property = null;
//	private static String password_property = null;
//
//	/*
//	 * Constructor
//	 */
//	public PsgInterface() throws Exception {
////		if(logger.isDebugEnabled())
////			logger.debug("PsgInterface()");
//		// Properties are read by Servlet on startup.
//		wsdlURL			= MDWCInterfaceServlet.getPsgWsdlURL();
//		namespaceURI	= MDWCInterfaceServlet.getPsgNamespaceURI();
//		endpoint_address_property = MDWCInterfaceServlet.getPsgEndpoint_address_property();
//		username_property = MDWCInterfaceServlet.getUsername_property();
//		password_property = MDWCInterfaceServlet.getPassword_property();
//		if(endpoint_address_property == null || endpoint_address_property.isEmpty()) {
////			logger.error("No endpoint address property set!");
//			throw new Exception("No endpoint address property set!");
//		}
////		if(logger.isDebugEnabled()) {
////			logger.debug("wsdlURL                  : " + wsdlURL);
////			logger.debug("namespaceURI             : " + namespaceURI);
////			logger.debug("endpoint_address_property: " + endpoint_address_property);
////			logger.debug("username_property        : " + username_property);
////			logger.debug("password_property        : " + password_property);
////		}
//		try {
//			PsgParlayX service;
//			QName serviceName = new QName(namespaceURI, "psgParlayX");
//			service = new PsgParlayX(new URL(wsdlURL), serviceName);
//
//			PsgServices = service.getPort(new QName(namespaceURI, "psgParlayXSoap"), PsgParlayXSoap.class);
//
//			Map<String, Object> requestContext = ((BindingProvider) PsgServices).getRequestContext();
//			requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint_address_property);
//			// N.B. PSG does have user authentication enabled!
//			requestContext.put(BindingProvider.USERNAME_PROPERTY, username_property);
//			requestContext.put(BindingProvider.PASSWORD_PROPERTY, password_property);
//	        // Map<String, List<String>> headers = new HashMap<String, List<String>>();
//	        // headers.put("Username", Collections.singletonList(username_property));
//	        // headers.put("Password", Collections.singletonList(password_property));
//	        // requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
////			if(logger.isDebugEnabled())
////				logger.debug(requestContext.values());
//		} catch (Exception e) {
////			logger.error("Exception: " + e.getMessage(), e);
//			throw (new Exception(e));
//		}
//	}
//
//
//	/*
//	 * PSG require number without leading zero
////	 */
////	public static String stripLeadingZero(String msisdn) {
////		String retVal = null;
////		if(msisdn == null || msisdn.length() < CTN.LENGTH_VALID_FROM_USER)
////			return msisdn;
////		if(msisdn.charAt(0) == CTN.LEADING_ZERO)
////			retVal = msisdn.substring(1);
////		if(logger.isDebugEnabled())
////			logger.debug("stripLeadingZero(" + msisdn + ") returns: " + retVal);
////		return retVal;
////	}
//}
