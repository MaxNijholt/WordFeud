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

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SPasswordField;
import Utility.STextField;

@SuppressWarnings("serial")
public class RegisterPanel extends Panel implements ActionListener {

	private STextField		username;
	private SPasswordField	password, passwordValidate;
	private SButton			register, back;
	private GUI				gui;
	
	public RegisterPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.gui = gui;
		
		username 			= new STextField("Username");
		password 			= new SPasswordField("Password");
		passwordValidate 	= new SPasswordField("Confirm Password");
		
		register			= new SButton("Register", SButton.GREY);
		back				= new SButton("Back", SButton.GREY);
		
		register.addActionListener(this);
		back.addActionListener(this);
		
		this.add(username);
		this.add(password);
		this.add(passwordValidate);
		this.add(register);
		this.add(back);
		
		username.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 + 100 - 160, 220, 40);
		password.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 115 + 100, 220, 40);
		passwordValidate.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 70 + 100, 220, 40);
		register.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2 - 25 + 100, 220, 40);
		back.setBounds(GUI.WIDTH / 2 - 110, GUI.HEIGHT / 2  + 120, 220, 40);
	}
	
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
