import java.util.Random;

public class Die {

	private int value;

	public Die() {
		this.value = 0;
	}

	public int getDie() {
		return rollDie();
	}

	public int rollDie() {
		Random random = new Random();
		int compteur = 0;
		// TODO : ajouter pause
		value = random.nextInt(6) + 1;

		while (value == 0 % 6 && compteur < 2) { // reroll the die until 3 times, if 3 times 6 the turn pass
			compteur++;
			// TODO : ajouter pause
			value += random.nextInt(6) + 1;
		}

		if (value == 18) {
			value = 0;
		}

		return value;
	}

	// public int rewardRoll() {
	// Random random = new Random();
	// int die;
	// // TODO : ajouter pause
	// die = random.nextInt(6) + 1;
	// return die;
	// }

}
