package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CompetitionPanel extends Panel {

	private JPanel currentCompPanel, finishedCompPanel, competitions;
	private ArrayList<JButton> currentCompetitions, finishedCompetitions;
	private JLabel current, finished;
	private MenuPanel menu;
	private Color bg = new Color(94, 94, 94);
	
	
	public CompetitionPanel(GUI gui){
		this.setBackground(bg);
		this.setLayout(new BorderLayout());
		menu = new MenuPanel(gui, new PlayerPanel(gui));
		
		currentCompetitions = new ArrayList<JButton>();
		finishedCompetitions = new ArrayList<JButton>();
		
		currentCompPanel = new JPanel();	
		finishedCompPanel = new JPanel();
		competitions = new JPanel();
		
		current = new JLabel("Current competitions");
		current.setFont( new Font("Arial", Font.BOLD, 20));
		
		finished = new JLabel("Finished competitions");
		finished.setFont( new Font("Arial", Font.BOLD, 20));
		

		currentCompPanel.setLayout(new BoxLayout(currentCompPanel, BoxLayout.Y_AXIS));
		currentCompPanel.setBackground(bg);
		currentCompPanel.add(current);
		currentCompPanel.setAlignmentX(CENTER_ALIGNMENT);

		
		finishedCompPanel.setLayout(new BoxLayout(finishedCompPanel, BoxLayout.Y_AXIS));
		finishedCompPanel.setBackground(bg);
		finishedCompPanel.add(finished);
		finishedCompPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		competitions.setLayout(new BoxLayout(competitions, BoxLayout.Y_AXIS));
		competitions.setBackground(bg);
		competitions.add(currentCompPanel);
		competitions.add(finishedCompPanel);
		
		
		this.add(menu, BorderLayout.NORTH);
		this.add(competitions, BorderLayout.CENTER);
		
	}
	
}
