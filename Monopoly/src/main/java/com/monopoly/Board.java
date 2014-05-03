/**
 * 
 */
package com.monopoly;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * @author Carlos
 *
 */
public class Board extends JPanel {
	private static final long serialVersionUID = 3149157930506422886L;
	private Image board;
	private int paddingX , paddingY;
	
	public Board( int paddingX , int paddingY )
	{
		ImageIcon auxBoard = new ImageIcon(this.getClass().getClassLoader().getResource("tabuleiro.png"));
        board = auxBoard.getImage();
        
        this.paddingX = paddingX;
        this.paddingY = paddingY;
	}
	
	public Board()
	{
		this( 0 , 0 );
	}
	
	public void paint(Graphics g)
	{
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage( board , paddingX , paddingY , null );
	}	
}
