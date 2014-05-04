package com.monopoly.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.monopoly.game.CurrentPlayer;
import com.monopoly.game.GameEvents;
import com.monopoly.game.Player;

public class CurrentPlayerView 
	extends JPanel
	implements Observer, GameEvents 
{
	private static final long serialVersionUID = 7970826428341424057L;
	CurrentPlayer player;

	public  CurrentPlayerView( int x , int y )
	{
		player = null ;		
		this.setBounds( x , y , 300, 20 );
	}
	
	public void setCurrentPlayer( CurrentPlayer p )
	{
		this.player = p ;
	}
	
	public void update(Observable player, Object message) {
		if( player instanceof CurrentPlayer &&
			message.equals( GAME_NEW_PLAYER ) )
		{
			this.player = ( CurrentPlayer ) player;	
						
			this.repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
				
		Graphics2D g2d = (Graphics2D) g;
		
		String color = Player.PlayerColor.colorToString( player.getPlayer().getPinColor() ).toUpperCase();

        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
            									RenderingHints.VALUE_ANTIALIAS_ON );
        rh.put( RenderingHints.KEY_RENDERING ,
        		RenderingHints.VALUE_RENDER_QUALITY );
        
        g2d.setFont( new Font("Times New Roman", Font.BOLD, 16) );
               
        g2d.setRenderingHints(rh);        
        g2d.drawString("Jogador atual: " + color + " pin" , 12, 12);
	}

}
