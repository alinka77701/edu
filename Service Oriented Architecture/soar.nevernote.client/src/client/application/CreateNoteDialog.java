package client.application;

import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.*;
 
public class CreateNoteDialog extends JDialog {
 
    private JTextField nameNoteField;
    private JLabel nameNoteLabel;
    private JTextField descriptionNoteField;
    private JLabel descriptionNoteLabel;
    private JButton btnCreate;
    private JButton btnCancel;
    private boolean succeeded;
    
    public CreateNoteDialog(JFrame parent) {
        super(parent, "Create a note", true);
        

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        nameNoteLabel = new JLabel("Title: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(nameNoteLabel, cs);
 
        nameNoteField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(nameNoteField, cs);
        
        descriptionNoteLabel = new JLabel("Description: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(descriptionNoteLabel, cs);
 
        descriptionNoteField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(descriptionNoteField, cs);

        btnCreate = new JButton("Create");
 
        btnCreate.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (!(nameNoteField.getText().isEmpty()) && !(descriptionNoteField.getText().isEmpty())) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CreateNoteDialog.this,
                            "To create a note you should provide its title and description.",
                            "Create a note",
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
       
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getTitleNote() {
        return nameNoteField.getText().trim();
    }
    public String getDescriptionNote() {
        return descriptionNoteField.getText().trim();
    }
    public boolean isSucceeded() {
        return succeeded;
    }

}
