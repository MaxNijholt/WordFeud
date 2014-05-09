package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private GUI gui;
	private SButton logout = new SButton("Logout", SButton.GREY.darker()),
			polls = new SButton("polls", SButton.GREY.darker()),
					admin = new SButton("Admin", SButton.GREY.darker()),
			stats = new SButton("stats", SButton.GREY.darker());
	private ActionAdapter aa = new ActionAdapter();

	public MenuPanel(GUI gui, String currUser) {
		this.setPreferredSize(new Dimension( GUI.WIDTH, 50));
		this.gui = gui;
		this.add(logout);
		this.add(polls);
		this.add(stats);
		this.add(admin);

		this.admin.addActionListener(aa);
		this.logout.addActionListener(aa);
		this.polls.addActionListener(aa);
		this.stats.addActionListener(aa);
	}

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(stats)) {
				gui.switchPanel(new StatisticsPanel());
			}
			if (e.getSource().equals(admin)){
				gui.switchPanel(new AdminPanel(gui));
			}
		}
	}
}
