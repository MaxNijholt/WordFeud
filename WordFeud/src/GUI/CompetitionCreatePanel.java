package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField name;
	private SLabel nameLabel, compLabel, layoutLabel, signupLabel, typeLabel, inviteLabel;
	private SButton create, back;
	private SComboBox comp, layout, type, invite;
	private GUI gui;
	
	public CompetitionCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.gui = gui;
		
		name = new STextField("Competition name");
		
		nameLabel	= new SLabel("Competition name", SLabel.RIGHT, 220, 40);
		compLabel 	= new SLabel("Private Competition", SLabel.RIGHT, 220, 40);
		layoutLabel = new SLabel("Board layout", SLabel.RIGHT, 220, 40);
		signupLabel = new SLabel("Sign up period", SLabel.RIGHT, 220, 40);
		typeLabel 	= new SLabel("Competition type", SLabel.RIGHT, 220, 40);
		inviteLabel	= new SLabel("Invite Players", SLabel.RIGHT, 220, 40);
		
		create 		= new SButton("Create", SButton.GREY);
		back 		= new SButton("Back", SButton.GREY);
		comp 		= new SComboBox(new String[] {"ON", "OFF"});
		layout 		= new SComboBox(new String[] {"Random", "Static"});
		type 		= new SComboBox(new String[] {"Single", "Double", "Triple"});
		invite		= new SComboBox();
		for(int i = 0; i < 100; i++) {
			invite.addItem("Test " + (i + 1));
		}
		
		back.addActionListener(this);
		
		this.add(name);
		this.add(create);
		this.add(back);
		this.add(comp);
		this.add(layout);
		this.add(type);
		this.add(invite);
		
		this.add(nameLabel);
		this.add(compLabel);
		this.add(layoutLabel);
		this.add(signupLabel);
		this.add(typeLabel);
		this.add(inviteLabel);
		
		nameLabel.setBounds(100, 100, 220, 40);
		compLabel.setBounds(100, 150, 220, 40);
		layoutLabel.setBounds(100, 200, 220, 40);
		signupLabel.setBounds(100, 250, 220, 40);
		typeLabel.setBounds(100, 300, 220, 40);
		inviteLabel.setBounds(660, 100, 220, 40);
		
		name.setBounds(330, 100, 220, 40);
		comp.setBounds(330, 150, 220, 40);
		layout.setBounds(330, 200, 220, 40);
		type.setBounds(330, 300, 220, 40);
		invite.setBounds(660, 150, 220, 40);
		
		create.setBounds(100, 350, 220, 40);
		back.setBounds(330, 350, 220, 40);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(94, 94, 94));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 40));
		g2d.drawString("Create a competition", 100, 75);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new LoginPanel(gui));
		}
	}
	
}
