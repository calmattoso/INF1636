/**
 * 
 */
package com.monopoly;

import java.awt.Dimension;

/**
 * @author Carlos Mattoso
 *
 */
public class MonopolyApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Board board = new Board( 10 , 10 );
		GUI window = new GUI( "Monopoly" , new Dimension( 1024 , 1024 ) );
		
		window.add( board );
		window.setupWindow();
		
		
		window.displayWindow( true );
	}

}
