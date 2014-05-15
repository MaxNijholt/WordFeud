package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

<<<<<<< .merge_file_a03336
=======
import AccountType.Account;
>>>>>>> .merge_file_a06724
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.STextField;

/**
 * @author Max Nijholt
 * 
 */

public class SettingsPanel extends JPanel{
	private GUI gui;
<<<<<<< .merge_file_a03336
	private String user;
=======
	private Account user;
>>>>>>> .merge_file_a06724
	private SPasswordField passwordfield, passwordControle;
	private SLabel password, username;
	private STextField userfield;
	private SButton save;
	private ActionAdapter aa = new ActionAdapter();

<<<<<<< .merge_file_a03336
	public SettingsPanel(GUI gui ,String user){
=======
	public SettingsPanel(GUI gui , Account user){
>>>>>>> .merge_file_a06724
		this.gui = gui;
		this.user = user;

		this.setPreferredSize(new Dimension(GUI.WIDTH , GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.passwordfield 		= new SPasswordField("password");
		this.passwordControle 	= new SPasswordField("passwordc");
		this.password			= new SLabel("New password:", 0);
		this.username			= new SLabel("New username:", 0);
<<<<<<< .merge_file_a03336
		this.userfield			= new STextField(user);
=======
		this.userfield			= new STextField(user.getUsername());
>>>>>>> .merge_file_a06724
		this.save				= new SButton("Save", SButton.GREY);


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
	}

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(save)) {
				//TODO write what to do on save.
			}
		}
	}
}