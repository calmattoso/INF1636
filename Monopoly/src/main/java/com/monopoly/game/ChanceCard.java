package com.monopoly.game;

public class ChanceCard extends Card {
	private String description;
	private String type;
	private int amount;

	public ChanceCard(String title,String description,String type,int amount){
		this.title = title;
		this.description = description;
		this.type = type;
		this.amount = amount;
		
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}


}
