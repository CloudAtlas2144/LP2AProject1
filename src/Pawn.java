import java.awt.*;

public class Pawn {
    private Color color;
    private Pawn isDoubled;
    private boolean hasEaten;
    private int location;
    private int endLocation;

    /** Clickable target associated to the pawn. */
    public Rectangle target;
    /** Image file of the pawn. */
    public Image img;
    /** Location of the pawn on the window. */
    public Point gLoc;

    Pawn(Color color) {
        this.color = color;
        this.isDoubled = null;
        this.hasEaten = false;
        this.location = -1;
        this.endLocation = -1;

        this.gLoc = new Point(0, 0);
        this.target = new Rectangle(-10, -10, 1, 1);
    }

    public int getLocation() {
        return location;
    }

    public Pawn isDoubled() {
        return isDoubled;
    }

    public void setDoubled(Pawn isDoubled) {
        this.isDoubled = isDoubled;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public int getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(int endlocation) {
        this.endLocation = endlocation;
    }

    public void duplicate() { // à implémenter

        this.isDoubled().setLocation(this.getLocation());
        this.isDoubled().setHasEaten(this.hasEaten());
        this.setDoubled(null);
    }

    public boolean isOut() {
        if (this.location != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void remove() {
        this.setDoubled(null);
        this.setHasEaten(false);
        this.setLocation(-1);
    }

    public void move(int die) {

        if (this.getLocation() + die >= 52 - this.getColor().toInt() * 13 && this.hasEaten()) {

            this.setEndLocation(this.getLocation() + die - 52 - this.getColor().toInt() * 13);
            Board.getMainArray().remove(this);

        } else {

            this.location = (this.location + die) % 52;

        }

    }

    public boolean moveEndLocation(int die) {

        if ((this.getEndLocation() + die) % 6 == 0) {
            this.setEndLocation(6);
            return true;
        } else {
            return false;
        }
    }

    public boolean isSafe() {
        if (this.location == 8 % 13 || this.location == 0 % 13) {
            return true;
        } else {
            return false;
        }

    }

}
