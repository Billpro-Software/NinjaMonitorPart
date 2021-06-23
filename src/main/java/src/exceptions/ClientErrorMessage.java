package src.exceptions;


import src.enums.ErrorType;

import java.util.HashMap;
import java.util.Map;

public class ClientErrorMessage {

	private Map<Integer,String> mapErrorMessages;
	
	private static ClientErrorMessage singletonInstance;

	static 	{ singletonInstance = new ClientErrorMessage(); }

	private ClientErrorMessage()
	{
		this.mapErrorMessages = new HashMap<Integer,String>();

		mapErrorMessages.put(ErrorType.CUSTOMER_NAME_ALREADY_EXISTS.getInternalErrorCode(), "Customer name already taken");

		mapErrorMessages.put(ErrorType.USER_LOGIN_NAME_ALREADY_EXISTS.getInternalErrorCode(), "User already taken");
		
		mapErrorMessages.put(ErrorType.COMPANY_NAME_ALREADY_EXISTS.getInternalErrorCode(), "Company name already taken");
		
		mapErrorMessages.put(ErrorType.COUPON_TITLE_ALREADY_EXISTS.getInternalErrorCode(), "Coupon title already taken");

		mapErrorMessages.put(ErrorType.INVALID_EMAIL.getInternalErrorCode(), "Invalid email");

		mapErrorMessages.put(ErrorType.GENERAL_ERROR.getInternalErrorCode(), "Action failed");

		mapErrorMessages.put(ErrorType.DAO_CREATE_ERROR.getInternalErrorCode(), "ACTION FAILED");

		mapErrorMessages.put(ErrorType.DAO_GET_ERROR.getInternalErrorCode(), "ACTION FAILED");

		mapErrorMessages.put(ErrorType.DAO_UPDATE_ERROR.getInternalErrorCode(), "ACTION FAILED");

		mapErrorMessages.put(ErrorType.DAO_DELETE_ERROR.getInternalErrorCode(), "ACTION FAILED");

		mapErrorMessages.put(ErrorType.LOGIN_ERROR.getInternalErrorCode(), "Login failed");

		mapErrorMessages.put(ErrorType.COUPON_OUT_OF_STOCK_ERROR.getInternalErrorCode(), "Coupon is out of stock");

		mapErrorMessages.put(ErrorType.DUPLICATE_COUPON_FOR_CUSTOMER.getInternalErrorCode(), "You have already purchased this item");

		mapErrorMessages.put(ErrorType.INVALID_USER_PROFILE_ID.getInternalErrorCode(), "Invalid user profile");

		mapErrorMessages.put(ErrorType.BLO_GET_ERROR.getInternalErrorCode(), "ACTION FAILED");

		mapErrorMessages.put(ErrorType.COUPON_IS_NOT_FOR_SALE.getInternalErrorCode(), "Coupon is not for sale");
	}
	
	public static ClientErrorMessage 	getInstance()  {
		return singletonInstance;
	}

	public  String 	getErrorMessage(int errorCode)  
	{
		return mapErrorMessages.get(errorCode);
	}

}
