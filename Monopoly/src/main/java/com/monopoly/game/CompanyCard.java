package com.monopoly.game;

public class CompanyCard extends Card {
	private int multiplier; // multiplicador 
	private int mortgage; // valor da hipoteca

	public CompanyCard(String name, int mult, int mort){
		this.title = name;
		this.multiplier = mult;
		this.mortgage = mort;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public int getMortgage() {
		return mortgage;
	}

	public int getDue(int sum) {
		return this.multiplier * sum;
	}
}
