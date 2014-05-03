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
	
	private SLabel	name;
	private SButton arrow;
	private JPopupMenu pop;
	private ArrayList<SButton> items;
	
	public SComboBox(int width, int height, String[] items){	
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(255, 255, 255, 0));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.pop	= new JPopupMenu();
		this.items	= new ArrayList<SButton>();
		pop.setLayout(new GridLayout(0, 1));
		pop.setOpaque(false);
		UIManager.put("PopupMenu.background", new Color(255, 255, 255, 0));
		UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
		
		for(int i = 0; i < items.length; i++) {
			if(i == 0) {
				this.name	= new SLabel(items[i], SLabel.PADDINGLEFT, 180, 40);
				name.setName(items[i]);
			}
			addItem(items[i]);
		}
		
		name.changeTextColor(new Color(100, 100, 100), Color.WHITE);
		name.drawBackground(true);
		this.arrow 	= new SButton("\u25BC", SButton.WHITE, 40, 40);
		arrow.setColors(new Color(255, 255, 255), new Color(235, 235, 235), new Color(220, 220, 220));
		arrow.setTextColor(Color.BLACK);
		arrow.addActionListener(this);
		arrow.setBottomRounded(false);
		arrow.setRounded(true);
		arrow.setRightRounded(true);
		name.setOpaque(false);
		this.add(name, BorderLayout.WEST);
		this.add(arrow, BorderLayout.EAST);
	}
	
	public void addItem(String item) {
		SButton s = new SButton(item, Color.WHITE, this.getWidth(), this.getHeight());
		s.setTextColor(new Color(100, 100, 100));
		s.setRounded(true);
		s.setTextX(1);
		if(items.isEmpty()) {s.setTopRounded(true);}
		else {s.setBottomRounded(true);}
		s.setColors(new Color(255, 255, 255), new Color(235, 235, 235), new Color(220, 220, 220));
		s.setPreferredSize(this.getPreferredSize());
		for(int i = 1; i < items.size(); i++) {
			items.get(i).setRounded(false);
		}
		items.add(s);
		s.addActionListener(this);
		pop.add(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		pop.show(this, 0, this.getHeight());
		for(SButton s:items) {
			if(e.getSource().equals(s)) {
				name.setName(s.getName());
				repaint();
				pop.setVisible(false);
			}
		}
	}
	
	public String getSelectedItem() {return name.getName();}
	
}
