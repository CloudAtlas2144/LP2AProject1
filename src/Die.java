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

		do {

			compteur++;
			// TODO : ajouter pause
			this.value += random.nextInt(6) + 1;
			System.out.println(value);
		} while (this.value % 6 == 0 && compteur < 3);

		// reroll the die up to 3 times, if 3 times 6 the turn passes

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
