package Utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class SComboBox extends JComboBox<String> implements ListCellRenderer<Object> {
	
	public SComboBox(){		
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		this.setRenderer(this);
		this.setForeground(new Color(100, 100, 100));
	}
	
	public SComboBox(String[] items){		
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		this.setRenderer(this);
		this.setForeground(new Color(100, 100, 100));
		for(int i = 0; i < items.length; i++) {
			this.addItem(items[i]);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public Component getListCellRendererComponent(JList<?> list, final Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = new JLabel() {
			public Dimension getPreferredSize() {
				return new Dimension(getWidth(), 40);
			}
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g.create();
				g2d.setColor(new Color(100, 100, 100));
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				FontMetrics fm = g2d.getFontMetrics();
				g2d.drawString(String.valueOf(value), 5, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
				g2d.dispose();
			}
		};
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		label.setForeground(new Color(100, 100, 100));
		return label;
	}
}
