package src.enums;

public enum ErrorType {
	
	 CUSTOMER_NAME_ALREADY_EXISTS	( 100)
	 ,USER_LOGIN_NAME_ALREADY_EXISTS( 110)
	 ,COMPANY_NAME_ALREADY_EXISTS   ( 120)
	 ,COUPON_TITLE_ALREADY_EXISTS	( 200)
	,INVALID_EMAIL					( 300)
	,GENERAL_ERROR					( 400)
	
	,DAO_CREATE_ERROR				( 500)
	,DAO_GET_ERROR					( 600)
	,DAO_UPDATE_ERROR				( 700)
	,DAO_DELETE_ERROR				( 800)

	,LOGIN_ERROR					( 900)
	,COUPON_OUT_OF_STOCK_ERROR		(1000)
	,DUPLICATE_COUPON_FOR_CUSTOMER	(1100)
	,INVALID_USER_PROFILE_ID		(1200)
	,BLO_GET_ERROR					(1300)
	,COUPON_IS_NOT_FOR_SALE			(1400)

	,SESSION_EXPIRED				(9999)

	;
	
	private int internalErrorCode;
	
	ErrorType(int internalErrorCode){
		this.internalErrorCode = internalErrorCode;
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;
	}
	
}	
