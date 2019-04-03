package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import nevernote.note.server.Note;
import nevernote.notebook.server.InvalidNotebookDetailsFault;
import nevernote.notebook.server.Notebook;
import nevernote.notebook.server.Notebooks;
import nevernote.user.server.User;

public class NotebookMenu extends JPanel{
	
	public static NeverNoteApi servers;
	
	public static User currentUser;
	
	private JPanel contentPanel; 
	private JPanel contentPublicPanel; 
	private JScrollPane notificationsPanel; 
	
	private JFrame parentFrame;
	
	private JLabel inboxLine;
	private JTextArea notificationsArea;
	
	private Notebook [] notebooks;
	
	private Session session = null;
	
	private Topic publicNotebookTopic;
	private Topic publicNotesTopic;
	
	private MessageConsumer consumerNotebooks;
	private MessageConsumer consumerNotes;

	
	private Connection connection;
	private MySessionConnection mySessionConnection;
	
	public NotebookMenu(NeverNoteApi serversApi, User user, JFrame parent ){
		parentFrame = parent;
		servers = serversApi;
		currentUser=user;
		
		try {
			mySessionConnection = MySessionConnection.getInstance();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		session = mySessionConnection.getSession();
		connection = mySessionConnection.getConnection();
		
		initializeUI();
	}
	
    public void initializeUI()  {
    	
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(true);
		setBackground(Color.WHITE);
		
		contentPanel = new JPanel(new GridLayout(3, 5, 10, 10));
		contentPanel.setBackground(Color.WHITE);
		
		contentPublicPanel=new JPanel(new GridLayout(3, 5, 10, 10));
		contentPublicPanel.setBackground(Color.WHITE);
		
		notificationsArea = new JTextArea();
		notificationsArea.setText("");
		notificationsArea.setEnabled(false);
		notificationsArea.setCaretPosition(0);
        
		notificationsPanel=new JScrollPane(notificationsArea);
		notificationsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Font font = new Font(null, Font.BOLD, 25);
		Border solidBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		JButton addNewNotebookButton = new JButton("+");
		addNewNotebookButton.setFont(font);
		addNewNotebookButton.setBorder(solidBorder);
		contentPanel.add(addNewNotebookButton);
		
		setNotebooks();
		setPublicNotebooks();
		
		addNewNotebookButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CreateNotebookDialog createNotebookDlg = new CreateNotebookDialog(parentFrame);
        		createNotebookDlg.setVisible(true); 
        		if(createNotebookDlg.isSucceeded()) {
        			
        			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        			Date today = Calendar.getInstance().getTime();        
        			String createdDate = df.format(today);
        			
        			Notebook notebook = new Notebook(createdDate,0,createNotebookDlg.getNameNotebook(),false,false,"regular",currentUser.getUserId());
        			try {
        				int size =0;
        				if(servers.getAllNotebooks(currentUser.getUserId())==null) {
        					size = 0;
        				}
        				else {
            				size = servers.getAllNotebooks(currentUser.getUserId()).length;
        				}
        				if(size<14) {
        					servers.createNotebook(notebook);
    						notebook = servers.findNotebook(notebook.getName());
    		        		contentPanel.add(new NotebookPanel(notebook,servers,contentPanel,currentUser, parentFrame,"contentPanel"));
    		        		contentPanel.revalidate(); 
    		        		contentPanel.repaint();
    		        		JOptionPane.showMessageDialog(parentFrame, "Notebook "+notebook.getName()+" has been created successfully!");
        				} else {
    		        		JOptionPane.showMessageDialog(parentFrame, "You have reached the maximum number of notebooks. To create a new notebook, you should delete one from the created notebooks.");
        				}
						
					} catch (InvalidNotebookDetailsFault fault) {
						JOptionPane.showMessageDialog(parentFrame, "Error. "+fault.getMessage1());
						fault.printStackTrace();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(parentFrame, "Error occured while creating a notebook."+e1.getMessage());
						e1.printStackTrace();
					}
        		}
        	}
        });
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 0, 900, 100);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

		searchPanel.setBackground(Color.white);
		JSearchTextField searchBox = new JSearchTextField();
		searchBox.setSize(500, 50);
		searchBox.setMaximumSize(new Dimension(500, 50));
		
		JButton searchButton = new JButton("Search");

		searchButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String searchNotebook = searchBox.getText();
        		
        		if(notebooks!=null && !searchNotebook.isEmpty()) {
        		
        			for(Component component : contentPanel.getComponents()) {
        	            if(component instanceof JPanel) {
        	               component.setVisible(false);
        	               Component [] componentsInPanel = ((JPanel) component).getComponents();
        	               for(Component componentInPanel : componentsInPanel) {
        	            	   if(componentInPanel instanceof JLabel) {
        	            		   System.out.println( ((JLabel)componentInPanel).getText());
        	            		   if(((JLabel)componentInPanel).getText().equals(searchNotebook)) {
        	            			   component.setVisible(true);
        	            		   }
        	            	   }
        	               }
        	            } 
        	        }
        	    }
        		else if(searchNotebook.isEmpty())
        		{
        			for(Component component : contentPanel.getComponents()) {
        				  if(component instanceof JPanel) {
           	                component.setVisible(true);
           	              }
        			}
        		}
        		
        }});
		
		JButton deleteAllNotebooksButton = new JButton("Delete all notebooks");
		deleteAllNotebooksButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JDialog.setDefaultLookAndFeelDecorated(true);
        	    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all notebooks?", "Confirm",
        	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        	    try {
	        	    if (response == JOptionPane.YES_OPTION) {
	        	    	int size =0;
        				if(servers.getAllNotebooks(currentUser.getUserId())==null) {
        					size = 0;
        				}
        				else {
            				size = servers.getAllNotebooks(currentUser.getUserId()).length;
        				}
        				Notebook [] notebooks = getNotebooks();
        		    	if(notebooks!=null) {
        		    		for(int i=0; i<size;i++) {
            					servers.deleteAllNotesInNotebook(notebooks[i].getId(),currentUser.getNickName());
            					if(notebooks[i].getShared()) {
            						servers.deletePublicNotebook(notebooks[i].getId(),currentUser.getNickName());
            					}
    						}
    						servers.deleteAllNotebooks(currentUser.getUserId());
    		            	for(Component componentInRoot : contentPanel.getComponents()) {
    		       	            if(componentInRoot instanceof JPanel) {       
    		       	            	contentPanel.remove(componentInRoot); 
    		       	            	contentPanel.revalidate(); 
    		       	            	contentPanel.repaint();
    		       	            } 
    	       	            }
    		            	setNotebooks();
    		                JOptionPane.showMessageDialog(parentFrame, "All notebooks have been deleted successfully!");
        		    	}
        				
						
	        	    }
        	    } catch (InvalidNotebookDetailsFault fault) {
					JOptionPane.showMessageDialog(parentFrame, "Error occured while updating the notebook. Error: "+fault.getMessage1());
					fault.printStackTrace();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame, "Error occured while updating the notebook. Error ex: "+e1.getMessage());
					e1.printStackTrace();
				} 
        }});
		
		JLabel titleLabel = new JLabel("Notebooks");
		titleLabel.setVerticalAlignment(JLabel.TOP);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(font);
	    add(titleLabel);
	    searchPanel.add(searchBox);
		searchPanel.add(searchButton);
		searchPanel.add(deleteAllNotebooksButton);
		
		add(searchPanel);	
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.addTab("My Notebooks", contentPanel);
		tabbedPane.addTab("Public Notebooks", contentPublicPanel);
		tabbedPane.addTab("Log Book", notificationsPanel);

		add(tabbedPane);	
		try {
			subscribeToNotebooksNotifications();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		inboxLine = new JLabel();
	    parentFrame.add(inboxLine,BorderLayout.SOUTH);
	}
    
    public void setNotebooks() {
    	Notebook [] notebooks = getNotebooks();
    	if(notebooks!=null) {
          for (int i = 0; i  < notebooks.length; i++) {
        	  contentPanel.add(new NotebookPanel(notebooks[i],servers, contentPanel,currentUser,parentFrame,"contentPanel"));
          }
    	}
    }
    
    public void setPublicNotebooks() {
    	Notebook[] notebooks=null;
		try {	
			notebooks = servers.getAllPublicNotebooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(notebooks!=null) {
          for (int i = 0; i <notebooks.length; i++) {
        	  contentPublicPanel.add(new NotebookPanel(notebooks[i],servers, contentPublicPanel,currentUser,parentFrame,"contentPublicPanel"));
          }
    	}
    }
    
	public Notebook [] getNotebooks() {
		try {
			if(servers.getAllNotebooks(currentUser.getUserId())!=null) {
				int size = servers.getAllNotebooks(currentUser.getUserId()).length;
				notebooks = new  Notebook [size];
				notebooks=servers.getAllNotebooks(currentUser.getUserId());
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(NotebookMenu.this, "Error occured while excrating all notebooks from server."+e.getMessage());
			e.printStackTrace();
		}
		return notebooks;
	}
	
	public void subscribeToNotebooksNotifications() throws Exception {
		try {
			publicNotebookTopic = session.createTopic("publicNotebooks");
			consumerNotebooks = session.createConsumer(publicNotebookTopic);
			
			publicNotesTopic = session.createTopic("publicNotes");
			consumerNotes = session.createConsumer(publicNotesTopic);
			
			Font font = new Font(null, Font.BOLD, 13);
			
			consumerNotebooks.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message m) {
					try {
						TextMessage message = (TextMessage) m;
						
						String author =message.getStringProperty("AUTHOR");
						String text= currentUser.getNickName() +" has new message: "+message.getText()+" by a user '"+author+"'.";
						
						inboxLine.setFont(font);
						inboxLine.setText(text);
						
						String existingNotifications=notificationsArea.getText();
						String updatedNotifications=existingNotifications+"\n"+message.getText()+" by a user '"+author+"'.";
						notificationsArea.setText(updatedNotifications);
						
						notificationsPanel.removeAll();
						notificationsPanel.add(notificationsArea);
						
						System.out.println(currentUser.getNickName() +" has new message: "+message.getText()+" by a user '"+author+"'.");
						System.out.println("To see latest changes, please, update notebooks.");	

						contentPublicPanel.removeAll();			
						contentPublicPanel.revalidate();
						contentPublicPanel.repaint();
						
		    			setPublicNotebooks();
		    			
		    			contentPublicPanel.revalidate();
						contentPublicPanel.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			});
			
			consumerNotes.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message m) {
					try {
						TextMessage message = (TextMessage) m;
						String changedByUser =message.getStringProperty("MODIFIED");
						int parentNotebookId =message.getIntProperty("PARENT_NOTEBOOK_ID");
						String parentNotebookName="";
						try {
							parentNotebookName=servers.findNotebookById(parentNotebookId).getName();
						}
						catch(Exception e) {
							parentNotebookName="that has been deleted";
						}
						
						String text= currentUser.getNickName() +" has new message: "+message.getText()+" by a user '"+changedByUser+"' in the notebook '"+parentNotebookName+"'."+"\nTo see latest changes, please, update notebooks.";
						
						inboxLine.setFont(font);
						inboxLine.setText(text);
						
						String existingNotifications=notificationsArea.getText();
						String updatedNotifications=existingNotifications+"\n"+message.getText()+" by a user '"+changedByUser+"' in the notebook '"+parentNotebookName+"'.";
						notificationsArea.setText(updatedNotifications);
						
						notificationsPanel.removeAll();
						notificationsPanel.add(notificationsArea);
						
						System.out.println(currentUser.getNickName() +" has new message: "+message.getText()+" by a user '"+changedByUser+"'.");	
						System.out.println("To see latest changes, please, update notebooks.");	
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public Note [] getAllStarredNotes() {
		Note [] notes = null;
		try {
			if(servers.getAllStarredNotes(currentUser.getUserId())!=null) {
				int size = servers.getAllStarredNotes(currentUser.getUserId()).length;
				notes = new  Note [size];
				notes=servers.getAllStarredNotes(currentUser.getUserId());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(NotebookMenu.this, "Error occurred while extracting all starred notes from server."+e.getMessage());
			e.printStackTrace();
		}
		return notes;
 }	
}
