package client.application;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nevernote.user.server.User;
import nevernote.user.server.Users;

public class HeaderPanel extends JPanel {
	public static NeverNoteApi servers;
	public HeaderPanel(NeverNoteApi serversApi, User currentUser, JFrame parent) {
	  super();
	  servers = serversApi;
	  this.setLayout(new BorderLayout());
      this.setBackground(Color.WHITE);
      
      JMenuBar menuBar = new JMenuBar();
      menuBar.setBackground(Color.WHITE);
      
      JMenu fileMenu = new JMenu("Menu");
      fileMenu.setBackground(Color.WHITE);
     
      
      JMenuItem changeInfoItem = new JMenuItem("Edit profile");
      changeInfoItem.setBackground(Color.WHITE);
      fileMenu.add(changeInfoItem);
       
      fileMenu.addSeparator();
       
      JMenuItem exitItem = new JMenuItem("Log out");
      exitItem.setBackground(Color.WHITE);
      fileMenu.add(exitItem);
       
      exitItem.addActionListener(new ActionListener() {           
          public void actionPerformed(ActionEvent e) {
              System.exit(0);             
          }           
      });
      
      changeInfoItem.addActionListener(new ActionListener() {           
          public void actionPerformed(ActionEvent e) {
        	  RegisterDialog registerDlg = new RegisterDialog("Update", parent);
        	 
        	  //Retrieve information about current user
        	  try {
				User user = new User();
				user = servers.findUserById(currentUser.getUserId());
				registerDlg.setEmail(user.getEmail());
				registerDlg.setName(user.getName());
				//registerDlg.setNickName(user.getNickName());
			  } catch (Exception e2) {
				  e2.printStackTrace();
			  }
        	  
              registerDlg.setVisible(true);  
              
              if(registerDlg.isSucceeded()) {
            	  User user = new User(registerDlg.getEmail(), registerDlg.getName(),currentUser.getNickName(),currentUser.getPassword(),currentUser.getUserId());
            	  try {
            		  servers.updateUser(user);
            		  JOptionPane.showMessageDialog(HeaderPanel.this,
                              "User's details have been updated successfully!");

  					  System.out.println("User "+user.getNickName() +" updated.");
				} catch (Exception e1) {
					e1.printStackTrace();
					 JOptionPane.showMessageDialog(HeaderPanel.this,
							 "Can't update a user because of the fault: "+e1.getMessage(),
	                            "Login",
	                            JOptionPane.ERROR_MESSAGE);
					System.out.println("Can't update a user because of the fault: "+e1.getMessage());
				} 
              }
          }           
      });
      
      menuBar.add(fileMenu);

      this.add(menuBar, BorderLayout.WEST);
      
      setVisible(true);
	}
}
