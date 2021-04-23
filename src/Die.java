import java.util.Random;

/** Class representing the die of the game. */
public class Die {

	/** Contains the sum of the successive rolls of the die. */
	private int value;

	/** Creates and initializes a new die. */
	public Die() {
		this.value = 0;
	}

	/**
	 * Returns the value of the {@code Die}. Returns 0 if the player rolled three 6
	 * in a row.
	 * 
	 * @return the die value
	 */
	public int getDieValue() {
		return this.value;
	}

	/**
	 * Rolls the die for a given player turn according to the ludo game rules and
	 * calls the {@code showRoll()} and {@code showPass()} functions to display the
	 * die value or that the turn passes.
	 * 
	 * @return the sum of the different die rolls, 0 if the player rolled three 6
	 */
	public int rollDie() {
		Random random = new Random();
		int counter = 0;

		// We roll the die up to 3 times
		do {
			counter++;
			int newRollValue = random.nextInt(6) + 1;
			this.value += newRollValue;
			Board.infoPanel.showRoll(newRollValue, (newRollValue == 6 && counter < 3), this.value);
		} while (this.value % 6 == 0 && counter < 3);

		// If he got three 6, the turn passes
		if (this.value == 18) {
			this.value = 0;
			Board.infoPanel.showPass();
		}

		return value;
	}

}
