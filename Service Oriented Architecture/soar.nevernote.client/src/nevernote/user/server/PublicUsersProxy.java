package nevernote.user.server;

public class PublicUsersProxy implements nevernote.user.server.PublicUsers {
  private String _endpoint = null;
  private nevernote.user.server.PublicUsers publicUsers = null;
  
  public PublicUsersProxy() {
    _initPublicUsersProxy();
  }
  
  public PublicUsersProxy(String endpoint) {
    _endpoint = endpoint;
    _initPublicUsersProxy();
  }
  
  private void _initPublicUsersProxy() {
    try {
      publicUsers = (new nevernote.user.server.PublicUsersServiceLocator()).getPublicUsers();
      if (publicUsers != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)publicUsers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)publicUsers)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (publicUsers != null)
      ((javax.xml.rpc.Stub)publicUsers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public nevernote.user.server.PublicUsers getPublicUsers() {
    if (publicUsers == null)
      _initPublicUsersProxy();
    return publicUsers;
  }
  
  public void createUser(nevernote.user.server.User user) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserDetailsFault{
    if (publicUsers == null)
      _initPublicUsersProxy();
    publicUsers.createUser(user);
  }
  
  public nevernote.user.server.User findUser(java.lang.String nickName) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault{
    if (publicUsers == null)
      _initPublicUsersProxy();
    return publicUsers.findUser(nickName);
  }
  
  public nevernote.user.server.User[] getAllUsers() throws java.rmi.RemoteException{
    if (publicUsers == null)
      _initPublicUsersProxy();
    return publicUsers.getAllUsers();
  }
  
  
}