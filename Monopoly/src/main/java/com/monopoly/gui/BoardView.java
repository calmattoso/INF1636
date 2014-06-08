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
	    
	    spacesToCoordinates.put( Board.BoardSpaces.INICIO, new Point( 624 , 54 ));
	    spacesToCoordinates.put( Board.BoardSpaces.LEBLON ,new Point( 629 , 122 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE ,new Point( 634 , 179 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PRESIDENTE_VARGAS ,new Point( 633 , 233 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_NOSSA_S_DE_COPACABANA ,new Point( 633 , 284 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_FERROVIARIA ,new Point( 633 , 339 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_BRIGADEIRO_FARIA_LIMA ,new Point( 633 , 395 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_VIACAO ,new Point( 633 , 448 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_REBOUCAS ,new Point( 633 , 503 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_9_DE_JULHO ,new Point( 633 , 554 ));
	    spacesToCoordinates.put( Board.BoardSpaces.PRISAO ,new Point( 633 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_EUROPA ,new Point( 547 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE2 ,new Point( 495 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.RUA_AUGUSTA ,new Point( 441 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PACAEMBU ,new Point( 388 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_TAXI ,new Point( 336 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE3 ,new Point( 282 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.INTERLAGOS ,new Point( 228, 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.LUCROS ,new Point( 173 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.MORUMBI ,new Point( 123 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.PASSE_LIVRE, new Point( 55 , 619 ));
	    spacesToCoordinates.put( Board.BoardSpaces.FLAMENGO, new Point( 55 , 553 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE4, new Point( 55 , 501 ));
	    spacesToCoordinates.put( Board.BoardSpaces.BOTAFOGO, new Point( 55 , 449 ));
	    spacesToCoordinates.put( Board.BoardSpaces.IMPOSTO, new Point( 55 , 395 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_NAVEGACAO, new Point( 55 , 340 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_BRASIL, new Point( 55 , 288 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE5, new Point( 55 , 231 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_PAULISTA, new Point( 55 , 176 ));
	    spacesToCoordinates.put( Board.BoardSpaces.JARDIM_EUROPA, new Point( 55 , 123 ));
	    spacesToCoordinates.put( Board.BoardSpaces.VA_PARA_A_PRISAO, new Point( 55 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COPACABANA, new Point( 120 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_AVIACAO, new Point( 173 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_VIEIRA_SOUTO, new Point( 228 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.AV_ATLANTICA, new Point( 282 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.COMPANHIA_DE_TAXI_AEREO, new Point( 340 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.IPANEMA, new Point( 391 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.SORTE6, new Point( 447 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.JARDIM_PAULISTA, new Point( 502 , 53 ));
	    spacesToCoordinates.put( Board.BoardSpaces.BROOKLIN, new Point( 555 , 53 ));
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
