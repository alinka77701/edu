/**
 * NotebooksServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.notebook.server;

public class NotebooksServiceLocator extends org.apache.axis.client.Service implements nevernote.notebook.server.NotebooksService {

    public NotebooksServiceLocator() {
    }


    public NotebooksServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NotebooksServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Notebooks
    private java.lang.String Notebooks_address = "http://localhost:7672/nevernote.server/services/Notebooks";

    public java.lang.String getNotebooksAddress() {
        return Notebooks_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotebooksWSDDServiceName = "Notebooks";

    public java.lang.String getNotebooksWSDDServiceName() {
        return NotebooksWSDDServiceName;
    }

    public void setNotebooksWSDDServiceName(java.lang.String name) {
        NotebooksWSDDServiceName = name;
    }

    public nevernote.notebook.server.Notebooks getNotebooks() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Notebooks_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotebooks(endpoint);
    }

    public nevernote.notebook.server.Notebooks getNotebooks(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            nevernote.notebook.server.NotebooksSoapBindingStub _stub = new nevernote.notebook.server.NotebooksSoapBindingStub(portAddress, this);
            _stub.setPortName(getNotebooksWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotebooksEndpointAddress(java.lang.String address) {
        Notebooks_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (nevernote.notebook.server.Notebooks.class.isAssignableFrom(serviceEndpointInterface)) {
                nevernote.notebook.server.NotebooksSoapBindingStub _stub = new nevernote.notebook.server.NotebooksSoapBindingStub(new java.net.URL(Notebooks_address), this);
                _stub.setPortName(getNotebooksWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Notebooks".equals(inputPortName)) {
            return getNotebooks();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.notebook.nevernote", "NotebooksService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.notebook.nevernote", "Notebooks"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Notebooks".equals(portName)) {
            setNotebooksEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
