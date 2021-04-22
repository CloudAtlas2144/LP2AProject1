import java.util.ArrayList;

/**
 * Class responsible for managing the general evolution of the game as well as
 * creating the game windows.
 */
public class Board {

    /** Array containing the pawns located on the baord. */
    private static ArrayList<Pawn> mainArray;

    /** Arrays containing all the pawns of the game. */
    public static Pawns pBlue, pRed, pGreen, pYellow;

    /** Groups all the {@code Pawns} in one array. */
    public static Pawns[] allPawns;

    /** Reference to the instance of {@code GamePanel}. */
    public static GamePanel gamePanel;

    /** Reference to the instance of {@code InfoPanel}. */
    public static InfoPanel infoPanel;

    /**
     * Initializes the different arrays containing the pawns and instantiates a
     * {@code GamePanel} and an {@code InfoPanel}.
     */
    Board() {
        mainArray = new ArrayList<Pawn>();

        pBlue = new Pawns(Color.BLUE);
        pRed = new Pawns(Color.RED);
        pGreen = new Pawns(Color.GREEN);
        pYellow = new Pawns(Color.YELLOW);

        allPawns = new Pawns[4];
        allPawns[0] = pBlue;
        allPawns[1] = pRed;
        allPawns[2] = pGreen;
        allPawns[3] = pYellow;

        gamePanel = new GamePanel();
        infoPanel = new InfoPanel();
    }

    public static ArrayList<Pawn> getMainArray() {
        return mainArray;
    }

    /**
     * Manages the turn of the player, checks if he plays the correct pawn
     * 
     * @param l the player's array of pawns
     * @return checks if the player has won the game at the end of the turn
     */
    public static boolean playerTurn(Pawns l) {
        Die die = new Die();
        boolean test = false;
        infoPanel.showTurn(l.getColor());
        die.rollDie();
        System.out.println("tour du joueur " + l.getColor());
        Pawn selectedPawn = null;

        // If the player did not roll a 6 three times in a row he can play
        if (die.getDieValue() != 0) {

            if (isMovePossible(l, die.getDieValue())) {

                // If all the pawns are in the player's storage zone
                if (l.allStock()) {

                    // If the player rolled a six, he can pull one pawn out
                    if (die.getDieValue() >= 6) {
                        selectedPawn = gamePanel.getSelectedPawn(l.getColor());
                        selectedPawn.setLocation(13 * selectedPawn.getColor().toInt());
                        movePawn(selectedPawn, die.getDieValue() - 6);
                        gamePanel.unstorePawn(selectedPawn);
                        mainArray.add(selectedPawn);
                        test = true;

                    } else {
                        infoPanel.showPass();
                    }
                } else { // there is already one pawn or more on the board
                    do {

                        selectedPawn = gamePanel.getSelectedPawn(l.getColor());

                        if (selectedPawn.isOut()) { // the pawn is on the board

                            if (selectedPawn.getEndLocation() == -1) { // the pawn is on the common way

                                test = movePawn(selectedPawn, die.getDieValue());

                            } else { // the pawn is on the color way

                                test = selectedPawn.moveEndLocation(die.getDieValue());

                            }

                        } else { // the pawn is in the storage

                            if (die.getDieValue() >= 6) {

                                selectedPawn.setLocation(13 * selectedPawn.getColor().toInt());
                                movePawn(selectedPawn, die.getDieValue() - 6);
                                gamePanel.unstorePawn(selectedPawn);
                                mainArray.add(selectedPawn);
                                test = true;

                            }
                        }

                    } while (test == false);

                }
            }

        }
        gamePanel.repaint();
        return l.isWin(); // check if a player has finished the game

    }

    /**
     * Check if the movement of the pawn is correct and manage the different
     * possibilities (eaten, doubled,...)
     * 
     * @param p   the pawn to check
     * @param die the die's value
     * @return true if their was no problem, false if the movement can't occur
     */
    public static boolean movePawn(Pawn p, int die) {

        Pawn place;
        boolean movement = false;
        if (p.isDoubled() != null) { // the pawn is doubled

            if (die % 2 == 0) { // the value of the die is even

                die = die / 2;
                place = isFree(p, die);

                if (place == null) { // the case is free

                    p.move(die);
                    movement = true;

                } else { // the case is already occupied

                    if (!(place.getLocation() % 13 == 0 || place.getLocation() % 13 == 8)) { // the pawn don't land on a
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

                place = isFree(p, die);

                if (place == null) { // the case is free

                    p.move(die);
                    movement = true;

                } else { // the case is already occupied

                    if (!(place.getLocation() % 13 == 0 || place.getLocation() % 13 == 8)) {
                        // the pawn don't land on a safe case

                        if (p.getColor() == place.getColor()) { // both have the same color

                            if (place.isDoubled() == null) { // place is not a doubled

                                doublePawn(place, p);
                                movement = true;

                            }

                        } else { // the pawns have a different color

                            if (place.isDoubled() == null) { // place is not a doubled

                                eatPawn(place, p);
                                p.move(die);
                                movement = true;

                            }
                        }
                    } else { // the pawns land on a safe case

                        if (place.getColor() == p.getColor()) { // both have the same color

                            if (place.isDoubled() == null) {

                                doublePawn(place, p);
                                movement = true;

                            }
                        }
                    }
                }
            }
        }

        return movement;

    }

    /**
     * Checks if the pawn will land on a free square or not
     * 
     * @param p   the moving pawn
     * @param die the die's value
     * @return {@code null} if the square is free or the {@code Pawn} on the
     *         occupied square
     */
    public static Pawn isFree(Pawn p, int die) {

        // The pawn already in our destination square
        Pawn landingPawn = null;
        int i = 0;

        // For each pawn in mainArray, we check if it is located in our destination
        // square
        while (i < mainArray.size() && landingPawn == null) {

            if (p.getLocation() + die == mainArray.get(i).getLocation()) {
                landingPawn = mainArray.get(i);
            }
            i++;
        }
        return landingPawn;
    }

    /**
     * Check if a pawn can be doubled and change the different values if it is
     * possible
     * 
     * @param p1 the pamn already on the case
     * @param p2 the moving pawn
     * @return true if the pawn was doubled
     */
    public static void doublePawn(Pawn p1, Pawn p2) {

        if (p1.isDoubled() == null && p2.isDoubled() == null) {

            p1.setDoubled(p2);
            p2.setLocation(-10);
            mainArray.remove(p2);
        }
    }

    /**
     * Manage the differents variables if a pawn is eaten by an other
     * 
     * @param p1 the eaten pawn
     * @param p2 the eater pawn
     */
    public static void eatPawn(Pawn p1, Pawn p2) {

        p2.setHasEaten(true);

        if (p1.isDoubled() != null) { // p1 is a doubled

            mainArray.remove(p1);
            p1.isDoubled().remove();
            p1.remove();

        } else { // p1 is simple

            mainArray.remove(p1);
            p1.remove();

        }

    }

    /**
     * Check if a pawn will pass a doubled pawn
     * 
     * @param p   the pawn who will moved
     * @param die the value of the movement
     * @return false if the pawn is not blocked, true if a doubled block the pawn
     */
    public static boolean cantPass(Pawn p, int die) {// check if the pawn pass over a doubled
        boolean test = false;
        for (int i = 0; i < mainArray.size(); i++) {

            if (p.getLocation() < mainArray.get(i).getLocation() // location before the turn
                    && mainArray.get(i).getLocation() < (p.getLocation() + die) // location if the turn is complete
                    && mainArray.get(i).isDoubled() != null && p.isDoubled() == null) {

                test = true;

            }
        }

        return test;
    }

    /**
     * the first roll of die to decide which player will start the game
     * 
     * @return the color of the player who start
     */
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

        infoPanel.showStartingPlayer(pStarterColor);
        return pStarterColor;
    }

    /**
     * Checks if at least one of the player's pawns can be moved.
     *
     * @param l   the list of pawns to test
     * @param die the value of the die
     * @return {@code true} if one pawn can move, {@code false} if all the pawns are
     *         stuck
     */
    public static boolean isMovePossible(Pawns l, int die) {
        // Counts how many pawns he cannot move
        int blockedPawns = 0;
        int i = 0;
        boolean result = true;
        Pawn testPawn = null;

        while (i < 4 && blockedPawns == i) {

            // we clone the pawn to do our testings without affecting the real pawn
            testPawn = (Pawn) l.pawns[i].clone();

            if (testPawn.isOut()) {
                if (!testMove(testPawn, die)) {
                    blockedPawns++;
                }
            } else { // the pawn is in the storage
                if (die >= 6) { // the pawn could go out of the storage

                    testPawn.setLocation(13 * testPawn.getColor().toInt());

                    // checks if the pawn can be moved out of storage
                    if (!testMove(testPawn, die - 6)) {

                        blockedPawns++;
                    }
                } else {
                    blockedPawns++;
                }

            }

            i++;

        }

        if (blockedPawns == 4) {
            result = false;
            infoPanel.showPass();
        }

        return result;
    }

    /**
     * 
     * @param p
     * @param die
     * @return
     */
    public static boolean testMove(Pawn p, int die) {

        // Pawn located in the destination square of p
        Pawn landingPawn;
        boolean movement = false;

        // If the pawn is doubled
        if (p.isDoubled() != null) {

            // If the value of the die is even
            if (die % 2 == 0) {
                die = die / 2;
                landingPawn = isFree(p, die);

                // the destination square is free
                if (landingPawn == null) {

                    movement = true;

                } else { // the square is already occupied

                    // the pawn is not located on an entry or on a safe square
                    if (!(landingPawn.getLocation() % 13 == 0 || landingPawn.getLocation() % 13 == 8)) {

                        if (landingPawn.getColor() != p.getColor()) { // the pawns are not of the same color

                            movement = true;
                        }
                    }
                }
            }
        } else { // the pawn is simple

            if (p.getLocation() != -10) {

                if (!cantPass(p, die)) {

                    landingPawn = isFree(p, die);

                    if (landingPawn == null) { // the square is free

                        movement = true;

                    } else { // the square is already occupied

                        if (!(landingPawn.getLocation() % 13 == 0 || landingPawn.getLocation() % 13 == 8)) {
                            // the pawn don't land on a safe square

                            if (p.getColor() == landingPawn.getColor()) { // both have the same color

                                if (landingPawn.isDoubled() == null) { // place is not a doubled

                                    movement = true;

                                }

                            } else { // the pawns have a different color

                                if (landingPawn.isDoubled() == null) { // place is not a doubled

                                    movement = true;

                                }
                            }
                        } else { // the pawns are on a safe square

                            if (landingPawn.getColor() == p.getColor()) { // both have the same color

                                if (landingPawn.isDoubled() == null) {

                                    movement = true;

                                }
                            }
                        }
                    }
                }
            }
        }

        return movement;
    }
}