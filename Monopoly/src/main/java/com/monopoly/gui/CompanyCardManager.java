package com.monopoly.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.monopoly.game.Bank;
import com.monopoly.game.CompanyCard;
import com.monopoly.game.TerrainCard;

public class CompanyCardManager 
	extends JFrame 
{
	private static final long serialVersionUID = -3226065208456309022L;
	
	private final int WIDTH  = 480;
	private final int HEIGHT = 365;
	
	private CompanyCard card;
	
	private JPanel menu;

	public CompanyCardManager(CompanyCard c, int x, int y)
	{
		this.card = c;
		
		menu = new NegotiationPanel(card, this);
		menu.setLayout(null);
		menu.setBounds(0,0,WIDTH,HEIGHT);
		
		Toolkit tk=Toolkit.getDefaultToolkit();

		int screenWidth  = tk.getScreenSize().width,
			screenHeight = tk.getScreenSize().height ;
		
		setBounds( screenWidth/2 - screenWidth/16, screenHeight/2 - screenHeight/4 , WIDTH, HEIGHT);
		setResizable( false );
		setTitle( "Gerência de Empresa" );
		setIconImage(tk.getImage("src/main/resources/company.png"));
		
		getContentPane().setLayout(null);
		getContentPane().add( menu );
	}
	
	public void closeWindow() {
        WindowEvent event = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}
	
	private static class NegotiationPanel 
		extends JPanel
		implements ActionListener
	{
		private static final long serialVersionUID = -953621073692591648L;
		private final String SELL_EVENT = "sellCard";
		private final String MORTGAGE_EVENT = "mortgageCard";
		private final String PAYBACK_MORTGAGE_EVENT  = "paybackMortgage";
		
		private CompanyCard card;
		
		private CompanyCardManager parent;
		
		private ImageIcon cardImage;
		private ImageIcon mortgageImage;
		private ImageIcon sellImage;
		private ImageIcon paybackImage;
		
		public NegotiationPanel(CompanyCard card, CompanyCardManager parent)
		{
			this.card = card; 
			this.parent = parent;
			
			this.cardImage = new ImageIcon("src/main/resources/cards/company/" + String.valueOf(card.getID()) + ".png");
			this.mortgageImage = new ImageIcon("src/main/resources/mortgage.png");
			this.sellImage 	   = new ImageIcon("src/main/resources/sell.png");
			this.paybackImage  = new ImageIcon("src/main/resources/payback.png");			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			setLayout(null);

			RenderingHints rh = new RenderingHints( RenderingHints.KEY_ANTIALIASING  , 
					RenderingHints.VALUE_ANTIALIAS_ON );
			rh.put( RenderingHints.KEY_RENDERING ,
			RenderingHints.VALUE_RENDER_QUALITY );  
			
			g2d.setRenderingHints(rh); 
			g2d.setColor(Color.BLACK);
			
			g2d.setFont(new Font("Comic Sans", Font.BOLD, 20));			
			g2d.drawString(this.card.getTitle() , 10, 30);

			/**
			 * Show company card.
			 */
			g2d.drawImage(cardImage.getImage(), 
						  15, 50, 15 + cardImage.getIconWidth() , 50 + cardImage.getIconHeight(),
						  0, 0, cardImage.getIconWidth(), cardImage.getIconHeight(),
						  null);
			
			/**
			 * Button for selling the card to the bank.
			 */
			CustomButton sellCard = new CustomButton( "Vender carta" , sellImage, 200, 30 );
			sellCard.setBounds( 265 , 50 , 190 , 40 );
			sellCard.addActionListener(this);
			sellCard.setActionCommand( SELL_EVENT );
			add(sellCard);
			
			CustomButton mortgageCard = new CustomButton( "Hipotecar (R$" + card.getMortgage() +")"  , mortgageImage, 200, 70 );
			mortgageCard.setBounds( 265 , 100 , 190 , 40 );
			mortgageCard.addActionListener(this);
			mortgageCard.setActionCommand( MORTGAGE_EVENT );			
			add(mortgageCard);	
			
			
			CustomButton paybackMortgage = new CustomButton( "Pagar Hipoteca (R$" + (int)(card.getMortgage()*1.2) + ")" , paybackImage, 200, 110 );
			paybackMortgage.setBounds( 265 , 150 , 190 , 40 );
			paybackMortgage.addActionListener(this);
			paybackMortgage.setActionCommand( PAYBACK_MORTGAGE_EVENT );
			add(paybackMortgage);
			
			if( card.isMortgaged() == true)
			{
				mortgageCard.setEnabled(false);
				sellCard.setEnabled(false);
			}
			else
			{
				paybackMortgage.setEnabled(false);
			}

		}

		/**
		 * Handle the rules for when one of the above buttons gets clicked.
		 */
		public void actionPerformed(ActionEvent event) {
			String message = event.getActionCommand();
			
			if( message.equals( SELL_EVENT ))
			{	
				Bank.CondRet ret = Bank.getBank().sellToBank( card.getOwner() , card );
				
				if( ret == Bank.CondRet.MISSING_REQUIREMENTS )
				{
					JOptionPane.showMessageDialog(null, "Ação inválida!");
				}				
				
				System.out.println("sellCard");
			}
			else if( message.equals( MORTGAGE_EVENT ))
			{
				Bank.getBank().mortgage( this.card );
				
				System.out.println("mortgageCard");
			}
			else if( message.equals( PAYBACK_MORTGAGE_EVENT ))
			{
				Bank.CondRet ret = Bank.getBank().payMortgageBack( this.card );
				
				if( ret == Bank.CondRet.MISSING_REQUIREMENTS )
				{
					JOptionPane.showMessageDialog(null, "Ação inválida!");
				}				
				
				System.out.println("payback");
			}
			
			parent.closeWindow();
		}
	}
}
