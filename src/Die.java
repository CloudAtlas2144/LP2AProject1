import java.util.Random;

public class Die {
	
	private int value;
	
	public Die() {
		this.value = 0; 
	}
	
	public int getDie() {
		return this.value;
	}
	
	public int rollDie() {
		Random random = new Random();
		int compteur = 0;
		value = random.nextInt(6)+1;
		
		while (value == 0%6 && compteur<2) { // reroll the die until 3 times, if 3 times 6 the turn pass 
			compteur++;
			value+= random.nextInt(6)+1;
		}
		
		if (value == 18) {
			value = 0;
		}
		
		return value;
	}

}
