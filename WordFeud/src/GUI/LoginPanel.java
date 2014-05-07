package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Utility.ImageLoader;
import Utility.SButton;
import Utility.SPasswordField;
import Utility.STextField;
import WordFeud.GameStone;
import WordFeud.Login;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	// Instance Variables
	private JPanel			title;
	private STextField 		username;
	private SPasswordField 	password;
	private SButton 		login, register, spectate, exit;
	private GUI 			gui;
	private Login 			l;
	
	/**
	 * The panel that is used to log in to our program.
	 */
	public LoginPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.gui 	= gui;
		this.l		= new Login(gui);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints gbc = new GridBagConstraints();
		
		title 		= new JPanel();
		username 	= new STextField("Username", 220, 40);
		password 	= new SPasswordField("Password", 220, 40);

		login		= new SButton("Connect", SButton.GREY, 220, 40);
		register 	= new SButton("Register", SButton.GREY, 220, 40);
		spectate 	= new SButton("Spectate", SButton.GREY, 155, 40);
		exit 		= new SButton("Exit", SButton.GREY, 60, 40);
		
		login.addActionListener(this);
		register.addActionListener(this);
		spectate.addActionListener(this);
		exit.addActionListener(this);
		
		title.setLayout(new GridLayout(1, 8, 2, 2));
		title.setBackground(new Color(255, 255, 255, 0));
		String letters 	= "WORDFEUD";
		String values	= "51224122";
		for(int i = 0; i < letters.length(); i++) {
			GameStone s = new GameStone(Integer.valueOf(Character.getNumericValue(values.charAt(i))), letters.charAt(i));
			s.setDimension(70, 70);
			s.setFonts(new Font("Arial", Font.BOLD, 55), new Font("Arial", Font.PLAIN, 20));
			title.add(s);
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(new Color(255, 255, 255, 0));
		mainPanel.setOpaque(false);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel1.setBackground(new Color(255, 255, 255, 0));
		panel1.setOpaque(false);
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
		
		panel1.add(spectate);
		panel1.add(exit);
		mainPanel.add(panel1, c);
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 50, 0);
		this.add(title, gbc);
		gbc.gridy++;
		this.add(mainPanel, gbc);
	}

	public void paintComponent(Graphics g) {
		if(ImageLoader.BACKGROUND == null) {return;}
		g.drawImage(ImageLoader.BACKGROUND, 0, 0, ImageLoader.BACKGROUND.getWidth() * 2, ImageLoader.BACKGROUND.getHeight() * 2, null);
	}
	
	/**
	 * This is a method that is testing with the DBCommunicator if the user name and password are correct
	 */
	private void login() {
		String text	= l.login(username.getText(), String.valueOf(password.getPassword()));
		if(text == "0") {return;}
		if(text != null) {
			Graphics2D g2d = (Graphics2D)this.getGraphics();
			FontMetrics fm = this.getFontMetrics(new Font("Arial", Font.BOLD, 16));
			g2d.setFont(new Font("Arial", Font.BOLD, 16));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setColor(Color.RED);
			g2d.fillRoundRect(10, 10, 335, 30, 10, 10);
			g2d.setColor(Color.WHITE);
			g2d.drawString(text, (350 / 2) - (fm.stringWidth(text) / 2), (0 + (50+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		}
	}

	/**
	 * This method sends you to the register panel
	 */
	private void register() {gui.switchPanel(new RegisterPanel(gui));}

	/**
	 * This methods sends you to the spectator panel
	 */
	private void spectate() {gui.switchPanel(new SpectatorPanel());}

	/**
	 * This method is the actionListener for the buttons in the LoginPanel
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(login)) 	{login();}
		if(e.getSource().equals(register)) 	{register();}
		if(e.getSource().equals(spectate)) 	{spectate();}
		if(e.getSource().equals(exit)) 		{System.exit(0);}
	}

}
