package client.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import nevernote.note.server.InvalidNoteDetailsFault;
import nevernote.note.server.Note;
import nevernote.notebook.server.InvalidNotebookDetailsFault;
import nevernote.notebook.server.InvalidNotebookFault;
import nevernote.notebook.server.Notebook;
import nevernote.user.server.User;

public class NotebookPanel extends JPanel{
	private Notebook currentNotebook; 
	public static NeverNoteApi servers;
	private JPanel contentPanel; 
	private JPanel parentPanel; 
	private User currentUser; 
	private JFrame parentFrame;
	private Boolean isInPublicPanel;
	private String panelName;
	
	private MySessionConnection mySessionConnection;
	
	NotebookPanel (Notebook notebook, NeverNoteApi serversApi, 
			       JPanel parent, User user, JFrame parentFr,
			       String parentPanelName){

		currentNotebook = notebook;
		servers = serversApi;
		parentPanel=parent;
		currentUser= user;
		parentFrame = parentFr;
		panelName=parentPanelName;
		initializeUI();
	}
	
	 public void initializeUI()  {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(true); 
		
		Color transYellow = new Color( 255, 255, 0, 30 );
		setBackground(transYellow);
		
		Border solidBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(solidBorder);
		
		Font font = new Font(null, Font.BOLD, 15);
		Dimension labelSize = new Dimension(80, 20);
		
		LayerPanel layerPanel = new LayerPanel();
		layerPanel.setMaximumSize(new Dimension(this.getWidth(), 10));
		layerPanel.setBackground(transYellow);

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.setBackground(Color.white);
		
		JLabel notebookNameLabel = new JLabel(currentNotebook.getName());

		add(layerPanel);
		add(headerPanel);
		
		JButton deleteButton = null;
		try {
		   ImageIcon deleteIcon = new ImageIcon("icons/delete.png");
		   Image image = deleteIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   deleteIcon = new ImageIcon(newimg); 
		   deleteButton = new JButton(deleteIcon);	
		   deleteButton.setPreferredSize(new Dimension(20, 20));	
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotebookPanel.this, "Error occured."+ex.getMessage());
		  ex.printStackTrace();
		}
		deleteButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
    				servers.deleteAllNotesInNotebook(currentNotebook.getId(),currentUser.getNickName());
					servers.deleteNotebook(currentNotebook.getName(),currentUser.getNickName());  
					for(Component component : parentPanel.getComponents()) {
        	            if(component instanceof JPanel) {
        	               Component [] componentsInPanel = ((JPanel) component).getComponents();
        	               for(Component componentInPanel : componentsInPanel) {
        	            	   if(componentInPanel instanceof JLabel) {  
        	            		   if(((JLabel)componentInPanel).getText().equals(currentNotebook.getName())) {
        	            		    parentPanel.remove(component); 
        	       					parentPanel.revalidate(); 
        	       					parentPanel.repaint();
        	            		   }
        	            	   }
        	               }
        	            } 
        	        }
					
					JOptionPane.showMessageDialog(NotebookPanel.this, "The notebook '"+currentNotebook.getName()+"' has been deleted successfully!");
				} catch (InvalidNotebookDetailsFault fault) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while deleting the notebook "+currentNotebook.getName()+". Error: "+fault.getMessage1());	
					fault.printStackTrace();	   	   
		        }
	    		catch (InvalidNotebookFault fault) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while deleting the notebook "+currentNotebook.getName()+". Error: "+fault.getMessage1());	
					fault.printStackTrace();	   	   
		        }catch (Exception e1) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while deleting the notebook "+currentNotebook.getName()+". Error exception: "+e1.getMessage());	
					e1.printStackTrace();
				} 
	    	}
        });
		if(currentNotebook.getName().equals("Starred"+currentUser.getNickName()) || panelName.equals("contentPublicPanel"))
			deleteButton.setEnabled(false);
		
			
		headerPanel.add(deleteButton);
		
		JButton editButton = null;
		try {
		   ImageIcon editIcon = new ImageIcon("icons/edit.png");
		   Image image = editIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   editIcon = new ImageIcon(newimg); 
		   editButton = new JButton(editIcon);	
		   editButton.setPreferredSize(new Dimension(20, 20));
		
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotebookPanel.this, "Error occured."+ex.getMessage());
		  ex.printStackTrace();  
		}
		
		editButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EditNotebookDialog updateNotebookDlg = new EditNotebookDialog(parentFrame,currentNotebook);
        		updateNotebookDlg.setVisible(true); 
				if(updateNotebookDlg.isSucceeded()) {
					String oldNotebookName = currentNotebook.getName();
					currentNotebook.setName(updateNotebookDlg.getNameNotebook());
					Notebook notebook = new Notebook(currentNotebook.getCreated(),currentNotebook.getId(),currentNotebook.getName(),currentNotebook.getReadOnly(),currentNotebook.getShared(),currentNotebook.getType(),currentNotebook.getUserId());
					try {
						if(!currentNotebook.getName().equals(oldNotebookName)) {
							servers.updateNotebook(notebook,currentUser.getNickName());
							for(Component component : parentPanel.getComponents()) {
		        	            if(component instanceof JPanel) {
		        	               Component [] componentsInPanel = ((JPanel) component).getComponents();
		        	               for(Component componentInPanel : componentsInPanel) {
		        	            	   if(componentInPanel instanceof JLabel) {  
		        	            		   if(((JLabel)componentInPanel).getText().equals(oldNotebookName)) {	
		        	            			    ((JLabel)componentInPanel).setText(currentNotebook.getName());
		        	            			    component.revalidate(); 
		        	            			    component.repaint();
			        	       					parentPanel.revalidate(); 
			        	       					parentPanel.repaint();
		        	            		   }
		        	            	   }
		        	               }
		        	            }
							 }
							 JOptionPane.showMessageDialog(parentFrame, "The notebook "+notebook.getName()+" has been updated successfully!");
						}
						if (updateNotebookDlg.isChecked()) {
	        	            	servers.deleteAllNotesInNotebook(currentNotebook.getId(),currentUser.getNickName());
	        	            	for(Component componentInRoot : contentPanel.getComponents()) {
	                	            if(componentInRoot instanceof JPanel) {       
	                	            	contentPanel.remove(componentInRoot); 
	                	            	contentPanel.revalidate(); 
	                	            	contentPanel.repaint();
	                	            	parentPanel.revalidate(); 
	        	       					parentPanel.repaint();
	                	            	//contentPanel.setBackground( Color.WHITE );
	                	            } 
	                	        }
	    			      JOptionPane.showMessageDialog(parentFrame, "All notes in the notebook '"+notebook.getName()+"' have been deleted successfully!");
	        	         }
						
	        	        
					} catch (InvalidNotebookDetailsFault fault) {
						JOptionPane.showMessageDialog(parentFrame, "Error occured while updating the notebook. Error: "+fault.getMessage1());
						fault.printStackTrace();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(parentFrame, "Error occured while updating the notebook. Error ex: "+e1.getMessage());
						e1.printStackTrace();
					} 	
				} 
        	}
	    });
		
		if(currentNotebook.getName().equals("Starred"+currentUser.getNickName()))
			editButton.setEnabled(false);
		if(panelName.equals("contentPublicPanel") && currentNotebook.getReadOnly())
			editButton.setEnabled(false);
		
		headerPanel.add(editButton);
		
		JButton shareButton = new JButton();
		try {
		   ImageIcon shareIcon = new ImageIcon("icons/share.png");
		   Image image = shareIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   shareIcon = new ImageIcon(newimg); 
		   shareButton = new JButton(shareIcon);	
		   shareButton.setPreferredSize(new Dimension(20, 20));
		  
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotebookPanel.this, "Error occured. "+ex.getMessage());
		  ex.printStackTrace();
		}
		shareButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AccessLevelDialog accessLevelDialog = new AccessLevelDialog(parentFrame,currentNotebook.getShared());
        		accessLevelDialog.setVisible(true); 
        		
				if(accessLevelDialog.isSucceeded()) {
					Boolean readOnly = accessLevelDialog.isReadOnly();
					currentNotebook.setReadOnly(readOnly);
					currentNotebook.setShared(!currentNotebook.getShared());
					try {
						servers.updateNotebook(currentNotebook,currentUser.getNickName());
						servers.makeNotebookPublic(currentNotebook.getId(),currentUser.getNickName());
						String accessLevel="", shared="";
						if(currentNotebook.getReadOnly())
							accessLevel = "read only";
						else 
							accessLevel = "read/write";
						if(currentNotebook.getShared())
							shared = "shared";
						else 
							shared = "not shared";
						 JOptionPane.showMessageDialog(parentFrame, "Now the notebook '"+currentNotebook.getName()+"' is " + shared+". The access level is: "+accessLevel);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else {
						
				}
			}
    	});
		
		if(currentNotebook.getName().equals("Starred"+currentUser.getNickName()) || panelName.equals("contentPublicPanel"))
			shareButton.setEnabled(false);
		headerPanel.add(shareButton);
		
		JButton updateNotesButton = null;
		try {
		   ImageIcon updateIcon = new ImageIcon("icons/update.png");
		   Image image = updateIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   updateIcon = new ImageIcon(newimg); 
		   updateNotesButton = new JButton(updateIcon);	
		   updateNotesButton.setPreferredSize(new Dimension(20, 20));	
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotebookPanel.this, "Error occured."+ex.getMessage());
		  ex.printStackTrace();
		}
		updateNotesButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {	    			
	    			contentPanel.removeAll();
	    			
	    			setNotes();
	    			String updatedName = servers.findNotebookById(currentNotebook.getId()).getName();
	    			currentNotebook.setName(updatedName);
	    			notebookNameLabel.setText(updatedName);
	    			contentPanel.revalidate();
	    			contentPanel.repaint();
					JOptionPane.showMessageDialog(NotebookPanel.this, "The notebook '"+currentNotebook.getName()+"' has been updated successfully!");
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while updating the notebook "+currentNotebook.getName()+". Error exception: "+e1.getMessage());	
					e1.printStackTrace();
				} 
	    	}
        });

		headerPanel.add(updateNotesButton);
		
		JButton addNewNoteButton = new JButton("+");;
		addNewNoteButton.addActionListener(new ActionListener() {
			Note note = null;
	    	public void actionPerformed(ActionEvent e) {
	    		try {
		    			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        			Date today = Calendar.getInstance().getTime();        
	        			String createdDate = df.format(today);
	        			
	        			CreateNoteDialog createNoteDlg = new CreateNoteDialog(parentFrame);
	        			createNoteDlg.setVisible(true); 
	            		if(createNoteDlg.isSucceeded()) {
	            			int size =0;
	        				if(servers.getAllNotesInNotebook(currentNotebook.getId())==null) {
	        					size = 0;
	        				}
	        				else {
	        					size= servers.getAllNotesInNotebook(currentNotebook.getId()).length;
	        				}
	            			if(size<4){
	            				note = new Note(createdDate,createNoteDlg.getDescriptionNote(),0,currentNotebook.getId(),false,createNoteDlg.getTitleNote(),currentUser.getUserId());	            			
	            				servers.createNote(note,currentUser.getNickName());  
	 							note= servers.findNote(note.getTitle(),currentNotebook.getId());
	 							contentPanel.add(new NotePanel(note,contentPanel,servers,parentFrame,false,currentUser,isInPublicPanel));
	 							
	         	            	contentPanel.revalidate(); 
	         	            	contentPanel.repaint();
	         	            	parentPanel.revalidate(); 
	 	       					parentPanel.repaint();
	 							JOptionPane.showMessageDialog(NotebookPanel.this, "The note'"+note.getTitle()+"' has been added successfully!");

	            			}
	            			else {
	    		        		JOptionPane.showMessageDialog(parentFrame, "You have reached the maximum number of notes. To create a new note, you should delete one from the created notes.");
	            			} 
		        	    }
            		}
				 catch (InvalidNoteDetailsFault fault) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while adding the note. Error: "+fault.getMessage1());	
					fault.printStackTrace();	   	   
		        } catch (Exception e1) {
					JOptionPane.showMessageDialog(NotebookPanel.this, "Error ocurred while adding the note. Error exception: "+e1.getMessage());	
					e1.printStackTrace();
				} 
	    	}
        });
		
		if(currentNotebook.getName().equals("Starred"+currentUser.getNickName()))
			addNewNoteButton.setEnabled(false);
		if(panelName.equals("contentPublicPanel") && currentNotebook.getReadOnly())
			addNewNoteButton.setEnabled(false);
		headerPanel.add(addNewNoteButton);
		
		notebookNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
	    notebookNameLabel.setVerticalAlignment(JLabel.TOP);
	    notebookNameLabel.setHorizontalAlignment(JLabel.CENTER);
	    notebookNameLabel.setFont(font);
	    notebookNameLabel.setBackground(Color.RED);
	    notebookNameLabel.setPreferredSize(labelSize);
	    add(notebookNameLabel);
	    
	    LayerPanel layerPanelForHeader = new LayerPanel();
	    layerPanelForHeader.setMaximumSize(new Dimension(this.getWidth(), 10));
	    layerPanelForHeader.setBackground(transYellow);
	    add(layerPanelForHeader);
	    
	    LayerPanel layerPanelForLabel = new LayerPanel();
	    layerPanelForLabel.setMaximumSize(new Dimension(this.getWidth(), 10));
	    layerPanelForLabel.setBackground(transYellow);
	    add(layerPanelForLabel);
	    
	    contentPanel = new JPanel();
	    LayoutManager layout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
	    contentPanel.setLayout(layout);
	    contentPanel.setBackground( Color.WHITE );
	    add(contentPanel);
	    
	    isInPublicPanel=panelName.equals("contentPublicPanel");
	    setNotes();	    
	 }
	 
	 public void setNotes() {
		 Note [] currentNotes=null;
		 if(currentNotebook.getName().equals("Starred"+currentUser.getNickName())) {
	    		currentNotes = getAllStarredNotes();
	        	if(currentNotes!=null) {
	              for (int i = 0; i < currentNotes.length; i++) {
	            	  contentPanel.add(new NotePanel(currentNotes[i], contentPanel,servers,parentFrame,true,currentUser,isInPublicPanel));
	              }
	        	}
	    	}else {
	    		currentNotes = getAllNotes();
	        	if(currentNotes!=null) {
	              for (int i = 0; i < currentNotes.length; i++) {
	            	  contentPanel.add(new NotePanel(currentNotes[i], contentPanel,servers,parentFrame,false,currentUser,isInPublicPanel));
	              }
	        	}
	    	}
	 }
	 
	 public Note [] getAllNotes() {
			Note [] notes = null;
			try {
				if(servers.getAllNotesInNotebook(currentNotebook.getId())!=null) {
					int size = servers.getAllNotesInNotebook(currentNotebook.getId()).length;
					notes = new  Note [size];
					notes=servers.getAllNotesInNotebook(currentNotebook.getId());
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(NotebookPanel.this, "Error occurred while extracting all notes from server."+e.getMessage());
				e.printStackTrace();
			}
			return notes;
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
				JOptionPane.showMessageDialog(NotebookPanel.this, "Error occurred while extracting all starred notes from server."+e.getMessage());
				e.printStackTrace();
			}
			return notes;
	 }
}
