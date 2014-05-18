package com.monopoly.game;

import java.util.ArrayList;
import java.util.Observable;

public class Player 
	extends Observable
{
	private Board.BoardSpaces position;
	private PlayerColor pinColor;
	private ArrayList<Card> ownedCards;
	private int money;
	
	
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
		
 		public static String colorToString( PlayerColor p )
		{
			return p.toString;
		}	
 		
 		public PlayerColor getNext(){
 			return values()[ (ordinal() + 1 ) % values().length ];
 		}
	}
	
	
 	public Player( Board.BoardSpaces position , int money , PlayerColor pinColor )
	{
		this.position = position;		
		this.money = money;		
		this.pinColor = pinColor;
		this.ownedCards = new ArrayList<Card>();
	}
	
	public Player()
	{
		this( Board.BoardSpaces.INICIO , 2458 , PlayerColor.BLACK );
	}
	
	public PlayerColor getPinColor(){
		return pinColor;
	}
	
	public void setPinColor( PlayerColor p ) {
		this.pinColor = p;
	}
	
	public double getMoney(){
		return money;
	}
	
	public void depositMoney( double ammount ){
		money += ammount;
	}
	
	public void withdrawMoney( double ammount ){
		money -= ammount;
	}
	
	public ArrayList<Card> getOwnedCards()
	{
		return this.ownedCards;
	}
	
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
	
	public Board.BoardSpaces getPosition(){
		return position;
	}
	
	public void setPosition( Board.BoardSpaces newPosition )
	{
		this.position = newPosition;
		this.setChanged();				
		this.notifyObservers( this.position );	
	}
	
}
