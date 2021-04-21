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

    /**
     * Manage the turn of the player, check if he plays the good pawn
     * 
     * @param l the player's array of pawn
     * @return check if the player win the game at the end of the turn
     */
    public static boolean playerTurn(Pawns l) {
        Die die = new Die();
        boolean test = false;
        die.rollDie();
        System.out.println("tour du joueur " + l.getColor());
        // FIXME : TEMPORARY WORKAROUND
        Pawn selectedPawn = new Pawn(Color.RED);

        if (die.getDie() != 0) {

            if (isPossibleMove(l, die.getDie())) {

                if (l.allStock()) { // all the pawn are in the storage

                    if (die.getDie() >= 6) { // the player roll a six, he can pull one pawn out

                        selectedPawn = gamePanel.getSelectedPawn(l.getColor());
                        selectedPawn.setLocation(13 * selectedPawn.getColor().toInt());
                        movePawn(selectedPawn, die.getDie() - 6);
                        gamePanel.unstorePawn(selectedPawn);
                        mainArray.add(selectedPawn);
                        test = true;

                    }
                } else { // there is already one pawn or more on the board
                    do {

                        selectedPawn = gamePanel.getSelectedPawn(l.getColor());

                        if (selectedPawn.isOut()) { // the pawn is on the board

                            if (selectedPawn.getEndLocation() == -1) { // the pawn is on the common way

                                test = movePawn(selectedPawn, die.getDie());

                            } else { // the pawn is on the color way

                                test = selectedPawn.moveEndLocation(die.getDie());

                            }

                        } else { // the pawn is in the storage

                            if (die.getDie() >= 6) {

                                selectedPawn.setLocation(13 * selectedPawn.getColor().toInt());
                                movePawn(selectedPawn, die.getDie() - 6);
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
     * @return true if their was no problem, false if the movement can't occure
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
     * Check if the pawn will land on a free case or not
     * 
     * @param p   the moving pawn
     * @param die the die's value
     * @return null if the case is free or the pawn on the occupied case
     */
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

        return pStarterColor;

    }

    /**
     * Check if at least one pawn can move
     *
     * @param l   the list of pawn to test
     * @param die the value of the die
     * @return true if one pawn can move, false if all the pawn are stucked
     */
    public static boolean isPossibleMove(Pawns l, int die) {

        int test = 0; // count how many pawn can't move
        int i = 0;
        boolean result = true;
        Pawn pawnTest = new Pawn(Color.BLUE);

        while (i < 4 && test == i) {

            pawnTest = (Pawn) l.pawns[i].clone();

            if (pawnTest.isOut()) {

                if (!testMove(pawnTest, die)) {
                    test++;
                }
            } else { // the pawn is in the storage
                if (die >= 6) { // the pawn could go out of the storage

                    pawnTest.setLocation(13 * pawnTest.getColor().toInt());

                    if (!testMove(pawnTest, die - 6)) { // check if the pawn can go out of the
                        // storage

                        test++;

                    }
                } else {

                    test++;
                }

            }

            i++;

        }

        if (test == 4) {
            result = false;
        }

        return result;
    }

    public static boolean testMove(Pawn p, int die) {

        Pawn place;
        boolean movement = false;

        if (p.isDoubled() != null) { // the pawn is doubled

            if (die % 2 == 0) { // the value of the die is even

                die = die / 2;
                place = isFree(p, die);

                if (place == null) { // the case is free

                    movement = true;

                } else { // the case is already occupied

                    if (!(place.getLocation() % 13 == 0 || place.getLocation() % 13 == 8)) { // the pawn don't land on a
                                                                                             // safe case

                        if (place.getColor() != p.getColor()) { // the pawns have a different color

                            movement = true;
                        }
                    }
                }
            }
        } else { // the pawn is simple

            if (p.getLocation() != -10) {

                if (!cantPass(p, die)) {

                    place = isFree(p, die);

                    if (place == null) { // the case is free

                        movement = true;

                    } else { // the case is already occupied

                        if (!(place.getLocation() % 13 == 0 || place.getLocation() % 13 == 8)) {
                            // the pawn don't land on a safe case

                            if (p.getColor() == place.getColor()) { // both have the same color

                                if (place.isDoubled() == null) { // place is not a doubled

                                    movement = true;

                                }

                            } else { // the pawns have a different color

                                if (place.isDoubled() == null) { // place is not a doubled

                                    movement = true;

                                }
                            }
                        } else { // the pawns are on a safe case

                            if (place.getColor() == p.getColor()) { // both have the same color

                                if (place.isDoubled() == null) {

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

    // FIXME : TEMPORARY WORKAROUND
    private static void createDummyBoard() {

        return;
    }
}