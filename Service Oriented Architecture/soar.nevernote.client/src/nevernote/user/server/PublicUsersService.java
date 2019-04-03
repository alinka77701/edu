/**
 * PublicUsersService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.user.server;

public interface PublicUsersService extends javax.xml.rpc.Service {
    public java.lang.String getPublicUsersAddress();

    public nevernote.user.server.PublicUsers getPublicUsers() throws javax.xml.rpc.ServiceException;

    public nevernote.user.server.PublicUsers getPublicUsers(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
