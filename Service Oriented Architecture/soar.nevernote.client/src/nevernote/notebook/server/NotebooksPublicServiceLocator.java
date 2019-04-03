/**
 * NotebooksPublicServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.notebook.server;

public class NotebooksPublicServiceLocator extends org.apache.axis.client.Service implements nevernote.notebook.server.NotebooksPublicService {

    public NotebooksPublicServiceLocator() {
    }


    public NotebooksPublicServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NotebooksPublicServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NotebooksPublic
    private java.lang.String NotebooksPublic_address = "http://localhost:7672/nevernote.server/services/NotebooksPublic";

    public java.lang.String getNotebooksPublicAddress() {
        return NotebooksPublic_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotebooksPublicWSDDServiceName = "NotebooksPublic";

    public java.lang.String getNotebooksPublicWSDDServiceName() {
        return NotebooksPublicWSDDServiceName;
    }

    public void setNotebooksPublicWSDDServiceName(java.lang.String name) {
        NotebooksPublicWSDDServiceName = name;
    }

    public nevernote.notebook.server.NotebooksPublic getNotebooksPublic() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotebooksPublic_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotebooksPublic(endpoint);
    }

    public nevernote.notebook.server.NotebooksPublic getNotebooksPublic(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            nevernote.notebook.server.NotebooksPublicSoapBindingStub _stub = new nevernote.notebook.server.NotebooksPublicSoapBindingStub(portAddress, this);
            _stub.setPortName(getNotebooksPublicWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotebooksPublicEndpointAddress(java.lang.String address) {
        NotebooksPublic_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (nevernote.notebook.server.NotebooksPublic.class.isAssignableFrom(serviceEndpointInterface)) {
                nevernote.notebook.server.NotebooksPublicSoapBindingStub _stub = new nevernote.notebook.server.NotebooksPublicSoapBindingStub(new java.net.URL(NotebooksPublic_address), this);
                _stub.setPortName(getNotebooksPublicWSDDServiceName());
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
        if ("NotebooksPublic".equals(inputPortName)) {
            return getNotebooksPublic();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.notebook.nevernote", "NotebooksPublicService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.notebook.nevernote", "NotebooksPublic"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NotebooksPublic".equals(portName)) {
            setNotebooksPublicEndpointAddress(address);
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
