package com.monopoly.game;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.monopoly.gui.GUI;
import com.monopoly.gui.GUIEvents;

public class GameController 
	implements Observer, GUIEvents
{
	private Game game;
	private GUI  window;
	
	public GameController( int numberOfPlayers ) {
		this.game   = new Game( numberOfPlayers );
		this.window = new GUI( "Banco Imobiliario" , new Dimension(1020, 1024) , numberOfPlayers );
		
		window.addObserver( this );
		
		window.setUpPlayerPinViews( game.getPlayers() );
		window.setUpDiceView( game.getDice() );	
		window.setUpCurrentPlayer( game.getCurrentPlayer() );
		
		window.setupWindow();		
		
		window.displayWindow( true );
	}

	public GameController() {
		this(2);
	}

	/**
	 * Based on events triggered by the GUI, update the game accordingly.
	 */
	public void update(Observable gui, Object event) {
		if (event instanceof String) {
			String message = (String) event;

			/**
			 * If someone has clicked on the roll dice button, evaluate the current game turn.
			 */
			if ( message.equals( GUI_BTN_DICE_ROLLED  ) )
			{
				this.game.evaluateMovement();
			
			}
		}

	}

	
}
