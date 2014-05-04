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
	private CurrentPlayer currentPlayer; // Torna possível observar quem é o atual jogador
	private int[] doubleCount;
	private int[] roundsInPrison;
	private boolean[] isInPrison;

	public Game(int numberOfPlayers) {
		players = new Player[numberOfPlayers];
		createPlayers(numberOfPlayers);
		doubleCount = new int[numberOfPlayers];
		roundsInPrison = new int[numberOfPlayers];
		isInPrison = new boolean[numberOfPlayers];

		currentPlayerID = 0;
		currentPlayer = new CurrentPlayer( players[ currentPlayerID ] );

		dice = new Dice(6);
	}

	public Game() {
		this(2);
	}

	public void evaluateMovement() {
		Player currentPlayer = players[currentPlayerID];
		Board.BoardSpaces position = currentPlayer.getPosition(), nextPosition = Board.BoardSpaces.INICIO;

		// boolean haltTurn = false;

		dice.roll();

		// Calcula imediatamente a potencial próxima posição

		
		//se não for dupla
		if((dice.getDie1() != dice.getDie2()))
		{

			if(isInPrison[currentPlayerID] == true)
			{
				//se o jogador estiver na prisão, aumenta o tempo de rodadas dele
				roundsInPrison[currentPlayerID]++;
				if(roundsInPrison[currentPlayerID] >3)
					// se o jogador tiver 4 dias na prisão, sai pagando multa
					getOutOfJail(currentPlayer,currentPlayerID,true);
			}
			else
			{
				//reseta o contador de duplas, caso ele não esteja na prisão
				doubleCount[currentPlayerID]=0;
			}

			// em todos os casos, o jogador anda a quantidade dos dados e passa a vez
			nextPosition = position.getNext( dice.getSum() );
			currentPlayer.setPosition( nextPosition );

			nextPlayer();
		}

		else
		{
			//se for dupla

			if(isInPrison[currentPlayerID] == true)
			{
				// se o jogador estiver na prisão ele sai sem multa, anda e passa a vez
				getOutOfJail(currentPlayer,currentPlayerID,false);
				nextPosition = position.getNext( dice.getSum() );
				currentPlayer.setPosition( nextPosition );
				nextPlayer();
			}
			else
			{
				// se estiver fora da prisão aumenta sua contagem de duplas
				doubleCount[currentPlayerID]++;
				if(doubleCount[currentPlayerID]>2)
				{
					// se chegar em 3 duplas vai para a prisão e passa a vez
					goToJail(currentPlayerID);
					nextPlayer();
				}
				else{
					// se o jogador estiver com menos de 3 duplas, anda e joga novamente
				nextPosition = position.getNext( dice.getSum() );
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

	private void getOutOfJail(Player player,int playerID, boolean fine)
	{
		if(fine == true)
		{
			player.withdrawMoney(50);
		}
		roundsInPrison[playerID]=0;
		isInPrison[playerID] = false;
	}

	private void goToJail(int playerID)
	{
		isInPrison[playerID] = true;
	}

	
}
