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
        boolean test = false;
        die.rollDie();
        System.out.println("tour du joueur " + l.getColor());
        // FIXME : TEMPORARY WORKAROUND
        Pawn selectedPawn = new Pawn(Color.RED);

        if (die.getDie() != 0) {

            if (l.allStock()) {

                if (die.getDie() >= 6) {

                    selectedPawn = gamePanel.getSelectedPawn(l.getColor());
                    selectedPawn.setLocation(die.getDie() - 6 + 13 * selectedPawn.getColor().toInt());
                    gamePanel.unstorePawn(selectedPawn);
                }

            } else {

                do {

                    selectedPawn = gamePanel.getSelectedPawn(l.getColor());

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
                        } else {
                            test = movePawn(selectedPawn, die.getDie());
                        }
                    } else {

                        if (die.getDie() >= 6) {
                            // TODO : problème si le pion sort et qu'il arrive sur une case occupée
                            selectedPawn.setLocation(die.getDie() - 6 + 13 * selectedPawn.getColor().toInt());
                            mainArray.add(selectedPawn);
                            gamePanel.unstorePawn(selectedPawn);
                            test = true;

                        }
                    }

                } while (test == false);
            }
        }

        gamePanel.repaint();
        // FIXME : Moving line out to guarantee boolean returning
        return l.isWin(); // check if a player has finished the game

    }

    public static boolean movePawn(Pawn p, int die) {

        Pawn place;
        boolean movement = false;
        place = isFree(p, die);
        if (p.isDoubled() != null) { // the pawn is doubled

            if (die % 2 == 0) { // the value of the die is even

                if (place == null) { // the case is free

                    p.move(die);
                    movement = true;

                } else { // the case is already occupied

                    if (!(place.getLocation() % 13 == 0 && place.getLocation() % 13 == 8)) { // the pawn don't land on a
                                                                                             // safe case

                        if (place.getColor() != p.getColor()) { // the pawns have a different color

                            eatPawn(place, p);
                            p.move(die);
                            movement = true;
                        }
                    }
                }
            }
        } else { // the pawn is simple

            if (!cantPass(p, die)) {

                if (place == null) { // the case is free

                    p.move(die);
                    movement = true;

                } else { // the case is already occupied

                    if (!(place.getLocation() % 13 == 0 && place.getLocation() % 13 == 8)) {
                        // the pawn don't land on a safe case

                        if (p.getColor() == place.getColor()) { // both have the same color

                            if (place.isDoubled() == null) { // place is not a doubled

                                place.setDoubled(p);
                                movement = true;

                            }

                        } else { // the pawns have a different color

                            if (place.isDoubled() == null) { // place is not a doubled

                                eatPawn(place, p);
                                p.move(die);
                                movement = true;

                            }
                        }
                    } else { // the pawns are on a safe case

                        if (place.isDoubled() == null) {

                            place.setDoubled(p);
                            movement = true;

                        }
                    }
                }
            }
        }

        return movement;

    }

    public static Pawn isFree(Pawn p, int die) {

        Pawn free = new Pawn(Color.BLUE);
        free = null;
        int i = 0;

        while (i < mainArray.size() && free == null) {

            if (p.getLocation() + die == mainArray.get(i).getLocation()) {

                free = mainArray.get(i);

            }

            i++;

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

    public static void eatPawn(Pawn p1, Pawn p2) {

        p2.setHasEaten(true);

        if (p1.isDoubled() != null) { // p1 is a doubled

            mainArray.remove(p1);
            p1.remove();
            p1.duplicate();

        } else { // p1 is simple

            mainArray.remove(p1);
            p1.remove();

        }

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

    public static boolean sameCase(int l) { // maybe useless

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
            pStarter = pBlue.starter;
        }

        if (pStarter < pGreen.starter) {

            pStarterColor = pGreen.color;
            pStarter = pGreen.starter;

        }

        if (pStarter < pYellow.starter) {

            pStarterColor = pYellow.color;
            pStarter = pYellow.starter;

        }

        return pStarterColor;

    }

    // FIXME : TEMPORARY WORKAROUND
    private static void createDummyBoard() {

        return;
    }
}