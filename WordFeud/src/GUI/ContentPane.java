package GUI;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ContentPane extends JPanel {

	public ContentPane(JPanel panel) {
		this.setPreferredSize(new Dimension(1280, 720));
		this.add(panel);
	}
	
}
