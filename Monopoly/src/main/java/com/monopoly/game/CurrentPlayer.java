package com.monopoly.game;

import java.util.Observable;

public class CurrentPlayer 
	extends Observable
	implements GameEvents
{
	private Player player;
	
	public CurrentPlayer( Player p )
	{
		player = p;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public void setPlayer( Player p )
	{
		this.player = p;
		
		notifyObservers( GAME_NEW_PLAYER );
		setChanged();		
	}
}
