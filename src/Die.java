import java.util.Random;

public class Die {

	private int value;

	public Die() {
		this.value = 0;
	}

	public int getDie() {
		return this.value;
	}

	/**
	 * Roll the die for a player turn with the ludo game rules
	 * 
	 * @return the value of the die
	 */
	public int rollDie() {
		Random random = new Random();
		int compteur = 0;

		do {

			compteur++;
			int newRollValue = random.nextInt(6) + 1;
			this.value += newRollValue;
			Board.infoPanel.showRoll(newRollValue, (this.value % 6 == 0 && compteur < 3));
			System.out.println(value);
		} while (this.value % 6 == 0 && compteur < 3);

		// reroll the die up to 3 times, if 3 times 6 the turn passes

		if (this.value == 18) {
			this.value = 0;
			// TODO : check is correct position for function call
			Board.infoPanel.showPass();
		}

		return value;
	}

}
