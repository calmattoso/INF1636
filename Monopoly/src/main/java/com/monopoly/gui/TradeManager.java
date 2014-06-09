package com.monopoly.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.monopoly.game.Player;


public class TradeManager 
	extends JFrame
{
	private static final long serialVersionUID = -3226065208456309012L;
	
	private final int WIDTH  = 480;
	private final int HEIGHT = 365;
	
	JPanel menu; 
	
	
	public TradeManager(Player currentPlayer, Player[] players) {
		menu = new NegotiationPanel(currentPlayer, players, this);
		menu.setLayout(null);
		menu.setBounds(0,0,WIDTH,HEIGHT);
		
		Toolkit tk=Toolkit.getDefaultToolkit();

		int screenWidth  = tk.getScreenSize().width,
			screenHeight = tk.getScreenSize().height ;
		
		setBounds( screenWidth/2 - screenWidth/16, screenHeight/2 - screenHeight/4 , WIDTH, HEIGHT);
		setResizable( false );
		setTitle( "Troca entre Jogadores" );
		setIconImage(tk.getImage("src/main/resources/trade.png"));
		
		getContentPane().setLayout(null);
		getContentPane().add( menu );
	}
	
	public void closeWindow() {
        WindowEvent event = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);        
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}

	private static class NegotiationPanel 
	extends JPanel
	implements ActionListener
{
	private static final long serialVersionUID = -953621073692591648L;
	
	Player player;
	Player[] players;
	
	private TradeManager parent;
	
	public NegotiationPanel(Player player, Player[] players, TradeManager parent)
	{
		this.player = player;
		this.players = players;
		this.parent = parent;			
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		setLayout(null);
	}

	/**
	 * Handle the rules for when one of the above buttons gets clicked.
	 */
	public void actionPerformed(ActionEvent event) {
		String message = event.getActionCommand();
		
		parent.closeWindow();
	}
}
	
}