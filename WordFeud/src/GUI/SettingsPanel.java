package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import AccountType.Account;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.STextField;

/**
 * @author Max Nijholt
 * 
 */

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel{
	private GUI gui;
	private Account user;
	private SPasswordField passwordfield, passwordControle;
	private SLabel password, username;
	private STextField userfield;
	private SButton save;
	private JPanel allPanel;
	private MenuPanel mp;
	private ActionAdapter aa = new ActionAdapter();

	public SettingsPanel(GUI gui , Account user){
		this.gui = gui;
		gui.setLoadingCursor(true);
		this.user = user;
		
		mp = new MenuPanel(gui, null);
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		
		allPanel = new JPanel();
		allPanel.setPreferredSize(new Dimension(GUI.WIDTH , GUI.HEIGHT));
		allPanel.setBackground(new Color(94, 94, 94));

		allPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.passwordfield 		= new SPasswordField("password");
		this.passwordControle 	= new SPasswordField("passwordc");
		this.password			= new SLabel("New password:", 0);
		this.username			= new SLabel("New username:", 0);

		this.userfield			= new STextField(user.getUsername());
		this.save				= new SButton("Save", SButton.GREY);


		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		allPanel.add(username, c);
		c.gridy ++;
		allPanel.add(userfield, c);
		c.gridy ++;
		c.gridx ++;
		allPanel.add(password, c);
		c.gridx ++;
		allPanel.add(passwordfield, c);
		c.gridy ++;
		allPanel.add(passwordControle, c);
		c.gridx++;
		c.gridy++;
		allPanel.add(save, c);
		gui.setLoadingCursor(false);
		
		this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
	}

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(save)) {
				//TODO write what to do on save.
			}
		}
	}
}