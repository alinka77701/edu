package nevernote.notebook.server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.w3c.dom.Element;

import nevernote.database.Database;


public class PasswordHandler implements CallbackHandler {
	private Database db = Database.getInstance();
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException{
		    db.connect();
			for (Callback callback : callbacks) {
				String password = null;
				WSPasswordCallback wspc = (WSPasswordCallback) callback;
				String identifier = wspc.getIdentifier();   
				String sql = "SELECT PASSWORD FROM USERS WHERE NICKNAME = '"+identifier+"';";
			    try {
					ResultSet rs = db.query(sql);
		    		if (rs.next()) {
		    			password=rs.getString("PASSWORD");
		    			wspc.setPassword(password); 
		                System.out.println("Access server approved.");         
		    		}
		    		else {
		    			System.out.println("Access server denied."); 
		    		}
				} catch (SQLException e) {
					System.out.println("Access server denied.");
					e.printStackTrace();
				}
			}
		
	}

}
