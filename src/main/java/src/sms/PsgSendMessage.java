///*
// * $Id: PsgSendMessage.java,v 1.2 2015/12/11 10:02:09 xrism00 Exp $
// *
// * Copyright (c) 2011 Telia Danmark
// * All rights reserved.
// */
//package src.sms;
//
//import dk.telia.ninja.parlayx.*;
//import no.netcom.ninja.core.exception.NinjaException;
//import org.apache.log4j.Logger;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A client for the Psg ParLayX service - SendMessage.
// *
// * @author Richard Marriott
// */
//public class PsgSendMessage extends PsgInterface {
//	public static final String ident = "$Id: PsgSendMessage.java,v 1.2 2015/12/11 10:02:09 xrism00 Exp $";
//	//private static Logger logger = Logger.getLogger(PsgSendMessage.class);
//	public static final String defaultSenderName = "Telia";
//	public String senderName = defaultSenderName;
//
//	public PsgSendMessage() throws Exception {
//		super();
////		if(logger.isDebugEnabled())
////			logger.debug("PsgSendMessage()");
//	}
//
//	public PsgSendMessage(String sender) throws Exception {
//		super();
////		if(logger.isDebugEnabled())
////			logger.debug("PsgSendMessage(" + sender + ")");
//		if(sender != null && !sender.isEmpty()) {
//			senderName=sender;
//		}
//	}
//
//	public void sendMessage(String smsNumber, String message) throws Exception {
////		if(logger.isDebugEnabled())
////			logger.debug("SendMessage(" + smsNumber + ", " + message + ")");
//		if(PsgServices == null){
////			logger.error("No connection!");
//			throw new Exception("SOAP connection not instantiated!");
//		}
//		List<String> addresses = new ArrayList<String>();
//		addresses.add(smsNumber);
//		ChargingInformation charging = new ChargingInformation();
//		charging.setAccount(smsNumber);
//		charging.setAmount(new BigDecimal(0.0));
//		PsgServices.sendSms(addresses, senderName, charging, message);
////		if (logger.isDebugEnabled())
//			logger.debug("SendMessage() returns.");
//		return;
//	}
//}
