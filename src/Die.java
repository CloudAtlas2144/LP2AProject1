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
	 * Rolls the die for a given player turn according to the ludo game rules and
	 * calls the {@code showRoll()} and {@code showPass()} to display the die value
	 * or that the turn passes.
	 * 
	 * @return the value of the die
	 */
	public int rollDie() {
		Random random = new Random();
		int counter = 0;

		do {
			counter++;
			int newRollValue = random.nextInt(6) + 1;
			this.value += newRollValue;
			Board.infoPanel.showRoll(newRollValue, (this.value % 6 == 0 && counter < 3), this.value);
			System.out.println(value);
		} while (this.value % 6 == 0 && counter < 3);

		// Re-rolls the die up to 3 times, if we get a 6 three times in a row the turn
		// passes

		if (this.value == 18) {
			this.value = 0;
			Board.infoPanel.showPass();
		}

		return value;
	}

}
