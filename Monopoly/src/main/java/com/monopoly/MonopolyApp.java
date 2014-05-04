/**
 * 
 */
package com.monopoly;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import com.monopoly.game.Game;
import com.monopoly.gui.GUI;


public class MonopolyApp 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int numberOfPlayers = 6;
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
            	Game game = new Game( numberOfPlayers );		
        		GUI window = new GUI( "Banco Imobiliario" , new Dimension(1020, 1024) , numberOfPlayers );
        		
        		window.addObserver( game );
        		
        		window.setUpPlayerPinViews( game.getPlayers() );
        		window.setUpDiceView( game.getDice() );		
        		
        		window.setupWindow();		
        		
        		window.displayWindow( true );
            }
        });
		
		
		
	}

}
