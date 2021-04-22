import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

public class Pawns {
    public Pawn[] pawns;
    public Color color;
    public int starter;

    /** Image file of the pawn. */
    public Image img;

    public Color getColor() {
        return color;
    }

    public Pawns(Color c) {
        this.color = c;
        this.pawns = new Pawn[4];
        loadPawnImage();
        for (int i = 0; i < 4; i++) {
            pawns[i] = new Pawn(c);
            pawns[i].img = img;
        }
    }

    /**
     * Check if all the pawn are located in the storage
     * 
     * @return true if all the pawn are in the storage, false if it is not the case
     */
    public boolean allStock() {
        boolean test = true;
        for (int i = 0; i < 4; i++) {
            if (pawns[i].getLocation() != -1) {
                test = false;
            }
        }
        return test;
    }

    /**
     * Check if all of the 4 pawn have finished
     * 
     * @return true if they have finished, false if one or more have not finish
     */
    public boolean isWin() {
        int i = 0;
        boolean test = true;

        while (i < 4 && test == true) { // test if all pawns are in finish situation
            if (this.pawns[i].getEndLocation() != 6) {
                test = false;
            }
        }

        return test;
    }

    /**
     * Loads the Image file corresponding to the pawn's color and adds it to the
     * {@code img} variable.
     */
    private void loadPawnImage() {
        String imgName = "img/" + this.color.toCamelCase() + "Pawn.png";
        try {
            this.img = ImageIO.read(new File(imgName));
        } catch (Exception exception) {
            Main.imageNotFound();
        }
    }

    /**
     * Determines if the player is the one to begin by rolling the die.
     */
    public void start() {
        Random random = new Random();
        this.starter = random.nextInt(6) + 1;

        Board.infoPanel.showStartAttempt(color, this.starter);
        System.out.println(this.starter);
    }

}
