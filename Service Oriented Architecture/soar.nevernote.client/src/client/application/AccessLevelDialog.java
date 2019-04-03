package client.application;

import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.*;
 
public class AccessLevelDialog extends JDialog {
 
    private JRadioButton readButton;
    private JRadioButton readWriteButton;
    private JLabel sharedLabel;
    private JLabel sharedValueLabel;
    private JButton btnConfirm;
    private JButton btnCancel;
    private boolean succeeded;
    private boolean readOnly;

    public AccessLevelDialog(JFrame parent,Boolean isShared) {
        super(parent, "Specify access level", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;

        ButtonGroup bgrp = new ButtonGroup();
        
        readButton = new JRadioButton("Read", false);       
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
       
        if(!isShared)
        panel.add(readButton, cs);
 
        readWriteButton = new JRadioButton("Read/Write", true);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        bgrp.add(readWriteButton);
        bgrp.add(readButton);
        if(!isShared)
        	panel.add(readWriteButton, cs);
        String shared="";
        if(isShared)
        	shared = "shared. If you want to unshare this notebook, click 'Confirm' button";
        else
        	shared = "not shared.";
        sharedLabel= new JLabel("Now this notebook is "+shared);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(sharedLabel, cs);
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnConfirm = new JButton("Confirm");
 
        btnConfirm.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	    if(readButton.isSelected() || readWriteButton.isSelected()) {
            	    	succeeded = true;
                        if(readButton.isSelected()) {
                        	readOnly = true;
                        }
                        else if(readWriteButton.isSelected()) {
                        	readOnly = false;
                        }
                        dispose();
            	    }
            	    else {
            	    	JOptionPane.showMessageDialog(AccessLevelDialog.this,
                                "You should specify access level.",
                                "Specify access level error",
                                JOptionPane.ERROR_MESSAGE);
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
        bp.add(btnConfirm);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean isReadOnly() {
        return readOnly;
    }
    public boolean isSucceeded() {
        return succeeded;
    }
}
