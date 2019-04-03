/**
 * Users.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.user.server;

public interface Users extends java.rmi.Remote {
    public void updateUser(nevernote.user.server.User user) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault, nevernote.user.server.InvalidUserDetailsFault;
    public void deleteUser(int idUser) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault;
    public nevernote.user.server.User findUserById(int idUser) throws java.rmi.RemoteException, nevernote.user.server.InvalidUserFault;
}
