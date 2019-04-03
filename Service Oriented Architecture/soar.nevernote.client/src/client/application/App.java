package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nevernote.user.server.User;

public class App {
	
	private JFrame frame;
	private JPanel loginPanel, headerPanel;

	private static User currentUser;
	
	public static NeverNoteApi servers;
	
	private static MySessionConnection mySessionConnection;
	
	public static void main (String [] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	 
					 // Connecting to web-services
					servers = new NeverNoteApi();		
					// Initializing a window application
					 System.out.println("Welcome to NeverNote!");
					 App window = new App();
					 window.frame.setVisible(true);
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public App() throws Exception {
		initialize();
	}
	
	public void initialize() throws Exception {
		currentUser = new User();
		// Creating main frame 
		frame = new JFrame("NeverNote");
		frame.setBounds(0, 0, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.WHITE);
      
        // Setting close window application listener
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            	  try {
            		// Clean up
            		System.out.println("Close...");
					 try {
							mySessionConnection = MySessionConnection.getInstance();
						} catch (JMSException e) {
							e.printStackTrace();
					  }
					 //servers.closeDatabase();
					 mySessionConnection.closeSession();	
				  } catch (Exception e) {
					e.printStackTrace();
				  }
            	  System.out.println("Bye :)");
                  System.exit(0);
            }
        });
     
		loginPanel = new LoginPanel(servers, frame);
		frame.add(loginPanel, BorderLayout.CENTER);
		
		loginPanel.addComponentListener(new ComponentListener() {
	        public void componentHidden(ComponentEvent ce) {
				try {
					currentUser = servers.findUser(((LoginPanel) loginPanel).currentUser.getNickName());		
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
				
				headerPanel = new HeaderPanel(servers,currentUser, frame);
				frame.add(headerPanel, BorderLayout.NORTH);
	        }
	        public void componentShown(ComponentEvent ce) { }
	        public void componentMoved(ComponentEvent ce) {}
	        public void componentResized(ComponentEvent ce) {}
	    });
	}	
}
	
	

