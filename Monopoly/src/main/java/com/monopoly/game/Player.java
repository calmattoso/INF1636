package com.monopoly.game;

import java.util.Observable;

import com.monopoly.gui.Board;

public class Player 
	extends Observable
{
	private Board.BoardSpaces position;
	private PlayerColor pinColor;
	private double money;
	
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
	
 	public Player( Board.BoardSpaces position , double money , PlayerColor pinColor )
	{
		this.position = position;
		
		this.money = money;
		
		this.pinColor = pinColor;
	}
	
	public Player()
	{
		this( Board.BoardSpaces.INICIO , 0.0 , PlayerColor.BLACK );
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
