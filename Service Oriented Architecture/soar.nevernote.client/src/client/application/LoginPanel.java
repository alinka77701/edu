package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Session;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nevernote.notebook.server.InvalidNotebookDetailsFault;
import nevernote.notebook.server.Notebook;
import nevernote.user.server.InvalidUserDetailsFault;
import nevernote.user.server.User;
import client.application.NotebookMenu;

public class LoginPanel extends JPanel {
	public static User currentUser;
	public static NeverNoteApi servers;
	public static JFrame parentFrame;
	public LoginPanel(NeverNoteApi serversApi, JFrame parent) {
		currentUser = new User();
		servers = serversApi;
		parentFrame = parent;	

		this.setLayout(null);
		this.setBackground(Color.WHITE);	
		init();
	}

	public void init() {
		JButton btnLogin = new JButton("Log In");
		JButton btnRegister = new JButton("Register");  
		btnLogin.setBounds(360, 250, 180, 50);
		btnRegister.setBounds(360, 320, 180, 50);
		add(btnLogin);
		add(btnRegister);
		
		btnLogin.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) { 	
	            LoginDialog loginDlg = new LoginDialog(parentFrame);
	            loginDlg.setVisible(true); 
	            
	            // If a user has filled login and password fields
	            if(loginDlg.isSucceeded()) {
	            	
	            	 currentUser.setNickName(loginDlg.getNickName());
	        		 currentUser.setPassword(loginDlg.getPassword());
					 try {
						 // Authenticating a user
						  System.out.println("Authenticating a user...");
						  authenticateUser();
						  setVisible(false);
						  currentUser = servers.findUser(currentUser.getNickName());		
	  
						  NotebookMenu notebookPanel = new NotebookMenu(servers,currentUser,parentFrame);
						  parentFrame.add(notebookPanel, BorderLayout.CENTER);
						  // Showing dialog successful authentication
						  JOptionPane.showMessageDialog(parentFrame, "Successful authentication!");
					     } 
					     catch (Exception e1){
						    try {				    	
						    	// If login info is not valid, clear a credentials file
						    	PrintWriter writer = new PrintWriter("credentials"+currentUser.getNickName()+".txt");
						    	writer.print("");
						    	writer.close();
							} catch (Exception e2) {
								e2.printStackTrace();
						    }  
							 // Showing invalid user's information dialog 
							JOptionPane.showMessageDialog(parentFrame, "Invalid email/password or data base connection cannot be established.");
						    e1.printStackTrace();
					     }
	              }
	        }
        });
        
        btnRegister.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        RegisterDialog registerDlg = new RegisterDialog("Register", parentFrame);
                        registerDlg.setVisible(true); 
                        
                        // If a user has filled fields with valid information
                        if(registerDlg.isSucceeded()){
                        	currentUser.setEmail(registerDlg.getEmail());
                        	currentUser.setPassword(registerDlg.getPassword());
                        	currentUser.setName(registerDlg.getName());
                        	currentUser.setNickName(registerDlg.getNickName());
	                		 System.out.println(currentUser.getNickName());
							 try {
								  //Register a new user
								  System.out.println("Register a user...");
								  servers.createUser(currentUser);		
								  JOptionPane.showMessageDialog(LoginPanel.this, "Successful registration!");
								  
								  System.out.println("Authenticating a user after registration...");
								  currentUser = servers.findUser(currentUser.getNickName());
								  saveUserCredentials();
								  authenticateUser();
								  createStarredNotebook();
								//Changing Login panel to a user's Notebooks panel
								  setVisible(false);	 
								  
								  System.out.println(currentUser.getNickName());
								  NotebookMenu notebookPanel = new NotebookMenu(servers,currentUser,parentFrame);
								  parentFrame.add(notebookPanel, BorderLayout.CENTER);
								 
							} catch (InvalidUserDetailsFault fault) {
								JOptionPane.showMessageDialog(registerDlg, "Invalid details. "+fault.getMessage1());	
								fault.printStackTrace();	   
					        } catch (Exception e1) {
								e1.printStackTrace();
							} 	 
                       }
                    }
                });
	      }
	
	 //Try authenticate a user
	public static void authenticateUser() throws Exception { 
		servers.setUserName(currentUser.getNickName());
		servers.tryAuth();
		System.out.println("authenticated");
	}  
	
	public static void saveUserCredentials() throws Exception { 
		String str = currentUser.getNickName()+" "+currentUser.getPassword();
		try {
			FileOutputStream outputStream;
			outputStream = new FileOutputStream("credentials"+currentUser.getNickName()+".txt");
			byte[] strToBytes = str.getBytes();
	        outputStream.write(strToBytes);
	        outputStream.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void createStarredNotebook() { 
		System.out.println("Creating a starred notebook....");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();        
		String createdDate = df.format(today);
		
		Notebook notebook = new Notebook(createdDate,0,"Starred"+currentUser.getNickName(),false,false,"starred",currentUser.getUserId());
		try {
			servers.createNotebook(notebook);
			System.out.println("A starred notebook created!");

		} catch (InvalidNotebookDetailsFault fault) {
			JOptionPane.showMessageDialog( null, "Error ocurred while creating the starred notebook. Error: "+fault.getMessage1());	
			fault.printStackTrace();	   	   
        }catch (Exception e) {
			JOptionPane.showMessageDialog( null, "Error ocurred while creating the starred notebook. Error ex: "+e.getMessage());	
			e.printStackTrace();
		}
	}
}

