package nevernote.user.server;


public class InvalidUserFault extends Exception {
    protected String name;
	
	public InvalidUserFault() {}
	
	public InvalidUserFault(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String getMessage() {
		return "User " + name + " doesn't exist!";
	}
}
