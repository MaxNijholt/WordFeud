package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.MComboBox;
import Utility.SButton;
import Utility.SLabel;

/**
 * @author Max Nijholt
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class AdminPanel extends JPanel {
	private MComboBox Mcombo = new MComboBox(150, 25, null);
	private Vector<String> players;
	private SLabel selectPlayer;
	private GUI gui;
	private ArrayList<SButton> buttonCollection = new ArrayList<SButton>();
	private SButton newPlayer = new SButton("New account", SButton.GREY),
			editPlayer = new SButton("Edit Player", SButton.GREY),
			blacklist = new SButton("Blacklist", SButton.GREY),
			grantMod = new SButton("Grant Mod", SButton.GREY),
			grantAdmin = new SButton("Grant Admin", SButton.GREY),
			grantPlayer = new SButton("Grant Player", SButton.GREY),
			revokeMod = new SButton("Revoke Mod", SButton.GREY),
			revokeAdmin = new SButton("Revoke Admin", SButton.GREY),
			revokePlayer = new SButton("Revoke Player", SButton.GREY);
	private ActionAdapter aa = new ActionAdapter();

	public AdminPanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.gui = gui;

		this.selectPlayer = new SLabel("Select player: ", 0);
		this.players = new Vector<String>();

		// Adding components on the right locations
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		this.add(this.selectPlayer, c);
		this.add(this.Mcombo, c);
		c.gridy++;
		this.add(this.blacklist, c);
		c.gridy++;
		c.gridx = c.gridx + 3;
		this.add(this.newPlayer, c);
		c.gridx++;
		this.add(this.editPlayer, c);
		c.gridx--;
		c.gridy++;
		this.add(this.grantPlayer, c);
		c.gridx++;
		this.add(this.revokePlayer, c);
		c.gridx--;
		c.gridy++;
		this.add(this.grantMod, c);
		c.gridx++;
		this.add(this.revokeMod, c);
		c.gridx--;
		c.gridy++;
		this.add(this.grantAdmin, c);
		c.gridx++;
		this.add(this.revokeAdmin, c);

		// Adding buttons to the ButtonCollection.
		this.buttonCollection.add(this.blacklist);
		
		this.buttonCollection.add(blacklist);
		this.buttonCollection.add(editPlayer);
		this.buttonCollection.add(newPlayer);
		this.buttonCollection.add(grantPlayer);
		this.buttonCollection.add(revokePlayer);
		this.buttonCollection.add(grantAdmin);
		this.buttonCollection.add(grantMod);
		this.buttonCollection.add(revokeAdmin);
		this.buttonCollection.add(revokeMod);
		
		for(SButton sb : buttonCollection){
			sb.addActionListener(aa);
		}
	}

	/**
	 * Method to remove rights from selected player
	 */
	private void removePrivilege(String player, String right) {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ player + "' AND rol_type='" + right + "'");
	}

	/**
	 * Method to add rights to selected player
	 */
	private void addPrivilege(String player, String right) {
		DBCommunicator
				.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
						+ player + "','" + right + "')");
	}

	/**
	 * Method for a more easy lookup player rights
	 * @return ArrayList with data 0 up to 3 Strings
	 */
	private ArrayList<String> getUserRights(String player) {
		ArrayList<String> data = DBCommunicator
				.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam='"
						+ player + "'");
		return data;
	}
	
	

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(grantAdmin)) {
				addPrivilege(Mcombo.getSelectedItem(), "Administrator");
			}
			if (e.getSource().equals(grantMod)) {
				addPrivilege(Mcombo.getSelectedItem(), "Moderator");
			}
			if (e.getSource().equals(revokeMod)) {
				removePrivilege(Mcombo.getSelectedItem(), "Moderator");
			}
			if (e.getSource().equals(revokeAdmin)) {
				removePrivilege(Mcombo.getSelectedItem(), "Administrator");
			}
			if (e.getSource().equals(revokePlayer)) {
				removePrivilege(Mcombo.getSelectedItem(), "Player");
			}
			if (e.getSource().equals(grantPlayer)) {
				addPrivilege(Mcombo.getSelectedItem(), "Player");
			}
			if (e.getSource().equals(blacklist)) {
				// TODO Might need some editing?
				removePrivilege(Mcombo.getSelectedItem(), "Administrator");
				removePrivilege(Mcombo.getSelectedItem(), "Moderator");
				removePrivilege(Mcombo.getSelectedItem(), "Player");
			}
			if (e.getSource().equals(editPlayer)) {
				// TODO
			}
			if (e.getSource().equals(newPlayer)) {
				// TODO
			}
		}
	}

	/**
	 * method to update the buttons to have the right color.
	 */
	public void update() {
		ArrayList<String> rights = this.getUserRights(this.Mcombo
				.getSelectedItem());
		if (rights.size() != 0) {
			for (String s : rights) {
				for(SButton sb : buttonCollection){
					sb.setColor(SButton.CYAN);
				}
				if (s.equals("Player")) {
					this.grantPlayer.setColor(SButton.GREY);
				} else if (s.equals("Moderator")) {
					this.grantMod.setColor(SButton.GREY);
				} else if (s.equals("Administrator")) {
					this.grantAdmin.setColor(SButton.GREY);
				}
			}
		}
	}
}
