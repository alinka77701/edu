package client.application;

import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.*;
 
public class CreateNotebookDialog extends JDialog {
 
    private JTextField nameNotebookField;
    private JLabel nameNotebookLabel;
    private JButton btnCreate;
    private JButton btnCancel;
    private boolean succeeded;

    public CreateNotebookDialog(JFrame parent) {
        super(parent, "Create a notebook", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        nameNotebookLabel = new JLabel("Name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(nameNotebookLabel, cs);
 
        nameNotebookField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(nameNotebookField, cs);
        
        btnCreate = new JButton("Create");
 
        btnCreate.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (!(nameNotebookField.getText().isEmpty())) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CreateNotebookDialog.this,
                            "To create a notebook you should provide its name.",
                            "Create a notebook",
                            JOptionPane.ERROR_MESSAGE);
                    nameNotebookField.setText("");
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

    public String getNameNotebook() {
        return nameNotebookField.getText().trim();
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
 
}
