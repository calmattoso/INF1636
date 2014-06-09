package com.monopoly.game;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.monopoly.game.Player;
import com.monopoly.game.TerrainCard.PropertyType;

/**
* This class controls the game , including players, movments, jail time, etc
*/

public class Game 
	extends Observable
	implements GameEvents 
{
	private Player[] players;
	private Board board;
	private Bank bank;
	private int currentPlayerID;
	
	private HashMap<Integer,ChanceCard> chanceCardsDeck;
	private HashMap<Integer,CompanyCard> companyCardsDeck;
	private HashMap<Integer,TerrainCard> terrainCardsDeck;
	
	private Dice dice;	
	private Random cardPicker = new Random();
	
	private int[] doubleCount;
	private int[] roundsInPrison;
	private boolean[] isInPrison;

	/**
	* Constructor of a new game of Monopoly
	*
	*@param numberOfPlayers 		The number of players in the current game
	*/	

	public Game(int numberOfPlayers) {
		dice = new Dice(6);
		
		/**
		 * Information to control prison sentences 
		 */
		doubleCount    = new int[numberOfPlayers];
		roundsInPrison = new int[numberOfPlayers];
		isInPrison 	   = new boolean[numberOfPlayers];

		players = new Player[numberOfPlayers];
		createPlayers(numberOfPlayers);
		
		currentPlayerID = 0;
		
		bank = Bank.getBank();
		
		/**
		 * Load information about all cards.
		 */
		chanceCardsDeck = new HashMap<Integer,ChanceCard>();
		companyCardsDeck = new HashMap<Integer,CompanyCard>();
		terrainCardsDeck = new HashMap<Integer,TerrainCard>();
		
		
		loadTerrainCards("src/main/config/cards.xml");
		loadCompanyCards("src/main/config/cards.xml");
		loadChanceCards("src/main/config/cards.xml");
		
		/**
		 * Set up the board.
		 */
		this.board = new Board("src/main/config/board.xml");
	}
	
	public Game() {
		this(2);
	}
	
	public Card getCompanyCard(int id)
	{
		return this.companyCardsDeck.get(id);
	}
	
	public Card getTerrainCard(int id)
	{
		return this.terrainCardsDeck.get(id);
	}

	/**
	 * Parse the cards XML file and construct the terrain cards based on input
	 *   information.
	 *   
	 * @param path Complete path to cards XML file.
	 */
	private void loadTerrainCards(String path) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(path));
	
		    // normalize text representation
		    doc.getDocumentElement().normalize();
		    System.out.println ("Root element of the doc is " + 
		    	doc.getDocumentElement().getNodeName());
	
		    Element terrainCardList = (Element) doc.getElementsByTagName("terrain").item(0);
		    NodeList listOfCards = terrainCardList.getElementsByTagName("card");
		     
		    System.out.println("Total no of terrain cards : " + listOfCards.getLength() );
	
		    for( int i = 0; i < listOfCards.getLength() ; ++i)
		    {
		    	Node cardNode = listOfCards.item( i );
		        
		    	if( cardNode.getNodeType() == Node.ELEMENT_NODE )
		    	{
		    		Element cardElement = ( Element ) cardNode;
	
		    		/**
			         * Get the ID of the card.
			         */
			        NodeList list = cardElement.getElementsByTagName( "id" );
			        int id = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
		    		
		    		/**
		            * Get the base rent of the card.
		            */
		            list = cardElement.getElementsByTagName( "baseRent" );
		            int baseRent = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
		            
		            /**
			         * Get the mortgage rent of the card.
			         */
			        list = cardElement.getElementsByTagName( "mortgage" );
			        int mortgage = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the price of the card.
			         */
			        list = cardElement.getElementsByTagName( "price" );
			        int cardPrice = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the price per property which can be built on the card.
			         */
			        list = cardElement.getElementsByTagName( "propertyPrice" );
			        int propertyPrice = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
	
			        /**
			         * Get the color of the card.
			         */
			        list = cardElement.getElementsByTagName( "color" );
			        String color = list.item(0).getFirstChild().getTextContent();
			        System.out.println("color: " + color);
			        
			        /**
			         * Get the title of the card.
			         */
			        list = cardElement.getElementsByTagName( "title" );
			        String title = list.item(0).getFirstChild().getTextContent();	
			        
			        /**
			         * For each property, generate an array of its rent prices.
			         */
			        String properties[] = new String[]{"house", "hotel"};
			        TerrainCard.PropertyType[] propertyTypes = new TerrainCard.PropertyType[]{
			        	TerrainCard.PropertyType.HOUSE , 
			        	TerrainCard.PropertyType.HOTEL 
			        };
			        int[] capacities = new int[]{4,1};
			        HashMap< TerrainCard.PropertyType,int[]> propertyRents = 
			        	new HashMap< TerrainCard.PropertyType,int[]>();
			        
			        for( int k = 0; k < properties.length; k++ )
			        {
			        	String property = properties[k];
			        	
			        	Element propertyElement = ( Element ) cardElement.getElementsByTagName( property ).item(0);	
			        	
			        	NodeList rentsList = propertyElement.getElementsByTagName("rent").item(0).getChildNodes();
			        	
			        	int[] rents = new int[ capacities[k] ];
			        	
			        	for( int j = 0, ridx = 0; j < rentsList.getLength() ; ++j )
			        	{
			        		if( rentsList.item(j).getFirstChild() != null )
			        		{
			        			rents[ridx] = Integer.valueOf( rentsList.item(j).getFirstChild().getTextContent() );
			        			ridx++;
			        		}
			        	}
			        	
			        	propertyRents.put( propertyTypes[k] , rents );
			        }
			        
				    TerrainCard terrainCard = new TerrainCard(
				    	title, id, propertyPrice, mortgage, cardPrice ,
				    	baseRent, color, propertyRents
				    );
				    
				    this.terrainCardsDeck.put(id, terrainCard);
				    System.out.println( terrainCard );
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

	private void loadCompanyCards(String path) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(path));
	
		    // normalize text representation
		    doc.getDocumentElement().normalize();
		    System.out.println ("Root element of the doc is " + 
		    	doc.getDocumentElement().getNodeName());
	
		    Element terrainCardList = (Element) doc.getElementsByTagName("company").item(0);
		    NodeList listOfCards = terrainCardList.getElementsByTagName("card");
		     
		    System.out.println("Total no of company cards : " + listOfCards.getLength() );
	
		    for( int i = 0; i < listOfCards.getLength() ; ++i)
		    {
		    	Node cardNode = listOfCards.item( i );
		        
		    	if( cardNode.getNodeType() == Node.ELEMENT_NODE )
		    	{
		    		Element cardElement = ( Element ) cardNode;
	
		    		/**
			         * Get the ID of the card.
			         */
			        NodeList list = cardElement.getElementsByTagName( "id" );
			        int id = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
		    		
		            /**
			         * Get the mortgage rent of the card.
			         */
			        list = cardElement.getElementsByTagName( "mortgage" );
			        int mortgage = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the mortgage rent of the card.
			         */
			        list = cardElement.getElementsByTagName( "multiplier" );
			        int multiplier = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the price of the card.
			         */
			        list = cardElement.getElementsByTagName( "price" );
			        int cardPrice = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
		        
			        /**
			         * Get the title of the card.
			         */
			        list = cardElement.getElementsByTagName( "name" );
			        String title = list.item(0).getFirstChild().getTextContent();	
			        
				    CompanyCard companyCard = new CompanyCard(
				    	title, id, multiplier, mortgage, cardPrice
				    );
				    
				    this.companyCardsDeck.put(id, companyCard);
				    System.out.println( companyCard );
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

	private void loadChanceCards(String path) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(path));
	
		    // normalize text representation
		    doc.getDocumentElement().normalize();
		    System.out.println ("Root element of the doc is " + 
		    	doc.getDocumentElement().getNodeName());
	
		    Element terrainCardList = (Element) doc.getElementsByTagName("chance").item(0);
		    NodeList listOfCards = terrainCardList.getElementsByTagName("card");
		     
		    System.out.println("Total no of company cards : " + listOfCards.getLength() );
	
		    for( int i = 0; i < listOfCards.getLength() ; ++i)
		    {
		    	Node cardNode = listOfCards.item( i );
		        
		    	if( cardNode.getNodeType() == Node.ELEMENT_NODE )
		    	{
		    		Element cardElement = ( Element ) cardNode;
	
		    		/**
			         * Get the ID of the card.
			         */
			        NodeList list = cardElement.getElementsByTagName( "id" );
			        int id = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the title of the card.
			         */
			        list = cardElement.getElementsByTagName( "title" );
			        String title = list.item(0).getFirstChild().getTextContent();	
			        
			        /**
			         * Get the description of the card.
			         */
			        list = cardElement.getElementsByTagName( "description" );
			        String description = list.item(0).getFirstChild().getTextContent();	
		    		
		            /**
			         * Get the amount paid/taken by the card.
			         */
			        list = cardElement.getElementsByTagName( "amount" );
			        int amount = Integer.valueOf( list.item(0).getFirstChild().getTextContent() );
			        
			        /**
			         * Get the side effect type.
			         */
			        list = cardElement.getElementsByTagName( "sideEffect" );
			       	String sideEffectStr = list.item(0).getFirstChild().getTextContent();
			       	
			       	ChanceCard.SideEffect sideEffect = ChanceCard.SideEffect.fromString.get( sideEffectStr );
			        
				    ChanceCard chanceCard = new ChanceCard(
				    	title, id, description, amount, sideEffect
				    );
				    
				    this.chanceCardsDeck.put(id, chanceCard);
				    System.out.println( chanceCard );
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

	/**
	 * Execute one turn of movement for the current player, evaluating game rules.
	*/
	public void evaluateMovement() {
		Player currentPlayer = players[currentPlayerID];
		Board.BoardSpaces position = currentPlayer.getPosition(), 
						  nextPosition = Board.BoardSpaces.INICIO ;

		dice.roll();
		
		// Se o jogador anterior acabou caindo na posição "Vá para prisão",
		//   ele é enviado para a prisão.
		previousPlayerJailStatus();
		
		// Calcula imediatamente a potencial próxima posição
		nextPosition = position.getNext( dice.getSum() );
		boolean roundTrip = position.roundTrip( dice.getSum());
		
		//se não for dupla
		if( dice.getDie1() != dice.getDie2() )
		{
			if( isInPrison[ currentPlayerID ] == true )
			{
				//se o jogador estiver na prisão, aumenta o tempo de rodadas dele
				roundsInPrison[currentPlayerID]++;
				
				if( roundsInPrison[currentPlayerID] > 3 )
				{
					// se o jogador tiver passado 4 rodadas na prisão, sai pagando multa
					getOutOfJail( currentPlayerID , true );
				}
			}
			else
			{
				//reseta o contador de duplas, caso ele não esteja na prisão
				doubleCount[currentPlayerID] = 0;
				
				moveCurrentPlayer( nextPosition , roundTrip );
				
				checkLandingPosition();
			}			

			nextPlayer();
		}

		else
		{
			//se for dupla			
			if( isInPrison[ currentPlayerID ] == true )
			{
				// se o jogador estiver na prisão ele sai sem multa, anda e passa a vez
				getOutOfJail( currentPlayerID , false );
				
				moveCurrentPlayer( nextPosition , roundTrip );
				
				checkLandingPosition();
				
				nextPlayer();
			}
			else
			{
				// se estiver fora da prisão aumenta sua contagem de duplas
				doubleCount[currentPlayerID]++;
				
				if( doubleCount[currentPlayerID] > 2 )
				{
					// se chegar em 3 duplas vai para a prisão e passa a vez
					goToJail( currentPlayerID );
					
					nextPlayer();
				}
				else
				{
					// se o jogador estiver com menos de 3 duplas, anda e joga novamente
					moveCurrentPlayer( nextPosition , roundTrip );
					
					checkLandingPosition();
				}	
			}			
		}		
	}
	
	/**
	 * Update the position of the current player. If the player has moved past
	 *   the start, pay him/her 200 reais.
	 * 
	 * @param position
	 * @param roundTrip
	 */
	private void moveCurrentPlayer( Board.BoardSpaces position , boolean roundTrip )
	{
		players[currentPlayerID].setPosition( position );
		
		if(roundTrip == true)
		{
			bank.payFees( players[currentPlayerID] );
		}
	}

	/**
	 * 
	 *  Apply possible modifications and side effects to the current player,
	 *    based on those defined for the card associated with the position
	 *    on which the player has landed.
	 *  If it's a change card related slot, a random one will be drawn from the
	 *    deck and the player will be updated according to the rules of the 
	 *    chosen card.
	 *  If it's a company or terrain card not yet owned by any other player, 
	 *    an option should be offer to buy the card should be made to the 
	 *    current player.
	 * 
	 */
	public void checkLandingPosition( ) {
		Player currentPlayer = this.players[ this.currentPlayerID ];
		Board.CardInfo card = board.getCardAtPosition( currentPlayer.getPosition() );
		int diceSum = dice.getSum();
		
		System.out.println("Current card: " + card.getType() );
		/**
		 * Choose a random card and inform the player about what has happened.
		 */
		if( card.getType().equals("chance"))
		{
			int id = cardPicker.nextInt(this.chanceCardsDeck.size());
			ChanceCard chanceCard = this.chanceCardsDeck.get(id);
			System.out.println( chanceCard );
			
			String output = 
				"O jogador atual obteve:\n" + 
				chanceCard.getTitle() + "\n" +
				chanceCard.getDescription() + "\n";
			
			
			if( chanceCard.getAmount() != 0 )
				output += "Valor: " + Math.abs( chanceCard.getAmount()) + "\n"; 
			
			
			JOptionPane.showMessageDialog(null, output);		
			
			ChanceCard.SideEffect sideEffect = chanceCard.getSideEffect();
			
			switch( sideEffect )
			{
			case CHARGE_COMPETITORS:
				for(Player p: this.players)
				{
					if( p != this.players[ this.currentPlayerID ] )
					{
						p.updateMoney( -chanceCard.getAmount() );
					}
				}
				this.players[ this.currentPlayerID ].updateMoney(
					((this.players.length - 1) * chanceCard.getAmount())
				);
				
				break;
			case GET_OUT_OF_JAIL:
				this.players[ this.currentPlayerID ].setJailPass( true );
				break;
			case GO_TO_JAIL:
				this.goToJail( this.currentPlayerID );
				break;
			case GO_TO_START:
				this.players[ this.currentPlayerID ].setPosition( Board.BoardSpaces.INICIO );
				break;
			case NONE:
			default:
				this.players[ this.currentPlayerID ].updateMoney( chanceCard.getAmount() );
				break;			
			}		
		}
		else if( card.getType().equals("company"))
		{
			System.out.println( this.companyCardsDeck.get( card.getID() ));
			CompanyCard companyCard = this.companyCardsDeck.get( card.getID() );
			int money = currentPlayer.getMoney(); 
			
			
			if( companyCard.getHasOwner() == false &&
				money > companyCard.getPrice()
			)
			{
				int ans = JOptionPane.showConfirmDialog(null,
						"Você quer comprar a companhia " + 
						"\"" + companyCard.getTitle() + "\"?\n" +
						"Seu saldo: " + money + "\n" +
						"Preço: " + companyCard.getPrice(), 
						"Oferta de Empresa",
						JOptionPane.YES_NO_OPTION);
				
				if(ans == JOptionPane.YES_OPTION)
				{
					currentPlayer.updateMoney( -companyCard.getPrice() );	
					currentPlayer.addCard( (Card) companyCard );
					
					companyCard.setHasOwner(true);
					companyCard.setOwner( players[currentPlayerID] );
					
					
					
					JOptionPane.showMessageDialog(null,
						"Você acaba de adquirir: \"" +
						companyCard.getTitle() + "\"!\n"
					);
				}
			}
			/**
			 * Pay what's due to the owner.
			 */
			else
			{
				Player owner = companyCard.getOwner();
			
				owner.updateMoney( companyCard.getDue( diceSum ));
				currentPlayer.updateMoney( -companyCard.getDue( diceSum ));
				
				JOptionPane.showMessageDialog(null, "Jogador " + currentPlayer.getPinColor().toString() + 
						" pagou R$" + companyCard.getDue(diceSum) + " ao jogador " + owner.getPinColor().toString());
			}
		}
		else if( card.getType().equals("terrain"))
		{
			System.out.println( this.terrainCardsDeck.get( card.getID() ));
			TerrainCard terrainCard = this.terrainCardsDeck.get( card.getID() );
			int money = currentPlayer.getMoney(); 
			
			
			if( terrainCard.getHasOwner() == false &&
				money > terrainCard.getPrice()
			)
			{
				int ans = JOptionPane.showConfirmDialog(null,
						"Você quer comprar o terreno " + 
						"\"" + terrainCard.getTitle() + "\"?\n" +
						"Seu saldo: " + money + "\n" +
						"Preço do lote: " + terrainCard.getPrice(), 
						"Oferta de Terreno",
						JOptionPane.YES_NO_OPTION);
				
				if(ans == JOptionPane.YES_OPTION)
				{
					currentPlayer.updateMoney( -terrainCard.getPrice() );	
					currentPlayer.addCard( (Card) terrainCard );
					
					terrainCard.setHasOwner(true);
					terrainCard.setOwner( currentPlayer );
					
					JOptionPane.showMessageDialog(null,
						"Você acaba de adquirir: \"" +
						terrainCard.getTitle() + "\"!\n" +
						"Novo saldo: " + currentPlayer.getMoney()
					);
				}
			}
			else
			{
				Player owner = terrainCard.getOwner();
				
				owner.updateMoney( terrainCard.getRent( ));
				currentPlayer.updateMoney( -terrainCard.getRent( ));
				
				JOptionPane.showMessageDialog(null, "Jogador " + currentPlayer.getPinColor().toString() + 
						" pagou R$" + terrainCard.getRent() + " ao jogador " + owner.getPinColor().toString());
			}
		}
		/**
		 * The player has landed on a non-active card, so check for special positions.
		 * OBS: The go to jail effect has already been applied by this point.
		 */
		else 
		{
			switch(  currentPlayer.getPosition() )
			{
			case IMPOSTO:
				currentPlayer.updateMoney(-200);
				JOptionPane.showMessageDialog(null, "A Receita te tomou R$200 em impostos...");
				break;
			case LUCROS:
				Bank.getBank().payFees(currentPlayer);
				JOptionPane.showMessageDialog(null, "Você acaba de ganhaer R$200 de lucro!");
				break;
			case PASSE_LIVRE:
				this.players[ this.currentPlayerID ].setJailPass( true );
				JOptionPane.showMessageDialog(null, "Você ganhou um passe livre!");
				break;
			default:
				break;			
			}
		}

	}

	/**
	* Evaluates the Jail Status of the previous player, moving him to jail if necessary
	*/
	private void previousPlayerJailStatus( ) {
		int playerID = ( ( currentPlayerID == 0 ) ? 
			players.length - 1 : currentPlayerID - 1 
		);
		Player player = players[ playerID ];
		
		if( player.getPosition() == Board.BoardSpaces.VA_PARA_A_PRISAO )
		{
			goToJail( playerID );
		}		
	}

	/**
	* Creates a certain number of players
	*
	*@param numberOfPlayers 		The number of players to be created
	*/
	private void createPlayers(int numberOfPlayers) {
		Player.PlayerColor color = Player.PlayerColor.BLACK;

		for (int i = 0; i < numberOfPlayers; ++i) {
			players[i] = new Player(Board.BoardSpaces.INICIO, 2458 , color);
			
			color = color.getNext();
		}
	}

	/**
	* Ends the current player's turn, setting a new current player
	*/
	private void nextPlayer() {
		int i = (( currentPlayerID + 1 ) % players.length), 
			original = currentPlayerID;
		
		while( i != original )
		{
			if( players[i].isAlive() == true )
			{
				break;
			}
			
			i = ((i + 1) % players.length);
		} 
		
		currentPlayerID = i;
		
		this.setChanged();
		this.notifyObservers( GAME_NEW_PLAYER );		
	}

	/**
	* Returns the players of the current game
	* 
	*@return 			The set of players
	*/
	public Player[] getPlayers() {
		return this.players;
	}

	/**
	* Returns the dice
	* 
	*@return 			The dice
	*/

	public Dice getDice() {
		return this.dice;
	}

	/**
	* Returns the current player
	* 
	*@return The current player
	*/	
	public Player getCurrentPlayer()
	{
		return this.players[ this.currentPlayerID ];
	}
	
	/**
	* Returns the current player
	* 
	*@return The current player
	*/	
	public int getCurrentPlayerID()
	{
		return this.currentPlayerID;
	}

	/**
	* Sets the player free. If needed, makes the player pay a fine before leaving jail.
	* 
	*@param playerID 	The id of the player to be released
	*@param fine 		Boolean indicating if the player needs to pay a fine in order to leave jail
	*/

	public void getOutOfJail( int playerID , boolean fine )
	{
		if(fine == true)
		{
			players[ playerID ].updateMoney(-50);
		}
		
		roundsInPrison[ playerID ] = 0;
		isInPrison[ playerID ] = false;
		players[ playerID ].setInJail( false );
	}

	/**
	* Moves player to jail.
	*
	*@param playerID 	The id of the player to put in jail
	*/

	private void goToJail( int playerID )
	{
		doubleCount[ playerID ] = 0;
		isInPrison[ playerID ] = true ;
		
		players[ playerID ].setPosition( Board.BoardSpaces.PRISAO );
		players[ playerID ].setInJail( true );
	}

	public boolean anyoneAlive(Player[] players){
		for(Player p : players){
			if(p.isAlive()==true)
				return true;
		}
		return false;
	}
	
	/**
	 * Check if the player of the previous round is alive.
	 * 
	 * @return
	 */
	public void checkPreviousAlive()
	{
		int playerID = ((currentPlayerID - 1) == -1 ? players.length - 1 : (currentPlayerID - 1));
		while( playerID != currentPlayerID )
		{
			if( players[ playerID ].isAlive() == true )
			{
				break;
			}
			
			playerID = ((currentPlayerID - 1) == -1 ? players.length - 1 : (currentPlayerID - 1));
		}
		
		Player player = players[ playerID ];
		ArrayList<Card> cards = player.getOwnedCards();
		boolean alive = true;
		
		if( player.getMoney() < 0 )
		{	
			alive = false;
			for(Card c: cards)
			{
				if( ((PropertyCard)(c)).isMortgaged() == false )
				{
					alive = true;
					break;
				}
			}
		}

		if( alive == false )
		{
			player.kill();
		}
	}
	
	/**
	 * Check if a given property type can be built on the terrain card c
	 * 
	 * @param owner Owner of the card
	 * @param c Terrain card
	 * @param type Type of property to be built
	 * @return Whether it's possible to build the property
	 */
	public boolean canBuildOnTerrain(Player owner, TerrainCard c, PropertyType type) {
		
		if( c.getPropertyQuantity(type) == c.getPropertyCapacity(type) ||
			type == TerrainCard.PropertyType.HOTEL &&
			c.getPropertyQuantity(TerrainCard.PropertyType.HOUSE) != c.getPropertyCapacity(TerrainCard.PropertyType.HOUSE)			 
		) {
			return false;
		}		
		
		for(int i = 1; i <= terrainCardsDeck.size(); i++)
		{
			if( terrainCardsDeck.get(i).getColor() == c.getColor() &&
				terrainCardsDeck.get(i).getOwner() != owner)
			{
				return false;
			}
		}
		
		return true;
	}

	
	/**
	 * Return the last man standing.
	 * 
	 * @return
	 */
	public Player getWinner() {
		Player winner = null;
		
		for(Player p : players){
			if( p.isAlive() == true && winner == null)
				winner = p;
			else if( p.isAlive() == true && winner != null )
				return null;
		}		
		
		return winner;
	}
}
