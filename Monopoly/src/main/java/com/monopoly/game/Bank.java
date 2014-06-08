package com.monopoly.game;

import com.monopoly.game.TerrainCard.PropertyType;
import com.monopoly.game.TerrainCard.TerrainCondRet;

/**
* This class implements the Bank in a Monopoly Game. 
* The Bank never goes broke.
*/

public class Bank {
	private static Bank bank = null;	
	
	/**
	 * Singleton pattern
	 */
	public static Bank getBank()
	{
		if(bank == null)
			bank = new Bank();
		return bank;
	}
	
	/**
	* Possible return conditions
	*/

	public static enum CondRet
	{
		PROPERTY_LIMIT_REACHED,
		PROPERTY_ADDED,
		MISSING_REQUIREMENTS,
		REMOVAL_FAILED,
		PROPERTY_REMOVED,
		MORTGAGE_REPAID,
		TRADE_SUCCESSFULL,
		CANNOT_MORTGAGE,
		PROPERTY_MORTGAGED
	}
	
	/**
	 * Pays the player his fee when he/she reaches or passes the start square
	 * 
	 * @param p	The player
	 */
	public void payFees(Player p)
	{
		p.updateMoney(200);
	}
	
	/**
	 * Mortgages a terrain or company
	 * 
	 * @param c A terrain or company card
	 */	
	public CondRet mortgage(PropertyCard c)
	{
		if(c.isMortgaged()==true)
			return CondRet.CANNOT_MORTGAGE;
		 if (c instanceof TerrainCard)
			 return this.mortgageTerrain((TerrainCard)c);
		 else if (c instanceof CompanyCard)
			 return this.mortgageCompany((CompanyCard)c);	
		 else
			 return CondRet.CANNOT_MORTGAGE;
		}
	
	/**
	 * Mortgages a terrain, removing first all the properties from it.
	 * 
	 * @param t 		A terrain card
	 */	
	private CondRet mortgageTerrain(TerrainCard t)
	{
		if(t.getPropertyQuantity(PropertyType.HOUSE) != 0 || 
		   t.getPropertyQuantity(PropertyType.HOTEL) != 0)
		{
			this.sellAllProperties(t);
		}
		
		t.setMortgage(true);
		t.getOwner().updateMoney(t.getMortgage());
		return CondRet.PROPERTY_MORTGAGED;
	}
	
	/*
	 * Sells a hotel or house to the bank
	 * 
	 * @param c			A terrain card
	 * @param type		The type or property to be sold
	 * @return 			A return condition
	 */	
	public CondRet sellProperty(TerrainCard c, PropertyType type )			
	{
		if(c.subProperty(type) != TerrainCondRet.PROPERTY_REMOVED)
			return CondRet.REMOVAL_FAILED;
		
		c.getOwner().updateMoney(c.getPrice()/2);
		
		return CondRet.PROPERTY_REMOVED;
	}
	
	/**
	 * Sells all hotels and houses in a Terrain
	 * 
	 * @param c			A terrain card
	 * @return 			A return condition
	 */
	public CondRet sellAllProperties(TerrainCard c)
	{
		while(c.getPropertyQuantity(PropertyType.HOTEL) > 0){
			if(sellProperty(c, PropertyType.HOTEL )!=CondRet.PROPERTY_REMOVED)
				return CondRet.REMOVAL_FAILED;
		}
		while(c.getPropertyQuantity(PropertyType.HOUSE) > 0){
			if(sellProperty(c, PropertyType.HOUSE )!=CondRet.PROPERTY_REMOVED)
				return CondRet.REMOVAL_FAILED;
		}	
		return CondRet.PROPERTY_REMOVED;
	}
	
	/**
	 * Mortgages a company
	 * 
	 * @param c			A company card
	 */	
	private CondRet mortgageCompany(CompanyCard c)
	{
		if(c.isMortgaged() == true){
			return CondRet.CANNOT_MORTGAGE;
		}
		c.setMortgage(true);
		c.getOwner().updateMoney(c.getMortgage());
		return CondRet.PROPERTY_MORTGAGED;
	}
	
	/**
	 * Pays back one mortgage
	 * 
	 * @param p 		A property card
	 * @return 			A return condition
	 */	
	public CondRet payMortgageBack(PropertyCard p)
	{
		if (p.getOwner().getMoney() < p.getMortgage()*1.2 || p.isMortgaged() == false){
			return CondRet.MISSING_REQUIREMENTS;
		}
		p.getOwner().updateMoney((int)(-1*p.getMortgage()*1.2));
		p.setMortgage(false);
		return CondRet.MORTGAGE_REPAID;
	}
	
	/**
	 * Trades money from player 1 for a card from player 2.
	 * 
	 * @param p1 			Player 1
	 * @param p2			Player 2
	 * @param money			The previously agreed ammount of money
	 * @param card			The card to be purchased
	 * @return				A return condition
	 */	
	public CondRet trade(Player p1, Player p2, int money, PropertyCard card)
	{
		if (p1.getMoney() < (money + card.getMortgage()*0.2))
			return CondRet.MISSING_REQUIREMENTS;
		if(card instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card);
		p1.updateMoney((int)(-(money + card.getMortgage()*0.2)));
		card.setMortgage(false);
		p2.updateMoney(money);
		p2.removeCard(card);
		p1.addCard(card);
		card.setOwner(p1);
		return CondRet.TRADE_SUCCESSFULL;	
	}
	
	/**
	 * Trades money from player 2 for a card from player 1.
	 * 
	 * @param p1 			Player 1
	 * @param p2			Player 2
	 * @param money			The previously agreed ammount of money
	 * @param card			The card to be purchased
	 * @return				A return condition
	 */
	public CondRet trade(Player p1, Player p2,PropertyCard card, int money)
	{
		return trade(p2,p1,money, card);
	}
	
	/**
	 * Players exchange cards.
	 * 
	 * @param p1		Player 1
	 * @param p2		Player 2
	 * @param card1		The card to be exchanged
	 * @param card2		The card to be exchanged
	 * @return			A return condition
	 */	
	public CondRet trade(Player p1, Player p2, PropertyCard card1, PropertyCard card2)
	{
		if(card1.isMortgaged()== true && card2.isMortgaged() == false){
			if(p2.getMoney() < card1.getMortgage()*0.2)
				return CondRet.MISSING_REQUIREMENTS;
			p2.updateMoney((int)(-card1.getMortgage()*0.2));
			card1.setMortgage(false);
		}
		else if(card2.isMortgaged()==true && card1.isMortgaged() ==false){
			if(p1.getMoney() < card1.getMortgage()*0.2)
				return CondRet.MISSING_REQUIREMENTS;
			p1.updateMoney((int)(-card2.getMortgage()*0.2));
			card2.setMortgage(false);
		}
		else if(card2.isMortgaged()==true && card1.isMortgaged() ==true){
			if((p1.getMoney() < card1.getMortgage()*0.2) || (p2.getMoney() < card1.getMortgage()*0.2))
				return CondRet.MISSING_REQUIREMENTS;
			p1.updateMoney((int)(-card2.getMortgage()*0.2));
			p2.updateMoney((int)(-card1.getMortgage()*0.2));
			card1.setMortgage(false);
			card2.setMortgage(false);
		}		
		
		if(card1 instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card1);
		if(card2 instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card2);
		
		p1.removeCard(card1);
		p2.removeCard(card2);
		p1.addCard(card2);
		p2.addCard(card1);
		card1.setOwner(p2);
		card2.setOwner(p1);
		
		return CondRet.TRADE_SUCCESSFULL;
	}
		
	public CondRet sellToBank(Player p, PropertyCard c ){
		if(c.isMortgaged()==true)
			return CondRet.MISSING_REQUIREMENTS;
		 if (c instanceof TerrainCard)
			 return this.sellTerrain((TerrainCard)c);
		 else if (c instanceof CompanyCard)
			 return this.sellCompany((CompanyCard)c);	
		 else
			 return CondRet.MISSING_REQUIREMENTS;
	}
	
	private CondRet sellCompany(CompanyCard c)
	{
		c.getOwner().updateMoney(c.getPrice()/2);
		c.getOwner().removeCard(c);
		c.setHasOwner(false);
		c.setOwner(null);
		return CondRet.TRADE_SUCCESSFULL;
	}
	
	private CondRet sellTerrain(TerrainCard t){
		if(t.getPropertyQuantity(PropertyType.HOUSE) != 0 || 
			t.getPropertyQuantity(PropertyType.HOTEL) != 0)
			{
				this.sellAllProperties(t);
			}
		t.getOwner().updateMoney(t.getPrice()/2);
		t.getOwner().removeCard(t);
		t.setHasOwner(false);
		t.setOwner(null);
		return CondRet.TRADE_SUCCESSFULL;
	}
}
