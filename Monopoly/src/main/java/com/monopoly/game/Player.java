package com.monopoly.game;

import java.util.ArrayList;
import java.util.Observable;

/**
* This class implements the players of a Monopoly game.
*/

public class Player 
	extends Observable
{
	private Board.BoardSpaces position;
	private PlayerColor pinColor;
	private ArrayList<Card> ownedCards;
	private int money;
	private boolean jailPass;
	
	/**
	* This is the list of colors available for player pins. Each player is represented by one pin. All pins in an ongoing game
	* have different colors. 
	*/
	
	public static enum PlayerColor {
		BLACK,
		BLUE,
		ORANGE,
		PURPLE,
		RED,
		YELLOW;
		
		private String toString;
		
		static {
			BLACK.toString  = "black";
			BLUE.toString   = "blue";
			ORANGE.toString = "orange";
			PURPLE.toString = "purple";
			RED.toString    = "red";
			YELLOW.toString = "yellow";
		}
		/**
		* Turns player color into a string
		*
		*@param p 			The color of the player			
		*@return 			A string representing the player color.
		*/

 		public static String colorToString( PlayerColor p )
		{
			return p.toString;
		}	

		/**
 		* Returns the next player's color
 		*
 		*@return 			A player color
 		*/
 		
 		public PlayerColor getNext(){
 			return values()[ (ordinal() + 1 ) % values().length ];
 		}
	}
	
	/**
	* Player constructor with specified parameters
	* 
	*@param position 		The player's position
	*@param money 			The player's initial money amount
	*@param pinColor 		The player's pin color
	*/
	
 	public Player( Board.BoardSpaces position , int money , PlayerColor pinColor )
	{
		this.position = position;		
		this.money = money;		
		this.pinColor = pinColor;
		this.ownedCards = new ArrayList<Card>();
		this.jailPass = false;
	}

	/**
	* Player constructor
	*/
	
	public Player()
	{
		this( Board.BoardSpaces.INICIO , 2458 , PlayerColor.BLACK );
	}
	
	/**
	* Returns the player's pin color
	*
	*@return 				The pin color
	*/

	public PlayerColor getPinColor(){
		return pinColor;
	}

	/**
	* Sets a player's pin color
	*
	*@param p 				The pin color
	*/
	
	public void setPinColor( PlayerColor p ) {
		this.pinColor = p;
	}

	/**
	* Returns the player's money amount
	*
	*@return 				The money amount
	*/
	
	public int getMoney(){
		return money;
	}

	/**
	* Gives or takes from the player player a certain amount of money
	*
	*@param amount 			The amount of money
	*/
	
	public void updateMoney( int ammount ){
		money += ammount;
	}

	/**
	* Returns the cards this player owns
	*
	*@return 				An array of the cards this player owns
	*/
	
	public ArrayList<Card> getOwnedCards()
	{
		return this.ownedCards;
	}

	/**
	* Sets the jailPass status of a player
	*
	*@param v 				Boolen indicating if the player posesses or not a Jail Pass
	*/
	
	public void setJailPass( boolean v )
	{
		this.jailPass = true;
	}
	
	/**
	* Adds a card to the player's list of owned cards
	*
	*@param newCard			The new card to be added
	*/

	public void addCard( Card newCard )
		throws IllegalArgumentException
	{
		if( newCard.getHasOwner() == true )
		{
			throw new IllegalArgumentException("Card already has an owner!");
		}
		if( this.ownedCards.contains( newCard ) == true )
		{
			throw new IllegalArgumentException("Player already has this card!");
		}
		
		this.ownedCards.add( newCard );
	}

	/**
	* Returns a player's current position
	*
	*@return 				The current position
	*/
	
	public Board.BoardSpaces getPosition(){
		return position;
	}

	/**
	* Sets a player's new position
	*
	*@param newPosition 	The new position
	*/

	
	public void setPosition( Board.BoardSpaces newPosition )
	{
		this.position = newPosition;
		this.setChanged();				
		this.notifyObservers( this.position );	
	}
	
}
