package com.monopoly.gui;

import javax.swing.JButton;

public class DataButton 
	extends JButton
{
	private static final long serialVersionUID = -8001322901733189875L;
	
	private Object data;
	
	public DataButton(Object data)
	{
		this.data = data;
	}
	
	public DataButton()
	{
		this(null);
	}
	
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object newData)
	{
		this.data = newData;
	}
}
