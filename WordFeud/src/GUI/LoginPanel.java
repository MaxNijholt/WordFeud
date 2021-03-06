package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import AccountType.Account;
import Utility.DBCommunicator;
import Utility.Loader;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.SPopupMenu;
import Utility.STextField;
import Utility.SplashText;
import WordFeud.GameStone;
import WordFeud.Login;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	// Instance Variables
	private JPanel			title;
	private STextField 		username;
	private SPasswordField 	password;
	private SButton 		login, register, spectate, exit;
	private GUI 			gui;
	private Login 			l;
	private SPopupMenu		popup;
	private SplashText		sp = new SplashText(SplashText.PRE2_T, SplashText.PRE2_S, 760, 120, this);
	
	/**
	 * The panel that is used to log in to our program.
	 */
	public LoginPanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.l		= new Login(gui);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints gbc = new GridBagConstraints();
		
		title 		= new JPanel();
		username 	= new STextField("Username", 220, 40);
		password 	= new SPasswordField("Password", 220, 40);
		login		= new SButton("Login", SButton.GREY, 220, 40);
		register 	= new SButton("Register", SButton.GREY, 220, 40);
		spectate 	= new SButton("Spectate", SButton.GREY, 155, 40);
		exit 		= new SButton("Exit", SButton.GREY, 60, 40);
		
		username.addActionListener(this);
		password.addActionListener(this);
		login.addActionListener(this);
		register.addActionListener(this);
		spectate.addActionListener(this);
		exit.addActionListener(this);
		
		title.setLayout(new GridLayout(1, 8, 1, 1));
		title.setBackground(new Color(255, 255, 255, 0));
		
		String letters 	= "WORDFEUD";

		for(int i = 0; i < letters.length(); i++) {
			GameStone s = new GameStone(-1, letters.charAt(i));
			if(DBCommunicator.checkConnection() != null)
				s = new GameStone(Integer.parseInt(Loader.TILEVALUES.get(String.valueOf(letters.charAt(i)))), letters.charAt(i));
			s.setFonts(new Font("Arial", Font.BOLD, 55), new Font("Arial", Font.PLAIN, 20));
			s.setDimension(80, 80);
			title.add(new SLabel(s.getImage(), 80, 80));
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(new Color(255, 255, 255, 0));
		mainPanel.setOpaque(false);
		
		JPanel sideBySidePanel = new JPanel();
		sideBySidePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		sideBySidePanel.setBackground(new Color(255, 255, 255, 0));
		sideBySidePanel.setOpaque(false);
		sideBySidePanel.add(spectate);
		sideBySidePanel.add(exit);
		
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(5, 0, 0, 0);
		mainPanel.add(username, c);
		c.gridy++;
		mainPanel.add(password, c);
		c.gridy++;
		mainPanel.add(login, c);
		c.gridy++;
		mainPanel.add(register, c);
		c.gridy++;
		mainPanel.add(sideBySidePanel, c);
		
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 50, 0);
		this.add(title, gbc);
		gbc.gridy++;
		this.add(mainPanel, gbc);
		gui.setLoadingCursor(false);
		
		this.popup = new SPopupMenu();
	}

	/**
	 * Overridden paintComponent(Graphics g) method from JComponent used to draw the background
	 */
	public void paintComponent(Graphics g) {
		if(Loader.BACKGROUNDHD == null) {return;}
		g.drawImage(Loader.BACKGROUNDHD, 0, 0, Loader.BACKGROUNDHD.getWidth() * 2, Loader.BACKGROUNDHD.getHeight() * 2, null);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		sp.drawSplash(g);
	}
	
	/**
	 * This is a method that is testing with the DBCommunicator if the user name and password are correct
	 */
	private void login() {
		String text	= l.login(new Account(username.getText()), String.valueOf(password.getPassword()));
		if(text == "0") {
			sp.setRunning(false);
			return;
		}
		if(text != null) popup.show(username, username.getWidth() + 10, 0, 260, popup.getTextDimension(text).height, text, SButton.RED);
	}

	/**
	 * This method sends you to the register panel
	 */
	private void register() {
		gui.switchPanel(new RegisterPanel(gui));
		sp.setRunning(false);
	}

	/**
	 * This methods sends you to the spectator panel
	 */
	private void spectate() {
		gui.switchPanel(new SpectatorCompetitionsPanel(gui));
		sp.setRunning(false);
	}

	/**
	 * This method is the actionListener for the buttons in the LoginPanel
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(username)) 	{password.requestFocusInWindow();}
		if(e.getSource().equals(password)) 	{login();}
		if(e.getSource().equals(login)) 	{login();}
		if(e.getSource().equals(register)) 	{register();}
		if(e.getSource().equals(spectate)) 	{spectate();}
		if(e.getSource().equals(exit)) 		{System.exit(0);}
	}

}
