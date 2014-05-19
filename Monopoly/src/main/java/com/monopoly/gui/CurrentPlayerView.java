package com.monopoly.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
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
	public void paint(Graphics g)
	{						
		Graphics2D g2d = (Graphics2D) g;
		
		// Obtém a string de cor
		String color = Player.PlayerColor.colorToString( player.getPlayer().getPinColor() ).toUpperCase(),
			   output = " Jogador atual: " + color + " pin ";
		
		
		// Vamos pintar a substring de cor com sua respectiva cor
		AttributedString as1 = new AttributedString( output );
		as1.addAttribute(TextAttribute.FONT, new Font("Calibri", Font.BOLD , 16));
        as1.addAttribute(TextAttribute.FOREGROUND, getColor( player.getPlayer().getPinColor() ), 16 , output.length() - 4 );
        as1.addAttribute(TextAttribute.BACKGROUND, Color.lightGray );

        // Melhora qualidade do texto
        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
            									RenderingHints.VALUE_ANTIALIAS_ON );
        rh.put( RenderingHints.KEY_RENDERING ,
        		RenderingHints.VALUE_RENDER_QUALITY );               
        g2d.setRenderingHints(rh);        
        
        // Exibe
        g2d.drawString( as1.getIterator() , 12 , 12 );
	}
	
	private Color getColor( Player.PlayerColor color )
	{
		switch( color )
		{
		case BLACK:
			return Color.black;
		case BLUE:
			return Color.blue;
		case ORANGE:
			return Color.orange;
		case PURPLE:
			return Color.magenta;
		case RED:
			return Color.red;
		case YELLOW:
			return Color.yellow;
		default:
			return Color.black;		
		}
	}

}
