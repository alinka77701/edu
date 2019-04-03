/**
 * PublicUsers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.user.server;

public interface PublicUsers extends java.rmi.Remote {
    public void createUser(nevernote.user.server.User user) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserDetailsFault;
    public nevernote.user.server.User findUser(java.lang.String nickName) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault;
    public nevernote.user.server.User[] getAllUsers() throws java.rmi.RemoteException;
}
