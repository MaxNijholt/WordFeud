package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utility.DBCommunicator;
import Utility.SButton;

@SuppressWarnings("serial")
public class RegisterPanel extends Panel implements ActionListener {

	private JTextField		username;
	private JPasswordField	password, passwordValidate;
	private SButton			register, back;
	private GUI				gui;
	
	public RegisterPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.gui = gui;
		
		username 	= new JTextField() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(this.getText() == null || (this.getText().length() < 1)) {
					Graphics2D g2d = (Graphics2D)g.create();
					g2d.setFont(this.getFont().deriveFont(Font.ITALIC));
					g2d.setColor(Color.BLACK);
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g2d.drawString("Username", 5, 25);
					g2d.dispose();
				}
			}
		};
		password 	= new JPasswordField() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(String.valueOf(this.getPassword()) == null || (String.valueOf(this.getPassword()).length() < 1)) {
					Graphics2D g2d = (Graphics2D)g.create();
					g2d.setFont(this.getFont().deriveFont(Font.ITALIC));
					g2d.setColor(Color.BLACK);
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g2d.drawString("Password", 5, 25);
					g2d.dispose();
				}
			}
		};
		passwordValidate 	= new JPasswordField() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(String.valueOf(this.getPassword()) == null || (String.valueOf(this.getPassword()).length() < 1)) {
					Graphics2D g2d = (Graphics2D)g.create();
					g2d.setFont(this.getFont().deriveFont(Font.ITALIC));
					g2d.setColor(Color.BLACK);
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g2d.drawString("Password validate", 5, 25);
					g2d.dispose();
				}
			}
		};
		register	= new SButton("Register", SButton.GREY);
		back		= new SButton("Back", SButton.GREY);
		
		register.addActionListener(this);
		back.addActionListener(this);
		
		username.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		username.setFont(new Font("Arial", Font.PLAIN, 16));
		password.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		password.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordValidate.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		passwordValidate.setFont(new Font("Arial", Font.PLAIN, 16));
		
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
		//GradientPaint gradient = new GradientPaint(0, 0, new Color(180, 180, 180), getWidth(), getHeight(), new Color(255, 255, 255));
		//g2d.setPaint(gradient);
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
