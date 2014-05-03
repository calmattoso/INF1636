
public class Dice {
	private int d1; //Dado numero 1
	private int d2; //Dado numero 2
	
	public Dice(){
		// Inicializa os dados com valores aleatórios
		roll();
	}
	public void roll(){
		// Designa um valor aleatório de 1 a 6 para cada dado
		d1 = (int) (Math.random()*6) +1;
		d2 = (int) (Math.random()*6) +1;
	}
	
	public int getDie1(){
		// retorna o valor do dado 1
		return d1;
	}
	
	public int getDie2(){
		// retorna o valor do dado 2
		return d2;
	}
	public int getSum(){
		// retorna a soma dos valores obtidos nos dados 1 e 2
		return d1+d2;
	}

}
