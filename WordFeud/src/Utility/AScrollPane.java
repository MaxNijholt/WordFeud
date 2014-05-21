package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class AScrollPane extends JScrollPane implements MouseListener{

	private Color bg;
	private Color normal = new Color(68, 68, 68);
	private Color hLight = new Color(58, 58, 58);
	private Color clicked = new Color(78, 78, 78);
	private int state = 0;
	
	public AScrollPane(int width, int height, JPanel panel, Boolean horizontal, Boolean vertical) {

		super(panel);
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(null);
		this.setBackground(bg);

		
//		normal	 = new Color(237, 67, 33);
		bg		 = new Color(94, 94, 94);
		
		if (horizontal == true && vertical == true) {
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("hor"));
			this.setVerticalScrollBar(getScrollBarForScrollPane("vert"));
			
			this.getHorizontalScrollBar().setUnitIncrement(16);
			this.getVerticalScrollBar().setUnitIncrement(16);
			
			this.getHorizontalScrollBar().setBackground(bg);
			this.getVerticalScrollBar().setBackground(bg);
		}
		
		if (horizontal == true && vertical == false) {
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("hor"));
			
			this.getHorizontalScrollBar().setUnitIncrement(16);
			
			this.getHorizontalScrollBar().setBackground(bg);
		}
		
		if (horizontal == false && vertical == true) {
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("vert"));
			
			this.getVerticalScrollBar().setUnitIncrement(16);
		
			this.getVerticalScrollBar().setBackground(bg);
		}

	}
	


	@Override
	public void mouseClicked(MouseEvent arg0) {
		state = 1; repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		state = 2; repaint();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		state = 3; repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		state = 1; repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		state = 5; repaint();
		
	}

	 public JScrollBar getScrollBarForScrollPane(String orientation) {
		 JScrollBar jScrollBar = null;
		 if(orientation.equals("vert")){
		        jScrollBar = new JScrollBar(JScrollBar.VERTICAL);
		 }
		 
		 else if(orientation.equals("hor")){
			 jScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		 }

	        jScrollBar.setUI(new BasicScrollBarUI()
	        {   
	        	@Override
	        	protected void configureScrollBarColors() {
	        		super.configureScrollBarColors();
	        		if(state == 0){
		        		thumbColor = normal;
		        		thumbDarkShadowColor = normal;
		        		thumbHighlightColor = normal;
		        		thumbLightShadowColor = normal;
		        		trackColor = bg;
	        		}

	        		if(state == 1){
		        		thumbColor = normal;
		        		thumbDarkShadowColor = clicked;
		        		thumbHighlightColor = clicked;
		        		thumbLightShadowColor = clicked;
		        		trackColor = bg;
	        		}


	        	}
	        	
	            @Override
	            protected JButton createDecreaseButton(int orientation) {
	                return createButton();
	            }
	            @Override
	            protected JButton createIncreaseButton(int orientation) {	            	
	                return createButton();
	            }
	            
	           

	            private JButton createButton() {
	                JButton jbutton = new JButton();
	                jbutton.setPreferredSize(new Dimension(0, 0));
	                jbutton.setMinimumSize(new Dimension(0, 0));
	                jbutton.setMaximumSize(new Dimension(0, 0));
	                return jbutton;
	            }
	        });
	        return jScrollBar;
	    }
	
}
