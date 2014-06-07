package com.monopoly.game;

import java.util.HashMap;
import java.util.Map;

/**
* This class implements the terrain cards. Terrains are properties that can be owned by players. In them, the player can build
* houses and a hotel.
*/

public class TerrainCard
	extends PropertyCard
{
	private Map<PropertyType, Integer> propertyCapacity; // quantidade máxima por propriedade	
	private Map<PropertyType, Integer> propertyQuantity; // quantas propriedades de cada tipo
	private Map<PropertyType, int[]> rents; // associa cada tipo de propriedade aos possíveis valores de aluguel
	
	private int propertyCost; // custo de construção para casas e hoteis
	private int baseRent;
	private int price; // custo de compra da carta
	
	private Color color;
	
	
	/**
	 * Types of properties which can be built on a given terrain.
	 */
	public static enum PropertyType 
	{
		HOUSE,
		HOTEL;
	}
	
	/**
	 * 
	 * Each terrain card has a color which segments it into a specific group.
	 * 
	 */
	public static enum Color 
	{
		BLUE,
		GREEN,
		YELLOW,
		ORANGE,
		PINK,
		RED;
		
		/**
		 * Get the color for a given string.
		 */
		private static HashMap< String , Color > fromString;		
		static 
		{
			fromString = new HashMap<String,Color>();
			
			fromString.put( "BLUE"   , BLUE   );
			fromString.put( "GREEN"  , GREEN  );
			fromString.put( "YELLOW" , YELLOW );
			fromString.put( "ORANGE" , ORANGE );
			fromString.put( "PINK"   , PINK   );
			fromString.put( "RED"    , RED    );     
		}
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
		TRADE_SUCCESSFULL
	}

	/**
	* Terrain card constructor
	*
	*@param title 				The name of the terrain
	*@param id 					The numeric id of the terrain
	*@param propertyCost		The cost to build a property
	*@param mortage 			The morgage cost
	*@param price 				The price of the card
	*@param baseRent 			The rent cost with no constructions
	*@param color 				The color of the card
	*@param rents 				The rent prices with 1-3 houses and a hotel
	*@param PropertyCapacity 	The maximum ammount of properties
	*/	

	public TerrainCard(String title, int id, int propertyCost, int mortgage,
					   int price, int baseRent, String color,
					   Map<PropertyType, int[]> rents)
	{
		super(title, id);
		
		this.propertyCost = propertyCost;
		this.mortgage = mortgage;
		this.price = price;
		this.baseRent = baseRent;
		this.color = Color.fromString.get( color );
		
		this.rents = new HashMap<PropertyType, int[]>( rents );
		
		this.propertyCapacity = new HashMap<PropertyType, Integer>();
		this.propertyCapacity.put(PropertyType.HOUSE, rents.get(PropertyType.HOUSE).length  );
		this.propertyCapacity.put(PropertyType.HOTEL, rents.get(PropertyType.HOTEL).length  );
		
		this.propertyQuantity = new HashMap<PropertyType, Integer>();
		this.propertyQuantity.put( PropertyType.HOUSE , 0 );
		this.propertyQuantity.put( PropertyType.HOTEL , 0 );		
	}

	/**
	* Returns the cost of the property
	*
	*@return 		The cost of the property
	*/
	
	public int getCost()
	{
		return propertyCost;
	}

	
	/**
	* Returns the price of the card
	*
	*@return 		The price of the card
	*/
	
	public int getPrice()
	{
		return price;
	}

	/**
	* Returns quantity of properties already in that terrain
	*
	*@param type 	The type of the property
	*@return 		The quantity of properties
	*/
	
	public int getPropertyQuantity( PropertyType type )
	{
		return propertyQuantity.get( type );
	}

	/**
	* Returns the property capacity for hotels and houses
	*
	*@param type 	The type of property, which can be a house or a hotel
	*@return 		The property capacity
	*/
	
	public int getPropertyCapacity( PropertyType type )
	{
		return propertyCapacity.get( type );
	}

	/**
	* Determine rent value based on what properties exist on the terrain,
	* given their order of precedence. 
	* If there are no properties, return the base rent value.
	*
	*@return 			The rent value
	*/

	public int getRent( )
	{
		int currentHotelQuantity = this.propertyQuantity.get( PropertyType.HOTEL ),
			currentHouseQuantity = this.propertyQuantity.get( PropertyType.HOUSE ) ;
		
		/**
		 * Determine rent value based on what properties exist on the terrain,
		 *   given their order of precedence. 
		 * If there are no properties, return the base rent value.
		 */
		if( currentHotelQuantity > 0 )
		{
			return (this.rents.get( PropertyType.HOTEL ))[ currentHotelQuantity - 1 ];
		}
		else if( currentHouseQuantity > 0 )
		{
			return (this.rents.get( PropertyType.HOTEL ))[ currentHouseQuantity - 1 ];
		}
		else 
		{
			return baseRent;
		}
	}
	
	/**
	* Returns the color of the terrain card
	*
	*@return 			The color
	*/

	public Color getColor()
	{
		return this.color;
	}
	
	@Override
	public String toString()
	{
		String output;
		
		output =
			"Title: " + this.title + "\n" +
			"ID   : " + this.id    + "\n" +
			"Color: " + this.color + "\n" +
			"Price: " + this.price + "\n" +
			"PropPrice: " + this.propertyCost + "\n";
		
		PropertyType[] propertyTypes = new PropertyType[]{
			PropertyType.HOUSE , 
	        PropertyType.HOTEL 
		};
		
		for( PropertyType type: propertyTypes )
		{
			output += type + ": [ ";
			for( int value : this.rents.get(type) ) 
			{
				output += value + ", ";
			}
			output += "]\n";
		}
		
		return output;		
	}
	
	/**
	* Tries to build a property in that terrain
	*
	*@param type 		The type of property
	*@return 			A condition indicating if the property was successfully added, if the terrain is full,
	*					or if there were missing requirements
	*/

	public CondRet addProperty( PropertyType type )
			throws IllegalArgumentException
	{
		switch( type )
		{
		case HOUSE:
			return addHouse();			
			
		case HOTEL:
			return addHotel();
		
		default:
			throw new IllegalArgumentException("Invalid property type!");
		}
	}
	
	/**
	* Tries to build a Hotel in that terrain
	*
	*@return 			A condition indicating if the hotel was successfully added, if the terrain is full,
	*					or if there were missing requirements
	*/

	private CondRet addHotel() {
		int currentHotelQuantity = this.propertyQuantity.get( PropertyType.HOTEL ),
			currentHouseQuantity = this.propertyQuantity.get( PropertyType.HOUSE ) ;	
		
		/**
		 * A hotel can only be added if there's still an empty slot for it.
		 */
		if( currentHotelQuantity < this.propertyCapacity.get( PropertyType.HOTEL ) )
		{
			/**
			 * Furthermore, all houses must have been built
			 */
			if( currentHouseQuantity == this.propertyCapacity.get( PropertyType.HOUSE ))
			{
				this.propertyQuantity.put( PropertyType.HOTEL , currentHotelQuantity + 1 );
				
				return CondRet.PROPERTY_ADDED;
			}
			else
			{
				return CondRet.MISSING_REQUIREMENTS;
			}
		}			
		else
		{
			return CondRet.PROPERTY_LIMIT_REACHED;
		}
	}

	/**
	* Tries to build a House in that terrain
	*
	*@return 			A condition indicating if the house was successfully added, if the terrain is full,
	*					or if there were missing requirements
	*/

	private CondRet addHouse()
	{
		int currentHouseQuantity = this.propertyQuantity.get( PropertyType.HOUSE );
		
		/**
		 * Houses can always be added as long as the terrain's housing capacity has not been reached.
		 */
		if( currentHouseQuantity < this.propertyCapacity.get( PropertyType.HOUSE ) )
		{
			this.propertyQuantity.put( PropertyType.HOUSE , currentHouseQuantity + 1 );
			
			return CondRet.PROPERTY_ADDED;
		}
		else 
		{
			return CondRet.PROPERTY_LIMIT_REACHED;
		}
	}
	
	/**
	 * Removes one property from the terrain
	 * 
	 * @param type	Type of the property	
	 * @return		A return condition
	 * @throws IllegalArgumentException
	 */
	public CondRet subProperty( PropertyType type )
			throws IllegalArgumentException
	{
		switch( type )
		{
		case HOUSE:
			return subHouse();			
			
		case HOTEL:
			return subHotel();
		
		default:
			throw new IllegalArgumentException("Invalid property type!");
		}
	}
	/**
	 * Removes a hotel from the terrain
	 * 
	 * @return		A return condition
	 */
	private CondRet subHotel() {
		int currentHotelQuantity = this.propertyQuantity.get( PropertyType.HOTEL );	
		
		if( currentHotelQuantity == 0 )
		{
			return CondRet.REMOVAL_FAILED;
		}			
		else
		{
			this.propertyQuantity.put( PropertyType.HOTEL , currentHotelQuantity - 1 );
			return CondRet.PROPERTY_REMOVED;
		}
	}
	
	/**
 	*Removes a house from the terrain
 	* 
 	* @return		A return condition
 	*/
	private CondRet subHouse()
	{
		int currentHouseQuantity = this.propertyQuantity.get( PropertyType.HOUSE ),
			currentHotelQuantity = this.propertyQuantity.get( PropertyType.HOTEL );
		

		if( currentHotelQuantity > 0 || currentHouseQuantity == 0 )
		{
			return CondRet.REMOVAL_FAILED;
		}
		else 
		{	
			this.propertyQuantity.put( PropertyType.HOUSE , currentHouseQuantity - 1 );
			return CondRet.PROPERTY_REMOVED;
		}
	}
	
	
}
