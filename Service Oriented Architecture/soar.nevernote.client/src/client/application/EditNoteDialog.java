package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import nevernote.note.server.InvalidNoteFault;
import nevernote.note.server.Note;
import nevernote.user.server.User;

public class EditNoteDialog extends JDialog {
	private NeverNoteApi servers;
	private JTextField titleNoteField;
    private JLabel titleNoteLabel;
	private JTextField descriptionNoteField;
    private JLabel descriptionNoteLabel;
    private JButton btnUpdate;
    private JButton btnStarred;
    private JButton btnOk;
    private JButton btnCancel;
    private Note currentNote;
   
    private JFrame parentFrame;
    private JPanel parentPanel;
    
    private JLabel starredNoteLabel;
    private JLabel starredValueNoteLabel;
    private JLabel createdDateNoteLabel;
    private JLabel createdDateValueNoteLabel;
    
    public boolean isNotebookStarred;
    private boolean succeeded;
    private boolean starred;
    private boolean isPublicNotebook;
    
    private User currentUser;
    public EditNoteDialog(JFrame parent, Note note, NeverNoteApi serverss, JPanel parentPanell, Boolean starredNotebook, User user, Boolean publicNotebook) {
        super(parent, "Edit a note", true);
        currentUser = user;
        isNotebookStarred=starredNotebook;
        parentFrame = parent;
        parentPanel = parentPanell;
        currentNote = note;
    	servers = serverss;
    	starred = currentNote.getStarred();
    	isPublicNotebook=publicNotebook;
    	
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        titleNoteLabel = new JLabel("Title: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(titleNoteLabel, cs);
 
        titleNoteField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        titleNoteField.setEnabled(false);
        panel.add(titleNoteField, cs);
        
        descriptionNoteLabel = new JLabel("Description: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(descriptionNoteLabel, cs);
 
        descriptionNoteField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        descriptionNoteField.setEnabled(false);
        panel.add(descriptionNoteField, cs);
        
        starredNoteLabel = new JLabel("Starred: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(starredNoteLabel, cs);
        
        starredValueNoteLabel = new JLabel(currentNote.getStarred().toString());
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(starredValueNoteLabel, cs);
        
        createdDateNoteLabel = new JLabel("Created: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(createdDateNoteLabel, cs);
        
        createdDateValueNoteLabel = new JLabel(currentNote.getCreated());
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(createdDateValueNoteLabel, cs);
        
        if(currentNote.getTitle()!=null) {
        	titleNoteField.setText(currentNote.getTitle());
        	descriptionNoteField.setText(currentNote.getDescription());
        }
        btnOk = new JButton("Update");
        btnOk.setEnabled(false);
 
        btnOk.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (!(titleNoteField.getText().isEmpty())) {
                	 try {
    	            	 Note note = new Note(currentNote.getCreated(),getDescriptionNote(),currentNote.getId(),currentNote.getNoteBookId(),currentNote.getStarred(),getTitleNote(),currentNote.getUserId());
    	            	 servers.updateNote(note,currentUser.getNickName());
						 succeeded = true;
		                 dispose();
    	             }
    	             catch (InvalidNoteFault fault) {
    						JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error: "+fault.getMessage1());	
    						fault.printStackTrace();	   	   
    			     } catch (Exception e1) {
    				   JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error exception: "+e1.getMessage());	
    					e1.printStackTrace();
    				}
                   
                } else {
                    JOptionPane.showMessageDialog(EditNoteDialog.this,
                            "To update a note you should provide its title and description.",
                            "Edit a note",
                            JOptionPane.ERROR_MESSAGE);
                    titleNoteField.setText("");
                    succeeded = false;
                }
            }
        });
        btnCancel = new JButton("Cancel");
        
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    
        btnUpdate = new JButton();
		try {
			   ImageIcon updateIcon = new ImageIcon("icons/update.png");
			   Image image = updateIcon.getImage(); 
			   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
			   updateIcon = new ImageIcon(newimg); 
			   btnUpdate = new JButton(updateIcon);	
			   btnUpdate.setPreferredSize(new Dimension(20, 20));
			  
			} catch (Exception ex) {
			  JOptionPane.showMessageDialog(EditNoteDialog.this, "Error occured. "+ex.getMessage());
			  ex.printStackTrace();
		}
		
		btnUpdate.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
	            		currentNote = servers.findNoteById(currentNote.getId());
	            		titleNoteField.setText(currentNote.getTitle());
	    	            descriptionNoteField.setText(currentNote.getDescription());
	    	            createdDateValueNoteLabel.setText(currentNote.getCreated());
	    	            starredValueNoteLabel.setText(currentNote.getStarred().toString());
	    	    		if(!isNotebookStarred) {
	    	    			descriptionNoteField.setEnabled(true);
	    	    			titleNoteField.setEnabled(true);
	    	    			if(!isPublicNotebook)
	    	    				btnStarred.setEnabled(true);
		    	            btnOk.setEnabled(true);
	    	    		}
						JOptionPane.showMessageDialog(EditNoteDialog.this, "Now you see the updated note. ");
	            	}
	            	catch(InvalidNoteFault fault) {
	    				JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error: "+fault.getMessage1());	
	            		fault.printStackTrace();
	            	} catch (Exception e1) {
	    				JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error exception: "+e1.getMessage());	
						e1.printStackTrace();
					}
	            
	            }
	    });
		
		btnStarred = new JButton();
		
		try {
			   ImageIcon starredIcon = new ImageIcon("icons/star.png");
			   Image image = starredIcon.getImage(); 
			   Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
			   starredIcon = new ImageIcon(newimg); 
			   btnStarred = new JButton(starredIcon);	
			   btnStarred.setPreferredSize(new Dimension(20, 20));
			   btnStarred.setEnabled(false);
			  
			} catch (Exception ex) {
			  JOptionPane.showMessageDialog(EditNoteDialog.this, "Error occured. "+ex.getMessage());
			  ex.printStackTrace();
		}
		
		btnStarred.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
		             try {
		            	 currentNote.setStarred(!currentNote.getStarred());
		            	 Note note = new Note(currentNote.getCreated(),getDescriptionNote(),currentNote.getId(),currentNote.getNoteBookId(),currentNote.getStarred(),getTitleNote(),currentNote.getUserId());
		            	 servers.updateNote(note,currentUser.getNickName());
		            	 starredValueNoteLabel.setText(currentNote.getStarred().toString());;
		            	 starred = note.getStarred();
						 JOptionPane.showMessageDialog(EditNoteDialog.this, "The note +'"+currentNote.getTitle()+"' has beed added to a starred notebook successfully!");
		             }
		             catch (InvalidNoteFault fault) {
							JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error: "+fault.getMessage1());	
							fault.printStackTrace();	   	   
				     } catch (Exception e1) {
					   JOptionPane.showMessageDialog(EditNoteDialog.this, "Error ocurred while updating the note '"+currentNote.getTitle()+"'. Error exception: "+e1.getMessage());	
						e1.printStackTrace();
					}
	            }
	    });
		
        JPanel bp = new JPanel();
        bp.add(btnUpdate);
        bp.add(btnStarred);
        bp.add(btnCancel);
        bp.add(btnOk);
        
        Color color = UIManager.getColor ( "EditNoteDialog.background" );
        
        LayerPanel layerWest = new LayerPanel();
        layerWest.setBackground(color);
        getContentPane().add(layerWest, BorderLayout.WEST);
        
        LayerPanel layerEast = new LayerPanel();
        layerEast.setBackground(color);
        getContentPane().add(layerEast, BorderLayout.EAST);
        
        LayerPanel layerTop = new LayerPanel();
        layerTop.setBackground(color);
        getContentPane().add(layerTop, BorderLayout.NORTH);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    public String getTitleNote() {
        return titleNoteField.getText().trim();
    }
    public String getDescriptionNote() {
        return descriptionNoteField.getText().trim();
    }
    public boolean getStarredNote() {
        return starred;
    }
    public boolean isSucceeded() {
        return succeeded;
    }
}
