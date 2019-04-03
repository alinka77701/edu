package nevernote.note.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import nevernote.database.Database;
import nevernote.note.server.InvalidNoteFault;
import nevernote.note.server.Note;

public class Notes {
	
	public Notes() throws Exception{}
	
	public void createNote(Note note,String changedByUser) throws SQLException, InvalidNoteDetailsFault, JMSException {
		Database db = Database.getInstance();
		ResultSet rs = db.query("SELECT * FROM NOTES WHERE TITLE = '"+note.title+"' AND ID_USER ="+note.userId+";");
		
		String nameNote="";
        while (rs.next()) {
        	nameNote = rs.getString("TITLE");
		}
		if(nameNote=="") {
			db.updateQuery("INSERT INTO NOTES " +
					 "VALUES (default, '"+note.title + "','"+ note.description +
					 "'," + note.starred+",'"+note.created+"',"+note.notebookId+","+note.userId+");");		}
		else {
			throw new InvalidNoteDetailsFault(note.title);
		}

		rs = db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+note.getNoteBookId()+";");
		Boolean isNotebookShared=null;
		while (rs.next()) {
			 isNotebookShared=rs.getBoolean("SHARED");
		}
		if(isNotebookShared) {
			pushNotification(note.getTitle(),changedByUser,false,true,note.getNoteBookId());
		}
		System.out.println("Note " + note.title + " has been created successfully!");
	}
	
	public void pushNotification(String noteTitle, String nickName, Boolean isDeleted, Boolean isCreated, int parentNotebookId) throws JMSException {	
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic publicTopic = session.createTopic("publicNotes");
		

		String content ="";
		if(isDeleted) {
			 content = "Note '"+noteTitle+"' has been deleted ";
		} else if (isCreated){
			 content = "Note '"+noteTitle+"' has been created ";
		}
		else {
			 content = "Note '"+ noteTitle + "' has been changed ";
		}
		if(noteTitle==" ")
             content = "All notes have been deleted ";
		TextMessage message;
		try {
			message = session.createTextMessage(content);
			message.setStringProperty("MODIFIED", nickName);
			message.setIntProperty("PARENT_NOTEBOOK_ID", parentNotebookId);
			session.createProducer(publicTopic).send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("msg has been sent to Notes Topic ");
		connection.close();
		session.close();
	}
	
	public Note findNote(String titleNote, int notebookId) throws InvalidNoteFault, SQLException {
		Database db = Database.getInstance();
		ResultSet rs = db.query("SELECT * FROM NOTES WHERE TITLE = '"
		+titleNote+ "' AND ID_NOTEBOOK = "+Integer.toString(notebookId)+";");
		
		if (rs.next()) {
			Note note = new Note();
			note.id=rs.getInt("ID_NOTE");
			note.title=rs.getString("TITLE");
			note.description=rs.getString("DESCRIPTION");
			note.starred=rs.getBoolean("STARRED");
			note.created=rs.getString("CREATED");
			note.notebookId=rs.getInt("ID_NOTEBOOK");
			note.userId=rs.getInt("ID_USER");

            return note;
		} 
	    else {
			throw new InvalidNoteFault(titleNote);
		}	
	}
	
	public Note findNoteById(int idNote) throws InvalidNoteFault, SQLException {
		Database db = Database.getInstance();
		ResultSet rs = db.query("SELECT * FROM NOTES WHERE ID_NOTE = "+ Integer.toString(idNote)+";");
		
		if (rs.next()) {
			Note note = new Note();
			note.id=rs.getInt("ID_NOTE");
			note.title=rs.getString("TITLE");
			note.description=rs.getString("DESCRIPTION");
			note.starred=rs.getBoolean("STARRED");
			note.created=rs.getString("CREATED");
			note.notebookId=rs.getInt("ID_NOTEBOOK");
			note.userId=rs.getInt("ID_USER");
            return note;
		} 
	    else {
			throw new InvalidNoteFault(Integer.toString(idNote));
		}	
	}
	
	public void deleteNoteFromNotebook(int idNote, int idNotebook, String changedByUser) throws InvalidNoteFault, SQLException, JMSException {
		Database db = Database.getInstance();
		Note note = findNoteById(idNote);

		String sql =  "DELETE NOTES WHERE ID_NOTE = "+idNote+" AND ID_NOTEBOOK = "+idNotebook+";";
		db.updateQuery(sql);
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+idNotebook+";");
		Boolean isNotebookShared=null;
		while (rs.next()) {
			 isNotebookShared=rs.getBoolean("SHARED");
		}
		if(isNotebookShared) {
			pushNotification(note.getTitle(),changedByUser,true,false,note.getNoteBookId());
		}
		
		System.out.println("Note " + idNote + " has been deleted successfully!");
	}
	
	public Note [] getAllNotesInNotebook(int idNotebook) throws SQLException {
		Database db = Database.getInstance();
		Note note = new Note();
		
	    ArrayList<Note> array = new ArrayList<Note> ();
	    
		ResultSet rs = db.query("SELECT * FROM NOTES WHERE ID_NOTEBOOK = "+idNotebook+";");
		
        while (rs.next()) {
        	note.id=rs.getInt("ID_NOTE");
			note.title=rs.getString("TITLE");
			note.description=rs.getString("DESCRIPTION");
			note.starred=rs.getBoolean("STARRED");
			note.created=rs.getString("CREATED");
			note.notebookId=rs.getInt("ID_NOTEBOOK");
			note.userId=rs.getInt("ID_USER");
        	array.add(note);
        	note = new Note();
		}
        
        Note [] notes = null;
        notes =new  Note [array.size()];
        notes = array.toArray(notes);

        return notes;
	}
	
	public void updateNoteInNotebook(Note note, String changedByUser) throws InvalidNoteFault, InvalidNoteDetailsFault, SQLException {
		Database db = Database.getInstance();
		Note oldNote=findNoteById(note.id);
		String sql =  "UPDATE NOTES SET TITLE = '" +
				 note.title + "', DESCRIPTION = '"+note.description + "', STARRED = " + note.starred +
				 ", CREATED = '" + note.created + "', ID_NOTEBOOK = " +note.notebookId+", ID_USER = "+note.userId+" WHERE ID_NOTE = " + note.id;
		db.updateQuery(sql);
		try {
			ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+note.getNoteBookId()+";");
			Boolean isNotebookShared=null;
			while (rs.next()) {
				 isNotebookShared=rs.getBoolean("SHARED");
			}
			if(isNotebookShared) {
				pushNotification(oldNote.getTitle(),changedByUser,false,false,oldNote.getNoteBookId());
			}
		} catch (Exception e) {
			System.out.println("Error occured while updating a note !"+note.getTitle());
			e.printStackTrace();
		}
		System.out.println("Note "+ note.title + " has been updated successfully!");
	}
	
	public void deleteAllNotesInNotebook(int idNotebook, String changedByUser) throws SQLException, JMSException {
		Database db = Database.getInstance();
		String sql =  "DELETE NOTES WHERE ID_NOTEBOOK = "+idNotebook+";";
		db.updateQuery(sql);
		ResultSet rs = db.query("SELECT * FROM NOTEBOOKS WHERE ID = "+idNotebook+";");
		Boolean isNotebookShared=null;
		while (rs.next()) {
			 isNotebookShared=rs.getBoolean("SHARED");
		}
		//String noteTitle, String nickName, Boolean isDeleted, Boolean isCreated, int parentNotebookId) {	

		if(isNotebookShared) {
			pushNotification(" ",changedByUser,true,false,idNotebook);
		}
		
		System.out.println("Notes have been deleted successfully!");
	}
	public Note [] getAllStarredNotes(int idUser) throws SQLException {
		Database db = Database.getInstance();
		Note note = new Note();
		
	    ArrayList<Note> array = new ArrayList<Note> ();
	    
		ResultSet rs = db.query("SELECT * FROM NOTES WHERE STARRED=true AND ID_USER = "+idUser+";");
		
        while (rs.next()) {
        	note.id=rs.getInt("ID_NOTE");
			note.title=rs.getString("TITLE");
			note.description=rs.getString("DESCRIPTION");
			note.starred=rs.getBoolean("STARRED");
			note.created=rs.getString("CREATED");
			note.notebookId=rs.getInt("ID_NOTEBOOK");
			note.userId=rs.getInt("ID_USER");
        	array.add(note);
        	note = new Note();
		}
        
        Note [] notes = new  Note [array.size()];
        notes = array.toArray(notes);

        return notes;
	}
}
