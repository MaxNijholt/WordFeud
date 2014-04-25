package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utility.DBCommunicator;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	/*
	 * Instance Variables
	 */
	private JTextField 		username;
	private JPasswordField 	password;
	private JLabel 			name, pass;
	private JButton			login, register, spectate, exit;
	
	
	/**
	 * The panel that is used to log in to our program.
	 */
	public LoginPanel() {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		
		username 	= new JTextField();
		password 	= new JPasswordField();
		name 		= new JLabel("Username");
		pass 		= new JLabel("Password");
		
		login 		= new JButton("Login");
		register 	= new JButton("Register");
		spectate 	= new JButton("Spectate");
		exit		= new JButton("Exit");

		
		username.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		username.setFont(new Font("Arial", Font.PLAIN, 18));
		password.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		password.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setForeground(Color.BLACK);
		pass.setForeground(Color.BLACK);

		login.setBorderPainted(false);
		login.setFocusPainted(false);
		register.setBorderPainted(false);
		register.setFocusPainted(false);
		spectate.setBorderPainted(false);
		spectate.setFocusPainted(false);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		
		login.addActionListener(this);
		register.addActionListener(this);
		spectate.addActionListener(this);
		exit.addActionListener(this);
		
		this.add(username);
		this.add(password);
		this.add(name);
		this.add(pass);
		this.add(login);
		this.add(register);
		this.add(spectate);
		this.add(exit);
		
		name.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 100 - 145, 200, 30);
		username.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 100 - 120, 200, 30);
		pass.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 95 + 100, 200, 30);
		password.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 70 + 100, 200, 30);
		login.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 35 + 100, 200, 30);
		register.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 100, 200, 30);
		spectate.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 135, 95, 30);
		exit.setBounds(GUI.WIDTH / 2, GUI.HEIGHT / 2 + 135, 100, 30);
	}
	
	/**
	 * Overridden paintComponent where this panel draws its title
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		GradientPaint gradient = new GradientPaint(0, 0, new Color(180, 180, 180), getWidth(), getHeight(), new Color(255, 255, 255));
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 100));
		g2d.drawString(GUI.TITLE, (int) ((GUI.WIDTH / 2) - (g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth() / 2)), 150);
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
		System.out.println("Switch to register panel");
	}
	
	/**
	 * This methods sends you to the spectate panel
	 */
	private void spectate() {
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
