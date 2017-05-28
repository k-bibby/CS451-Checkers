import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;
import javax.swing.JComponent;

public class HostNameIP extends JFrame {

	private JFrame frame;
	private JPanel gui;
	private JPanel input;
	private JPanel buttons;
	private JLabel label;
	private JTextPane text;
	private JButton btnSubmit;
	private JButton cancel;
	private GridLayout grid;
	private UserInterface player;
	/**
	 * @wbp.parser.entryPoint
	 */
	public HostNameIP(UserInterface player) {
		super();
		frame = this;
		this.player = player;
		frame.setBounds(125, 125, 350, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		 gui = new JPanel();
		 input = new JPanel();
		 buttons = new JPanel();
	     frame.setAlwaysOnTop(true);
	     gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	     grid = new GridLayout(2,1);
	     gui.setLayout(grid);
	     label = new JLabel();
	     label.setText("Enter IP to connect to: ");
	     label.setVisible(true);
	     label.setFont(new Font(Font.SANS_SERIF, 3, 16));
	     //gui.add(text);
	     text = new JTextPane();
		 text.setEditable(true);;
		 text.setVisible(true);
		 text.setFont(new Font(Font.SANS_SERIF, 3, 16));
		 text.setMinimumSize(new Dimension(25,25));
		 input.add(label);
		 input.add(text);
	     btnSubmit = new JButton("Submit");
	     btnSubmit.setFont(new Font(Font.SANS_SERIF, 3, 16));
		 btnSubmit.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
					
							 	frame.setVisible(false);
								try{ 									
									player.begin(text.getText());
								}
								catch (Exception e2) {
								 	frame.dispose();
								 	new ErrorScreen("No Game found on Server");
								 	new Menu(player).initialize();
								}
					}				
				});
		 cancel = new JButton("Cancel");
		 cancel.setFont(new Font(Font.SANS_SERIF, 3, 16));
	     cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			 	new Menu(player).initialize();
			}
	    	 
	     });
	     buttons.add(btnSubmit);
	     buttons.add(cancel);
	     gui.add(input);
	     gui.add(buttons);
	     //frame.getContentPane().add(btnSubmit);
	     frame.add(gui);
	    // frame.getContentPane().add(gui); 
	     input.setVisible(true);
		 gui.setVisible(true);
		 buttons.setVisible(true);
	     frame.setVisible(true);
	}
	
	public JComponent getGui(){
		return gui;
	}
	
	public void open(CheckersBoard cb){
		gui.setVisible(true);
		
	}
	
	public void close(){
		gui.setVisible(false);
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
	}
}

