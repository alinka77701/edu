package nevernote.user.server;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nevernote.database.Database;
import nevernote.notebook.server.InvalidNotebookDetailsFault;
import nevernote.user.server.InvalidUserFault;
import nevernote.user.server.User;

public class Users {
	public User findUserById(int idUser) throws InvalidUserFault, SQLException {
		Database db =  Database.getInstance();
		ResultSet rs = db.query("SELECT * FROM USERS WHERE ID_USER = "+ Integer.toString(idUser)+";");

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
			throw new InvalidUserFault(Integer.toString(idUser));
		}	
	}
	
	public void deleteUser(int idUser) throws InvalidUserFault, Exception {
		Database db =  Database.getInstance();
		User user = findUserById(idUser);
		String sql =  "DELETE USERS WHERE ID_USER = "+idUser+";";
		db.updateQuery(sql);
		System.out.println("User " + user.getName() + " has been deleted successfully!");
	}
	
	public void updateUser(User user) throws InvalidUserDetailsFault,InvalidUserFault, SQLException {
		Database db =  Database.getInstance();
		User existingUser = findUserById(user.user_id);
		EmailValidator emailValidator = new EmailValidator();
		boolean emailIsValid = emailValidator.validateEmail(user.email);
	
        if(user.nickName.length()>16) {
        	throw new InvalidUserDetailsFault(user.nickName,false,true,false,false);
        }
        else if(!emailIsValid) {
        	throw new InvalidUserDetailsFault(user.nickName,false,false,true,false);
        }
        else if(user.password.length()<7) {
        	throw new InvalidUserDetailsFault(user.nickName,true,false,false,false);
        }
       
        String sql =  "UPDATE USERS SET NAME = '" +
				 user.name + "', NICKNAME = '"+user.nickName + "', EMAIL = '" + user.email +
				 "', PASSWORD = '" + user.password + "' WHERE ID_USER = " + user.user_id;
		try {
			if(existingUser.getNickName().equals(user.getNickName())) {
				 db.updateQuery(sql);
				 System.out.println("User "+ user.nickName + " has been updated successfully!");
			}
			else {
				findUserById(user.getUserId());
				throw new InvalidUserDetailsFault(user.getNickName(),false,false,false,true);
			}
		}
		catch(InvalidUserFault e) {
			throw new InvalidUserFault();
		}
       
	} 
}
