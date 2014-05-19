/**
 * 
 */
package com.monopoly;

import javax.swing.SwingUtilities;

import com.monopoly.game.GameController;


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
            	new GameController( numberOfPlayers );
            }
        });
		
		
		
	}

}
