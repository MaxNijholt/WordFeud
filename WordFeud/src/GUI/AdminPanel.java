package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import AccountType.Account;
import AccountType.Administrator;
import Utility.DBCommunicator;
import Utility.Loader;
import Utility.MComboBox;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.STextField;

/**
 * @author Max Nijholt
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class AdminPanel extends JPanel {
	private MComboBox 			playerLookupBox = new MComboBox(150, 25, null, this);
	private Vector<String> 		players;
	private SLabel 				selectPlayer;
	private GUI 				gui;
	private ArrayList<SButton> 	buttonCollection = new ArrayList<SButton>();
	private SButton 			newPlayer = new SButton("New account", SButton.CYAN),
								editPlayer = new SButton("Edit Player", SButton.GREY),
								grantMod = new SButton("Grant Mod", SButton.GREY),
								grantAdmin = new SButton("Grant Admin", SButton.GREY),
								grantPlayer = new SButton("Grant Player", SButton.GREY),
								revokeMod = new SButton("Revoke Mod", SButton.GREY),
								revokeAdmin = new SButton("Revoke Admin", SButton.GREY),
								revokePlayer = new SButton("Revoke Player", SButton.GREY);
	private ActionAdapter 		aa = new ActionAdapter();
	private Administrator		admin = null;
	private RegisterPanel 		rp;
	private JFrame 				newPlayerFrame, 
								editPlayerFrame;
	private JPanel 				newPlayerPanel, 
								wrapper, 
								editPlayerPanel;
	private String 				title = "New Player";
	private MenuPanel 			mp;
	private STextField 			username;
	private SPasswordField 		password, 
								passwordValidate;
	private SButton 			register, 
								back;

	public AdminPanel(GUI gui) {
		rp = new RegisterPanel(gui);
		this.gui = gui;
		gui.setLoadingCursor(true);

		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BorderLayout());

		if (gui.getApplication().getCurrentAccount().getPlayer() != null) {
			mp = new MenuPanel(gui, new PlayerPanel(gui));
			this.add(mp, BorderLayout.NORTH);
		}

		wrapper = new JPanel();
		wrapper.setLayout(new GridBagLayout());
		wrapper.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();

		this.admin = gui.getApplication().getCurrentAccount().getAdmin();

		this.selectPlayer = new SLabel("Select player: ", 0);
		this.players = new Vector<String>();

		// Adding components on the right locations
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		wrapper.add(this.selectPlayer, c);
		wrapper.add(this.playerLookupBox, c);
		c.gridy++;
		c.gridx = c.gridx + 3;
		wrapper.add(this.newPlayer, c);
		c.gridx++;
		wrapper.add(this.editPlayer, c);
		c.gridx--;
		c.gridy++;
		wrapper.add(this.grantPlayer, c);
		c.gridx++;
		wrapper.add(this.revokePlayer, c);
		c.gridx--;
		c.gridy++;
		wrapper.add(this.grantMod, c);
		c.gridx++;
		wrapper.add(this.revokeMod, c);
		c.gridx--;
		c.gridy++;
		wrapper.add(this.grantAdmin, c);
		c.gridx++;
		wrapper.add(this.revokeAdmin, c);

		// Adding buttons to the ButtonCollection.
		this.buttonCollection.add(editPlayer);
		this.buttonCollection.add(newPlayer);
		this.buttonCollection.add(grantPlayer);
		this.buttonCollection.add(revokePlayer);
		this.buttonCollection.add(grantAdmin);
		this.buttonCollection.add(grantMod);
		this.buttonCollection.add(revokeAdmin);
		this.buttonCollection.add(revokeMod);

		for (SButton sb : buttonCollection) {
			sb.addActionListener(aa);
		}

		this.playerLookupBox.getField().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				update();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				update();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				update();
			}
		});
		this.add(wrapper, BorderLayout.CENTER);
		gui.setLoadingCursor(false);
	}

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!(playerLookupBox.getSelectedItem().equals("") || playerLookupBox
					.getSelectedItem().equals(null))) {
				if (e.getSource().equals(grantAdmin)) {
					admin.addPrivilege(playerLookupBox.getSelectedItem(),
							"Administrator");
				}
				if (e.getSource().equals(grantMod)) {
					admin.addPrivilege(playerLookupBox.getSelectedItem(),
							"Moderator");
				}
				if (e.getSource().equals(revokeMod)) {
					admin.removePrivilege(playerLookupBox.getSelectedItem(),
							"Moderator");
				}
				if (e.getSource().equals(revokeAdmin)) {
					admin.removePrivilege(playerLookupBox.getSelectedItem(),
							"Administrator");
				}
				if (e.getSource().equals(revokePlayer)) {
					admin.removePrivilege(playerLookupBox.getSelectedItem(),
							"Player");
				}
				if (e.getSource().equals(grantPlayer)) {
					admin.addPrivilege(playerLookupBox.getSelectedItem(),
							"Player");
				}
				if (e.getSource().equals(editPlayer)) {
					editPlayerFrame = new JFrame();
					editPlayerPanel = new SettingsPanel(gui, new Account(
							playerLookupBox.getSelectedItem()), editPlayerFrame);
					editPlayerFrame.setResizable(false);
					editPlayerFrame.setTitle(title);
					editPlayerFrame.setContentPane(editPlayerPanel);
					editPlayerFrame.setIconImage(Loader.ICON);

					editPlayerFrame.pack();
					editPlayerFrame.setLocationRelativeTo(null);
					editPlayerFrame.setVisible(true);
				}

				update();
			}
			if (e.getSource().equals(newPlayer)) {
				newPlayerFrame = new JFrame();
				newPlayerPanel = new JPanel();

				username = new STextField("Username", 220, 40);
				password = new SPasswordField("Password", 220, 40);
				passwordValidate = new SPasswordField("Confirm Password", 220,
						40);
				register = new SButton("Register", SButton.GREY, 220, 40);
				back = new SButton("Back", SButton.GREY, 220, 40);

				newPlayerFrame.setResizable(false);
				newPlayerFrame.setTitle(title);
				newPlayerFrame.setContentPane(newPlayerPanel);
				newPlayerFrame.setIconImage(Loader.ICON);
				// newPlayerFrame.add(newPlayerPanel);

				newPlayerPanel.setLayout(new GridBagLayout());
				newPlayerPanel.setPreferredSize(new Dimension(300, 300));
				newPlayerPanel.setBackground(new Color(94, 94, 94));

				GridBagConstraints c = new GridBagConstraints();

				c.gridy = 0;
				c.insets = new Insets(5, 0, 0, 0);
				newPlayerPanel.add(username, c);
				c.gridy++;
				newPlayerPanel.add(password, c);
				c.gridy++;
				newPlayerPanel.add(passwordValidate, c);
				c.gridy++;
				newPlayerPanel.add(register, c);
				c.gridy++;
				newPlayerPanel.add(back, c);

				username.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent action) {
						if (action.getSource().equals(username)) {
							password.requestFocusInWindow();
						}

					}
				});

				password.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent action) {
						if (action.getSource().equals(password)) {
							passwordValidate.requestFocusInWindow();
						}

					}
				});

				passwordValidate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent action) {
						if (action.getSource().equals(passwordValidate)) {
							registerPlayer();
						}

					}
				});

				register.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent action) {
						if (action.getSource().equals(register)) {
							registerPlayer();
							newPlayerFrame.dispose();
						}

					}
				});

				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent action) {
						if (action.getSource().equals(back)) {
							newPlayerFrame.dispose();
						}

					}
				});

				newPlayerFrame.pack();
				newPlayerFrame.setLocationRelativeTo(null);
				newPlayerFrame.setVisible(true);

			}
		}

		private void registerPlayer() {
			boolean allowed = true;
			for (int i = 0; i < username.getText().length(); i++) {
				if (Character.isWhitespace(username.getText().charAt(i))) {
					allowed = false;
				}

			}
			String[] safe = new String[] { "\"", "\'" };
			for (int i = 0; i < safe.length; i++) {
				if (username.getText().contains(safe[i])
						|| String.valueOf(password.getPassword()).contains(
								safe[i])) {
					allowed = false;
				}

			}
			if (allowed) {
				if (DBCommunicator
						.requestData("SELECT naam FROM account WHERE naam = '"
								+ username.getText() + "'") == null) {
					if (String.valueOf(password.getPassword()).equals(
							String.valueOf(passwordValidate.getPassword()))) {
						if (!String.valueOf(password.getPassword()).isEmpty()
								&& !(String.valueOf(password.getPassword())
										.length() < 1)) {
							DBCommunicator
									.writeData("INSERT INTO account(naam, wachtwoord) VALUES('"
											+ username.getText()
											+ "', '"
											+ String.valueOf(password
													.getPassword()) + "')");
							DBCommunicator
									.writeData("INSERT INTO accountrol(account_naam, rol_type) VALUES('"
											+ username.getText()
											+ "', 'Player')");
						}
					}
				}

			}
		}
	}

	/**
	 * method to update the buttons to have the right color.
	 */
	public void update() {
		if (this.playerLookupBox.getSelectedItem().equals("")) {
			for (SButton sb : buttonCollection) {
				sb.setColor(SButton.GREY);
				sb.setEnabled(false);
			}
			this.newPlayer.setColor(SButton.CYAN);
			this.newPlayer.setEnabled(true);
		} else {
			for (SButton sb : buttonCollection) {
				sb.setColor(SButton.CYAN);
				sb.setEnabled(true);
			}
			Account acc = new Account(this.playerLookupBox.getSelectedItem());
			if (acc.isPlayer()) {
				this.grantPlayer.setColor(SButton.GREY);
				this.grantPlayer.setEnabled(false);
			} else {
				this.revokePlayer.setColor(SButton.GREY);
				this.revokePlayer.setEnabled(false);
			}
			if (acc.isModerator()) {
				this.grantMod.setColor(SButton.GREY);
				this.grantMod.setEnabled(false);
			} else {
				this.revokeMod.setColor(SButton.GREY);
				this.revokeMod.setEnabled(false);
			}
			if (acc.isAdministrator()) {
				this.grantAdmin.setColor(SButton.GREY);
				this.grantAdmin.setEnabled(false);
			} else {
				this.revokeAdmin.setColor(SButton.GREY);
				this.revokeAdmin.setEnabled(false);
			}
		}
		repaint();
	}
}
