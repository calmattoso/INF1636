/**
 * 
 */
package com.monopoly;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author Carlos Mattoso
 *
 */
public class MonopolyApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Board board = new Board( 50 , 50 );
		
		final Player p1 = new Player( Board.BoardSpaces.START , 0.0 , Player.PlayerColor.BLACK );
		final PlayerPinView p1View = new PlayerPinView( p1 , new Point( 25 , 25 ));
		
		final Dices d = new Dices( 6 );		
		
				
		final GUI window = new GUI( "Monopoly" , new Dimension( 1020 , 1024 ) );
		
		p1.addObserver( p1View );
		
		JButton changePosition = new JButton("Roll Dice");
		changePosition.setBounds(0, 0, 130, 20);
		
		changePosition.setEnabled(true);
		changePosition.setActionCommand("change");
		changePosition.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("button clicked");
				
				d.roll();
				System.out.println("Dice rolled:" + String.valueOf(d.getDie1()) + " " + String.valueOf(d.getDie2()));
				
				Board.BoardSpaces newSpace = p1.getPosition().getNext( d.getSum() );
				
				p1.setPosition( newSpace );
			}
		});
		
		
		
		window.addComponent( changePosition , 2 , 0 );
		window.addComponent( p1View , 1 , 0 );
		window.addComponent( board , 0 , 0 );		
				
		window.setupWindow();		
		
		window.displayWindow( true );
	}

}
