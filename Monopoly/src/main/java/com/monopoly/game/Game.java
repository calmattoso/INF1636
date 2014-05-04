package com.monopoly.game;

import java.util.Observable;
import java.util.Observer;

import com.monopoly.game.Player;
import com.monopoly.gui.Board;

public class Game 
	implements Observer, GameEvents 
{
	private Player[] players;
	private Dice dice;
	private int currentPlayerID;
	private CurrentPlayer currentPlayer; // Torna poss�vel observar quem � o atual jogador
	private int[] doubleCount;
	private int[] roundsInPrison;
	private boolean[] isInPrison;

	public Game(int numberOfPlayers) {
		players = new Player[numberOfPlayers];
		createPlayers(numberOfPlayers);
		
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
		
		// Se em um movimento anterior o jogador acabou caindo na posi��o, v�
		//	para pris�o, ele � primeiramente colocado l�
		if( position == Board.BoardSpaces.VA_PARA_A_PRISAO )
		{
			currentPlayer.setPosition( Board.BoardSpaces.PRISAO );
			position = Board.BoardSpaces.PRISAO;
			
			isInPrison[ currentPlayerID ]  = true;
			doubleCount[ currentPlayerID ] = 0;
		}
		
		// Calcula imediatamente a potencial pr�xima posi��o
		nextPosition = position.getNext( dice.getSum() );
		
		//se n�o for dupla
		if( dice.getDie1() != dice.getDie2() )
		{
			if(isInPrison[ currentPlayerID ] == true)
			{
				//se o jogador estiver na pris�o, aumenta o tempo de rodadas dele
				roundsInPrison[currentPlayerID]++;
				
				if( roundsInPrison[currentPlayerID] > 3 )
				{
					// se o jogador tiver passado 4 rodadas na pris�o, sai pagando multa
					getOutOfJail( currentPlayerID , true );
				}
			}
			else
			{
				//reseta o contador de duplas, caso ele n�o esteja na pris�o
				doubleCount[currentPlayerID] = 0;
				
				currentPlayer.setPosition( nextPosition );
			}			

			nextPlayer();
		}

		else
		{
			//se for dupla			
			if( isInPrison[ currentPlayerID ] == true )
			{
				// se o jogador estiver na pris�o ele sai sem multa, anda e passa a vez
				getOutOfJail( currentPlayerID , false );
				
				currentPlayer.setPosition( nextPosition );
				
				nextPlayer();
			}
			else
			{
				// se estiver fora da pris�o aumenta sua contagem de duplas
				doubleCount[currentPlayerID]++;
				
				if( doubleCount[currentPlayerID] > 2 )
				{
					// se chegar em 3 duplas vai para a pris�o e passa a vez
					goToJail( currentPlayerID );
					
					nextPlayer();
				}
				else
				{
					// se o jogador estiver com menos de 3 duplas, anda e joga novamente
					currentPlayer.setPosition( nextPosition );
				}	
			}
			
		}
	}

	private void createPlayers(int numberOfPlayers) {
		Player.PlayerColor color = Player.PlayerColor.BLACK;

		for (int i = 0; i < numberOfPlayers; ++i) {
			players[i] = new Player(Board.BoardSpaces.INICIO, 0.0, color);

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

	private void goToJail(int playerID)
	{
		isInPrison[ playerID ] = true ;
	}

	
}
