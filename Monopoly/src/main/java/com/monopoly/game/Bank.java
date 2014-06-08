package com.monopoly.game;

import com.monopoly.game.TerrainCard.CondRet;
import com.monopoly.game.TerrainCard.PropertyType;

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
	public void mortgage(PropertyCard c)
	{
		 if (c instanceof TerrainCard)
			 this.mortgageTerrain((TerrainCard)c);
		 else if (c instanceof CompanyCard)
			 this.mortgageCompany((CompanyCard)c);
			 
		}
	
	/**
	 * Mortgages a terrain, removing first all the properties from it.
	 * 
	 * @param t 		A terrain card
	 */	
	public void mortgageTerrain(TerrainCard t)
	{
		if(t.getPropertyQuantity(PropertyType.HOUSE) != 0 || 
		   t.getPropertyQuantity(PropertyType.HOTEL) != 0)
		{
			this.sellAllProperties(t);
		}
		
		t.addMortgage();
		t.getOwner().updateMoney(t.getMortgage());
	}
	
	/**
	 * Sells a hotel or house to the bank
	 * 
	 * @param c			A terrain card
	 * @param type		The type or property to be sold
	 * @return 			A return condition
	 */	
	public CondRet sellProperty(TerrainCard c, PropertyType type )			
	{
		if(c.subProperty(type) != CondRet.PROPERTY_REMOVED)
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
	public void mortgageCompany(CompanyCard c)
	{
		c.addMortgage();
		c.getOwner().updateMoney(c.getMortgage());
	}
	
	/**
	 * Pays back one mortgage
	 * 
	 * @param p 		A property card
	 * @return 			A return condition
	 */	
	public CondRet payMortgageBack(PropertyCard p)
	{
		if (p.getOwner().getMoney() < p.getMortgage()*1.2 || p.getMortgageCount() == 0){
			return CondRet.MISSING_REQUIREMENTS;
		}
		p.getOwner().updateMoney((int)(-1*p.getMortgage()*1.2));
		p.subMortgage();
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
		if (p1.getMoney() < money || card.getMortgageCount() != 0)
			return CondRet.MISSING_REQUIREMENTS;
		if(card instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card);
		p1.updateMoney(-money);
		p2.updateMoney(money);
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
		if(card1.getMortgageCount() != 0 || card1.getMortgageCount() != 0 )
			return CondRet.MISSING_REQUIREMENTS;
		
		if(card1 instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card1);
		if(card2 instanceof TerrainCard)
			Bank.getBank().sellAllProperties((TerrainCard)card2);
		
		card1.setOwner(p2);
		card2.setOwner(p1);
		
		return CondRet.TRADE_SUCCESSFULL;
	}
		
}
