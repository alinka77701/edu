/**
 * Notes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.note.server;

public interface Notes extends java.rmi.Remote {
    public void createNote(nevernote.note.server.Note note, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteDetailsFault;
    public nevernote.note.server.Note findNote(java.lang.String titleNote, int notebookId) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault;
    public void pushNotification(java.lang.String noteTitle, java.lang.String nickName, boolean isDeleted, boolean isCreated, int parentNotebookId) throws java.rmi.RemoteException;
    public nevernote.note.server.Note findNoteById(int idNote) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault;
    public void deleteNoteFromNotebook(int idNote, int idNotebook, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault;
    public void updateNoteInNotebook(nevernote.note.server.Note note, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.note.server.InvalidNoteFault, nevernote.note.server.InvalidNoteDetailsFault;
    public nevernote.note.server.Note[] getAllNotesInNotebook(int idNotebook) throws java.rmi.RemoteException;
    public void deleteAllNotesInNotebook(int idNotebook, java.lang.String changedByUser) throws java.rmi.RemoteException;
    public nevernote.note.server.Note[] getAllStarredNotes(int idUser) throws java.rmi.RemoteException;
}
