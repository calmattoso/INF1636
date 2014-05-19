package com.monopoly.game;

import java.util.Observable;

public class Card 
	extends Observable
{
	protected int id;
	protected String title;
	protected boolean hasOwner;
	
	public Card(String title, int id)
	{
		this.title = title;
		this.id = id;
		this.hasOwner = false;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean getHasOwner()
	{
		return this.hasOwner;
	}
	
	public void setHasOwner( boolean v )
	{
		hasOwner = v;
	}
}
