package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginPanel extends Panel implements ActionListener {

	private JTextField 		username;
	private JPasswordField 	password;
	private JLabel 			name, pass;
	private JButton			login, register, spectate;
	
	public LoginPanel() {
		this.setPreferredSize(new Dimension(1280, 720));
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		
		username 	= new JTextField();
		password 	= new JPasswordField();
		name 		= new JLabel("Username");
		pass 		= new JLabel("Password");
		
		login 		= new JButton("Login");
		register 	= new JButton("Register");
		spectate 	= new JButton("Spectate");
		
		this.add(username);
		this.add(password);
		this.add(name);
		this.add(pass);
		this.add(login);
		this.add(register);
		this.add(spectate);
		
		name.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 145, 200, 30);
		username.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 120, 200, 30);
		pass.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 95, 200, 30);
		password.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 70, 200, 30);
		login.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 35, 200, 30);
		register.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2, 200, 30);
		spectate.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 35, 200, 30);
		
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
	private void login() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(login)) {
			if(login.getText()) {
				
			}
		}
	}
	
}
