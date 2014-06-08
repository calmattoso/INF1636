package com.monopoly.game;

public class PropertyCard extends Card {
	private boolean mortgaged;
	protected int mortgage; 	// valor da hipoteca
	
	public PropertyCard(String title, int id){
		super(title,id);
	}
	
	/**
	* Returns the mortgage cost of the card
	*
	*@return 		The mortgage cost
	*/

	public int getMortgage()
	{
		return this.mortgage;
	}

	/**
	 * Returns the number of mortgages taken on that property
	 * 
	 * @return 			The number of mortgages
	 */
	public boolean isMortgaged(){
		return this.mortgaged;
	}
	
	/**
	 * Adds one to the mortgage count
	 */
	
	public void setMortgage(boolean b){
		this.mortgaged = b;
	}
		
}
