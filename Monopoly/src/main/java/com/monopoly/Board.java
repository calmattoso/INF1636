/**
 * 
 */
package com.monopoly;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * @author Carlos
 *
 */
public class Board extends JPanel {
	private static final long serialVersionUID = 3149157930506422886L;
	private ImageIcon board;
	private int x , y;
	
	public static enum BoardSpaces {
		START, LEBLON, SORTE, AV_PRESIDENTE_VARGAS, AV_NOSSA_S_DE_COPACABANA,
		COMPANHIA_FERROVIARIA, AV_BRIGADEIRO_FARIA_LIMA, COMPANHIA_DE_VIACAO,
		AV_REBOUCAS, AV_9_DE_JULHO, PRISAO, AV_EUROPA, SORTE2, RUA_AUGUSTA,
		AV_PACAEMBU, COMPANHIA_DE_TAXI, SORTE3, INTERLAGOS, LUCROS, MORUMBI,
		LIVRE, FLAMENGO, SORTE4, BOTAFOGO, IMPOSTO, COMPANHIA_DE_NAVEGACAO,
		AV_BRASIL, SORTE5, AV_PAULISTA,	JARDIM_EUROPA, VA_PARA_A_PRISAO,
		COPACABANA,	COMPANHIA_DE_AVIACAO, AV_VIEIRA_SOUTO, AV_ATLÂNTICA,
		COMPANHIA_DE_TAXI_AEREO, IPANEMA, SORTE6, JARDIM_PAULISTA, BROOKLYN;
		
		private Point coordinates;
		private BoardSpaces next;
		
		// faz um mapeamento das posições para respectivas coordenadas
		static {
			START.coordinates = new Point( 860 , 72 );
			LEBLON.coordinates = new Point( 860 , 168 );
			SORTE.coordinates = new Point( 860 , 240 );
			AV_PRESIDENTE_VARGAS.coordinates = new Point( 860 , 316 );
			AV_NOSSA_S_DE_COPACABANA.coordinates = new Point( 854 , 390 );
			COMPANHIA_FERROVIARIA.coordinates = new Point( 854 , 462 );
			AV_BRIGADEIRO_FARIA_LIMA.coordinates = new Point( 852 , 536 );
			COMPANHIA_DE_VIACAO.coordinates = new Point( 852 , 610 );
			AV_REBOUCAS.coordinates = new Point( 850 , 684 );
			AV_9_DE_JULHO.coordinates = new Point( 846 , 754 );
			PRISAO.coordinates = new Point( 840 , 840 );
			AV_EUROPA.coordinates = new Point( 746 , 840 );
			SORTE2.coordinates = new Point( 674 , 840 );
			RUA_AUGUSTA.coordinates = new Point( 604 , 840 );
			AV_PACAEMBU.coordinates = new Point( 530 , 840 );
			COMPANHIA_DE_TAXI.coordinates = new Point( 456 , 840 );
			SORTE3.coordinates = new Point( 386 , 840 );
			INTERLAGOS.coordinates = new Point( 314 , 840 );
			LUCROS.coordinates = new Point( 240 , 840 );
			MORUMBI.coordinates = new Point( 168 , 840 );
			LIVRE.coordinates = new Point( 76 , 840 );
			FLAMENGO.coordinates = new Point( 74 , 752 );
			SORTE4.coordinates = new Point( 70 , 682 );
			BOTAFOGO.coordinates = new Point( 70 , 610 );
			IMPOSTO.coordinates = new Point( 66 , 354 );
			COMPANHIA_DE_NAVEGACAO.coordinates = new Point( 66 , 462 );
			AV_BRASIL.coordinates = new Point( 64, 388 );
			SORTE5.coordinates = new Point( 64 , 314 );
			AV_PAULISTA.coordinates = new Point( 68 , 240 );
			JARDIM_EUROPA.coordinates = new Point( 66 , 164 );
			VA_PARA_A_PRISAO.coordinates = new Point( 72 , 72 );
			COPACABANA.coordinates = new Point( 164 , 66 );
			COMPANHIA_DE_AVIACAO.coordinates = new Point( 236 , 66 );
			AV_VIEIRA_SOUTO.coordinates = new Point( 312 , 66 );
			AV_ATLÂNTICA.coordinates = new Point( 386 , 66 );
			COMPANHIA_DE_TAXI_AEREO.coordinates = new Point( 462 , 66 );
			IPANEMA.coordinates = new Point( 536 , 66 );
			SORTE6.coordinates = new Point( 610 , 66 );
			JARDIM_PAULISTA.coordinates = new Point( 684 , 66 );
			BROOKLYN.coordinates = new Point( 760 , 72 );
		}

		public static Point spaceToPoint( BoardSpaces space )
		{
			return space.coordinates;
		}
		
		public BoardSpaces getNext( int offset ) {
			return values()[ (ordinal() + offset ) % values().length ];
		}		
	}
	
	public Board( int x , int y )
	{
		board = new ImageIcon(this.getClass().getClassLoader().getResource("tabuleiro.png"));
                 
        this.x = x;
        this.y = y;
        
        Dimension d = getDimension();
		
		this.setPreferredSize( d );
		this.setBounds(x, y, d.width , d.height);
	}
	
	public Board()
	{
		this( 0 , 0 );
	}
	
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
}
