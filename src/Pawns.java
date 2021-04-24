import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

/** Class responsible for managing all pawns of a same color. */
public class Pawns {
    public Pawn[] pawns;
    public Color color;
    public int startRoll;

    /** Image file of the pawn. */
    public Image img;

    public Pawns(Color c) {
        this.color = c;
        this.pawns = new Pawn[4];
        loadPawnImage();
        for (int i = 0; i < 4; i++) {
            pawns[i] = new Pawn(c);
            pawns[i].setImg(img);
        }
    }

    public Color getColor() {
        return color;
    }

    /**
     * Checks if all the pawn are located in the storage
     * 
     * @return true if all the pawns are in the storage, false if it is not the case
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
     * Checks if all of the 4 pawns have finished
     * 
     * @return true if they have finished, false if one or more have not finish
     */
    public boolean isWin() {
        int i = 0;
        boolean test = true;

        while (i < 4 && test == true) { // tests if all pawns have finished
            if (this.pawns[i].getEndLocation() != 5) {
                test = false;
                i++;
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
        this.startRoll = random.nextInt(6) + 1;

        Board.getInfoPanel().showStartAttempt(color, this.startRoll);
    }

}
