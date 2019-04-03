package client.application;

import java.net.URL;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;

import nevernote.notebook.server.Notebook;
import nevernote.notebook.server.Notebooks;
import nevernote.notebook.server.NotebooksPublic;
import nevernote.notebook.server.NotebooksPublicServiceLocator;
import nevernote.notebook.server.NotebooksServiceLocator;
import nevernote.notebook.server.NotebooksSoapBindingStub;
import nevernote.note.server.Note;
import nevernote.note.server.Notes;
import nevernote.note.server.NotesServiceLocator;
import nevernote.note.server.NotesSoapBindingStub;
import nevernote.user.server.PublicUsers;
import nevernote.user.server.PublicUsersServiceLocator;
import nevernote.user.server.User;
import nevernote.user.server.Users;
import nevernote.user.server.UsersServiceLocator;
import nevernote.user.server.UsersSoapBindingStub;


public class NeverNoteApi {
 
	public static PublicUsers publicUsersServer;
	public static NotebooksPublic publicNotebooksServer;
	
	public static NotebooksSoapBindingStub privateNotebooksServer;
	public static NotesSoapBindingStub privateNotesServer;
	public static UsersSoapBindingStub privateUsersServer;
	
	NeverNoteApi() throws Exception{
		 System.out.println("Connecting to web-services...");
		 EngineConfiguration configNotebooks = new FileProvider(NeverNoteApi.class.getResourceAsStream("secure-notebook-client.wsdd"));
		 EngineConfiguration configNotes = new FileProvider(NeverNoteApi.class.getResourceAsStream("secure-notebook-client.wsdd"));
		 EngineConfiguration configUsers = new FileProvider(NeverNoteApi.class.getResourceAsStream("secure-notebook-client.wsdd"));

		 privateNotebooksServer = (NotebooksSoapBindingStub) new NotebooksServiceLocator(configNotebooks).getNotebooks();
		 privateNotesServer = (NotesSoapBindingStub) new NotesServiceLocator(configNotes).getNotes();
		 privateUsersServer = (UsersSoapBindingStub) new UsersServiceLocator(configUsers).getUsers();

		 publicUsersServer=new PublicUsersServiceLocator().getPublicUsers(new URL("http://localhost:8081/nevernote.server/services/PublicUsers"));
		 publicNotebooksServer=new NotebooksPublicServiceLocator().getNotebooksPublic(new URL("http://localhost:8081/nevernote.server/services/NotebooksPublic"));	
	}
	
	public void setUserName(String name) {
		privateNotebooksServer.setUsername(name);
		privateNotesServer.setUsername(name);
		privateUsersServer.setUsername(name);
	}
	
   /*==============================================================
                            NOTEBOOKS API
    ==============================================================*/

   public void createNotebook(Notebook notebook) throws Exception  {	
	   privateNotebooksServer.createNotebook(notebook);
   }
   public void deleteNotebook(String name, String changedByUser) throws Exception  {
	   privateNotebooksServer.deleteNotebook(name,changedByUser);
   }
   
   public Notebook findNotebook(String name) throws Exception  {
       return privateNotebooksServer.findNotebook(name);
   }
   
   public Notebook findNotebookById(int id) throws Exception  {
       return privateNotebooksServer.findNotebookById(id);
   }
   
   public Notebook [] getAllNotebooks(int userId) throws Exception  {
	   return privateNotebooksServer.getAllNotebooks(userId);
   }
   
   public void updateNotebook(Notebook notebook, String changedByUser) throws Exception  {
	   privateNotebooksServer.updateNotebook(notebook,changedByUser);
   }
  
   public void deleteAllNotebooks(int userId) throws Exception  {
	  privateNotebooksServer.deleteAllNotebooks(userId);
   }

   public void makeNotebookPublic(int notebookId,String nickName) throws Exception  {
	  privateNotebooksServer.makeNotebookPublic(notebookId,nickName);
   }
  
   public void deletePublicNotebook(int notebookId, String changedByUser) throws Exception  {
	  privateNotebooksServer.deletePublicNotebook(notebookId,changedByUser);
   }
  
   public void tryAuth() throws Exception  {
	  privateNotebooksServer.tryAuth();
   }
  /*==============================================================
                    PUBLIC NOTEBOOKS API
  ==============================================================*/

  public Notebook [] getAllPublicNotebooks() throws Exception  {
		 return publicNotebooksServer.getAllPublicNotebooks();
  }
  /*==============================================================
                          NOTES API
  ==============================================================*/
  public void createNote(Note note, String changedByUser) throws Exception  {
	  privateNotesServer.createNote(note,changedByUser);
  }
  
  public Note findNote(String title, int notebookId) throws Exception  {	
	  return privateNotesServer.findNote(title, notebookId);
  }
  
  public Note findNoteById(int noteId) throws Exception  {	
	  return privateNotesServer.findNoteById(noteId);
  }
  
  public void deleteNoteFromNotebook(int idNote, int notebookId, String changedByUser) throws Exception  {
	  privateNotesServer.deleteNoteFromNotebook(idNote, notebookId,changedByUser);
  }
  
  public Note [] getAllNotesInNotebook(int notebookId) throws Exception  {	
		return privateNotesServer.getAllNotesInNotebook(notebookId);
  }
  
  public Note [] getAllStarredNotes(int userId) throws Exception  {	
		return privateNotesServer.getAllStarredNotes(userId);
  
  }
  public void updateNote(Note note, String changedByUser) throws Exception  {
	  privateNotesServer.updateNoteInNotebook(note,changedByUser);
  }
  
  public void deleteAllNotesInNotebook(int idNotebook, String changedByUser) throws Exception  {
	  privateNotesServer.deleteAllNotesInNotebook(idNotebook,changedByUser);
  }
  
  /*==============================================================
                             USERS API
  ==============================================================*/
  
  public void deleteUser(int idUser) throws Exception  {
	  privateUsersServer.deleteUser(idUser);
  }
  
  public void updateUser(User user) throws Exception  {
	  privateUsersServer.updateUser(user);
  }

  public User findUserById(int idUser) throws Exception  {
	 return privateUsersServer.findUserById(idUser);
  }
  /*==============================================================
                          PUBLIC USERS API
  ==============================================================*/
  public void createUser(User user) throws Exception  {
	  publicUsersServer.createUser(user);
  }
  
  public User findUser(String nickName) throws Exception  {
	  return publicUsersServer.findUser(nickName);
  }
  
  public User [] getAllUsers() throws Exception  {
	  return publicUsersServer.getAllUsers();
  }  
}
