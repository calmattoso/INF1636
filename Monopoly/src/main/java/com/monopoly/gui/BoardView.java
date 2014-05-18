/**
 * 
 */
package com.monopoly.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedHashMap;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import com.monopoly.game.Board;
import com.monopoly.game.Board.BoardSpaces;

public class BoardView 
	extends JPanel 
{
	private static final long serialVersionUID = 3149157930506422886L;
	private static LinkedHashMap<Board.BoardSpaces, Point> spacesToCoordinates;
	
	private ImageIcon board;
	private int x , y;	
	
	/**
	 * Mapping of Board positions to their center on screen relative to the top-left corner
	 *   of the viewport.
	 */
	static {
		spacesToCoordinates = new LinkedHashMap<Board.BoardSpaces, Point>();
	    
	    spacesToCoordinates.put( Board.BoardSpaces.INICIO, new Point( 860 , 72 ));
	    spacesToCoordinates.put( Board.BoardSpaces.LEBLON ,new Point( 860 , 168 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE ,new Point( 860 , 240 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PRESIDENTE_VARGAS ,new Point( 860 , 316 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_NOSSA_S_DE_COPACABANA ,new Point( 854 , 390 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_FERROVIARIA ,new Point( 854 , 462 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_BRIGADEIRO_FARIA_LIMA ,new Point( 852 , 536 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_VIACAO ,new Point( 852 , 610 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_REBOUCAS ,new Point( 850 , 684 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_9_DE_JULHO ,new Point( 846 , 754 ));
	    spacesToCoordinates.put( Board.BoardSpaces.PRISAO ,new Point( 840 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_EUROPA ,new Point( 746 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE2 ,new Point( 674 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.RUA_AUGUSTA ,new Point( 604 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PACAEMBU ,new Point( 530 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_TAXI ,new Point( 456 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE3 ,new Point( 386 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.INTERLAGOS ,new Point( 314 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.LUCROS ,new Point( 240 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.MORUMBI ,new Point( 168 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.LIVRE, new Point( 76 , 840 ));
	    spacesToCoordinates.put( Board.BoardSpaces.FLAMENGO, new Point( 74 , 752 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE4, new Point( 70 , 682 ));
	    spacesToCoordinates.put( Board.BoardSpaces.BOTAFOGO, new Point( 70 , 610 ));
	    spacesToCoordinates.put( Board.BoardSpaces.IMPOSTO, new Point( 66 , 354 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_NAVEGACAO, new Point( 66 , 462 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_BRASIL, new Point( 64, 388 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE5, new Point( 64 , 314 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PAULISTA, new Point( 68 , 240 ));
	    spacesToCoordinates.put( Board.BoardSpaces.JARDIM_EUROPA, new Point( 66 , 164 ));
	    spacesToCoordinates.put( Board.BoardSpaces.VA_PARA_A_PRISAO, new Point( 72 , 72 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COPACABANA, new Point( 164 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_AVIACAO, new Point( 236 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_VIEIRA_SOUTO, new Point( 312 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_ATLÂNTICA, new Point( 386 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_TAXI_AEREO, new Point( 462 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.IPANEMA, new Point( 536 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE6, new Point( 610 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.JARDIM_PAULISTA, new Point( 684 , 66 ));
	    spacesToCoordinates.put( Board.BoardSpaces.BROOKLYN, new Point( 760 , 72 ));
	}
	
	public BoardView( int x , int y )
	{
		board = new ImageIcon("src/main/resources/tabuleiro.png");
                 
        this.x = x;
        this.y = y;
        
        Dimension d = getDimension();
		
		this.setPreferredSize( d );
		this.setBounds(x, y, d.width , d.height);
	}
	
	public BoardView()
	{
		this( 0 , 0 );
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage( board.getImage() , 0 , 0 , null );
	}	
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Dimension getDimension()
	{
		return new Dimension( board.getIconWidth() , board.getIconHeight() );
	}

	
	/**
	 * 
	 * This function is used by other views to know where the center of a given position
	 *  is on screen relative to the top-left corner of the viewport.
	 * 
	 * @param position - Position of the board
	 * @return Coordinates on screen of the center of position
	 */
	public static Point spaceToPoint(BoardSpaces position) {
		return BoardView.spacesToCoordinates.get( position );
	}
}
