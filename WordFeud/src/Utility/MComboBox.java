package Utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MComboBox extends JPanel implements ActionListener, ObjectChangeListener {

	private STextField placeholder;
	private SButton arrow;
	private JPopupMenu pop;
	private ArrayList<SButton> buttonList;

	public MComboBox(int width, int height, String[] items) {
		// Default Component stuff
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(255, 255, 255, 0));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		this.pop = new JPopupMenu();
		pop.setLayout(new GridLayout(0, 1));
		pop.setOpaque(false);

		// ///////// UIManager ///////////
		UIManager.put("PopupMenu.background", new Color(255, 255, 255, 0));
		UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
		// ///////// UIManager ///////////

		this.buttonList = new ArrayList<SButton>();

		// Adding the items that are given in the String[] parameter
		// and setting the first String in the String[] to the currentSelected
		// item
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				if (i == 0) {
					this.placeholder = new STextField(items[i]);
				}
				addItem(items[i]);
			}
		} else {
			this.placeholder = new STextField("Name");
			this.placeholder.setEditable(true);
			this.placeholder.setCustomRounded(false, false, false, false);
		}

		this.arrow = new SButton("\u25BC", SButton.WHITE, 40, 40);
		arrow.setColors(new Color(255, 255, 255), new Color(235, 235, 235),
				new Color(220, 220, 220));
		arrow.setTextColor(Color.BLACK);
		arrow.addActionListener(this);
		arrow.setCustomRounded(false, false, false, false);

		this.add(placeholder, BorderLayout.CENTER);
		this.add(arrow, BorderLayout.EAST);

		this.placeholder.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				buttonList = new ArrayList<SButton>();
				pop.removeAll();
					ArrayList<String> data = null;
					data = DBCommunicator
							.requestMoreData("SELECT naam FROM account WHERE naam LIKE '"
									+ placeholder.getText() + "%'");
					for (String pl : data) {
						addItem(pl);
					}
			}});
	}

	public void addItem(String item) {
		final SButton s = new SButton(item, Color.WHITE, this.getWidth(),
				this.getHeight());
		s.setTextColor(new Color(100, 100, 100));
		s.setRounded(true);
		s.setAlignment(SButton.LEFT);

		if(buttonList.isEmpty()) {
			s.setCustomRounded(false, false, false, false);
		} 
		else {
			s.setCustomRounded(false, false, false, false);
		}

		s.setColors(new Color(255, 255, 255), new Color(235, 235, 235),
				new Color(220, 220, 220));
		s.setPreferredSize(this.getPreferredSize());
		s.addActionListener(this);
		for (int i = 1; i < buttonList.size(); i++) {
			buttonList.get(i).setRounded(false);
		}

		buttonList.add(s);
		pop.add(s);
			s.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
						placeholder.setText(s.getName());
				}
				
			});
		
	}

	public void actionPerformed(ActionEvent e) {
		pop.show(this, 0, this.getHeight());
		for (SButton s : buttonList) {
			if (e.getSource().equals(s)) {
				placeholder.setText(s.getName());
				pop.setVisible(false);
				revalidate();
			}
		}
	}

	public String getSelectedItem() {
		System.out.println(placeholder.getText());
		return placeholder.getText();
	}

	public STextField getField(){
		return placeholder;
	}

	public ArrayList<SButton> getButtons(){
		return buttonList;
	}

	@Override
	public void namingExceptionThrown(NamingExceptionEvent evt) {
		
	}

	@Override
	public void objectChanged(NamingEvent evt) {
		
	}

}