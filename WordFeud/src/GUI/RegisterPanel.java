package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.STextField;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel implements ActionListener {

	private SLabel			title;
	private STextField		username;
	private SPasswordField	password, passwordValidate;
	private SButton			register, back;
	private GUI				gui;
	
	public RegisterPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.gui = gui;
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		title 				= new SLabel("Wordfeud", SLabel.CENTER, new Font("Arial", Font.BOLD, 100));
		username 			= new STextField("Username");
		password 			= new SPasswordField("Password");
		passwordValidate 	= new SPasswordField("Confirm Password");
		
		register			= new SButton("Register", SButton.GREY, 220, 40);
		back				= new SButton("Back", SButton.GREY, 220, 40);
		
		register.addActionListener(this);
		back.addActionListener(this);
		
		c.gridy = 0;
		c.insets = new Insets(5, 0, 50, 0);
		this.add(title, c);
		c.gridy++;
		c.insets = new Insets(5, 0, 0, 0);
		this.add(username, c);
		c.gridy++;
		this.add(password, c);
		c.gridy++;
		this.add(passwordValidate, c);
		c.gridy++;
		this.add(register, c);
		c.gridy++;
		this.add(back, c);
	}
	
	private void register() {
		if(!(username.getText().length() < 1) || !username.getText().isEmpty()) {
			if(String.valueOf(password.getPassword()).equals(String.valueOf(passwordValidate.getPassword()))) {
				if(DBCommunicator.requestData("SELECT naam FROM account WHERE naam = '" + username.getText() + "'") != null) {
					JOptionPane.showMessageDialog(this, "That username is not available");
					return;
				}
				else if(!String.valueOf(password.getPassword()).isEmpty() && String.valueOf(password.getPassword()).length() < 1) {
					DBCommunicator.writeData("INSERT INTO account(naam, wachtwoord) VALUES('" + username.getText() + "', '" + String.copyValueOf(password.getPassword()) +"')");
					JOptionPane.showMessageDialog(this, "You have been registered!");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Passwords do not match!");
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Please fill in an username!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(register)) {
			register();
		}
		if(e.getSource().equals(back)) {
			gui.switchPanel(new LoginPanel(gui));
		}
		
	}
	
}
