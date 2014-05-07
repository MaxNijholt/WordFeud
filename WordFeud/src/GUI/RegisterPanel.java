package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SPasswordField;
import Utility.STextField;
import WordFeud.GameStone;
import WordFeud.Login;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel implements ActionListener {

	private JPanel			title;
	private STextField		username;
	private SPasswordField	password, passwordValidate;
	private SButton			register, back;
	private GUI				gui;
	private Login			l;
	
	public RegisterPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.gui 	= gui;
		this.l		= new Login(gui);
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		title 				= new JPanel();
		username 			= new STextField("Username", 220, 40);
		password 			= new SPasswordField("Password", 220, 40);
		passwordValidate 	= new SPasswordField("Confirm Password", 220, 40);
		
		register			= new SButton("Register", SButton.GREY, 220, 40);
		back				= new SButton("Back", SButton.GREY, 220, 40);
		
		register.addActionListener(this);
		back.addActionListener(this);
		
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
		String text = l.register(username.getText(), String.valueOf(password.getPassword()), String.valueOf(passwordValidate.getPassword()));
		if(text == "0") {return;}
		if(text != null) {
			Graphics2D g2d 	= (Graphics2D)this.getGraphics();
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(register)) 	{register();}
		if(e.getSource().equals(back)) 		{gui.switchPanel(new LoginPanel(gui));}
	}
}
