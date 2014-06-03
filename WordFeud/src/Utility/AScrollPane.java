package Utility;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;


@SuppressWarnings("serial")
public class AScrollPane extends JScrollPane{

	private Color bg		 = new Color(94, 94, 94);;
	private Color normal	 = new Color(68, 68, 68);
//	private Color clicked 	 = new Color(80, 80, 80);
	private Color hLight	 = new Color(88, 88, 88);
	
	public AScrollPane(int width, int height, JComponent panel, Boolean horizontal, Boolean vertical) {

		super(panel);
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(null);
		this.setBackground(bg);

		if (horizontal == true && vertical == true) {
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("hor"));
			this.setVerticalScrollBar(getScrollBarForScrollPane("vert"));
			
			this.getHorizontalScrollBar().setUnitIncrement(30);
			this.getVerticalScrollBar().setUnitIncrement(30);
			
			this.getHorizontalScrollBar().setBackground(bg);
			this.getVerticalScrollBar().setBackground(bg);
		}
		
		if (horizontal == true && vertical == false) {
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("hor"));
			
			this.getHorizontalScrollBar().setUnitIncrement(30);
			
			this.getHorizontalScrollBar().setBackground(bg);
		}
		
		if (horizontal == false && vertical == true) {
			this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			this.setVerticalScrollBar(getScrollBarForScrollPane("vert"));
			
			this.getVerticalScrollBar().setUnitIncrement(30);
		
			this.getVerticalScrollBar().setBackground(bg);
		}

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
		        		thumbColor 				= normal;
		        		thumbDarkShadowColor	= normal;
		        		thumbHighlightColor 	= normal;
		        		thumbLightShadowColor 	= normal;
		        		
		        		trackColor 				= hLight;
		        		trackHighlightColor		= bg;
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
