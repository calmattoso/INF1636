package com.monopoly.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel 
	extends JPanel
{
	private static final long serialVersionUID = -489231726981479372L;
	private ImageIcon background; 
	
	public ImagePanel(ImageIcon background)
	{
		this.background = background;
		
		this.setPreferredSize( new Dimension( background.getIconWidth(), background.getIconHeight()));
		this.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(background.getImage(), 0, 0, null);
	}
}
