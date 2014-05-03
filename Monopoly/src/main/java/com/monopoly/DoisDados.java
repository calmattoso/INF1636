
public class DoisDados {
	private int dado1; //Dado numero 1
	private int dado2; //Dado numero 2
	
	public DoisDados(){
		// Inicializa os dados com valores aleatórios
		rodaDados();
	}
	public void rodaDados(){
		// Designa um valor aleatório de 1 a 6 para cada dado
		dado1 = (int) (Math.random()*6) +1;
		dado2 = (int) (Math.random()*6) +1;
	}
	
	public int obtemDado1(){
		return dado1;
	}
	
	public int obtemDado2(){
		return dado2;
	}
	public int obtemSoma(){
		return dado1+dado2;
	}

}
