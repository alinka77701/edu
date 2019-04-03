package client.application;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class MySessionConnection {

	private  Session session;
	private  Connection connection;
	
	public Session getSession() {
		return session;
	}

	public void closeSession() throws JMSException {
		session.close();
		connection.close();
	}
	public Connection getConnection() {
		return connection;
	}

	private static MySessionConnection mySessionConnection = null; 
	
	private MySessionConnection() throws JMSException {
		 ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
         connection = connectionFactory.createConnection();
         connection.start();
		 session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public static MySessionConnection getInstance() throws JMSException  { 
        if (mySessionConnection == null) 
        	mySessionConnection = new MySessionConnection(); 
        return mySessionConnection; 
    } 
}
