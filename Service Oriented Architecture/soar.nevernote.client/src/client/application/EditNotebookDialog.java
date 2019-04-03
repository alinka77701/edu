package client.application;

import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.*;

import nevernote.notebook.server.Notebook;
 
public class EditNotebookDialog extends JDialog {
 
    private JTextField nameNotebookField;
    private JLabel nameNotebookLabel;
    private JButton btnCreate;
    private JButton btnCancel;
    private boolean succeeded;
    private JCheckBox deleteAllNotebooksBox;
    private boolean checkedDeleteAllNotes;
    private JLabel sharedNoteLabel;
    private JLabel sharedValueNotebookLabel;
    private JLabel createdDateNotebookLabel;
    private JLabel createdDateValueNotebookLabel;
    private JLabel readonlyNotebookLabel;
    private JLabel readonlyValueNotebookLabel;
    private Notebook currentNotebook;
    
    public EditNotebookDialog(JFrame parent, Notebook notebook) {
        super(parent, "Update a notebook", true);
        currentNotebook = notebook;
        checkedDeleteAllNotes = false;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        Font bold = new Font(null, Font.BOLD, 13);
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        nameNotebookLabel = new JLabel("Name: ");
        nameNotebookLabel.setFont(bold);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(nameNotebookLabel, cs);
 
        nameNotebookField = new JTextField(20);
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(nameNotebookField, cs);
        
        deleteAllNotebooksBox = new JCheckBox("Delete all notes");
        sharedNoteLabel = new JLabel("Shared: ");
        sharedNoteLabel.setFont(bold);
        sharedValueNotebookLabel = new JLabel(currentNotebook.getShared().toString());
        createdDateNotebookLabel = new JLabel("Created: ");
        createdDateNotebookLabel.setFont(bold);
        createdDateValueNotebookLabel = new JLabel(currentNotebook.getCreated());

        deleteAllNotebooksBox.setEnabled(true);
        deleteAllNotebooksBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if(checkedDeleteAllNotes) 
            		checkedDeleteAllNotes = false;
            	else 
            		checkedDeleteAllNotes = true;
            }
        }); 
       
    	nameNotebookField.setText(currentNotebook.getName());
    	deleteAllNotebooksBox.setEnabled(true);
        cs.gridx = 1;
        cs.gridy =1;
        cs.gridwidth = 2;
        panel.add(deleteAllNotebooksBox, cs); 	
        
        
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(sharedNoteLabel, cs);
        
       
        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(sharedValueNotebookLabel, cs);
        
       
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(createdDateNotebookLabel, cs);
        
       
        cs.gridx = 2;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(createdDateValueNotebookLabel, cs);
        
        readonlyNotebookLabel = new JLabel("Read only: ");
        readonlyNotebookLabel.setFont(bold);
        readonlyValueNotebookLabel = new JLabel(currentNotebook.getReadOnly().toString());
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(readonlyNotebookLabel, cs);
        
       
        cs.gridx =2;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(readonlyValueNotebookLabel, cs);
        
        btnCreate = new JButton("Update");
 
        btnCreate.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (!(nameNotebookField.getText().isEmpty())) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(EditNotebookDialog.this,
                            "To update a notebook you should provide its name.",
                            "Update a notebook",
                            JOptionPane.ERROR_MESSAGE);
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
        
        JPanel bp = new JPanel();
       
        bp.add(btnCancel);
        bp.add(btnCreate);
        
        Color color = UIManager.getColor ( "EditNotebookDialog.background" );
        
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

    public String getNameNotebook() {
        return nameNotebookField.getText().trim();
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
    public boolean isChecked() {
        return checkedDeleteAllNotes;
    }
}
