/**
 * Notebooks.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.notebook.server;

public interface Notebooks extends java.rmi.Remote {
    public void tryAuth() throws java.rmi.RemoteException;
    public void createNotebook(nevernote.notebook.server.Notebook notebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookDetailsFault;
    public nevernote.notebook.server.Notebook findNotebook(java.lang.String nameNotebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault;
    public nevernote.notebook.server.Notebook findNotebookById(int idNotebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault;
    public void deleteNotebook(java.lang.String name, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault;
    public void makeNotebookPublic(int notebookId, java.lang.String nickName) throws java.rmi.RemoteException;
    public nevernote.notebook.server.Notebook[] getAllNotebooks(int userId) throws java.rmi.RemoteException;
    public void updateNotebook(nevernote.notebook.server.Notebook notebook, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault, nevernote.notebook.server.InvalidNotebookDetailsFault;
    public void deleteAllNotebooks(int userId) throws java.rmi.RemoteException;
    public void pushNotification(java.lang.String changedByUser, nevernote.notebook.server.Notebook notebook, boolean isDeleted, boolean isChangedShared) throws java.rmi.RemoteException;
    public void deletePublicNotebook(int notebookId, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault;
}
