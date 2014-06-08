package com.monopoly.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;















import com.monopoly.game.Card;
import com.monopoly.game.CompanyCard;
import com.monopoly.game.Game;
import com.monopoly.game.GameEvents;
import com.monopoly.game.Player;
import com.monopoly.game.TerrainCard;


public class CurrentPlayerView 
	extends JPanel
	implements Observer, GameEvents, GUIEvents 
{
	private static final long serialVersionUID = 7970826428341424057L;
	private static final short COMPANY_CARDS_GRID_WIDTH   = 6;
	private static final short COMPANY_CARDS_GRID_HEIGHT  = 1;
	private static final short TERRAIN_CARDS_GRID_WIDTH   = 6;
	private static final short TERRAIN_CARDS_GRID_HEIGHT  = 4;
	
	private Player player;
	
	private HashMap< Integer, ImageIcon > terrainCards;
	private HashMap< Integer, ImageIcon > companyCards;
	private HashMap< Player.PlayerColor, ImageIcon > pinsIcons;
	
	private JLabel currentPlayer;
	private JPanel companyCardsPanel;
	private JPanel terrainCardsPanel;
	
	private JButton[][] companyCardsGrid;
	private JButton[][] terrainCardsGrid;

	public  CurrentPlayerView( int x , int y , int width, int height )
	{
		player = null ;		
		this.setBounds( x , y , width , height );
		this.setLayout(new BorderLayout());
		
		/**
		 * These hashmaps have the cards and pins images preloaded into them.
		 */
		terrainCards = new HashMap<Integer,ImageIcon>();
		companyCards = new HashMap<Integer,ImageIcon>();
		pinsIcons    = new HashMap< Player.PlayerColor, ImageIcon >();
		
		loadCardsImages("company", 6);
		loadCardsImages("terrain", 22);		
		loadPinImages();
		
		ImageIcon basePin = pinsIcons.get(Player.PlayerColor.BLACK );
		currentPlayer = new JLabel( basePin );
		
		/**
		 * Now setup the view for cards grids.
		 */
		companyCardsPanel = new JPanel();		
		terrainCardsPanel = new JPanel();
		terrainCardsPanel.setPreferredSize(new Dimension(width, height/2 + 120));		

		this.add(currentPlayer, BorderLayout.NORTH );
		this.add(companyCardsPanel, BorderLayout.CENTER);
		this.add(terrainCardsPanel, BorderLayout.SOUTH);
		
		prepareCardsPanel(this.companyCardsPanel, "Cartas de Empresa");
		prepareCardsPanel(this.terrainCardsPanel, "Cartas de Terreno");
		
		/**
		 * Use a grid layout to facilitate display of cards.
		 */
		terrainCardsPanel.setLayout( new GridLayout(TERRAIN_CARDS_GRID_HEIGHT,TERRAIN_CARDS_GRID_WIDTH));
		companyCardsPanel.setLayout( new GridLayout(COMPANY_CARDS_GRID_HEIGHT,COMPANY_CARDS_GRID_WIDTH));		
		
		/**
		 * Instantialize the grids.
		 */
		terrainCardsGrid = new JButton[TERRAIN_CARDS_GRID_HEIGHT][TERRAIN_CARDS_GRID_WIDTH];
		companyCardsGrid = new JButton[COMPANY_CARDS_GRID_HEIGHT][COMPANY_CARDS_GRID_WIDTH];
		
		/**
		 * Finally, style the current player's panel border to be lowered etched.
		 */
		TitledBorder title;
		title = BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ),	
			"Jogador Atual"
		);		
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitlePosition( TitledBorder.CENTER );
		title.setTitleFont( new Font("Comic Sans MS", Font.PLAIN , 20) );
		
		this.setBorder( title );
	}
	
	/**
	 * Set up the layout for the company and terrain cards panels.
	 * 
	 * @param cardsPanel A cards list view.
	 * @param title The title for the panel.
	 */
	private void prepareCardsPanel(JPanel cardsPanel, String title) {
		TitledBorder title1;
		title1 = BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ),	
			title
		);		
		title1.setTitleJustification(TitledBorder.CENTER);
		title1.setTitlePosition( TitledBorder.CENTER );
		title1.setTitleFont( new Font("Comic Sans MS", Font.BOLD , 14 ) );
		
		cardsPanel.setBorder( title1 );			
	}

	/**
	 *  Load the card images icons to display by the side of its corresponding balance.
	 * 
	 * @param folder The base folder for the images (within project path)
	 * @param numberOfImages The total number of image files to be loaded
	 */
	private void loadCardsImages( String folder, int numberOfImages )
	{
		for( int i = 0; i < numberOfImages; i++ )
		{
			String path = "src/main/resources/cards/" + folder + "/" + String.valueOf(i + 1) + ".png";
			ImageIcon cardImage = resizeImage( new ImageIcon( path ));
			
			if( folder.equals("company"))
				this.companyCards.put(i+1, cardImage);
			else
				this.terrainCards.put(i+1, cardImage);
		}
	}
	
	/**
	 * Load the pin icons to display by the side of its corresponding balance.
	 */
	private void loadPinImages( )
	{
		Player.PlayerColor color = Player.PlayerColor.BLACK;
		
		do 
		{
			String path = "src/main/resources/pins/" + color.toString().toLowerCase() + "_pin.png";
			ImageIcon pinIcon = new ImageIcon( path );
			
			this.pinsIcons.put(color, pinIcon );
			
			color = color.getNext();
		} while( color != Player.PlayerColor.BLACK);
	}	
	
	/**
	 * Resizes icon to half of its original size.
	 * 
	 * @param icon Image to be resized.
	 * @return New ImageIcon with half of the original size of icon.
	 */
	private ImageIcon resizeImage( ImageIcon icon)
	{
		int width = 73, height = 125;
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(icon.getImage(), 0, 0, width, height, null);
	    g2.dispose();
	    
	    return new ImageIcon(resizedImg);
	}
	
	/**
	 * Update the grids to show the images of the cards the current player owns.
	 */
	private void refreshCardsGrids()
	{
		ArrayList<Card> cards = player.getOwnedCards();
		
		cleanGrids();
		
		int cc_i = 0, cc_j = 0,
			tc_i = 0, tc_j = 0;
		
		for(Card c: cards)
		{
			int id = c.getID();
			if(c instanceof CompanyCard)
			{
				ImageIcon cardImage = this.companyCards.get(id);
				
				this.companyCardsGrid[cc_i][cc_j].setIcon( cardImage );
				this.companyCardsGrid[cc_i][cc_j].setEnabled( true );
				
				cc_j = (cc_j + 1) % COMPANY_CARDS_GRID_WIDTH;
				if(cc_j == 0)
					cc_i = (cc_i + 1) % COMPANY_CARDS_GRID_HEIGHT;
			}
			else if(c instanceof TerrainCard)
			{
				ImageIcon cardImage = this.terrainCards.get(id);
				
				this.terrainCardsGrid[tc_i][tc_j].setIcon( cardImage );
				this.terrainCardsGrid[tc_i][tc_j].setEnabled( true );
				
				tc_j = (tc_j + 1) % TERRAIN_CARDS_GRID_WIDTH;
				if(tc_j == 0)
					tc_i = (tc_i + 1) % TERRAIN_CARDS_GRID_HEIGHT;
			}
		}
		
		return;
	}
	
	/**
	 * Remove the current grid images.
	 */
	private void cleanGrids() {
		for(int i = 0; i < COMPANY_CARDS_GRID_HEIGHT; ++i)
			for(int j = 0; j < COMPANY_CARDS_GRID_WIDTH; ++j)
			{
				this.companyCardsGrid[i][j].setIcon(null);
				this.companyCardsGrid[i][j].setEnabled(false);
			}
		
		/**
		 * Set up terrain cards grid.
		 */
		for(int i = 0; i < TERRAIN_CARDS_GRID_HEIGHT; ++i)
			for(int j = 0; j < TERRAIN_CARDS_GRID_WIDTH; ++j)
			{
				this.terrainCardsGrid[i][j].setIcon(null);
				this.terrainCardsGrid[i][j].setEnabled(false);
			}
	}

	/**
	 * Create the buttons on which the cards will be displayed.
	 * @param controller
	 */
	public void setupCardsGrids(ActionListener controller) {
		/**
		 * Set up company cards grid.
		 */
		for(int i = 0; i < COMPANY_CARDS_GRID_HEIGHT; ++i)
		{
			for(int j = 0; j < COMPANY_CARDS_GRID_WIDTH; ++j)
			{
				JButton cardButton = new JButton();
						
				cardButton.setOpaque(false);
				cardButton.setContentAreaFilled(false);
				cardButton.setBorderPainted(false);
				cardButton.setEnabled(false);
				
				
				cardButton.setActionCommand( GUI_BTN_COMPANY_CARD );
				cardButton.addActionListener( controller );
				cardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
				this.companyCardsGrid[i][j] = cardButton;
				
				this.companyCardsPanel.add( cardButton );
			}
		}		
		
		/**
		 * Set up terrain cards grid.
		 */
		for(int i = 0; i < TERRAIN_CARDS_GRID_HEIGHT; ++i)
		{
			for(int j = 0; j < TERRAIN_CARDS_GRID_WIDTH; ++j)
			{
				JButton cardButton = new JButton();		
				
				cardButton.setOpaque(false);
				cardButton.setContentAreaFilled(false);
				cardButton.setBorderPainted(false);				
				cardButton.setEnabled(false);
				
				cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				cardButton.setActionCommand( GUI_BTN_TERRAIN_CARD );
				cardButton.addActionListener( controller );
				cardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
				this.terrainCardsGrid[i][j] = cardButton;
				
				this.terrainCardsPanel.add( cardButton );
			}
		}
	}
	
	/**
	 * Update current player information upon new player.
	 */
	public void update( Observable game, Object message ) {
		if( game instanceof Game &&
			message.equals( GAME_NEW_PLAYER ) )
		{
			this.player = ((Game) game).getCurrentPlayer();	
			
			/**
			 * Update the top level icon
			 */
			ImageIcon pin = this.pinsIcons.get( player.getPinColor() );
			this.currentPlayer.setIcon( pin );	
			
			/**
			 * Update both cards panel.
			 */
			refreshCardsGrids();
			
			this.repaint();
		}
	}

	public void setCurrentPlayer( Player p )
	{
		this.player = p;
		ImageIcon pin = this.pinsIcons.get( player.getPinColor() );
		this.currentPlayer.setIcon( pin );
	}

}
