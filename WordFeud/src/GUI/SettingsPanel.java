package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import AccountType.Account;
import Utility.SButton;
import Utility.SLabel;
import Utility.SPasswordField;
import Utility.SPopupMenu;
import Utility.STextField;

/**
 * @author Max Nijholt
 * 
 */

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel{
	private GUI 			gui;
	private Account 		user;
	private SPasswordField 	passwordfield, passwordControle;
	private SLabel 			password, username, passwordConfirm;
	private STextField 		userfield;
	private SButton 		save;
	private JPanel 			allPanel;
	private MenuPanel 		mp;
	private ActionAdapter 	aa = new ActionAdapter();
	private SPopupMenu		pop = new SPopupMenu(),
							pop2 = new SPopupMenu();
	private JFrame 			frame = null;
	private boolean			passChange = false,
							userChange= false,
							showmenu;

	public SettingsPanel(GUI gui, Account user){
		this.gui = gui;
		this.user = user;
		this.showmenu = true;
		this.save				= new SButton("Save", SButton.GREY);
		init();
	}
	public SettingsPanel(GUI gui, Account user, final JFrame frame){
		this.gui = gui;
		this.user = user;
		this.frame = frame;
		this.showmenu = false;
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.requestFocus();
		this.frame.addWindowListener(new WindowAdapter() {
            //
            // Invoked when a window is de-activated.
            //
            public void windowDeactivated(WindowEvent e) {
                frame.dispose();
            }
 
        });
		this.save				= new SButton("Save or Cancel", SButton.GREY);
		init();
	}
	
	private void init(){
		gui.setLoadingCursor(true);
		mp = new MenuPanel(gui, "LoginPanel");
		
		this.setFocusable(true);
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		
		allPanel = new JPanel();
		allPanel.setPreferredSize(new Dimension(GUI.WIDTH , GUI.HEIGHT));
		allPanel.setBackground(new Color(94, 94, 94));

		allPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.passwordfield 		= new SPasswordField("password", 120, 30);
		this.passwordControle 	= new SPasswordField("password", 120, 30);
		this.password			= new SLabel("New password:", 0);
		this.passwordConfirm	= new SLabel("Confirm password:", 0);
		this.username			= new SLabel("New username:", 0);

		this.userfield			= new STextField(user.getUsername(), 120, 30);
//		this.save				= new SButton("Save or Cancel", SButton.GREY);
		this.save.addActionListener(aa);


		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		allPanel.add(username, c);
		c.gridy ++;
		allPanel.add(userfield, c);
		c.gridy ++;
		c.gridx ++;
		allPanel.add(password, c);
		c.gridy++;
		allPanel.add(passwordConfirm, c);
		c.gridy--;
		c.gridx ++;
		allPanel.add(passwordfield, c);
		c.gridy ++;
		allPanel.add(passwordControle, c);
		c.gridx++;
		c.gridy++;
		allPanel.add(save, c);
		gui.setLoadingCursor(false);
		if(this.showmenu) this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
		
		userfield.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) { if(!userfield.getText().equals("")){userChange=true;}else{userChange=false;}}
			  public void removeUpdate(DocumentEvent e) { if(!userfield.getText().equals("")){userChange=true;}else{userChange=false;}}
			  public void insertUpdate(DocumentEvent e) { if(!userfield.getText().equals("")){userChange=true;}else{userChange=false;}}
		});
		passwordfield.getDocument().addDocumentListener(new DocumentListener() {
			@SuppressWarnings("deprecation")
			public void changedUpdate(DocumentEvent e) { if(!passwordfield.getText().equals("")){passChange=true;}else{passChange=false;}}
			@SuppressWarnings("deprecation")
			public void removeUpdate(DocumentEvent e) { if(!passwordfield.getText().equals("")){passChange=true;}else{passChange=false;}}
			@SuppressWarnings("deprecation")
			public void insertUpdate(DocumentEvent e) { if(!passwordfield.getText().equals("")){passChange=true;}else{passChange=false;}}
		});
	}

	class ActionAdapter implements ActionListener {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			if(gui.getApplication().getCurrentAccount().getUsername().equals(user.getUsername())){
				if (e.getSource().equals(save)) {
					if(passwordfield.getText().equals(passwordControle.getText()) && passChange)
						gui.getApplication().getCurrentAccount().changePassword(passwordfield.getText());
					else if(!passChange){
						String s =  "No changes!";
						Color c = Color.orange;
						pop.show(gui, passwordfield.getX()+100, passwordfield.getY(), 300, 20, s, c);
					} else {
						String s =  "Passwords do not match!";
						Color c = Color.red;
						pop.show(gui, passwordfield.getX()+100, passwordfield.getY(), 300, 20, s, c);
					}
					if(userChange){
						gui.getApplication().getCurrentAccount().changeUsername(userfield.getText());
						String s = "Username changed";
						Color c = Color.green;
						pop2.show(gui, passwordfield.getX()+100, userfield.getY(), 300, 20, s, c);
					}
				}
			} else{
				if(e.getSource().equals(save)){
					if(passwordfield.getText().equals(passwordControle.getText())){
						if(!passwordfield.getText().equals("") && passChange)
							gui.getApplication().getCurrentAccount().getAdmin().changePassword(user, passwordfield.getText());
					}else if(!passChange){
						String s =  "No changes!";
						Color c = Color.orange;
						pop.show(gui, passwordfield.getX()+100, passwordfield.getY(), 300, 20, s, c);
					}  else {
						String s =  "Passwords do not match!";
						Color c = Color.red;
						pop.show(gui, passwordfield.getX()+100, passwordfield.getY(), 300, 20, s, c);
					}
					if(!userfield.getText().equals("") && userChange) {
						gui.getApplication().getCurrentAccount().getAdmin().changeUsername(user, userfield.getText());
						String s = "Username changed";
						Color c = Color.green;
						pop2.show(gui, passwordfield.getX()+100, userfield.getY(), 300, 20, s, c);
					}
					frame.dispose();
				}
			}
			
		}
	}
	public void windowGainedFocus(WindowEvent e){}
    public void windowLostFocus(WindowEvent e)
    {
        requestFocusInWindow();
        if(frame!=null)
        frame.dispose();
    }
}