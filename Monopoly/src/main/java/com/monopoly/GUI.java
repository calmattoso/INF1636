/**
 * 
 */
package com.monopoly;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Carlos
 *
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = 1643643618255734885L;
	private String title;	
	private int width, height;
	
	public GUI(String title , Dimension d )
	{
		this.title = title;
		
		this.width  = (int) d.getWidth();
		this.height = (int) d.getHeight();
	}
	
	public void addComponent( Component component )
	{
		add( component );
	}
	
	public void displayWindow( boolean b )
	{
		this.setVisible( b );
	}	

	public void setupWindow()
	{
		 setTitle( this.title );
		
		 setVisible( false );
	     
		 setDefaultCloseOperation( EXIT_ON_CLOSE );
	     
		 setSize( this.width , this.height );
	     
	     setLocationRelativeTo( null );
	     
	     setResizable( false );
	  
	}
	
	
	
	
}
