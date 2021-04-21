import java.awt.*;
import java.io.*;
import javax.imageio.*;
//import javax.swing.*;
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

    public boolean allStock() {
        boolean test = true;
        for (int i = 0; i < 4; i++) {
            if (pawns[i].getLocation() != -1) {
                test = false;
            }
        }
        return test;
    }

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
        String imgName = "";

        switch (this.color.toInt()) {
        case 0:// BLUE
            imgName = "img/BluePawn.png";
            break;
        case 1:// RED
            imgName = "img/RedPawn.png";
            break;
        case 2:// GREEN
            imgName = "img/GreenPawn.png";
            break;
        case 3:// YELLOW
            imgName = "img/YellowPawn.png";
            break;
        default:
            System.out.println("Exception : Pawn.loadPawnImage() : Unexpected color value.");
            break;
        }

        try {
            this.img = ImageIO.read(new File(imgName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /** Determines if the player is the one to begin by rolling the die. */
    public void start() {
        Random random = new Random();
        this.starter = random.nextInt(6) + 1;

        Board.infoPanel.showStartAttempt(color, this.starter);
        System.out.println(this.starter);
    }

}
