package nevernote.notebook.server;

import java.sql.*;
import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import nevernote.database.Database;
public class Notebooks  {
	public Notebooks() throws Exception{
	}

	public void pushNotification(String changedByUser, Notebook notebook, Boolean isDeleted, Boolean isChangedShared) throws Exception{	
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic publicTopic = session.createTopic("publicNotebooks");
		
		String content = "", readOnly="";
		
		content = "The notebook '"+notebook.getName()+ "' has been changed";
		if(!isDeleted) {
			if(notebook.getReadOnly()) {
				readOnly = "read only.";
			} else 
				readOnly = "read/write.";
			if(isChangedShared) {
				String isShared;
				if(notebook.getShared()) {
					isShared = "public";
				} else 
					isShared = "private";
				content = changedByUser+" has made the notebook '"+notebook.getName()+ "' "+isShared;
			}
		}
		else
			content = "The notebook '"+ notebook.getName() + "' has been deleted.";

		TextMessage message = session.createTextMessage(content);
		message.setStringProperty("AUTHOR", changedByUser);
		message.setBooleanProperty("ISREADONLY", notebook.getReadOnly());
		MessageProducer producer = session.createProducer(publicTopic);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		producer.send(message);
		
		System.out.println("msg has been sent to notebook Topic");

		connection.close();
		session.close();
	}
	
	public void createNotebook(Notebook notebook) throws InvalidNotebookDetailsFault, SQLException {
		Database db =Database.getInstance();
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE NAME = '"+notebook.name+"' AND USER_ID ="+notebook.userId+";");
		
		String nameNotebook="";
        while (rs.next()) {
        	nameNotebook = rs.getString("NAME");
		}
		if(nameNotebook=="") {
			db.updateQuery("INSERT INTO NOTEBOOKS VALUES (default, '"+notebook.name+ "','"
		+ notebook.type +"',"+ notebook.readOnly+",'"+notebook.created+"',"+notebook.shared+",'"+notebook.userId+"');");	
		}
		else {
			throw new InvalidNotebookDetailsFault(notebook.name);
		}
	}
	
	public Notebook findNotebook(String nameNotebook) throws InvalidNotebookFault, SQLException {
		Database db =Database.getInstance();
		Notebook notebook = null;
		
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE NAME = '"+nameNotebook+"';");
		
		if (rs.next()) {
			notebook = new Notebook();
			notebook.id=rs.getInt("ID");
        	notebook.name=rs.getString("NAME");
        	notebook.type=rs.getString("TYPE");
        	notebook.readOnly=rs.getBoolean("READONLY");
        	notebook.created=rs.getString("CREATED");
        	notebook.shared=rs.getBoolean("SHARED");
        	notebook.userId=rs.getInt("USER_ID");

            return notebook;
		} 
	    else {
			throw new InvalidNotebookFault(nameNotebook);
		}	
	}
	
	public Notebook findNotebookById(int idNotebook) throws InvalidNotebookFault, SQLException {
		Database db =Database.getInstance();
		Notebook notebook = null;
		
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+Integer.toString(idNotebook)+";");
		
		if (rs.next()) {
			notebook = new Notebook();
			notebook.id=rs.getInt("ID");
        	notebook.name=rs.getString("NAME");
        	notebook.type=rs.getString("TYPE");
        	notebook.readOnly=rs.getBoolean("READONLY");
        	notebook.created=rs.getString("CREATED");
        	notebook.shared=rs.getBoolean("SHARED");
        	notebook.userId=rs.getInt("USER_ID");

            return notebook;
		} 
	    else {
			throw new InvalidNotebookFault(Integer.toString(idNotebook));
		}	
	}
	
	public void deleteNotebook(String name, String changedByUser) throws InvalidNotebookFault {
		Database db =Database.getInstance();
		Notebook notebook = null;
		try {
			notebook=findNotebook(name);
		} catch (SQLException e1) {
			throw new InvalidNotebookFault();
		}

		try {
			db.updateQuery("DELETE FROM NOTEBOOKS WHERE NAME = '"+name+"';");
			db.updateQuery("DELETE FROM PUBLIC_NOTEBOOKS WHERE NOTEBOOK_ID = "+notebook.getId()+";");
			try {
				if(notebook.getShared()) {
					pushNotification(changedByUser,notebook,true,true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new InvalidNotebookFault();
		}
		System.out.println("Notebook " + name + " has been deleted successfully!");
	}
	
	public void tryAuth()  {
		System.out.println("Successful server auth!");
	}
	
	public void makeNotebookPublic(int notebookId, String nickName)  {
		Database db =Database.getInstance();
		try {
			Notebook notebook = findNotebookById(notebookId);
			if(notebook.getShared()) {
				db.updateQuery("INSERT INTO PUBLIC_NOTEBOOKS VALUES (default, "+notebookId+");");
				pushNotification(nickName,notebook,false,true);
			}
			else
			{
				db.updateQuery("DELETE FROM PUBLIC_NOTEBOOKS WHERE NOTEBOOK_ID = "+notebookId+";");
			    pushNotification(nickName,notebook,false,true); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidNotebookFault e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Notebook [] getAllNotebooks(int userId) throws SQLException {
		Database db =Database.getInstance();
		Notebook notebook = new Notebook();
		
	    ArrayList<Notebook> array = new ArrayList<Notebook> ();
	    
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE USER_ID = "+Integer.toString(userId)+";");
        while (rs.next()) {
        	notebook.id=rs.getInt("ID");
        	notebook.name=rs.getString("NAME");
        	notebook.type=rs.getString("TYPE");
        	notebook.readOnly=rs.getBoolean("READONLY");
        	notebook.created=rs.getString("CREATED");
        	notebook.shared=rs.getBoolean("SHARED");
        	notebook.userId=rs.getInt("USER_ID");
        	
        	array.add(notebook);
        	notebook = new Notebook();
		}

        Notebook [] notebooks = new  Notebook [array.size()];
        notebooks = array.toArray(notebooks);
		return notebooks;
	}
	
	public void updateNotebook(Notebook notebook, String changedByUser) throws InvalidNotebookFault, InvalidNotebookDetailsFault, SQLException {
		Database db =Database.getInstance();
		Notebook existingNotebook=findNotebookById(notebook.id);
		Boolean isChangedShared;
		if(existingNotebook.getShared()==notebook.getShared()) {
			isChangedShared = false;
		} else {
			isChangedShared = true;
		}
			
		try {
			if((existingNotebook.getName().equals(notebook.getName()))) {
				db.updateQuery("UPDATE NOTEBOOKS SET NAME = '" + notebook.name + "', TYPE = '"+notebook.type + "', READONLY = " + notebook.readOnly + ", CREATED = '" + notebook.created + "', SHARED = " + notebook.shared + " WHERE ID = " + notebook.id+";");
				try {
					if(existingNotebook.getShared())
						pushNotification(changedByUser,existingNotebook,false,isChangedShared);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Notebook "+ existingNotebook.name + " has been updated successfully!");
			}
			else {
				findNotebook(notebook.name);
				throw new InvalidNotebookDetailsFault(notebook.name);
			}
		}
		catch(InvalidNotebookFault e) {
			db.updateQuery("UPDATE NOTEBOOKS SET NAME = '" + notebook.name + "', TYPE = '"+notebook.type + "', READONLY = " + notebook.readOnly + ", CREATED = '" + notebook.created + "', SHARED = " + notebook.shared + " WHERE ID = " + notebook.id+";");
			try {
				if(existingNotebook.getShared())
					pushNotification(changedByUser,existingNotebook,false,isChangedShared);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("Notebook "+ existingNotebook.name + " has been updated successfully!");
		}		
	}
	
	public void deleteAllNotebooks(int userId) throws SQLException {
		Database db =Database.getInstance();
		db.updateQuery("DELETE FROM NOTEBOOKS WHERE USER_ID = "+userId+"AND TYPE!='starred';");
		System.out.println("Notebooks have been deleted successfully!");
	}
	public void deletePublicNotebook(int notebookId, String changedByUser) throws SQLException, InvalidNotebookFault {
		Database db =Database.getInstance();
		Notebook notebook = findNotebookById(notebookId);

		db.updateQuery("DELETE FROM PUBLIC_NOTEBOOKS WHERE NOTEBOOK_ID = "+notebookId+";");
		
		try {
			pushNotification(changedByUser,notebook,true,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Notebook have been deleted from public notebooks successfully!");
	}
}

