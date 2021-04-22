import java.awt.*;

public class Pawn implements Cloneable {
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

    public Pawn clone() {
        try {
            Pawn p = (Pawn) super.clone();
            return p;

        } catch (CloneNotSupportedException e) {
            throw new InternalError();
            // TODO: handle exception
        }
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

    /**
     * Check if the pawn is on the board
     * 
     * @return true if it is on the board, false if it is in the storage
     */
    public boolean isOut() {
        if (this.location != -1) {
            return true;
        } else {
            return false;
        }
    }

    /** remove a pawn from the board */
    public void remove() {
        this.setDoubled(null);
        this.setHasEaten(false);
        this.setLocation(-1);
    }

    /**
     * move the pawn on the correct location
     * 
     * @param die the value of the die
     */
    public void move(int die) {

        if (this.getLocation() + die > (50 + this.getColor().toInt() * 13) % 52 && this.hasEaten()) {

            this.setEndLocation((this.getLocation() + die % 52));
            Board.getMainArray().remove(this);

        } else {

            this.location = (this.location + die) % 52;

        }

    }

    /**
     * Moves the pawn to its new location if it is on the home column.
     * 
     * @param die the value of the die
     * @return {@code true} if the pawn has moved
     */
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
