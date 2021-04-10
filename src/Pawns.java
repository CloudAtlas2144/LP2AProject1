public class Pawns {
    public Pawn[] pawns;
    public Color color;

    public Pawns(Color c) {
        this.pawns = new Pawn[4];
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

}
