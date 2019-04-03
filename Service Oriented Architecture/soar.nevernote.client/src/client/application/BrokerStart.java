package client.application;

import org.apache.activemq.broker.BrokerService;

 public class BrokerStart {
    BrokerStart(){
	
 }

 public static void main( String [] args) throws Exception {
	// Creating a broker
	BrokerService broker = new BrokerService();
	
	broker.setUseJmx(true);	
	broker.addConnector("tcp://localhost:61616");
	broker.start();
 }
}
