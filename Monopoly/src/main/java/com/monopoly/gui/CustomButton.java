package com.monopoly.gui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class CustomButton 
	extends JButton 
{
	private static final long serialVersionUID = -674139597467208538L;

	public CustomButton(String text, ImageIcon icon, int x, int y) {
		super( text , icon );
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		configureStyles(x, y);				
	}
	
	public CustomButton(String text, ImageIcon icon) {
		this(text, icon, 0, 0);				
	}
	
	private void configureStyles(int x, int y) {		
		this.setBounds(x, y, 110, 50);
			
		Border border = BorderFactory.createBevelBorder(
			BevelBorder.RAISED,
			Color.LIGHT_GRAY,
			Color.GRAY
		);
		this.setBorder( border );	
		this.setBackground( new Color(249,249,249) );
	}
	
	

}
