package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class LoginPanel extends JPanel implements ActionListener {

	// Instance Variables
	private SLabel			title;
	private STextField 		username;
	private SPasswordField 	password;
	private SButton 		login, register, spectate, exit;
	private GUI 			gui;

	/**
	 * The panel that is used to log in to our program.
	 */
	public LoginPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.gui = gui;
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		title 		= new SLabel("Wordfeud", SLabel.CENTER, new Font("Arial", Font.BOLD, 100));
		username 	= new STextField("Username");
		password 	= new SPasswordField("Password");

		login		= new SButton("Connect", SButton.GREY, 220, 40);
		register 	= new SButton("Register", SButton.GREY, 220, 40);
		spectate 	= new SButton("Spectate", SButton.GREY, 155, 40);
		exit 		= new SButton("Exit", SButton.GREY, 60, 40);
		
		login.addActionListener(this);
		register.addActionListener(this);
		spectate.addActionListener(this);
		exit.addActionListener(this);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel1.setBackground(getBackground());
		c.gridy = 0;
		c.insets = new Insets(5, 0, 50, 0);
		this.add(title, c);
		c.gridy++;
		c.insets = new Insets(5, 0, 0, 0);
		this.add(username, c);
		c.gridy++;
		this.add(password, c);
		c.gridy++;
		this.add(login, c);
		c.gridy++;
		this.add(register, c);
		c.gridy++;
		
		panel1.add(spectate);
		panel1.add(exit);
		this.add(panel1, c);
	}

	/**
	 * This is a method that is testing with the DBCommunicator if the user name and password are correct
	 */
	private void login() {
		if (DBCommunicator.requestData("SELECT * FROM account WHERE naam = '"
				+ username.getText() + "'") == null) {
			JOptionPane.showMessageDialog(this, "That username doesn't exist!");
			return;
		}
		if (DBCommunicator
				.requestData("SELECT * FROM account WHERE wachtwoord = '"
						+ String.copyValueOf(password.getPassword()) + "'") == null) {
			JOptionPane.showMessageDialog(this,
					"Password does not match the username!");
			return;
		}

		if (DBCommunicator.requestData(
				"SELECT * FROM account WHERE naam = '" + username.getText()
						+ "'").equals(username.getText())) {
			if (DBCommunicator.requestData(
					"SELECT wachtwoord FROM account WHERE wachtwoord = '"
							+ String.copyValueOf(password.getPassword()) + "'")
					.equals(String.copyValueOf(password.getPassword()))) {
				JOptionPane.showMessageDialog(this, "You are logged in!");
			}
		}
	}

	/**
	 * This method sends you to the register panel
	 */
	private void register() {gui.switchPanel(new RegisterPanel(gui));}

	/**
	 * This methods sends you to the spectator panel
	 */
	private void spectate() {System.out.println("switch to spectate panel");}

	/**
	 * This method is the actionListener for the buttons in the LoginPanel
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(login)) {
			login();
		}
		if (e.getSource().equals(register)) {
			register();
		}
		if (e.getSource().equals(spectate)) {
			spectate();
		}
		if (e.getSource().equals(exit)) {
			System.exit(0);
		}
	}

}
