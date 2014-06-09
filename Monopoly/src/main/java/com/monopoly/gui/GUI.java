/**
 * 
 */
package com.monopoly.gui;

import com.monopoly.game.Card;
import com.monopoly.game.CompanyCard;
import com.monopoly.game.Dice;
import com.monopoly.game.Game;
import com.monopoly.game.GameController;
import com.monopoly.game.Player;
import com.monopoly.game.TerrainCard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class GUI 
	implements GUIEvents
{	
	// Propriedades da janela
	private JFrame viewPort;
	private JLayeredPane layers;
	private ImagePanel background;
	private String title;	
	private int width, height;
	
	// Views das entidades do jogo
	private final BoardView board;
	private PlayerPinView[] playerPins;
	private DiceView diceView;
	private CurrentPlayerView currentPlayerView;
	private PlayersBalanceView playersBalanceView;
	
	// Botão para lançar os dados
	private JButton rollDice;
	private JButton useJailPass;

	// Jail pass button controller, checks all rules, et al.
	private JailPassController useJailPassController;
	
	
	public GUI( String title , Dimension d , int numberOfPlayers , ActionListener actionListener )
	{
		// Propriedades da janela
		this.title = title;		
		this.width  = (int) d.getWidth();
		this.height = (int) d.getHeight();
		
		ImageIcon backgroundImage = new ImageIcon("src/main/resources/background.png");
		this.background = new ImagePanel( backgroundImage );
				
		viewPort = new JFrame();		
		layers = new JLayeredPane();
		
		// Criação das entidades do jogo
		board = new BoardView( 490 , 80 );
		playerPins = new PlayerPinView[ numberOfPlayers ];	
		
		diceView = new DiceView( this.width/2 - 40 , 45 );
		
		currentPlayerView = new CurrentPlayerView( 10 , 5 , 475, this.height - 215);
		playersBalanceView = new PlayersBalanceView( this.width/2 + 470 , 10, 220, this.height/2 - 45 , numberOfPlayers );
		
	}
	
	public void showCardManager(Card c, GameController gameController)
	{
		if(c instanceof CompanyCard)
		{
			/**
			 * Open the management windows for company cards
			 */
			JFrame companyCardManager = new CompanyCardManager( (CompanyCard)(c) , height/6, width/6 );
			companyCardManager.setVisible(true);
		}
		else if(c instanceof TerrainCard)
		{
			/**
			 * Open the management windows for company cards
			 */
			JFrame terrainCardManager = new TerrainCardManager( (TerrainCard)(c) , height/6, width/6 , gameController );
			terrainCardManager.setVisible(true);
		}
	}
	
	/**
	 * Configures the UI buttons.
	 * 
	 * @param actionListener Controller responsible for handling button clicks
	 * @param game The game model
	 */
	public void setUpControls( ActionListener actionListener , Game game )
	{
		/**
		 * Set up Roll dice controls
		 */
		ImageIcon rollDiceIcon = new ImageIcon("src/main/resources/dice.png");
		rollDice = new CustomButton( "Roll Dice", rollDiceIcon , this.width/2 - 155 , 25 );		
		rollDice.setActionCommand( GUI_BTN_DICE_ROLLED );
		rollDice.addActionListener( actionListener );
		
		/**
		 * Set up Jail pass controls
		 */
		ImageIcon jailPassIcon = new ImageIcon("src/main/resources/unlock.png");
		useJailPass = new CustomButton( "Use Jail Pass", jailPassIcon);		
		useJailPass.setActionCommand( GUI_BTN_JAIL_PASS );
		
		this.useJailPassController = new JailPassController( useJailPass );
		useJailPass.addActionListener( useJailPassController );
		useJailPass.addActionListener( actionListener );
		
		useJailPass.setBounds( this.width/2 + 250 , 25 , 130 , 50 );		
		
		/**
		 * The jail pass controller observers the game to enable the button
		 *   when necessary.
		 */
		game.addObserver( this.useJailPassController );
		
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
			playerPins[ i ] = new PlayerPinView( players[ i ] , new Point( 480 , 45 ) );
			
			players[ i ].addObserver( playerPins[ i ] );
		}
	}
	
	/**
	 * Faz a conexão entre a view que mostra o resultado dos dados e os dados em si.
	 * 
	 * @param players	array of Player models
	 */
	public void setUpDiceView( Dice d )
	{
		d.addObserver( diceView );
	}

	/**
	 * Set's up the view that shows all relevante information about the current player
	 * In order to do that, it observes the Game model.
	 * 
	 * @param game Game model.
	 */
	public void setUpCurrentPlayerView( Game game , ActionListener controller ) {
		currentPlayerView.setCurrentPlayer( game.getCurrentPlayer() );
		currentPlayerView.setupCardsGrids( controller );
		game.addObserver( currentPlayerView );			
	}
	
	/**
	 * Set's up the view that shows for each player its bank balance. 
	 * It also clearly indicates when a player has gone bankrupt.
	 * In order to do that, it observes the Game model.
	 * 
	 * @param game Game model.
	 */
	public void setUpPlayersBalanceView( Player[] players ) {
		for( Player p: players )
		{
			p.addObserver( this.playersBalanceView );
			
			this.playersBalanceView.setPlayerBalance( p );
		}
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
		
		viewPort.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/icon.png"));
		
		viewPort.getContentPane().add( layers , BorderLayout.CENTER );
		
		viewPort.setVisible( false );		 
  
		viewPort.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	     
		viewPort.setSize( this.width , this.height );
	     
		viewPort.setLocationRelativeTo( null );
	     
		viewPort.setResizable( false );	  
	}


	/**
	 * Adiciona todas as entidades gráficas na tela, antes desta ser 
	 * 	propriamente configurada.
	 * 
	 */
	private void setUpGraphicalComponents() {
		this.addComponent(background, 0, 0);
		
		// Adiciona a board
		this.addComponent(board, 1, 0);
		
		// Adiciona os jogadores
		for( int i = 0 ; i < this.playerPins.length ; ++i )
		{
			this.addComponent( playerPins[i] , 2 , i+1 );
		}					
		
		// Adiciona os botões
		this.addComponent( rollDice    , 2 , 1 );
		this.addComponent( useJailPass , 2 , 2 );
		
		// Adiciona a view dos dados
		this.addComponent( diceView , 3 , 1 );
		
		// Adiciona a view do jogador atual
		this.addComponent( currentPlayerView , 4 , 1 );
		
		this.addComponent( playersBalanceView, 5, 1);
	}
	
}
