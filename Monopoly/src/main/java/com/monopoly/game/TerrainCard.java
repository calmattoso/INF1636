package com.monopoly.game;

import java.util.HashMap;
import java.util.Map;

public class TerrainCard
	extends Card
{
	private Map<PropertyType, Integer> propertyCapacity; // quantidade máxima por propriedade	
	private Map<PropertyType, Integer> propertyQuantity; // quantas propriedades de cada tipo
	private Map<PropertyType, int[]> rents; // associa cada tipo de propriedade aos possíveis valores de aluguel
	
	private int propertyCost; // custo de construção para casas e hoteis
	private int baseRent;
	private int mortgage; 	// valor da hipoteca
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
	
	public static enum CondRet
	{
		PROPERTY_LIMIT_REACHED,
		PROPERTY_ADDED,
		MISSING_REQUIREMENTS
	}
	

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

	
	public int getCost()
	{
		return propertyCost;
	}
	
	public int getMortgage() 
	{
		return mortgage;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public int getPropertyQuantity( PropertyType type )
	{
		return propertyQuantity.get( type );
	}
	
	public int getPropertyCapacity( PropertyType type )
	{
		return propertyCapacity.get( type );
	}

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

	
}
