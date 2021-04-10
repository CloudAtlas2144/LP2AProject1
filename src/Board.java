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

    public void playerTurn(Pawns l) {

        Die die = new Die();
        boolean test = true;
        die.rollDie();
        if (die.getDie() != 0) {
            if (l.allStock() && die.getDie() >= 6) {
                // TODO : récupérer clic sur un pawn
                selectedPawn.location = die.getDie() - 6 + 13 * selectedPawn.getColor();

            } else {
                do {
                    // TODO : récupérer clic sur un pawn
                    if (selectedPawn.isOut()) {

                        if (sameCase(selectedPawn.getLocation() + die.getDie()) && selectedPawn.isDoubled() == null) { // reminder:
                                                                                                                       // a
                                                                                                                       // doubled
                                                                                                                       // can't
                                                                                                                       // move
                                                                                                                       // when
                                                                                                                       // a
                                                                                                                       // simple
                                                                                                                       // is
                                                                                                                       // on
                                                                                                                       // the
                                                                                                                       // same
                                                                                                                       // case

                            test = false;

                        } else {

                            test = movePawn(selectedPawn, die);

                        }
                    }
                    if (die.getDie() == 6) {
                        selectedPawn.setLocation(die.getDie() - 6 + 13 * selectedPawn.getColor());
                    }
                } while (test == false);
            }
        }
    }

    public boolean movePawn(Pawn p, Die die) {
        Pawn place;
        boolean movement = false; // check if the movement is valid

        place = isFree(p, die);

        if (place == null) { // the case is empty

            p.setLocation((p.getLocation() + die.getDie()) % 52);
            movement = true;
        } else { // the case is already occupied
            if (place.getColor() == p.getColor()) { // the case is occupied by the same color
                movement = doublePawn(place, p);
                if (movement == true) {
                    p.setLocation((p.getLocation() + die.getDie()) % 52);
                }
            } else { // the case is occupied by an other color
                movement = eatPawn(place, p);
                if (movement == true) {
                    p.setLocation((p.getLocation() + die.getDie()) % 52);
                }
            }

        }
        return movement;
    }

    public Pawn isFree(Pawn p, Die die) {

        Pawn free = null;
        int i = 0;

        while (!mainArray.isEmpty() && free == null) {

            if (p.getLocation() + die.getDie() == mainArray.get(i).getLocation()) {
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

        if (p2.isDoubled() != null) {
            p2.setHasEaten(true);

            if (p1.isDoubled() != null) {
                mainArray.remove(p1.isDoubled());
                p1.isDoubled().remove();
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
        int test = 0
        for (int i = 0; i < mainArray.size(); i++) {
            if (mainArray.get(i).getLocation() == l) {
                test++;
            }
        }
        if(test == 2){ 
            return true;
        }else{
            return false;
        }
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