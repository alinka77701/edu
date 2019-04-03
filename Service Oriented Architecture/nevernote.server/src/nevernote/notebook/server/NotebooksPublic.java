package nevernote.notebook.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nevernote.database.Database;

public class NotebooksPublic {
	//Doesn't need a user to be authorized
		public Notebook [] getAllPublicNotebooks() throws SQLException{
			Database db =Database.getInstance();
			Notebook notebook = new Notebook();
			
		    ArrayList<Notebook> array = new ArrayList<Notebook> ();
			
			ResultSet rs =	db.query("SELECT * FROM PUBLIC_NOTEBOOKS;");
			while (rs.next()) {
	        	String notebook_id=rs.getString("NOTEBOOK_ID");
	        	ResultSet rs1 =	db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+notebook_id+";");
	        	while (rs1.next()) {
	        		notebook.id=rs1.getInt("ID");
	            	notebook.name=rs1.getString("NAME");
	            	notebook.type=rs1.getString("TYPE");
	            	notebook.readOnly=rs1.getBoolean("READONLY");
	            	notebook.created=rs1.getString("CREATED");
	            	notebook.shared=rs1.getBoolean("SHARED");
	            	notebook.userId=rs1.getInt("USER_ID");
	            	
	        		array.add(notebook);
	            	notebook = new Notebook();
	        	}
			}
	        System.out.println("Array:"+array.size());
	        
	        Notebook [] notebooks = new  Notebook [array.size()];
	       
	        notebooks = array.toArray(notebooks);
	        System.out.println("Length:"+notebooks.length);
			return notebooks;
		}
}
