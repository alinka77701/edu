/**
 * NotebooksService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.notebook.server;

public interface NotebooksService extends javax.xml.rpc.Service {
    public java.lang.String getNotebooksAddress();

    public nevernote.notebook.server.Notebooks getNotebooks() throws javax.xml.rpc.ServiceException;

    public nevernote.notebook.server.Notebooks getNotebooks(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
