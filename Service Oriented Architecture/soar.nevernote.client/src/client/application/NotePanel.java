package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import nevernote.note.server.InvalidNoteFault;
import nevernote.note.server.Note;
import nevernote.notebook.server.InvalidNotebookDetailsFault;
import nevernote.notebook.server.Notebook;
import nevernote.user.server.User;

public class NotePanel extends JPanel {
	public Note currentNote;
	public JPanel parentPanel;
	public JFrame parentFrame;
	public static NeverNoteApi servers;
	public boolean isStarredNotebook;
	public boolean isNotebookInPublicPanel;
	private User currentUser;
	public NotePanel(Note note, JPanel parent,  NeverNoteApi serversApi, JFrame parentfr, Boolean isStarred, User user,Boolean isInPublicPanel) {
		servers = serversApi;
		parentFrame = parentfr;
		currentNote = note;
		parentPanel = parent;
		isStarredNotebook=isStarred;
		currentUser = user;
		isNotebookInPublicPanel=isInPublicPanel;
		initializeUI();
	}
	public void initializeUI()  {
		
		Notebook parentNotebook=null;
		try {
			parentNotebook = servers.findNotebookById(currentNote.getNoteBookId());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Color transYellow = new Color( 255, 255, 0, 30 );
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); 
		setVisible(true); 

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		setMinimumSize(new Dimension(250, 30));
	    setPreferredSize(new Dimension(250, 30));
	    setMaximumSize(new Dimension(250, 30));

		JLabel title = new JLabel(currentNote.getTitle());
		title.setMinimumSize(new Dimension(195, 15));
		title.setPreferredSize(new Dimension(195, 15));
		title.setMaximumSize(new Dimension(195, 15));
		add(title);

	    JButton editButton = null;
		try {
		   ImageIcon editIcon = new ImageIcon("icons/edit.png");
		   Image image = editIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   editIcon = new ImageIcon(newimg); 
		   editButton = new JButton(editIcon);	
		   editButton.setPreferredSize(new Dimension(20, 20));
		
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotePanel.this, "Error occured while uploading an icon for an edit note button."+ex.getMessage());
		  ex.printStackTrace();
		}
		editButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
	    			EditNoteDialog editNoteDlg = new EditNoteDialog(parentFrame,currentNote,servers,parentPanel,isStarredNotebook,currentUser,isNotebookInPublicPanel);
	    			editNoteDlg.setVisible(true);
	    			if(editNoteDlg.isSucceeded()) {
	    				 String oldTitle = currentNote.getTitle();
	    				 currentNote.setTitle(editNoteDlg.getTitleNote()); 
	    				 currentNote.setDescription(editNoteDlg.getDescriptionNote()); 
	    				 currentNote.setStarred(editNoteDlg.getStarredNote()); 
	    				 for(Component component : parentPanel.getComponents()) {
	         	            if(component instanceof JPanel) {
	         	               Component [] componentsInPanel = ((JPanel) component).getComponents();
	         	               for(Component componentInPanel : componentsInPanel) {
	         	            	   if(componentInPanel instanceof JLabel) {  
	         	            		   if(((JLabel)componentInPanel).getText().equals(oldTitle)) {
	         	            			((JLabel)componentInPanel).setText(currentNote.getTitle());
	         	       					parentPanel.revalidate(); 
	         	       					parentPanel.repaint();
	         	            		   }
	         	            	   }
	         	               }
	         	            } 
	         	        }
						 JOptionPane.showMessageDialog(parentFrame, "The note '"+ oldTitle+"' has beed updated successfully!");

	    			}	   	   
		        } catch (Exception e1) {
					JOptionPane.showMessageDialog(NotePanel.this, "Error ocurred while updating the note "+currentNote.getTitle()+". Error exception: "+e1.getMessage());	
					e1.printStackTrace();
				} 
	    	}
        });
		if(parentNotebook.getReadOnly() && isNotebookInPublicPanel && parentNotebook.getShared())
			editButton.setEnabled(false);
        add(editButton);	
        
        JButton deleteButton = null;
		try {
		   ImageIcon deleteIcon = new ImageIcon("icons/delete.png");
		   Image image = deleteIcon.getImage(); 
		   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		   deleteIcon = new ImageIcon(newimg); 
		   deleteButton = new JButton(deleteIcon);	
		   deleteButton.setPreferredSize(new Dimension(20, 20));
		
		} catch (Exception ex) {
		  JOptionPane.showMessageDialog(NotePanel.this, "Error occured while uploading an icon for an delete note button."+ex.getMessage());
		  ex.printStackTrace();
		}
		deleteButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {   if(isStarredNotebook) {
							JOptionPane.showMessageDialog(parentFrame, "The note +' "+ currentNote.getTitle()+"' has beed deleted from Starred Notebook!");
							currentNote.setStarred(!currentNote.getStarred());
							servers.updateNote(currentNote,currentUser.getNickName());

	    		        } 
			    		else {
			    			servers.deleteNoteFromNotebook(currentNote.getId(), currentNote.getNoteBookId(),currentUser.getNickName());
							JOptionPane.showMessageDialog(parentFrame, "The note +' "+ currentNote.getTitle()+"' has beed deleted successfully!");
			    		}
						for(Component component : parentPanel.getComponents()) {
		     	            if(component instanceof JPanel) {
		     	               Component [] componentsInPanel = ((JPanel) component).getComponents();
		     	               for(Component componentInPanel : componentsInPanel) {
		     	            	   if(componentInPanel instanceof JLabel) {  
		     	            		   if(((JLabel)componentInPanel).getText().equals(currentNote.getTitle())) {
		     	            		    parentPanel.remove(component);
		     	       					parentPanel.revalidate(); 
		     	       					parentPanel.repaint();
		     	            		   }
		     	            	   }
		     	               }
		     	            } 
		     	        }
	    			}	
	    		catch (InvalidNoteFault fault) {
					JOptionPane.showMessageDialog(NotePanel.this, "Error ocurred while deleting the note "+currentNote.getTitle()+". Error: "+fault.getMessage1());	
					fault.printStackTrace();
				} 
		         catch (Exception e1) {
					JOptionPane.showMessageDialog(NotePanel.this, "Error ocurred while deleting the note "+currentNote.getTitle()+". Error exception: "+e1.getMessage());	
					e1.printStackTrace();
				} 
	    	}
        });
		if(parentNotebook.getReadOnly() && isNotebookInPublicPanel && parentNotebook.getShared())
			deleteButton.setEnabled(false);

        add(deleteButton);	
	}
}
