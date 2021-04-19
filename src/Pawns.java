import java.util.Random;

public class Pawns {
    public Pawn[] pawns;
    public Color color;
    public int starter;

    public Pawns(Color c) {
        this.pawns = new Pawn[4];
        for (int i = 0; i < 4; i++) {
            pawns[i] = new Pawn(c);
        }
        this.color = c;
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

    public void start() {
        Random random = new Random();
        // TODO : ajouter dé
        this.starter = random.nextInt(6) + 1;

    }

}
