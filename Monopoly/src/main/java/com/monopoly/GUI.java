/**
 * 
 */
package com.monopoly;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * @author Carlos
 *
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = 1643643618255734885L;
	private JLayeredPane layers;
	private String title;	
	private int width, height;
	
	public GUI( String title , Dimension d )
	{
		this.title = title;
		
		this.width  = (int) d.getWidth();
		this.height = (int) d.getHeight();
		
		layers = new JLayeredPane();
	}
	
	public void addComponent( Component component , int zIndex , int innerIndex )
	{
		layers.add( component , zIndex , innerIndex );
	}
	
	public void displayWindow( boolean b )
	{
		this.setVisible( b );
	}	

	public void setupWindow()
	{
		 setTitle( this.title );
		
		 getContentPane().add( layers , BorderLayout.CENTER );
		 
		 setVisible( false );		 
  
		 setDefaultCloseOperation( EXIT_ON_CLOSE );
	     
		 setSize( this.width , this.height );
	     
	     setLocationRelativeTo( null );
	     
	     setResizable( false );
	  
	}
	
}
