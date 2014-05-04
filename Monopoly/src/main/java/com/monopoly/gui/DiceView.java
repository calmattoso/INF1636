package com.monopoly.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.monopoly.game.Dice;

public class DiceView
	extends JPanel
	implements Observer
{
	private static final long serialVersionUID = -8721788500047468026L;
	int die1, die2;	
	
	public DiceView( int x , int y )
	{
		die1 = 0;
		die2 = 0;
		
		this.setBounds( x , y , 300, 100 ) ;
	}

	public void update(Observable dices, Object event) {
		if( dices instanceof Dice )
		{
			Dice d = (Dice) dices;	
			
			die1 = d.getDie1();
			die2 = d.getDie2();	
			
			this.repaint();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{		
		// N�o exibe a string na primeira execu��o
		if( die1 == die2 && die1 == 0 )
		{
			return;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		System.out.println("Dado 1: " + String.valueOf( die1 ) + "  Dado 2: " + String.valueOf( die2 ));

        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
            									RenderingHints.VALUE_ANTIALIAS_ON );
        rh.put( RenderingHints.KEY_RENDERING ,
        		RenderingHints.VALUE_RENDER_QUALITY );
        
        g2d.setFont( new Font("Times New Roman", Font.BOLD, 16) );
               
        g2d.setRenderingHints(rh);        
        g2d.drawString("Dado 1: " + String.valueOf( die1 ) + "  Dado 2: " + String.valueOf( die2 ) , 12, 12);
	}

}
