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

	public Game(int numberOfPlayers) {
		players = new Player[numberOfPlayers];
		createPlayers(numberOfPlayers);
		currentPlayerID = 0;

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
		nextPosition = position.getNext( dice.getSum() );
		currentPlayer.setPosition( nextPosition );

		nextPlayer();
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

}
