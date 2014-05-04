/**
 * 
 */
package com.monopoly.gui;

import com.monopoly.game.Dice;
import com.monopoly.game.GameEvents;
import com.monopoly.game.Player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class GUI 
	extends    Observable 
	implements ActionListener, GameEvents, GUIEvents
{
	// Propriedades da janela
	private JFrame viewPort;
	private JLayeredPane layers;
	private String title;	
	private int width, height;
	
	// Views das entidades do jogo
	private final Board board;
	private PlayerPinView[] playerPins;
	private DiceView diceView;
	
	// Botão para lançar os dados
	private JButton rollDice;
	
	public GUI( String title , Dimension d , int numberOfPlayers )
	{
		// Propriedades da janela
		this.title = title;		
		this.width  = (int) d.getWidth();
		this.height = (int) d.getHeight();
		
		viewPort = new JFrame();		
		layers = new JLayeredPane();
		
		// Criação das entidades do jogo
		board = new Board( 50 , 50 );
		playerPins = new PlayerPinView[ numberOfPlayers ];	
		diceView = new DiceView( this.width/2 - 80 , 30 );
		
		// Criação do menu de controle do jogo
		rollDice = new JButton("Roll Dice");
		rollDice.setBounds(5, 5, 110, 30);
		rollDice.setActionCommand( BTN_DICE_ROLLED );
		rollDice.addActionListener( this );
	}
	
	/**
	 * Faz a conexão entre as views de Players e seus respectivos modelos. 
	 * 
	 * @param players	array of Player models
	 */
	public void setUpPlayerPinViews( Player[] players )
	{
		for( int i = 0 ; i < players.length ; ++i )
		{
			playerPins[ i ] = new PlayerPinView( players[ i ] , new Point( 25 , 25 ) );
			
			players[ i ].addObserver( playerPins[ i ] );
		}
	}
	
	public void setUpDiceView( Dice d )
	{
		d.addObserver( diceView );
	}

	/**
	 * Encapsula adição de novas entidade gráficas ao JLayeredPane usado para controle
	 * 	de camadas.
	 * 	
	 * @param component  Entidade gráfica a ser exibida na tela
	 * @param zIndex	 Plano em que se encontra tal entidade
	 * @param innerIndex Altura da entidade em seu respectivo plano
	 */
	public void addComponent( Component component , int zIndex , int innerIndex )
	{
		layers.add( component , zIndex , innerIndex );
	}
	
	/**
	 * Define se a janela deve ou não ser mostrada.
	 * 
	 * @param b Verdadeiro para exibir, falso para esconder.
	 */
	public void displayWindow( boolean b )
	{
		viewPort.setVisible( b );
	}	

	/**
	 * Executa uma série de tarefas para configurar a janela, como por exemplo
	 * 	definção de seu título e adição do controlador de camadas
	 */
	public void setupWindow()
	{
		this.setUpGraphicalComponents();
		
		viewPort.setTitle( this.title );
		
		viewPort.getContentPane().add( layers , BorderLayout.CENTER );
		
		viewPort.setVisible( false );		 
  
		viewPort.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	     
		viewPort.setSize( this.width , this.height );
	     
		viewPort.setLocationRelativeTo( null );
	     
		viewPort.setResizable( false );	  
	}


	/**
	 * Lida com algum evento gerado pelo usuário ao interagir com a GUI.
	 * 	Em geral, cliques em botões [TODO: ou valores em caixa de texto]
	 * 
	 * @param event
	 */
	public void actionPerformed( ActionEvent event ) {
		String message = event.getActionCommand();
		
		if( message == BTN_DICE_ROLLED )
		{	
			System.out.println("Dice rolled");
			this.setChanged();
			this.notifyObservers( GAME_DICE_ROLLED );
		}
	}
	
	
	/**
	 * Adiciona todas as entidades gráficas na tela, antes desta ser 
	 * 	propriamente configurada.
	 * 
	 */
	private void setUpGraphicalComponents() {
		// Adiciona a board
		this.addComponent(board, 0, 1);
		
		// Adiciona os jogadores
		for( int i = 0 ; i < this.playerPins.length ; ++i )
		{
			this.addComponent( playerPins[i] , 1 , i+1 );
		}					
		
		// Adiciona o botão de jogar dados
		this.addComponent( rollDice , 2 , 1 );
		
		// Adiciona a view dos dados
		this.addComponent( diceView , 3 , 1 );
	}
}
