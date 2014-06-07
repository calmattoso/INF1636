package com.monopoly.game;

import java.util.Observable;

/**
* This class implements the cards used in a Monopoly game
*/

public class Card 
	extends Observable
{
	protected int id;
	protected String title;
	protected boolean hasOwner;
	protected Player owner;

	/**
	* Card constructor
	* 
	*@param title 	Title of the card
	*@param id 		Numeric id of the card
	*/
	
	public Card(String title, int id)
	{
		this.title = title;
		this.id = id;
		this.hasOwner = false;
	}

	/**
	* Returns the title of a card
	* 
	*@return 		Title of the card
	*/
	
	public String getTitle(){
		return title;
	}
	
	/**
	* Returns the ownership status of the card
	* 
	*@return 		True if the card has an owner and false if it doesn't
	*/

	public boolean getHasOwner()
	{
		return this.hasOwner;
	}

	/**
	* Sets the ownership status of the card
	* 
	*@param v 		boolean indicating if the card has or not an owner
	*/
	
	public void setHasOwner( boolean v )
	{
		hasOwner = v;
	}
	/**
	 * Returns the owner of the card
	 * 
	 * @return 		The owner of the card
	 */
	public Player getOwner(){
		return this.owner;
	}
	
	/**
	 * Sets the owner of the card
	 * 
	 * @param p		The owner of the card
	 */
	
	public void setOwner(Player p){
		this.owner = p;
	}
}
