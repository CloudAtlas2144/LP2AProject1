import java.util.ArrayList;

import javax.swing.plaf.TreeUI;

public class Board {
    // Idée : ajouter des coordonnées au structures des pions pour connaître leur
    // posistoin sur l'image
    // Faire des case pour déterminer facilement leur position
    // Superposer deux images le cercle du pion avec un décalage de une ou deux
    // unités pour symbolyser de doublage
    public ArrayList<Pawn> mainArray;

    public ArrayList<Pawn> redArray;
    public ArrayList<Pawn> blueArray;
    public ArrayList<Pawn> yelArray;
    public ArrayList<Pawn> greenArray;

    // envie de tout casser
    // BONJOUUUUUUR @DalSulyvan
    Board() {

        this.mainArray = new ArrayList<Pawn>();
        this.blueArray = new ArrayList<Pawn>();
        this.redArray = new ArrayList<Pawn>();
        this.yelArray = new ArrayList<Pawn>();
        this.greenArray = new ArrayList<Pawn>();

    }

    public ArrayList<Pawn> getBlueArray() {
        return blueArray;
    }

    public ArrayList<Pawn> getRedArray() {
        return redArray;
    }

    public ArrayList<Pawn> getYelArray() {
        return yelArray;
    }

    public ArrayList<Pawn> getGreenArray() {
        return greenArray;
    }

    public ArrayList<Pawn> getMainArray() {
        return mainArray;
    }

    public void playerTurn(Pawns l) {
        
        int die = rollDie;
        // faire la selection du pawn chosePawn= ;
        movePawn(chosePawn)
    }

    public boolean movePawn(Pawn p, int die) {
        Pawn place;
        if (isOut(p)) {

            place = isFree(p, die);

            if (place == null) {

                p.setLocation((p.getLocation() + die) % 52);

            } else {
                if (place.getColor() == p.getColor()) {
                    doublePawn(place, p); // traiter s'il s'est doublé ou non
                }

            }
        }
    }

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
}
