import java.util.ArrayList;

public class Board {

    public static ArrayList<Pawn> mainArray;

    public static ArrayList<Pawn> redArray;
    public static ArrayList<Pawn> blueArray;
    public static ArrayList<Pawn> yelArray;
    public static ArrayList<Pawn> greenArray;

    public static Pawns pBlue; // à voir pour plus propre
    public static Pawns pRed;
    public static Pawns pGreen;
    public static Pawns pYellow;

    public static GamePanel gamePanel;

    Board() {
        mainArray = new ArrayList<Pawn>();
        blueArray = new ArrayList<Pawn>();
        redArray = new ArrayList<Pawn>();
        yelArray = new ArrayList<Pawn>();
        greenArray = new ArrayList<Pawn>();

        pBlue = new Pawns(Color.BLUE);
        pRed = new Pawns(Color.RED);
        pGreen = new Pawns(Color.GREEN);
        pYellow = new Pawns(Color.YELLOW);

        createDummyBoard();
        gamePanel = new GamePanel();
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

    public static boolean playerTurn(Pawns l) {

        // Die die = new Die();
        // boolean test = true;
        // die.rollDie();
        // if (die.getDie() != 0) {
        // if (l.allStock() && die.getDie() >= 6) { // TODO : récupérer clic sur un pawn

        // // TODO : Je me suis permis :
        // // selectedPawn.location = die.getDie() - 6 + 13 * selectedPawn.getColor();
        // selectedPawn.setLocation(die.getDie() - 6 + 13 *
        // selectedPawn.getColor().toInt());

        // } else {
        // do { // TODO : récupérer clic sur un pawn if (selectedPawn.isOut())
        // // TODO : un pion qui gagne ne doit plus pouvoir être cliqué

        // if (sameCase(selectedPawn.getLocation() + die.getDie()) &&
        // selectedPawn.isDoubled() == null) {
        // // reminder a doubled pawn can't move
        // // if a simple pawn is on the same

        // test = false;

        // } else {
        // if (selectedPawn.getEndlocation() != -1) {

        // test = selectedPawn.moveEndLocation(die.getDie());

        // } else {

        // test = movePawn(selectedPawn, die.getDie());

        // }
        // }
        // if (die.getDie() == 6) {
        // selectedPawn.setLocation(die.getDie() - 6 + 13 *
        // selectedPawn.getColor().toInt());
        // }
        // } while (test == false);
        // }

        // return l.isWin(); // check if a player have ended the game
        // }

        // FIXME : TEMPORARY
        return true;
    }

    public boolean movePawn(Pawn p, int die) {
        Pawn place;
        boolean movement = false; // check if the movement is valid

        place = isFree(p, die);

        if (place == null) { // the case is empty

            p.setLocation((p.getLocation() + die) % 52);
            movement = true;
        } else { // the case is already occupied
            if (place.getColor() == p.getColor()) { // the case is occupied by the same color
                movement = doublePawn(place, p);
                if (movement == true) {
                    p.setLocation((p.getLocation() + die) % 52);
                }
            } else { // the case is occupied by an other color
                movement = eatPawn(place, p);
                if (movement == true) {
                    p.setLocation((p.getLocation() + die) % 52);
                }
            }

        }
        return movement;
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
        if (p1.isDoubled() != null && p2.isDoubled() != null) {

            p1.setDoubled(p2);
            mainArray.remove(p2);
            return true;
        }

        return false;
    }

    public boolean eatPawn(Pawn p1, Pawn p2) {

        if (p2.isDoubled() != null) { // p2 is doubles
            p2.setHasEaten(true);

            if (p1.isDoubled() != null) { // p1 is doubles
                mainArray.remove(p1);
                p1.duplicate();
            }
            mainArray.remove(p1);
            p1.remove();

            return true;
        }

        if (p1.isDoubled() == null && p2.isDoubled() == null) { // both are simple
            mainArray.remove(p1);
            p1.remove();

            return true;

        }

        if (p1.isDoubled() != null && p1.isDoubled() == null) { // p1 is doubled but p2 is simple
            return true;
        }

        return false;
    }

    public boolean sameCase(int l) {
        int test = 0;
        for (int i = 0; i < mainArray.size(); i++) {
            if (mainArray.get(i).getLocation() == l) {
                test++;
            }
        }
        if (test == 2) {
            return true;
        } else {
            return false;
        }
    }

    public static Color firstToStart() {
        int pStarter;
        Color pStarterColor;

        pRed.start();
        pBlue.start();
        pGreen.start();
        pYellow.start();

        pStarter = pRed.starter;
        pStarterColor = pRed.color;

        if (pStarter < pBlue.starter) {
            pStarterColor = pBlue.color;
        }

        if (pStarter < pGreen.starter) {
            pStarterColor = pGreen.color;
        }

        if (pStarter < pYellow.starter) {
            pStarterColor = pYellow.color;
        }

        return pStarterColor;

    }

    // FIXME : TEMPORARY WORKAROUND
    private static void createDummyBoard() {
        Pawns[] allPawns = { pRed, pBlue, pGreen, pYellow };
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[1].setEndLocation(6);
        }
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[0].setEndLocation(2);
        }
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[2].setLocation(20 + i * 5);
        }

        return;
    }
}