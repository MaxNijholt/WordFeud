package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AccountType.Account;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.SPopupMenu;
import Utility.STextField;

/**
 * @author Max Nijholt
 * 
 */

public class SettingsPanel extends JPanel{
	private GUI gui;
	private Account user;
	private SPasswordField passwordfield, passwordControle;
	private SLabel password, username;
	private STextField userfield;
	private SButton save;
	private ActionAdapter aa = new ActionAdapter();
	private SPopupMenu pop = new SPopupMenu();

	public SettingsPanel(GUI gui , Account user){
		this.gui = gui;
		gui.setLoadingCursor(true);
		this.user = user;

		this.setPreferredSize(new Dimension(GUI.WIDTH , GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.passwordfield 		= new SPasswordField("password");
		this.passwordControle 	= new SPasswordField("passwordc");
		this.password			= new SLabel("New password:", 0);
		this.username			= new SLabel("New username:", 0);

		this.userfield			= new STextField(user.getUsername());
		this.save				= new SButton("Save", SButton.GREY);
		this.save.addActionListener(aa);


		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		this.add(username, c);
		c.gridy ++;
		this.add(userfield, c);
		c.gridy ++;
		c.gridx ++;
		this.add(password, c);
		c.gridx ++;
		this.add(passwordfield, c);
		c.gridy ++;
		this.add(passwordControle, c);
		c.gridx++;
		c.gridy++;
		this.add(save, c);
		gui.setLoadingCursor(false);
	}

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(save)) {
				gui.getApplication().getCurrentAccount().changeUsername(userfield.getText());
				if(passwordfield.getText().equals(passwordControle.getText())){
					gui.getApplication().getCurrentAccount().changePassword(passwordfield.getText());
				} else {
					String s =  "Passwords do not match!";
					pop.show(gui, passwordfield.getX()+100, passwordfield.getY(), 300, 20, s, Color.red);
				}
			}
		}
	}
}