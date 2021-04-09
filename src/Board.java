import java.util.ArrayList;

public class Board {
    // Idée : ajouter des coordonnées aux structures des pions pour connaître leur
    // posistion sur l'image
    // Faire des case pour déterminer facilement leur position
    // Superposer deux images le cercle du pion avec un décalage de une ou deux
    // unités pour symbolyser de doublage

    // @SulyvanDal, je les ai mis en static comme on aura qu'une seule instance
    // Board, nan?
    public static ArrayList<Pawn> mainArray;

    public static ArrayList<Pawn> redArray;
    public static ArrayList<Pawn> blueArray;
    public static ArrayList<Pawn> yelArray;
    public static ArrayList<Pawn> greenArray;

    private static Window window;

    Board() {
        mainArray = new ArrayList<Pawn>();
        blueArray = new ArrayList<Pawn>();
        redArray = new ArrayList<Pawn>();
        yelArray = new ArrayList<Pawn>();
        greenArray = new ArrayList<Pawn>();

        window = new Window();

        createDummyBoard();
    }

    public static ArrayList<Pawn> getBlueArray() {
        return blueArray;
    }

    public static ArrayList<Pawn> getRedArray() {
        return redArray;
    }

    public static ArrayList<Pawn> getYelArray() {
        return yelArray;
    }

    public static ArrayList<Pawn> getGreenArray() {
        return greenArray;
    }

    public static ArrayList<Pawn> getMainArray() {
        return mainArray;
    }

    // public void playerTurn(Pawns l) {

    // int die = Die.rollDie();
    // // faire la selection du pawn chosePawn=
    // movePawn(chosePawn)
    // }

    // public boolean movePawn(Pawn p, int die) {
    // Pawn place;
    // if (isOut(p)) {

    // place = isFree(p, die);

    // if (place == null) {

    // p.setLocation((p.getLocation() + die) % 52);

    // } else {
    // if (place.getColor() == p.getColor()) {
    // doublePawn(place, p); // traiter s'il s'est doublé ou non
    // }

    // }
    // }
    // }

    public boolean isOut(Pawn p) {
        if (p.getLocation() != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Pawn isFree(Pawn p, int die) {

        Pawn free = null;
        int i = 0;

        while (!mainArray.isEmpty() && free == null) {

            if (p.getLocation() + die == mainArray.get(i).getLocation()) {
                free = mainArray.get(i);
            }

        }

        return free;
    }

    public boolean doublePawn(Pawn p1, Pawn p2) {
        if (!p1.isDoubled() || !p2.isDoubled()) {

            p1.setDoubled(true);
            mainArray.remove(p2);
            return true;
        }

        return false;
    }

    // FIXME : fonction temporaire pour tester Window et Panel
    private static void createDummyBoard() {
        Pawn p = new Pawn(Color.BLUE);
        mainArray.add(p);
        for (int i = 0; i < 52; i++) {
            p.setLocation(i);
            window.getPanel().repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return;
    }
}