package nevernote.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.RunScript;

public class Database {
	private Connection conn = null;
	private Statement statement = null;

	private static Database database = null; 
	private Database(){
		
	}
	
	public static Database getInstance() 
    { 
        if (database == null) 
        	database = new Database(); 
  
        return database; 
    } 
	
	public void connect(){
		try {
			Class.forName ("org.h2.Driver"); 
			conn = DriverManager.getConnection ("jdbc:h2:~/test", "sa","1"); 
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='NOTEBOOKS';");
			if(rs.next()==false) {
				initialize();
			}
			System.out.println("Connected!");
		}
		catch(Exception ex) {
			System.out.println("Unable to connect to the database."+ex.getMessage());
		}
	}
	
	public void initialize() throws SQLException {
		try {
			File script = new File(getClass().getResource("/script.sql").getFile());
			RunScript.execute(conn, new FileReader(script));
			System.out.println("Database initialised!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Database initialize script error");
		}	
	}
	
	public ResultSet query(String query) throws SQLException{
		 statement = conn.createStatement();
		 String sql =  query;
		 ResultSet rs = statement.executeQuery(sql);
		 return rs;
	}
	
	public void updateQuery(String query) throws SQLException{
		 statement = conn.createStatement();
		 statement.executeUpdate(query); 
	}

    public void close() throws SQLException{
    	try {
	    	statement.close(); 
	        conn.close(); 
	        System.out.println("Disconnected!");
    	}
    	catch(Exception ex) {
    		System.out.println("Error occured while disconnecting.");
    	}
	}	
}
