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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;

/**
 * @author Max Nijholt
 * 
 */

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class AdminPanel extends JPanel implements ItemListener {
	private JComboBox playerList = new JComboBox();
	private Vector<String> players;
	private SLabel selectPlayer;
	private GUI gui;
	private SButton newPlayer = new SButton("New account", SButton.GREY),
			editPlayer = new SButton("Edit", SButton.GREY),
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

		this.playerList.setEditable(true);
		this.playerList.addItemListener(this);

		this.selectPlayer = new SLabel("Select player: ", 0);
		this.players = new Vector<String>();

		//Adding components on the right locations
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		this.add(this.selectPlayer, c);
		this.add(this.playerList, c);
		c.gridy ++;
		this.add(this.blacklist, c);
		c.gridy ++;
		c.gridx = c.gridx + 3;
		this.add(this.newPlayer, c);
		c.gridx ++;
		this.add(this.editPlayer, c);
		c.gridx --;
		c.gridy ++;
		this.add(this.grantPlayer, c);
		c.gridx ++;
		this.add(this.revokePlayer, c);
		c.gridx --;
		c.gridy ++;
		this.add(this.grantMod, c);
		c.gridx ++;
		this.add(this.revokeMod, c);
		c.gridx --;
		c.gridy ++;
		this.add(this.grantAdmin, c);
		c.gridx ++;
		this.add(this.revokeAdmin, c);

		
		//Adding actionadapter to the components.
		this.blacklist.addActionListener(aa);
		this.editPlayer.addActionListener(aa);
		this.newPlayer.addActionListener(aa);
		this.grantPlayer.addActionListener(aa);
		this.revokePlayer.addActionListener(aa);
		this.grantAdmin.addActionListener(aa);
		this.grantMod.addActionListener(aa);
		this.revokeAdmin.addActionListener(aa);
		this.revokeMod.addActionListener(aa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(playerList.getSelectedItem());
			ArrayList<String> data = null;
			data = DBCommunicator
					.requestMoreData("SELECT naam FROM account WHERE naam LIKE '"
							+ playerList.getSelectedItem() + "%'");

			players.clear();
			playerList.removeAll();

			for (String s : data) {
				System.out.println(s);
				players.add(s);
			}

			for (String pl : players) {
				playerList.addItem(pl);
			}
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
				addPrivilege(playerList.getSelectedItem().toString(),
						"Administrator");
			}
			if (e.getSource().equals(grantMod)) {
				addPrivilege(playerList.getSelectedItem().toString(),
						"Moderator");
			}
			if (e.getSource().equals(revokeMod)) {
				removePrivilege(playerList.getSelectedItem().toString(),
						"Moderator");
			}
			if (e.getSource().equals(revokeAdmin)) {
				removePrivilege(playerList.getSelectedItem().toString(),
						"Administrator");
			}
		}
	}

}
