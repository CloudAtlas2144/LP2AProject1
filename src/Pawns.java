public class Pawns {
    public Pawn[] blueP = new Pawn[4];
    public Pawn[] redP = new Pawn[4];
    public Pawn[] yelP = new Pawn[4];
    public Pawn[] greenP = new Pawn[4];

    Pawns() {
        for (int i = 0; i < 4; i++) {
            redP[i] = new Pawn(Color.RED);
            blueP[i] = new Pawn(Color.BLUE);
            yelP[i] = new Pawn(Color.BLUE);
            greenP[i] = new Pawn(Color.BLUE);
        }
    }

}
