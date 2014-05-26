package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.SButton;
import Utility.SLabel;
import WordFeud.Competition;

public class CompetitionPanel extends Panel {
	
	private AScrollPane currentScrollPane, finishedScrollPane, comps, joinableScrollPane;
	private JPanel currentCompPanel, finishedCompPanel, competitions, joinableCompPanel;
	private MenuPanel menu;
	private Color bg = new Color(94, 94, 94);
	private GUI gui;
	private ArrayList<Integer> compInts;
	
	public CompetitionPanel(GUI gui){
		this.gui = gui;
		this.setBackground(bg);
		this.setLayout(new BorderLayout());
		menu = new MenuPanel(gui, new PlayerPanel(gui));
		

		currentCompPanel = new JPanel();	
		finishedCompPanel = new JPanel();
		joinableCompPanel = new JPanel();
		competitions = new JPanel();
		

		currentCompPanel.setLayout(new BoxLayout(currentCompPanel, BoxLayout.Y_AXIS));
		currentCompPanel.setBackground(bg);
		currentCompPanel.add(addLabel("Competitions you're in", 0));
		currentCompPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		finishedCompPanel.setLayout(new BoxLayout(finishedCompPanel, BoxLayout.Y_AXIS));
		finishedCompPanel.setBackground(bg);
		finishedCompPanel.add(addLabel("Finished competitions", 0));
		finishedCompPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		joinableCompPanel.setLayout(new BoxLayout(joinableCompPanel, BoxLayout.Y_AXIS));
		joinableCompPanel.setBackground(bg);
		joinableCompPanel.add(addLabel("Joinable competitions", 0));
		joinableCompPanel.setAlignmentX(CENTER_ALIGNMENT);
		

		compInts = gui.getApplication().getPlayingCompetitions();
		if(compInts.size() != 0){

			currentCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : compInts){
				currentCompPanel.add(paintComp(e, "Active"));
				currentCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		


		

		
		compInts = gui.getApplication().getFinishedCompetitions();
		if(compInts.size() != 0){

			finishedCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : compInts){
				finishedCompPanel.add(paintComp(e, "Finished"));
				finishedCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		

		
		

		
		compInts = gui.getApplication().getAllCompetitions();
		ArrayList<Integer> activeInts = gui.getApplication().getPlayingCompetitions();
			int x = 0;
			while(x < activeInts.size()){
				if(compInts.contains(activeInts.get(x))){
					compInts.remove(activeInts.get(x));

				}					x++;
			}
			
		
		if(compInts.size() != 0){

			joinableCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : compInts){
				joinableCompPanel.add(paintComp(e, "Joinable"));
				joinableCompPanel.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		

		
		
		
		
		competitions.setLayout(new BoxLayout(competitions, BoxLayout.Y_AXIS));
		competitions.setBackground(bg);
		
		currentScrollPane = new AScrollPane(currentCompPanel.getWidth(), currentCompPanel.getHeight(), currentCompPanel, false, true);
		comps = new AScrollPane(competitions.getWidth(), competitions.getHeight(), competitions, false, true);
		finishedScrollPane = new AScrollPane(finishedCompPanel.getWidth(), finishedCompPanel.getHeight(), finishedCompPanel, false, true);
		joinableScrollPane = new AScrollPane(joinableCompPanel.getWidth(), joinableCompPanel.getHeight(), joinableCompPanel, false, true);
		
		competitions.add(currentScrollPane);
		competitions.add(joinableScrollPane);		
		competitions.add(finishedScrollPane);
		
		this.add(menu, BorderLayout.NORTH);
		this.add(comps, BorderLayout.CENTER);
		
	}
	
	
	private JPanel addLabel(String labelText, int type){
		JPanel panel = new JPanel();
		if (type == 0){
			panel.setMinimumSize(new Dimension(500,35));
			panel.setPreferredSize(new Dimension(500,35));
			panel.setMaximumSize(new Dimension(500,35));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 25));
			panel.add(label);
		}
		if (type == 1){
			panel.setMinimumSize(new Dimension(400,30));
			panel.setPreferredSize(new Dimension(400,30));
			panel.setMaximumSize(new Dimension(400,30));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
			panel.add(label);	
		}
		return panel;
	}
	
	private JPanel paintComp(final int compID, String compType){
		System.out.println("paint comp: " + compID);

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(600,90));
		panel.setPreferredSize(new Dimension(600,90));
		panel.setMaximumSize(new Dimension(600,90));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//for a game newly requested. option to accept or reject
		if(compType.equals("Invited")){
			SButton accept 	= new SButton("accept", SButton.GREY, 220, 40);
			SButton deny 	= new SButton("deny", SButton.GREY, 220, 40);
			JPanel opponent = new JPanel();
			JPanel play 	= new JPanel();
			
			opponent.add(new SLabel(gui.getCompetitionDescription(compID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
			play.add(new SLabel("wants to play a game with you", SLabel.CENTER, new Font("Arial", Font.PLAIN, 20)));
			
			opponent.setMinimumSize(new Dimension(200,40));
			opponent.setPreferredSize(new Dimension(200,40));
			opponent.setMaximumSize(new Dimension(200,40));
			play.setMinimumSize(new Dimension(300, 40));
			play.setPreferredSize(new Dimension(300, 40));
			play.setMaximumSize(new Dimension(300, 40));
			accept.setMinimumSize(accept.getPreferredSize());
			deny.setMinimumSize(deny.getPreferredSize());
			
			opponent.setBackground(panel.getBackground());
			play.setBackground(panel.getBackground());
			
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(5,15,0,0);
			panel.add(opponent, c);
			c.gridy++;
			panel.add(play,c);
			c.gridx++;
			c.gridy--;
			panel.add(accept, c);
			c.gridy++;
			panel.add(deny, c);
			
			accept.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.acceptGame(compID);
				}
			});
			deny.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.denyGame(compID);
				}
			});
		}
		
		//for a game that is playing. option to select
		else if(compType.equals("Active")){
			JPanel owner 	= new JPanel();
			JPanel description 	= new JPanel();
			SButton select 		= new SButton("Select", SButton.GREY, 220, 40);
			
			owner.add(new SLabel(gui.getCompetitionOwner(compID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
			description.add(new SLabel(gui.getCompetitionDescription(compID), SLabel.CENTER, new Font("Arial", Font.PLAIN, 15)));
			
			owner.setMinimumSize(new Dimension(200,40));
			owner.setPreferredSize(new Dimension(200,40));
			owner.setMaximumSize(new Dimension(200,40));
			description.setMinimumSize(new Dimension(200,40));
			description.setPreferredSize(new Dimension(200,40));
			description.setMaximumSize(new Dimension(200,40));
			select.setMinimumSize(select.getPreferredSize());
			
			owner.setBackground(panel.getBackground());
			description.setBackground(panel.getBackground());

			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,50,0,0);
			panel.add(owner, c);
			c.gridx++;
			c.gridheight = 2;
			panel.add(select, c);
			c.gridheight = 1;
			c.gridx = 0;
			c.gridy++;
			panel.add(description, c);
			
			select.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {		
					gui.getApplication().setSelectedCompetition(new Competition(compID));
					gui.switchPanel(new CompetitionPlayersPanel(gui, compID));
				}
			});
		}
		//for a game that has finished. option to watch/spectate
		else if(compType.equals("Finished")){
			JPanel owner		= new JPanel();
			JPanel description 	= new JPanel();
			SButton spectate 	= new SButton("Spectate", SButton.GREY, 220, 40);
			
			owner.add(new SLabel(gui.getCompetitionOwner(compID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
			description.add(new SLabel(gui.getCompetitionDescription(compID), SLabel.CENTER, new Font("Arial", Font.PLAIN, 15)));
			
			owner.setMinimumSize(new Dimension(200,30));
			description.setMinimumSize(new Dimension(200,30));
			spectate.setMinimumSize(spectate.getPreferredSize());
			
			owner.setBackground(panel.getBackground());
			description.setBackground(panel.getBackground());
			
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,50,0,0);
			panel.add(owner, c);
			c.gridx++;
			c.gridheight = 2;
			panel.add(spectate, c);
			c.gridheight = 1;
			c.gridx = 0;
			c.gridy++;
			panel.add(description, c);
			
			spectate.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.getApplication().setSelectedCompetition(new Competition(compID));
					gui.seeComps(compID, getPanel());
				}
			});
		}
		//for a game that has been requested. no options
		else if(compType.equals("Joinable")){
			JPanel owner 	= new JPanel();
			JPanel description 		= new JPanel();
			SButton join 	= new SButton("Join", SButton.GREY, 220, 40);
			SButton spectate 	= new SButton("Spectate", SButton.GREY, 220, 40);
			
			owner.add(new SLabel(gui.getCompetitionOwner(compID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
			description.add(new SLabel(gui.getCompetitionDescription(compID), SLabel.CENTER, new Font("Arial", Font.PLAIN, 15)));
			
			owner.setMinimumSize(new Dimension(200,30));
			description.setMinimumSize(new Dimension(200,30));
			join.setMinimumSize(join.getPreferredSize());
			
			owner.setBackground(panel.getBackground());
			description.setBackground(panel.getBackground());
			
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(5,15,0,0);
			panel.add(owner, c);
			c.gridy++;
			panel.add(description,c);
			c.gridx++;
			c.gridy--;
			panel.add(join, c);
			c.gridy++;
			panel.add(spectate, c);
				
				join.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						gui.getApplication().setSelectedCompetition(new Competition(compID));
						Competition comp = gui.getApplication().getSelectedCompetition();		

						comp.addPlayer(gui.getApplication().getCurrentAccount());
						gui.switchPanel(new CompetitionPlayersPanel(gui, compID));
						
					}
				});
				spectate.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						gui.getApplication().setSelectedCompetition(new Competition(compID));
						gui.spectateCompetition(compID);
					}
				});
			
		}
		return panel;
	}
	
	public JPanel getPanel(){
		return this;
	}
	
	
}
