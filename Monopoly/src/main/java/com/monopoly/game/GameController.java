package com.monopoly.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import com.monopoly.gui.CompanyCardManager;
import com.monopoly.gui.DataButton;
import com.monopoly.gui.GUI;
import com.monopoly.gui.GUIEvents;

public class GameController 
	implements Observer, GUIEvents, ActionListener
{
	private Game game;
	private GUI  window;
	
	public GameController( int numberOfPlayers ) {
		this.game   = new Game( numberOfPlayers );
		this.window = new GUI( "Banco Imobili�rio" , new Dimension(1440, 898) , numberOfPlayers , (ActionListener)(this) );
				
		window.setUpPlayerPinViews( game.getPlayers() );
		window.setUpDiceView( game.getDice() );	
		window.setUpCurrentPlayerView( game , (ActionListener)(this) );
		window.setUpControls( (ActionListener)(this) , game );
		window.setUpPlayersBalanceView( this.game.getPlayers() );
		
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

			
			if ( message.equals( GUI_BTN_DICE_ROLLED  ) )
			{
				
			
			}
		}

	}
	
	/**
	 * Usado para enviar mensagens para o controlador do jogo. (Game.java)
	 * Lida com algum evento gerado pelo usu�rio ao interagir com a GUI.
	 * 	Em geral, cliques em bot�es.
	 * 
	 * @param event
	 */
 	public void actionPerformed( ActionEvent event ) {
		String message = event.getActionCommand();
		
		/**
		 * If someone has clicked on the roll dice button, evaluate the current game turn.
		 */
		if( message == GUI_BTN_DICE_ROLLED )
		{	
			System.out.println("Dice rolled");
			
			this.game.evaluateMovement();
		}
		/**
		 * If someone has clicked on the jail pass button, get the current player out of jail.
		 * ( the button would be disabled otherwise ).
		 */
		else if( message == GUI_BTN_JAIL_PASS )
		{	
			this.game.getOutOfJail( this.game.getCurrentPlayerID() , false );
			this.game.getCurrentPlayer().setJailPass( false );
			
			System.out.println("Jail pass button clicked");
		}
		/**
		 * The player has decided to view information about one of his/her company cards.
		 */
		else if( message == GUI_BTN_COMPANY_CARD )
		{	
			DataButton source = (DataButton) event.getSource();
			int id = ((Board.CardInfo) source.getData()).getID();
			String output = this.game.getCompanyCard(id).toString();
			
			this.window.showCardManager(this.game.getCompanyCard(id));	
			
			System.out.println("Company card button clicked\n" + output);
		}
		/**
		 * The player has decided to view information about one of his/her terrain cards.
		 */
		else if( message == GUI_BTN_TERRAIN_CARD )
		{	
			DataButton source = (DataButton) event.getSource();
			int id = ((Board.CardInfo) source.getData()).getID();
			String output = this.game.getTerrainCard(id).toString();
			
			this.window.showCardManager(this.game.getTerrainCard(id));	
			
			System.out.println("Terrain card button clicked\n" + output);
		}
	}

	
}
