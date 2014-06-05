package com.monopoly.game;

/**
* This class implements the Company Cards in a Monopoly Game. 
*/

public class CompanyCard 
	extends Card
{
	private int price;      // preço da carta
	private int multiplier; // multiplicador 
	private int mortgage;   // valor da hipoteca

	/**
	* Company Card constructor with specified parameters
	*
	*@param name 		Name of the company
	*@param id 			Numerical id of the card
	*@param mult 		Multiplier of the card
	*@param mort 		The mortgage cost
	*@param price 		The price of the card
	*/
	
	public CompanyCard(String name, int id, int mult, int mort, int price)
	{
		super(name, id);
		
		this.multiplier = mult;
		this.mortgage = mort;
		this.price = price;
	}

	/**
	* Company Card constructor 
	*
	*@param name 		Name of the company
	*@param id 			Numerical id of the card
	*/
	
	public CompanyCard(String name, int id)
	{
		this(name, id , 1 , 0 , 0 );
	}

	/**
	* Returns the multiplier of the card
	*
	*@return 		The multiplier
	*/
	
	public int getMultiplier() 
	{
		return multiplier;
	}

	/**
	* Returns the mortgage cost of the card
	*
	*@return 		The mortgage cost
	*/

	public int getMortgage()
	{
		return mortgage;
	}

	/**
	* Returns the price of the card
	*
	*@return 		The price
	*/

	public int getPrice()
	{
		return price;
	}
	
	/**
	* Returns the amount due
	*
	*@return 		The multiplier times the base value
	*/

	public int getDue( int baseValue ) {
		return ( this.multiplier * baseValue );
	}

	/**
	* Converts the information stored in a CompanyCard to a String
	*
	*@return 		The formatted string
	*/
	
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
