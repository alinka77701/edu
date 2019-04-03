package nevernote.user.server;

public class UsersProxy implements nevernote.user.server.Users {
  private String _endpoint = null;
  private nevernote.user.server.Users users = null;
  
  public UsersProxy() {
    _initUsersProxy();
  }
  
  public UsersProxy(String endpoint) {
    _endpoint = endpoint;
    _initUsersProxy();
  }
  
  private void _initUsersProxy() {
    try {
      users = (new nevernote.user.server.UsersServiceLocator()).getUsers();
      if (users != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)users)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)users)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (users != null)
      ((javax.xml.rpc.Stub)users)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public nevernote.user.server.Users getUsers() {
    if (users == null)
      _initUsersProxy();
    return users;
  }
  
  public void updateUser(nevernote.user.server.User user) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault, nevernote.user.server.InvalidUserDetailsFault{
    if (users == null)
      _initUsersProxy();
    users.updateUser(user);
  }
  
  public void deleteUser(int idUser) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault{
    if (users == null)
      _initUsersProxy();
    users.deleteUser(idUser);
  }
  
  public nevernote.user.server.User findUserById(int idUser) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault{
    if (users == null)
      _initUsersProxy();
    return users.findUserById(idUser);
  }
  
  
}