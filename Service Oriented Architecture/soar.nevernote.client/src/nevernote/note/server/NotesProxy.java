package nevernote.note.server;

public class NotesProxy implements nevernote.note.server.Notes {
  private String _endpoint = null;
  private nevernote.note.server.Notes notes = null;
  
  public NotesProxy() {
    _initNotesProxy();
  }
  
  public NotesProxy(String endpoint) {
    _endpoint = endpoint;
    _initNotesProxy();
  }
  
  private void _initNotesProxy() {
    try {
      notes = (new nevernote.note.server.NotesServiceLocator()).getNotes();
      if (notes != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)notes)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)notes)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (notes != null)
      ((javax.xml.rpc.Stub)notes)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public nevernote.note.server.Notes getNotes() {
    if (notes == null)
      _initNotesProxy();
    return notes;
  }
  
  public nevernote.note.server.Note findNote(java.lang.String titleNote, int notebookId) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault{
    if (notes == null)
      _initNotesProxy();
    return notes.findNote(titleNote, notebookId);
  }
  
  public nevernote.note.server.Note findNoteById(int idNote) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault{
    if (notes == null)
      _initNotesProxy();
    return notes.findNoteById(idNote);
  }
  
  public nevernote.note.server.Note[] getAllNotesInNotebook(int idNotebook) throws java.rmi.RemoteException{
    if (notes == null)
      _initNotesProxy();
    return notes.getAllNotesInNotebook(idNotebook);
  }
  
  public nevernote.note.server.Note[] getAllStarredNotes(int idUser) throws java.rmi.RemoteException{
    if (notes == null)
      _initNotesProxy();
    return notes.getAllStarredNotes(idUser);
  }
  
  public void deleteNoteFromNotebook(int idNote, int idNotebook, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault{
    if (notes == null)
      _initNotesProxy();
    notes.deleteNoteFromNotebook(idNote, idNotebook, changedByUser);
  }
  
  public void updateNoteInNotebook(nevernote.note.server.Note note, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault, nevernote.note.server.InvalidNoteDetailsFault{
    if (notes == null)
      _initNotesProxy();
    notes.updateNoteInNotebook(note, changedByUser);
  }
  
  public void deleteAllNotesInNotebook(int idNotebook, java.lang.String changedByUser) throws java.rmi.RemoteException{
    if (notes == null)
      _initNotesProxy();
    notes.deleteAllNotesInNotebook(idNotebook, changedByUser);
  }
  
  public void createNote(nevernote.note.server.Note note, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteDetailsFault{
    if (notes == null)
      _initNotesProxy();
    notes.createNote(note, changedByUser);
  }
  
  public void pushNotification(java.lang.String noteTitle, java.lang.String nickName, boolean isDeleted, boolean isCreated, int parentNotebookId) throws java.rmi.RemoteException{
    if (notes == null)
      _initNotesProxy();
    notes.pushNotification(noteTitle, nickName, isDeleted, isCreated, parentNotebookId);
  }
  
  
}