package src.enums;

public enum UserProfileType {
	
	ADMIN(1),
	REGULAR(2),
	CUSTOMER(3);
	
	private int userProfileId;
	UserProfileType(int userProfileId){
		this.userProfileId = userProfileId;
	}
	
	public int getUserProfileId() {
		return userProfileId;
	}
}	
