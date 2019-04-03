package nevernote.user.server;


public class InvalidUserDetailsFault extends Exception {
	protected String nickName;
	protected Boolean uniqueNickName;
	protected Boolean invalidEmail;
	protected Boolean weakPassword;
	protected Boolean nickNameLong;
	
	public InvalidUserDetailsFault() {}
	
	public InvalidUserDetailsFault(String nickName,Boolean weakPassword, Boolean nickNameLong,Boolean invalidEmail,Boolean uniqueNickName) {
		this.weakPassword = weakPassword;
		this.uniqueNickName = uniqueNickName;
		this.invalidEmail= invalidEmail;
		this.nickNameLong= nickNameLong;
		this.nickName = nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName =nickName;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	@Override
	public String getMessage() {
		if(this.uniqueNickName)
			return "User with the nick name '" + nickName + "' already exist! User should have a unique nick name. ";
		else if(this.weakPassword)
			return "The password is invalid.";
		else if(this.invalidEmail)
			return "The email is invalid.";
		else if(this.nickNameLong)
			return "The length of the nick name is " + this.nickName.length()
			+ ". The nick name should be no more than 16 characters long.";
		else return "The error occured.";
	}
}
