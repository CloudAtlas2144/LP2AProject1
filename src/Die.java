import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;

public class Die {

	private int value;

	private static ImageIcon[] icons = new ImageIcon[7];

	private static boolean startAuto;

	public Die() {
		this.value = 0;
		loadDieImages();
		startAuto = false;
	}

	public int getDie() {
		return this.value;
	}

	public int rollDie() {
		Random random = new Random();
		int compteur = 0;

		do {

			compteur++;
			entryRollPopUp();
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

	/**
	 * Loads the images of the different sides of the die.
	 */
	private static void loadDieImages() {
		try {
			for (int i = 0; i < 7; i++) {
				String fileName = String.format("img/die_%d.png", i);
				Image img = ImageIO.read(new File(fileName)).getScaledInstance(70, 70, Image.SCALE_SMOOTH);
				icons[i] = new ImageIcon(img);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void entryRollPopUp() {
		String[] options = { "Auto-roll", "Roll" };
		int choice = JOptionPane.showOptionDialog(null, "Blue player, it is your turn. You may roll the die.",
				"Roll the die", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icons[0], options, options[1]);

	}

	// public int rewardRoll() {
	// Random random = new Random();
	// int die;
	// // TODO : ajouter pause
	// die = random.nextInt(6) + 1;
	// return die;
	// }

}
