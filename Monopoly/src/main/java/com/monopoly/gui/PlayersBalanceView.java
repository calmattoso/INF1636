package com.monopoly.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.monopoly.game.GameEvents;
import com.monopoly.game.Player;

public class PlayersBalanceView 
	extends JPanel
	implements GameEvents, Observer
{
	private static final long serialVersionUID = 7970826428341424055L;
	private HashMap< Player.PlayerColor , JLabel > bankBalances;
	private HashMap< Player.PlayerColor , ImageIcon > pinIcons;

	public PlayersBalanceView( int x , int y , int width, int height, int numberOfPlayers )
	{
		this.pinIcons = new HashMap< Player.PlayerColor , ImageIcon >();
		this.loadPinImages();
		
		this.bankBalances = new HashMap< Player.PlayerColor , JLabel >();
		this.setupGridLayout( numberOfPlayers );	
		
		this.setBounds( x , y , width , height );
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ),	
			"Saldo Bancário"
		);		
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitlePosition( TitledBorder.CENTER );
		title.setTitleFont( new Font("Comic Sans MS", Font.PLAIN , 18) );
		this.setBorder( title );
		
		this.setLayout( new GridLayout( 0, 2 ));
	}
	
	/**
	 * Preconfigure the hashmap associating colors and their
	 *   corresponding bank balances
	 * 
	 * @param numberOfPlayers
	 */
	private void setupGridLayout( int numberOfPlayers )
	{
		Player.PlayerColor color = Player.PlayerColor.BLACK;
		
		for(int i = 0; i < numberOfPlayers; i++, color = color.getNext())
		{
			JLabel icon = new JLabel( pinIcons.get( color ));
			JLabel balance = new JLabel( "R$0" );
			
			this.bankBalances.put( color , balance );
			
			this.add( icon );
			this.add( balance );
		}
	}

	/**
	 * Load the pin icons to display by the side of its corresponding balance.
	 */
	private void loadPinImages( )
	{
		Player.PlayerColor color = Player.PlayerColor.BLACK;
		
		do 
		{
			String path = "src/main/resources/pins/" + color.toString().toLowerCase() + "_pin.png";
			ImageIcon pinIcon = new ImageIcon( path );
			
			this.pinIcons.put(color, pinIcon );
			
			color = color.getNext();
		} while( color != Player.PlayerColor.BLACK);
	}
	
	/**
	 * Updates the locally stored balance for the given player, which
	 *   is matched to its unique color ID. 
	 * 
	 * @param p A player.
	 */
	public void setPlayerBalance( Player p )
	{
		Player.PlayerColor color = p.getPinColor();
		Integer money = p.getMoney();
		
		JLabel balance = this.bankBalances.get(color);	
		
		if( money > 0 )
			balance.setText( "R$" + money.toString() );
		else
			balance.setText( "FALIU" );
		
		balance.repaint();		
	}
	
	public void update( Observable player, Object message ) {			
		if( player instanceof Player &&
			message.equals( GAME_PLAYER_BALANCE_UPDATED ) )
		{			
			this.setPlayerBalance( (Player) player );
			System.out.println( "Player balance updated" );
			
			this.repaint();
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g)
	{	
		super.paintComponent(g); 
		Graphics2D g2d = (Graphics2D) g;
        
		//Player.PlayerColor color = Player.PlayerColor.BLACK;
		//int baseX = 10, baseY = 50, offsetY = 20, i = 0;
		
		// Melhora qualidade do texto
        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
            									RenderingHints.VALUE_ANTIALIAS_ON );
        rh.put( RenderingHints.KEY_RENDERING ,
        		RenderingHints.VALUE_RENDER_QUALITY );               
        g2d.setRenderingHints(rh); 
	}

}
