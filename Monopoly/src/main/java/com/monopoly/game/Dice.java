package com.monopoly.game;

import java.util.Observable;
import java.util.Random;

/** 
* This class implements two variable side dices. Both dice have the same number of sides, which is specified
* in the construction.
*/

public class Dice 
	extends Observable
{
	private int d1; //Dado numero 1
	private int d2; //Dado numero 2
	private int numberOfSides; // Quantidade de lados
	private Random dice;

	/**
	* Dice constructor
	*
	*@param numberOfSides 		Specifies the number of sides both dice will have.
	*/
	
	public Dice( int numberOfSides ){
		dice = new Random();
		this.numberOfSides = numberOfSides;
	}
	
	/**
	* Rolls both dice, assigning them a random number within their number of sides.
	*/


	public void roll(){
		d1 = dice.nextInt(numberOfSides) + 1;
		d2 = dice.nextInt(numberOfSides) + 1;
		
		updateObservers();
	}

	/**
	* Notifies the observers of the changes made
	*/
	
	private void updateObservers() {
		this.setChanged();		
		this.notifyObservers( "DICE_dicesRolled" );		
	}

	/**
	* Returns the value of die #1
	*
	*@return 		The result in die #1
	*/

	public int getDie1(){
		return d1;
	}

	/**
	* Returns the value of die #2
	*
	*@return 		The result in die #2
	*/
	
	public int getDie2(){
		return d2;
	}

	/**
	* Returns the sum of the values in die #1 and die #2
	*
	*@return 		The result in die #1 plus the result in die #2
	*/
	
	public int getSum(){
		return d1+d2;
	}

}
