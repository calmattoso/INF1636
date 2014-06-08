package com.monopoly.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.monopoly.game.Game;
import com.monopoly.game.GameEvents;
import com.monopoly.game.Player;

public class JailPassController 
	implements GameEvents, GUIEvents, Observer, ActionListener
{
	private JButton control;
	
	public JailPassController( JButton control ) {
		this.control = control;
		
		this.control.setEnabled( false );
		//this.control.setVisible( false );
	}

	/**
	 * When a round is over, the new current player may have the option of
	 *   using a jail pass. If that's the case, enable the button. 
	 * 
	 */
	public void update(Observable game, Object message) {
		if( game instanceof Game &&
			message.equals( GAME_NEW_PLAYER ))
		{
			Player player = ((Game) game).getCurrentPlayer();	
						
			if( player.isInJail() == true && player.hasJailPass() == true )
			{
				this.control.setEnabled( true );
			}
		}
	}

	/**
	 * When the Jail pass button is clicked, make sure to disable it.
	 */
	public void actionPerformed(ActionEvent event) {
		String eventCode = 	event.getActionCommand();
		
		if( eventCode.equals( GUI_BTN_JAIL_PASS ))
		{
			this.control.setEnabled( false );
			
			JOptionPane.showMessageDialog(null, "Você está livre!");
		}
	}
}
