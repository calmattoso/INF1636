package com.monopoly.game;

public class TerrainCard extends Card {
	private int propertyCost; // custo de construção para casas e hoteis
	private int mortgage; // valor da hipoteca
	private int[] rent; // ganho com aluguel de, respectivamente, 0,1,2,3 casas ou 1 hotel.
	private int capacity; // quantas edificacoes ha no terreno

	public TerrainCard(String id, int propCost, int mort, int[] rent, int cap){
		this.title = id;
		this.propertyCost = propCost;
		this.mortgage = mort;
		this.rent = rent;
		this.capacity = cap;
	}
	
	public int getCost(){
		return propertyCost;
	}
	
	public int getMortgage() {
		return mortgage;
	}
	
	public int[] getRent() {
		return rent;
	}


	public int getCapacity() {
		return capacity;
	}

	

	


}
