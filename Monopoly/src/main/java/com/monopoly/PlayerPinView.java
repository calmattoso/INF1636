package com.monopoly;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.Observable;
import java.util.Observer;

import com.monopoly.Player;

public class PlayerPinView extends JPanel implements Observer {
	private static final long serialVersionUID = 2386349901642426601L;
	private Point coordinates , offset;
	private ImageIcon pin;
	private Dimension pinDimension;
	
	PlayerPinView( Player p , Point offset )
	{
		this.coordinates = Board.BoardSpaces.spaceToPoint( p.getPosition() );
		this.offset = offset;
						
		loadPinImage( Player.PlayerColor.colorToString( p.getPinColor() ) );
		
		this.pinDimension = new Dimension( pin.getIconWidth() , pin.getIconHeight() );
		
		this.setPreferredSize( this.pinDimension );
		updateBounds();
	}
	
	PlayerPinView()
	{
		this( new Player() , new Point(0,0) );
	}
	
	public void paint(Graphics g)
	{
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage( pin.getImage() , 0 , 0 , null );
	}
		
	// retorna 
	public Dimension getDimension()
	{
		return this.pinDimension;
	}

	// Player notifica seus observadores que teve sua posição no tabuleiro alterada
	public void update( Observable playerModel , Object update ) {
		if( update instanceof Board.BoardSpaces ){
			Board.BoardSpaces space = (Board.BoardSpaces) update;
			
			this.coordinates = Board.BoardSpaces.spaceToPoint( space );
			System.out.println(coordinates);
			
			updateBounds();
		}
	}
	
	private void loadPinImage( String pinColor )
	{
		String path = "pins/" + pinColor + "_pin.png";
		
		pin = new ImageIcon(this.getClass().getClassLoader().getResource(path));
	}
	
	private void updateBounds(){		
		this.setBounds( coordinates.x  + offset.x , coordinates.y + offset.y ,
						pinDimension.width, pinDimension.height );
	}
}
