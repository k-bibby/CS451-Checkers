import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Menu {

	private JFrame frame;
	private JPanel gui;
	private JButton startButton;
	private JButton quitButton;
	private JButton joinButton;
	private UserInterface player;
	private int selected_button;
    private HostNameIP host;
	public Menu(UserInterface ui) {
		player = ui;
		selected_button = 0;
		//initialize();
	}

	public int initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		gui = new JPanel();
		frame.getContentPane().add(gui);
		
		startButton = new JButton("Start");
		startButton.setBounds(147, 75, 117, 29);
		startButton.setFont(new Font(Font.SANS_SERIF, 3, 16));
		joinButton = new JButton("Join");
		joinButton.setBounds(147, 116, 117, 29);
		joinButton.setFont(new Font(Font.SANS_SERIF, 3, 16));
		quitButton = new JButton("Quit");
		quitButton.setBounds(147, 158, 117, 29);
		quitButton.setFont(new Font(Font.SANS_SERIF, 3, 16));;
		frame.setVisible(true);
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 selected_button = 1;
				 try {
					 MainServer t = new MainServer(9876);
						 frame.setVisible(false);
						 player.begin("localhost");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					frame.setVisible(true);
					return;
				}
			}
		});
		
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 selected_button = 2;
					 	frame.setVisible(false);
					 
							System.out.println("Enter Friends IP address:");
							host = new HostNameIP(player);	 
							
						
				 
			}
		});
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 selected_button = 3;
				 close();
			}
		});
		
		frame.getContentPane().add(startButton);
		frame.getContentPane().add(joinButton);
		frame.getContentPane().add(quitButton);
		
		
		return 0;
	}
	
	public JPanel getGui(){
		return gui;	
	}


	public int open() {
		frame.setVisible(true);

		while(selected_button == 0){
			System.out.print(".");
		}
		System.out.println(selected_button);
				
		return selected_button;
	}

	public void close(){
		frame.setVisible(false);
		System.exit(0);
	}
}
