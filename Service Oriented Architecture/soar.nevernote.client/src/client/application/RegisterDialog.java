package client.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class RegisterDialog extends JDialog {
	 
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField nickNameField;
    private JLabel lbLogin;
    private JLabel lbPassword;
    private JLabel lbName;
    private JLabel lbNickName;
    private JButton btnRegister;
    private JButton btnCancel;
    private boolean succeeded;
 
    public RegisterDialog(String nameAction, JFrame parent) {
    	  super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbLogin = new JLabel("Email: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbLogin, cs);
 
        emailField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(emailField, cs);
 
        
       
 
       
        panel.setBorder(new LineBorder(Color.GRAY));
 
        lbName = new JLabel("Name: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbName, cs);
 
        nameField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(nameField, cs);

        lbNickName = new JLabel("Nick Name: ");
    	lbPassword = new JLabel("Password: ");
    	passwordField = new JPasswordField(20);
    	nickNameField = new JTextField(20);
    	
    	passwordField.setText("passsword");
    	nickNameField.setText("nickname");
    	
        if(nameAction.equals("Register")) {  
        	passwordField.setText("");
        	nickNameField.setText("");
        
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            panel.add(lbPassword, cs);
            
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 2;
         
        	panel.add(passwordField, cs);
        	
            cs.gridx = 0;
            cs.gridy = 3;
            cs.gridwidth = 1;
        	panel.add(lbNickName, cs);
        	
            cs.gridx = 1;
            cs.gridy = 3;
            cs.gridwidth = 2;
        	panel.add(nickNameField, cs);
        }
        btnRegister = new JButton(nameAction);
 
        btnRegister.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (!(emailField.getText().isEmpty())&&!(passwordField.getText().isEmpty())&&!(nameField.getText().isEmpty())&&!(nickNameField.getText().isEmpty())) {
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterDialog.this,
                            "You should provide your email, password, name and nick name.",
                            "Register/Update",
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
        bp.add(btnRegister);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getEmail() {
        return emailField.getText().trim();
    }
    public String getName() {
        return nameField.getText().trim();
    }
    public String getNickName() {
        return nickNameField.getText().trim();
    }
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    public void setEmail(String email) {
         emailField.setText(email);;
    }
    public void setName(String name) {
        nameField.setText(name);;
    }
    public void setNickName(String nickName) {
        nickNameField.setText(nickName);;
    }
    public boolean isSucceeded() {
        return succeeded;
    }
}

