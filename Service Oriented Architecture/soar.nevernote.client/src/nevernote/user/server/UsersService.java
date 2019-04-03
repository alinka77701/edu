/**
 * UsersService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.user.server;

public interface UsersService extends javax.xml.rpc.Service {
    public java.lang.String getUsersAddress();

    public nevernote.user.server.Users getUsers() throws javax.xml.rpc.ServiceException;

    public nevernote.user.server.Users getUsers(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
