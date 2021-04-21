import java.util.Random;

public class Pawns {
    public Pawn[] pawns;
    public Color color;
    public int starter;

    public Color getColor() {
        return color;
    }

    public Pawns(Color c) {
        this.pawns = new Pawn[4];
        for (int i = 0; i < 4; i++) {
            pawns[i] = new Pawn(c);
        }
        this.color = c;
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
     * The first roll of die of the player
     */
    public void start() {
        Random random = new Random();
        // TODO : ajouter dé
        this.starter = random.nextInt(6) + 1;
        System.out.println(this.starter);

    }

}
