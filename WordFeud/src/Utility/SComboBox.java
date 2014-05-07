package Utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class SComboBox extends JPanel implements ActionListener {

	private STextField			name;
	private SButton 			arrow;
	private JPopupMenu 			pop;
	private ArrayList<SButton> 	items;

	public SComboBox(int width, int height, String[] items) {
		// Default Component stuff
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(255, 255, 255, 0));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		this.pop = new JPopupMenu();
		pop.setLayout(new GridLayout(0, 1));
		pop.setOpaque(false);
		
		///////////	UIManager ///////////
		UIManager.put("PopupMenu.background", new Color(255, 255, 255, 0));
		UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
		///////////	UIManager ///////////
		
		this.items 	= new ArrayList<SButton>();
		
		// Adding the items that are given in the String[] parameter 
		// and setting the first String in the String[] to the currentSelected item
		for(int i = 0; i < items.length; i++) {
			if(i == 0) {
				this.name = new STextField(items[i], 180, 40);
				name.setName(items[i]);
				name.setEditable(false);
				name.setCustomRounded(true, false, true, false);
			}
			addItem(items[i]);
		}
		name.setOpaque(false);
		
		this.arrow = new SButton("\u25BC", SButton.WHITE, 40, 40);
		arrow.setColors(new Color(255, 255, 255), new Color(235, 235, 235), new Color(220, 220, 220));
		arrow.setTextColor(Color.BLACK);
		arrow.addActionListener(this);
		arrow.setCustomRounded(false, true, false, true);
		
		this.add(name, BorderLayout.CENTER);
		this.add(arrow, BorderLayout.EAST);
	}

	public void addItem(String item) {
		SButton s = new SButton(item, Color.WHITE, this.getWidth(), this.getHeight());
		s.setTextColor(new Color(100, 100, 100));
		s.setRounded(true);
		s.setAlignment(SButton.LEFT);
		
		if(items.isEmpty()) {
			s.setCustomRounded(true, true, false, false);
		} 
		else {
			s.setCustomRounded(false, false, true, true);
		}
		
		s.setColors(new Color(255, 255, 255), new Color(235, 235, 235), new Color(220, 220, 220));
		s.setPreferredSize(this.getPreferredSize());
		s.addActionListener(this);
		for(int i = 1; i < items.size(); i++) {items.get(i).setRounded(false);}
		
		items.add(s);
		pop.add(s);
	}

	public void actionPerformed(ActionEvent e) {
		pop.show(this, 0, this.getHeight());
		for(SButton s : items) {
			if(e.getSource().equals(s)) {
				name.setName(s.getName());
				pop.setVisible(false);
				repaint();
			}
		}
	}

	public String getSelectedItem() 	{return name.getName();}
	public void setEditable(boolean a) 	{name.setEditable(a);}
	
}
