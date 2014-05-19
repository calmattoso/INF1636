package com.monopoly.game;

import java.util.HashMap;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class Board {
	
	/**
	 * 
	 * Statically defined valid positions of the board.
	 *
	 */
	public static enum BoardSpaces {
		INICIO, LEBLON, SORTE, AV_PRESIDENTE_VARGAS, AV_NOSSA_S_DE_COPACABANA,
		COMPANHIA_FERROVIARIA, AV_BRIGADEIRO_FARIA_LIMA, COMPANHIA_DE_VIACAO,
		AV_REBOUCAS, AV_9_DE_JULHO, PRISAO, AV_EUROPA, SORTE2, RUA_AUGUSTA,
		AV_PACAEMBU, COMPANHIA_DE_TAXI, SORTE3, INTERLAGOS, LUCROS, MORUMBI,
		LIVRE, FLAMENGO, SORTE4, BOTAFOGO, IMPOSTO, COMPANHIA_DE_NAVEGACAO,
		AV_BRASIL, SORTE5, AV_PAULISTA,	JARDIM_EUROPA, VA_PARA_A_PRISAO,
		COPACABANA,	COMPANHIA_DE_AVIACAO, AV_VIEIRA_SOUTO, AV_ATLANTICA,
		COMPANHIA_DE_TAXI_AEREO, IPANEMA, SORTE6, JARDIM_PAULISTA, BROOKLIN;
		
		/**
		 * Convert a given position to string ( useful for parsing input file )
		 */
		private static HashMap< String , BoardSpaces > fromString;		
		static 
		{
			fromString = new HashMap<String,BoardSpaces>();
			
			fromString.put( "INICIO"                   ,  INICIO                    );               
			fromString.put( "LEBLON"                   ,  LEBLON                    );               
			fromString.put( "SORTE"                    ,  SORTE                     );               
			fromString.put( "AV_PRESIDENTE_VARGAS"     ,  AV_PRESIDENTE_VARGAS      );               
			fromString.put( "AV_NOSSA_S_DE_COPACABANA" ,  AV_NOSSA_S_DE_COPACABANA  );               
			fromString.put( "COMPANHIA_FERROVIARIA"    ,  COMPANHIA_FERROVIARIA     );               
			fromString.put( "AV_BRIGADEIRO_FARIA_LIMA" ,  AV_BRIGADEIRO_FARIA_LIMA  );               
			fromString.put( "COMPANHIA_DE_VIACAO"      ,  COMPANHIA_DE_VIACAO       );               
			fromString.put( "AV_REBOUCAS"              ,  AV_REBOUCAS               );               
			fromString.put( "AV_9_DE_JULHO"            ,  AV_9_DE_JULHO             );               
			fromString.put( "PRISAO"                   ,  PRISAO                    );               
			fromString.put( "AV_EUROPA"                ,  AV_EUROPA                 );               
			fromString.put( "SORTE2"                   ,  SORTE2                    );               
			fromString.put( "RUA_AUGUSTA"              ,  RUA_AUGUSTA               );               
			fromString.put( "AV_PACAEMBU"              ,  AV_PACAEMBU               );               
			fromString.put( "COMPANHIA_DE_TAXI"        ,  COMPANHIA_DE_TAXI         );               
			fromString.put( "SORTE3"                   ,  SORTE3                    );               
			fromString.put( "INTERLAGOS"               ,  INTERLAGOS                );               
			fromString.put( "LUCROS"                   ,  LUCROS                    );               
			fromString.put( "MORUMBI"                  ,  MORUMBI                   );               
			fromString.put( "LIVRE"                    ,  LIVRE                     );               
			fromString.put( "FLAMENGO"                 ,  FLAMENGO                  );               
			fromString.put( "SORTE4"                   ,  SORTE4                    );               
			fromString.put( "BOTAFOGO"                 ,  BOTAFOGO                  );               
			fromString.put( "IMPOSTO"                  ,  IMPOSTO                   );               
			fromString.put( "COMPANHIA_DE_NAVEGACAO"   ,  COMPANHIA_DE_NAVEGACAO    );               
			fromString.put( "AV_BRASIL"                ,  AV_BRASIL                 );               
			fromString.put( "SORTE5"                   ,  SORTE5                    );               
			fromString.put( "AV_PAULISTA"              ,  AV_PAULISTA               );               
			fromString.put( "JARDIM_EUROPA"            ,  JARDIM_EUROPA             );               
			fromString.put( "VA_PARA_A_PRISAO"         ,  VA_PARA_A_PRISAO          );               
			fromString.put( "COPACABANA"               ,  COPACABANA                );               
			fromString.put( "COMPANHIA_DE_AVIACAO"     ,  COMPANHIA_DE_AVIACAO      );               
			fromString.put( "AV_VIEIRA_SOUTO"          ,  AV_VIEIRA_SOUTO           );               
			fromString.put( "AV_ATLANTICA"             ,  AV_ATLANTICA              );               
			fromString.put( "COMPANHIA_DE_TAXI_AEREO"  ,  COMPANHIA_DE_TAXI_AEREO   );               
			fromString.put( "IPANEMA"                  ,  IPANEMA                   );               
			fromString.put( "SORTE6"                   ,  SORTE6                    );               
			fromString.put( "JARDIM_PAULISTA"          ,  JARDIM_PAULISTA           );               
			fromString.put( "BROOKLIN"                 ,  BROOKLIN                  );               
		}
		
		public BoardSpaces getNext( int offset ) {
			return values()[ (ordinal() + offset ) % values().length ];
		}		
	}
	
	/**
	 * 
	 * Helper class to store information about a card that is linked to
	 *  a specific position.
	 *
	 */
	public static class CardInfo
	{
		private int id;
		private String type;
		
		CardInfo( int id , String type )
			throws IllegalArgumentException
		{
			if( id >= 0 )
			{
				this.id = id;
			}
			else
			{
				throw new IllegalArgumentException("Invalid card ID!");
			}
			
			if( type.equals("chance")  || 
				type.equals("terrain") ||
				type.equals("company") ||
				type.equals("none"))
			{
				this.type = type;
			}
			else
			{
				throw new IllegalArgumentException("Invalid card type!");
			}			
		}
	
		public int getID()
		{
			return this.id;
		}
	
		public String getType()
		{
			return this.type;
		}
	}	
	
	private HashMap< BoardSpaces , CardInfo > positionToCard;	
	
	public Board( String path )
	{
		this.positionToCard = new HashMap< BoardSpaces , CardInfo >();
		
		parseBoardXML( path );
	}
	
	/**
	 *  Returns relevant infor
	 * 
	 * @param position
	 * @return
	 */
	public CardInfo getCardAtPosition( BoardSpaces position )
	{
		return ( this.positionToCard.get( position ) );
	}
	
	/**
	 * Parses the XML file which describes the links between cards and 
	 *   board positions. Based on this information the positionToCard map
	 *   is prepared.
	 * 
	 * @param path Complete path to the input XML
	 */
	private void parseBoardXML( String path )
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(path));
	
		    // normalize text representation
		    doc.getDocumentElement().normalize ();
		    System.out.println ("Root element of the doc is " + 
		    	doc.getDocumentElement().getNodeName());
	
		    NodeList listOfCardInfo = doc.getElementsByTagName("cardInfo");
		     
		    System.out.println("Total no of cards : " + listOfCardInfo.getLength() );
	
		    for( int i = 0; i < listOfCardInfo.getLength() ; ++i)
		    {
		    	Node cardInfoNode = listOfCardInfo.item( i );
		        
		    	if( cardInfoNode.getNodeType() == Node.ELEMENT_NODE )
		    	{
		    		Element cardInfoElement = ( Element ) cardInfoNode;
	
		           /**
		            * Get the type of the card.
		            */
		            NodeList typeList = cardInfoElement.getElementsByTagName( "type" );
		            String cardType = typeList.item(0).getFirstChild().getTextContent();	
		            System.out.println("Card type : " + cardType );
	
		            /**
			         * Get the ID of the card.
			         */
		            NodeList cardIDList = cardInfoElement.getElementsByTagName( "cardID" );
		            String cardID = cardIDList.item(0).getFirstChild().getTextContent();		            
			        System.out.println("Card id : " + cardID );
	
			        /**
			         * Get the ID of the card.
			         */
		            NodeList positionList = cardInfoElement.getElementsByTagName( "position" );
		            String position = positionList.item(0).getFirstChild().getTextContent();		
			        System.out.println("Position : " + position );
			        
			        /**
			         * Finally, build the card info object and save it in the map.
			         */
			        
			        if( BoardSpaces.fromString.get( position ) != null )
			        {
			        	this.positionToCard.put( 
			        		BoardSpaces.fromString.get( position ) ,
			        		new CardInfo( Integer.valueOf(cardID) , cardType )
			        	);
			        }
			        else
			        {
			        	throw new IllegalArgumentException("Non-existent card type!");
			        }
		    	}
		    }
		}
		catch (SAXParseException err) 
		{
			System.out.println ("** Parsing error" + ", line " 
				+ err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());
		}
		catch (SAXException e) 
		{
			Exception x = e.getException ();
		    ((x == null) ? e : x).printStackTrace ();
		}
		catch (Throwable t) 
		{
			t.printStackTrace ();
		}
	}
}
