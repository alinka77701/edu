package nevernote.notebook.server;

public class NotebooksPublicProxy implements nevernote.notebook.server.NotebooksPublic {
  private String _endpoint = null;
  private nevernote.notebook.server.NotebooksPublic notebooksPublic = null;
  
  public NotebooksPublicProxy() {
    _initNotebooksPublicProxy();
  }
  
  public NotebooksPublicProxy(String endpoint) {
    _endpoint = endpoint;
    _initNotebooksPublicProxy();
  }
  
  private void _initNotebooksPublicProxy() {
    try {
      notebooksPublic = (new nevernote.notebook.server.NotebooksPublicServiceLocator()).getNotebooksPublic();
      if (notebooksPublic != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)notebooksPublic)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)notebooksPublic)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (notebooksPublic != null)
      ((javax.xml.rpc.Stub)notebooksPublic)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public nevernote.notebook.server.NotebooksPublic getNotebooksPublic() {
    if (notebooksPublic == null)
      _initNotebooksPublicProxy();
    return notebooksPublic;
  }
  
  public nevernote.notebook.server.Notebook[] getAllPublicNotebooks() throws java.rmi.RemoteException{
    if (notebooksPublic == null)
      _initNotebooksPublicProxy();
    return notebooksPublic.getAllPublicNotebooks();
  }
  
  
}