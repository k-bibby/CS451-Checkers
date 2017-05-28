import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ErrorScreen {

	private JFrame frame;
	private JPanel gui;
	private JTextPane text;
	private JButton btnSubmit;
	private JButton btnReset;
	private GridLayout grid;
	private String m;
	private Boolean isBoard = false;
	/**
	 * @wbp.parser.entryPoint
	 */
	public ErrorScreen(String message) {
		frame = new JFrame();
		frame.setBounds(125, 125, 350, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		m = message;
		initialize();
	}
	public ErrorScreen(String message, Boolean isBoard) {
		frame = new JFrame();
		isBoard = true;
		frame.setBounds(125, 125, 350, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		m = message;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		 gui = new JPanel();
	     frame.setAlwaysOnTop(true);
	     gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	     grid = new GridLayout(2,1);
	     gui.setLayout(grid);
	     //gui.add(text);
	     text = new JTextPane();
		 text.setText(m);
		 text.setVisible(true);
		 text.setFont(new Font(Font.SANS_SERIF, 3, 16));
		 StyledDocument doc = text.getStyledDocument();
		 SimpleAttributeSet center = new SimpleAttributeSet();
		 StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		 doc.setParagraphAttributes(0, doc.getLength(), center, false);
		 
	     btnSubmit = new JButton("close");
	     btnSubmit.setFont(new Font(Font.SANS_SERIF, 3, 16));
	     if(!isBoard) {
		     btnSubmit.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						frame.setVisible(false);
						frame.dispose();
					}		
				});
	     }
	     else {
	    	 btnSubmit.addActionListener(new ActionListener() {
	    			
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}		
				});
	     }
	     gui.add(text);
	     gui.add(btnSubmit);
	     //frame.getContentPane().add(btnSubmit);
	     frame.add(gui);
	    // frame.getContentPane().add(gui); 

		 gui.setVisible(true);
		// frame.pack();
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
		frame.dispose();
		System.exit(0);
	}
}

