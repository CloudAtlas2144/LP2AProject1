import java.util.ArrayList;

public class Board {

    public static ArrayList<Pawn> mainArray;

    public static Pawns pBlue; // à voir pour plus propre
    public static Pawns pRed;
    public static Pawns pGreen;
    public static Pawns pYellow;

    public static GamePanel gamePanel;

    Board() {
        mainArray = new ArrayList<Pawn>();

        pBlue = new Pawns(Color.BLUE);
        pRed = new Pawns(Color.RED);
        pGreen = new Pawns(Color.GREEN);
        pYellow = new Pawns(Color.YELLOW);

        createDummyBoard();
        gamePanel = new GamePanel();
    }

    public static ArrayList<Pawn> getMainArray() {
        return mainArray;
    }

    public static boolean playerTurn(Pawns l) {
        Die die = new Die();
        boolean test = true;
        die.rollDie();

        // FIXME : TEMPORARY WORKAROUND
        Pawn selectedPawn = new Pawn(Color.RED);

        if (die.getDie() != 0) {

            if (l.allStock() && die.getDie() >= 6) { // TODO : récupérer clic sur un pawn

                selectedPawn.setLocation(die.getDie() - 6 + 13 * selectedPawn.getColor().toInt());

            } else {

                do { // TODO : récupérer clic sur un pawn
                     // TODO : un pion qui gagne ne doit plus pouvoir être cliqué

                    if (selectedPawn.isOut()) {

                        if (sameCase(selectedPawn.getLocation() + die.getDie()) && selectedPawn.isDoubled() == null
                                && !(selectedPawn.isSafe())) {

                            // reminder a doubled pawn can't move
                            // if a simple pawn is on the same

                            test = false;

                            // test = selectedPawn.moveEndLocation(die.getDie());

                            if (selectedPawn.getEndLocation() != -1) {

                                test = selectedPawn.moveEndLocation(die.getDie());

                            } else {

                                test = movePawn(selectedPawn, die.getDie());

                            }
                        }
                    } else {

                        if (die.getDie() == 6) {

                            selectedPawn.setLocation(die.getDie() - 6 + 13 * selectedPawn.getColor().toInt());
                            mainArray.add(selectedPawn);

                        }
                    }

                } while (test == false);
            }

            // return l.isWin(); // check if a player has finished the game
        }
        // FIXME : Moving line out to guarantee boolean returning
        return l.isWin(); // check if a player has finished the game

    }

    public static boolean movePawn(Pawn p, int die) {

        Pawn place;
        boolean movement = false; // check if the movement is valid
        if (p.isDoubled() != null && die % 2 == 0) {

            place = isFree(p, die);

            if (!cantPass(p, die)) {

                if (place == null) { // the case is empty

                    p.move(die);
                    movement = true;

                } else { // the case is already occupied

                    if (place.getColor() == p.getColor()) { // the case is occupied by the same color

                        movement = doublePawn(place, p);

                        if (movement == true) {

                            p.move(die);
                        }

                    } else { // the case is occupied by an other color

                        movement = eatPawn(place, p);

                        if (movement == true) {

                            p.move(die);

                        }
                    }
                }
            }
        }
        return movement;

    }

    public static Pawn isFree(Pawn p, int die) {

        Pawn free = null;
        int i = 0;

        while (!mainArray.isEmpty() && free == null) {

            if (p.getLocation() + die == mainArray.get(i).getLocation()) {
                free = mainArray.get(i);
            }

        }

        return free;
    }

    public static boolean doublePawn(Pawn p1, Pawn p2) {
        if (p1.isDoubled() != null && p2.isDoubled() != null) {

            p1.setDoubled(p2);
            mainArray.remove(p2);
            return true;
        }

        return false;
    }

    public static boolean eatPawn(Pawn p1, Pawn p2) {

        if (!p1.isSafe()) {

            if (p2.isDoubled() != null) { // p2 is doubled
                p2.setHasEaten(true);

                if (p1.isDoubled() != null) { // p1 is doubled

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
        }

        return false;
    }

    public static boolean cantPass(Pawn p, int die) {// check if the pawn pass over a doubled
        boolean test = false;
        for (int i = 0; i < mainArray.size(); i++) {

            if (p.getLocation() < mainArray.get(i).getLocation() // location before the turn
                    && mainArray.get(i).getLocation() < (p.getLocation() + die) // location if the turn is complete
                    && mainArray.get(i).isDoubled() != null) {

                test = true;

            }
        }

        return test;
    }

    public static boolean sameCase(int l) {

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
        Pawns[] allPawns = { Board.pBlue, Board.pRed, Board.pGreen, Board.pYellow };
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[0].setEndLocation(3);
        }
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[1].setLocation(0 + i * 13);
        }
        for (int i = 0; i < 4; i++) {
            allPawns[i].pawns[2].setEndLocation(6);
        }

        return;
    }
}