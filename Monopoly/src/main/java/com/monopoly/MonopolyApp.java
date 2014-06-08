/**
 * 
 */
package com.monopoly;

import javax.swing.SwingUtilities;

import com.monopoly.game.GameController;
import com.monopoly.game.Music;


public class MonopolyApp 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int numberOfPlayers = 6;
		final Music BGM = new Music();
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
            	new GameController( numberOfPlayers );
            }
        });
		SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
            	BGM.playMusic("src/main/resources/music/monopoly.wav");
            }
            
        });
		
		
	}

}
