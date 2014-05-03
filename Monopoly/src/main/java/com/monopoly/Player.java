package com.monopoly;

import java.util.Observable;

public class Player extends Observable {
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
	
 	Player( Board.BoardSpaces position , double money , PlayerColor pinColor )
	{
		this.position = position;
		
		this.money = money;
		
		this.pinColor = pinColor;
	}
	
	Player()
	{
		this( Board.BoardSpaces.START , 0.0 , PlayerColor.BLACK );
	}
	
	public PlayerColor getPinColor() {
		return pinColor;
	}
	
	public void setPinColor( PlayerColor p ) {
		this.pinColor = p;
	}
	
	public void depositMoney( double ammount ){
		money += ammount;
	}
	
	public void withdrawMoney( double ammount ){
		money -= ammount;
	}
	
	public double getMoney(){
		return money;
	}
	
	public Board.BoardSpaces getPosition(){
		return position;
	}
	
	public void setPosition( Board.BoardSpaces newPosition )
	{
		this.position = newPosition;
		this.setChanged();
		
		System.out.println(newPosition);
				
		this.notifyObservers( this.position );	
	}
}
