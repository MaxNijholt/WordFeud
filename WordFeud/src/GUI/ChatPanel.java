package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import Utility.STextArea;

public class ChatPanel extends Panel {

	private final int width = 200;
	private final int height = 400;

	private final int pHeight = (height - (height / 4) - 40);
	private final int tHeight = ((height / 4));

	private final int areaWidth = (width - 20);

	private STextArea printArea;
	private STextArea typeArea;

	public ChatPanel() {
		printArea = new STextArea();
		printArea.setEditable(false);
		printArea.setBounds(((width / 2) - 90), ((height / 2) - 190),
				areaWidth, pHeight);
		printArea.setText("test132");

		typeArea = new STextArea();
		typeArea.setBounds(((width / 2) - 90), (pHeight + 30), areaWidth,
				tHeight);

		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GRAY);
		this.setLayout(null);

		this.add(printArea);
		this.add(typeArea);
	}

}