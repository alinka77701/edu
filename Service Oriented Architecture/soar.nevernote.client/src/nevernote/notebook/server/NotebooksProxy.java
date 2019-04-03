package nevernote.notebook.server;

public class NotebooksProxy implements nevernote.notebook.server.Notebooks {
  private String _endpoint = null;
  private nevernote.notebook.server.Notebooks notebooks = null;
  
  public NotebooksProxy() {
    _initNotebooksProxy();
  }
  
  public NotebooksProxy(String endpoint) {
    _endpoint = endpoint;
    _initNotebooksProxy();
  }
  
  private void _initNotebooksProxy() {
    try {
      notebooks = (new nevernote.notebook.server.NotebooksServiceLocator()).getNotebooks();
      if (notebooks != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)notebooks)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)notebooks)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (notebooks != null)
      ((javax.xml.rpc.Stub)notebooks)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public nevernote.notebook.server.Notebooks getNotebooks() {
    if (notebooks == null)
      _initNotebooksProxy();
    return notebooks;
  }
  
  public void tryAuth() throws java.rmi.RemoteException{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.tryAuth();
  }
  
  public nevernote.notebook.server.Notebook findNotebook(java.lang.String nameNotebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault{
    if (notebooks == null)
      _initNotebooksProxy();
    return notebooks.findNotebook(nameNotebook);
  }
  
  public nevernote.notebook.server.Notebook findNotebookById(int idNotebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault{
    if (notebooks == null)
      _initNotebooksProxy();
    return notebooks.findNotebookById(idNotebook);
  }
  
  public nevernote.notebook.server.Notebook[] getAllNotebooks(int userId) throws java.rmi.RemoteException{
    if (notebooks == null)
      _initNotebooksProxy();
    return notebooks.getAllNotebooks(userId);
  }
  
  public void deleteAllNotebooks(int userId) throws java.rmi.RemoteException{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.deleteAllNotebooks(userId);
  }
  
  public void makeNotebookPublic(int notebookId, java.lang.String nickName) throws java.rmi.RemoteException{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.makeNotebookPublic(notebookId, nickName);
  }
  
  public void createNotebook(nevernote.notebook.server.Notebook notebook) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookDetailsFault{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.createNotebook(notebook);
  }
  
  public void deleteNotebook(java.lang.String name, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.deleteNotebook(name, changedByUser);
  }
  
  public void updateNotebook(nevernote.notebook.server.Notebook notebook, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault, nevernote.notebook.server.InvalidNotebookDetailsFault{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.updateNotebook(notebook, changedByUser);
  }
  
  public void pushNotification(java.lang.String changedByUser, nevernote.notebook.server.Notebook notebook, boolean isDeleted, boolean isChangedShared) throws java.rmi.RemoteException{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.pushNotification(changedByUser, notebook, isDeleted, isChangedShared);
  }
  
  public void deletePublicNotebook(int notebookId, java.lang.String changedByUser) throws java.rmi.RemoteException, nevernote.notebook.server.InvalidNotebookFault{
    if (notebooks == null)
      _initNotebooksProxy();
    notebooks.deletePublicNotebook(notebookId, changedByUser);
  }
  
  
}