package com.monopoly.game;

import java.util.Observable;
import java.util.Observer;

import com.monopoly.game.Player;

public class Game 
	implements Observer, GameEvents 
{
	private Player[] players;
	private Board board;
	private int currentPlayerID;
	private CurrentPlayer currentPlayer; // Torna possível observar quem é o atual jogador
	
	private Dice dice;	
	
	private int[] doubleCount;
	private int[] roundsInPrison;
	private boolean[] isInPrison;
	

	public Game(int numberOfPlayers) {
		players = new Player[numberOfPlayers];
		createPlayers(numberOfPlayers);
		
		this.board = new Board();
		
		doubleCount    = new int[numberOfPlayers];
		roundsInPrison = new int[numberOfPlayers];
		isInPrison 	   = new boolean[numberOfPlayers];

		currentPlayerID = 0;
		currentPlayer = new CurrentPlayer( players[ currentPlayerID ] );

		dice = new Dice(6);
	}

	public Game() {
		this(2);
	}

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
				
				currentPlayer.setPosition( nextPosition );
				affectCurrentPlayer();
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
				
				currentPlayer.setPosition( nextPosition );
				affectCurrentPlayer();
				
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
					currentPlayer.setPosition( nextPosition );
					
					affectCurrentPlayer();
				}	
			}
			
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
	private void affectCurrentPlayer() {
		
		
		return;		
	}

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

	private void createPlayers(int numberOfPlayers) {
		Player.PlayerColor color = Player.PlayerColor.BLACK;

		for (int i = 0; i < numberOfPlayers; ++i) {
			players[i] = new Player(Board.BoardSpaces.INICIO, 2458 , color);

			color = color.getNext();
		}
	}

	private void nextPlayer() {
		currentPlayerID = (currentPlayerID + 1) % players.length;
		currentPlayer.setPlayer( players[ currentPlayerID ] );
	}

	public void update(Observable gui, Object event) {
		if (event instanceof String) {
			String message = (String) event;

			if ( message.equals( GAME_DICE_ROLLED ) ) {
				evaluateMovement();
			}
		}

	}

	public Player[] getPlayers() {
		return this.players;
	}

	public Dice getDice() {
		return this.dice;
	}
	
	public CurrentPlayer getCurrentPlayer()
	{
		return this.currentPlayer;
	}

	private void getOutOfJail( int playerID , boolean fine )
	{
		if(fine == true)
		{
			players[ playerID ].withdrawMoney(50);
		}
		
		roundsInPrison[ playerID ] = 0;
		isInPrison[ playerID ] = false;
	}

	private void goToJail( int playerID )
	{
		doubleCount[ playerID ] = 0;
		isInPrison[ playerID ] = true ;
		players[ playerID ].setPosition( Board.BoardSpaces.PRISAO );
	}

	
}
