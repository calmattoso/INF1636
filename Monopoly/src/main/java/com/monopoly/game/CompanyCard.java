package com.monopoly.game;

public class CompanyCard 
	extends Card
{
	private int price;      // preço da carta
	private int multiplier; // multiplicador 
	private int mortgage;   // valor da hipoteca

	
	public CompanyCard(String name, int id, int mult, int mort, int price)
	{
		super(name, id);
		
		this.multiplier = mult;
		this.mortgage = mort;
		this.price = price;
	}
	
	public CompanyCard(String name, int id)
	{
		this(name, id , 1 , 0 , 0 );
	}

	
	public int getMultiplier() 
	{
		return multiplier;
	}

	public int getMortgage()
	{
		return mortgage;
	}

	public int getPrice()
	{
		return price;
	}
	
	public int getDue( int baseValue ) {
		return ( this.multiplier * baseValue );
	}
	
	public String toString()
	{
		String output;
		
		output =
			"Title: " + this.title + "\n" +
			"ID   : " + this.id    + "\n" +
			"Price: " + this.price + "\n" +
			"Multiplier: " + this.multiplier + "\n" +
			"Mortgage  : " + this.mortgage   + "\n" ;
		
		return output;
	}
	
	
}
