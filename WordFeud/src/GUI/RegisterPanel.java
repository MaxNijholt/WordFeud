package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utility.DBCommunicator;

@SuppressWarnings("serial")
public class RegisterPanel extends Panel {

	private JTextField		username;
	private JPasswordField	password;
	private JLabel 			name, pass;
	private JButton			register;
	
	public RegisterPanel() {
		this.setPreferredSize(new Dimension(1280, 720));
		this.setLayout(null);
		
		username 	= new JTextField();
		password	= new JPasswordField();
		name		= new JLabel("Username");
		pass		= new JLabel("Password");
		register	= new JButton("Register");
		
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		
		this.add(username);
		this.add(password);
		this.add(name);
		this.add(pass);
		this.add(register);
		
		name.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 95, 200, 30);
		username.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 70, 200, 30);
		pass.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 45, 200, 30);
		password.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 - 20, 200, 30);
		register.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 15, 200, 30);
	
	}
	
	public void paintComponent(Graphics g) {}
	
	private void register() {
		if(DBCommunicator.requestData("SELECT naam FROM account WHERE naam = '" + username.getText() + "'") != null) {
			JOptionPane.showMessageDialog(this, "That username is not available");
			return;
		}
		DBCommunicator.writeData("INSERT INTO account(naam, wachtwoord) VALUES('" + username.getText() + "', '" + String.copyValueOf(password.getPassword()) +"')");
	}
	
}
