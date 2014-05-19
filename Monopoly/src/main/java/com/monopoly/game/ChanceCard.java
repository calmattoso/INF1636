package com.monopoly.game;

import java.util.HashMap;

public class ChanceCard
	extends Card 	
{
	private String description;
	private boolean isGood;
	private SideEffect effect;
	private int amount;
	
	/**
	 * 
	 * List of all possible side effects a chance card might carry,
	 *   apart from taking from or giving to the player who picked 
	 *   it up (or to competitors) the value of {@link amount}.
	 *   
	 */
	public static enum SideEffect
	{
		GO_TO_JAIL,
		GET_OUT_OF_JAIL,
		GO_TO_START,
		CHARGE_COMPETITORS,
		NONE;
		
		public static HashMap<String,SideEffect> fromString;
		
		static 
		{
			fromString = new HashMap<String,SideEffect>();
			
			fromString.put( "GO_TO_JAIL"         , GO_TO_JAIL         );
			fromString.put( "GET_OUT_OF_JAIL"    , GET_OUT_OF_JAIL    );
			fromString.put( "GO_TO_START"        , GO_TO_START        );
			fromString.put( "CHARGE_COMPETITORS" , CHARGE_COMPETITORS );
			fromString.put( "NONE"               , NONE               );
		}
		
	}
		
	public ChanceCard(String title, int id, String description, int amount, SideEffect effect)
		throws IllegalArgumentException
	{
		super(title, id);
		
		
		if( title.equals( "SORTE" ))
		{
			this.isGood = true;
		}			
		else if ( title.equals( "AZAR" ))
		{
			this.isGood = false;
		}
		else
		{
			throw new IllegalArgumentException("Invalid title.");
		}		
			
		this.description = description;
		this.amount = amount;		
		this.effect = effect;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean getIsGood()
	{
		return isGood;
	}

	public int getAmount()
	{
		return amount;
	}
	
	public SideEffect getSideEffect()
	{
		return effect;
	}

	@Override
	public void setHasOwner( boolean v )
		throws IllegalArgumentException
	{
		/**
		 * Only the GET_OUT_OF_JAIL card can have an owner.
		 */
		if( v == true && this.effect != SideEffect.GET_OUT_OF_JAIL )
		{
			throw new IllegalArgumentException("Can't own this chance card!");
		}
		
		this.hasOwner = v;
	}
	
	public String toString()
	{
		String output;
		
		output =
			"Title : " + this.title + "\n" +
			"ID    : " + this.id    + "\n" +
			"Descr.: " + this.description + "\n" +
			"Amount: " + this.amount + "\n" +
			"SideEffect: " + this.effect  + "\n"  +
			"isGood    : " + this.isGood + "\n"; 
		
		return output;
	}
}
