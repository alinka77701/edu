/**
 * PublicUsersServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nevernote.user.server;

public class PublicUsersServiceLocator extends org.apache.axis.client.Service implements nevernote.user.server.PublicUsersService {

    public PublicUsersServiceLocator() {
    }


    public PublicUsersServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PublicUsersServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PublicUsers
    private java.lang.String PublicUsers_address = "http://localhost:7672/nevernote.server/services/PublicUsers";

    public java.lang.String getPublicUsersAddress() {
        return PublicUsers_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PublicUsersWSDDServiceName = "PublicUsers";

    public java.lang.String getPublicUsersWSDDServiceName() {
        return PublicUsersWSDDServiceName;
    }

    public void setPublicUsersWSDDServiceName(java.lang.String name) {
        PublicUsersWSDDServiceName = name;
    }

    public nevernote.user.server.PublicUsers getPublicUsers() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PublicUsers_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPublicUsers(endpoint);
    }

    public nevernote.user.server.PublicUsers getPublicUsers(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            nevernote.user.server.PublicUsersSoapBindingStub _stub = new nevernote.user.server.PublicUsersSoapBindingStub(portAddress, this);
            _stub.setPortName(getPublicUsersWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPublicUsersEndpointAddress(java.lang.String address) {
        PublicUsers_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (nevernote.user.server.PublicUsers.class.isAssignableFrom(serviceEndpointInterface)) {
                nevernote.user.server.PublicUsersSoapBindingStub _stub = new nevernote.user.server.PublicUsersSoapBindingStub(new java.net.URL(PublicUsers_address), this);
                _stub.setPortName(getPublicUsersWSDDServiceName());
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
        if ("PublicUsers".equals(inputPortName)) {
            return getPublicUsers();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.user.nevernote", "PublicUsersService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.user.nevernote", "PublicUsers"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PublicUsers".equals(portName)) {
            setPublicUsersEndpointAddress(address);
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
