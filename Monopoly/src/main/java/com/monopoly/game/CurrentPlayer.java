package com.monopoly.game;

import java.util.Observable;

/**
* This class allows the game to set and identify the current player
*/

public class CurrentPlayer 
	extends Observable
	implements GameEvents
{
	private Player player;
	
	/**
	* Player constructor
	*
	*@param p 		A player
	*/

	public CurrentPlayer( Player p )
	{
		player = p;
	}

	/**
	* Returns the current player
	*
	*@return 		The current player
	*/
	
	public Player getPlayer()
	{
		return this.player;
	}

	/** 
	* Sets the current player
	*
	*@param p 		A player
	*/
	
	public void setPlayer( Player p )
	{
		this.player = p;
		
		notifyObservers( GAME_NEW_PLAYER );
		setChanged();		
	}
}
