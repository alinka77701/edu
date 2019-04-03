package nevernote.user.server;


public class User {
	int user_id;
	String name;
	String nickName;
	String email;
	String password;
	

	public User() {
		super();	
	}
	
	
	public User(int user_id, String name, String nickName, String email, String password) throws Exception {
		super();
		
		this.user_id = user_id;
		this.name = name;
		this.nickName = nickName;
		this.email = email;
		this.password = password;
	}
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

