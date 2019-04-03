package nevernote.user.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nevernote.database.Database;

public class PublicUsers {
	//Doesn't need a user to be authorized
		public User [] getAllUsers() throws SQLException{
			Database db =  Database.getInstance();
			User user = new User();
			
		    ArrayList<User> array = new ArrayList<User> ();
			
			ResultSet rs =	db.query("SELECT * FROM USERS;");
			while (rs.next()) {
				user.user_id=rs.getInt("ID_USER");
				user.name=rs.getString("NAME");
				user.nickName=rs.getString("NICKNAME");
				user.email=rs.getString("EMAIL");
				user.password=rs.getString("PASSWORD");
	        	
	        	array.add(user);
	        	user = new User();
			}

	        User [] users = new  User [array.size()];
	        users = array.toArray(users);
			return users;
		}
		
	   //Doesn't need a user to be authorized
		public void createUser(User user) throws InvalidUserDetailsFault, Exception {
			Database db =  Database.getInstance();
			
			db.connect();
			System.out.println("DB: "+db);
			EmailValidator emailValidator = new EmailValidator();
			boolean emailIsValid = emailValidator.validateEmail(user.email);
	
			ResultSet rs = db.query("SELECT * FROM USERS WHERE NICKNAME = '"+ user.nickName+"';");
			String userInDb =null;
//			User userInDb=new User();
//			userInDb=findUser(user.nickName);
			while (rs.next()) {
				userInDb = rs.getString("NICKNAME");
			}
			if(userInDb==null) {
		        if(user.nickName.length()>16) {
		        	throw new InvalidUserDetailsFault(user.nickName,false,true,false,false);
		        }
		        else if(!emailIsValid) {
		        	throw new InvalidUserDetailsFault(user.nickName,false,false,true,false);
		        }
		        else if(user.password.length()<7) {
		        	throw new InvalidUserDetailsFault(user.nickName,true,false,false,false);
		        }
				db.updateQuery("INSERT INTO USERS VALUES (default, '"+user.name + "','"+ user.nickName + "','" + user.email+"','"+user.password+"');");
				System.out.println("User " + user.nickName + " has been created successfully!");
			}
			else {
				throw new InvalidUserDetailsFault(user.nickName,false,false,false,true);
			}
		}
		
		//Doesn't need a user to be authorized
		public User findUser(String nickName) throws InvalidUserFault, Exception {
			Database db =  Database.getInstance();
			ResultSet rs = db.query("SELECT * FROM USERS WHERE NICKNAME = '"+ nickName+"';");
			if (rs.next()) {
				User user = new User();
				user.user_id=rs.getInt("ID_USER");
				user.name=rs.getString("NAME");
				user.nickName=rs.getString("NICKNAME");
				user.email=rs.getString("EMAIL");
				user.password=rs.getString("PASSWORD");
		        return user;
			}
			else {
				throw new InvalidUserFault(nickName);
			}	
		}
}
