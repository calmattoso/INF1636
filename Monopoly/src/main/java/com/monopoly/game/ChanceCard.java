package com.monopoly.game;

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
		NONE
	}
		
	public ChanceCard(String title, int id, String description, int amount, SideEffect effect)
		throws IllegalArgumentException
	{
		super(title, id);
		
		if( title == "SORTE" )
		{
			this.isGood = true;
		}			
		else if ( title != "AZAR")
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
}
