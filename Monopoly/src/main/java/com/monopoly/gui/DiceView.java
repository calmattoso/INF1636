package com.monopoly.gui;

import java.awt.Color;
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
	int width, height;
	
	public DiceView( int x , int y )
	{
		this.setBounds( x , y , width, height ) ;
		this.setOpaque(false);
	}
	{
		die1 = 0;
		die2 = 0;
		width = 270;
		height = 20;
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
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		System.out.println("Dado 1: " + String.valueOf( die1 ) + "  Dado 2: " + String.valueOf( die2 ));

        RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
            									RenderingHints.VALUE_ANTIALIAS_ON );
        rh.put( RenderingHints.KEY_RENDERING ,
        		RenderingHints.VALUE_RENDER_QUALITY );  
        
        g2d.setColor( new Color(22,0,127));
        g2d.setRenderingHints(rh); 
        
		// Welcome player during first execution
		if( die1 == die2 && die1 == 0 )
		{
			g2d.setFont( new Font("Comic Sans", Font.BOLD, 16) );
			g2d.drawString("Bem Vindo! :)", width/2 - width/6, height - 8);			
			return;
		} 
		
		g2d.setFont( new Font("Comic Sans", Font.BOLD, 14) );
        
             
        g2d.drawString("Turno Anterior - Dado 1: " + String.valueOf( die1 ) + " | Dado 2: " + String.valueOf( die2 ) , 12, 12);
	}

}
