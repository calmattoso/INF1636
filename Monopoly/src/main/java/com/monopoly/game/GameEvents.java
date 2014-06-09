package com.monopoly.game;

/**
* This interface specifies the possible game events
*/

public interface GameEvents {
	public final String GAME_DICE_ROLLED            = "GAME_diceRolled";
	public final String GAME_NEW_PLAYER             = "GAME_newPlayer";
	public final String GAME_PLAYER_BALANCE_UPDATED = "GAME_playerBalanceUpdated";
	public final String GAME_PLAYER_CARDS_UPDATED   = "GAME_PLAYER_CARDS_UPDATED";
	public final String GAME_PLAYER_DIED            = "GAME_PLAYER_DIED";
}
