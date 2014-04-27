package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SPasswordField;
import Utility.STextField;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	/*
	 * Instance Variables
	 */
	private STextField 		username;
	private SPasswordField 	password;
	private SButton			login, register, spectate, exit;
	private GUI 			gui;
	
	/**
	 * The panel that is used to log in to our program.
	 */
	public LoginPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.gui = gui;
		
		username 	= new STextField("Username");
		password 	= new SPasswordField("Password");
		
		login 		= new SButton("Connect", SButton.GREY);
		register 	= new SButton("Register", SButton.GREY);
		spectate 	= new SButton("Spectate", SButton.GREY);
		exit		= new SButton("Exit", SButton.GREY);
		
		login.addActionListener(this);
		register.addActionListener(this);
		spectate.addActionListener(this);
		exit.addActionListener(this);
		
		this.add(username);
		this.add(password);
		this.add(login);
		this.add(register);
		this.add(spectate);
		this.add(exit);
		
		username.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 + 100 - 160, 220, 40);
		password.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 115 + 100, 220, 40);
		login.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 70 + 100, 220, 40);
		register.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 35 + 110, 220, 40);
		spectate.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 + 120, 145, 40);
		exit.setBounds(GUI.WIDTH / 2 + 40, GUI.HEIGHT / 2 + 120, 70, 40);
	}
	
	/**
	 * Overridden paintComponent where this panel draws its title
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(94, 94, 94));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 100));
		g2d.drawString(GUI.TITLE, (int) ((GUI.WIDTH / 2) - (g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth() / 2)), 150);
		for(int i = 0; i < 4; i++) {
			g2d.drawLine(30 + (int)((GUI.WIDTH / 2) - (g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth() / 2)), 170 + i, (int)((GUI.WIDTH / 2) - (g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth() / 2) + g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth()) - 30, 170 + i);
		}
	}
	
	/**
	 * This is a method that is testing with the DBCommunicator if the username and password are correct 
	 */
	private void login() {
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getText() + "'") == null) {
			JOptionPane.showMessageDialog(this, "That username doesn't exist!");	
			return;
		}
		if(DBCommunicator.requestData("SELECT * FROM account WHERE wachtwoord = '" + String.copyValueOf(password.getPassword()) + "'") == null) {
			JOptionPane.showMessageDialog(this, "Password does not match the username!");
			return;
		}
		
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getText() + "'").equals(username.getText())) {
			if(DBCommunicator.requestData("SELECT wachtwoord FROM account WHERE wachtwoord = '" + String.copyValueOf(password.getPassword()) + "'").equals(String.copyValueOf(password.getPassword()))) {
				JOptionPane.showMessageDialog(this, "You are logged in!");
			}
		}
	}
	
	/**
	 * This method sends you to the register panel
	 */
	private void register() {
		gui.switchPanel(new RegisterPanel(gui));
	}
	
	/**
	 * This methods sends you to the spectate panel
	 */
	private void spectate() {
		//TODO: add the panel switcher via the method: 'gui.switchPanel(new SpectatePanel(gui));'
		System.out.println("switch to spectate panel");
	}

	/**
	 * This method is the actionListener for the buttons in the LoginPanel 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(login)) {
			login();
		}
		if(e.getSource().equals(register)) {
			register();
		}
		if(e.getSource().equals(spectate)) {
			spectate();
		}
		if(e.getSource().equals(exit)) {
			System.exit(0);
		}
	}
	
}
