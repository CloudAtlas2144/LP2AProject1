import java.awt.*;

public class Pawn implements Cloneable {
    private Color color;
    private Pawn isDoubled;
    private boolean hasEaten;

    /**
     * Position of the pawn on the common path of the board. Is equal to -1 if the
     * pawn is in its base, -10 if it is doubled and carried by another pawn.
     */
    private int location;
    /**
     * Position of the pawn on its final line. Is equal to -1 when it is out of the
     * final line.
     */
    private int endLocation;

    /** Clickable target associated to the pawn. */
    private Rectangle target;
    /** Image file of the pawn. */
    private Image img;
    /** Graphical location of the pawn on the window. */
    private Point gLoc;

    Pawn(Color color) {
        this.color = color;
        this.isDoubled = null;
        this.hasEaten = false;
        this.location = -1;
        this.endLocation = -1;

        this.gLoc = new Point(0, 0);
        this.target = new Rectangle(-10, -10, 1, 1);
    }

    public Point getgLoc() {
        return gLoc;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg() {
        return img;
    }

    public void setTarget(int x, int y, int width, int height) {
        this.target.x = x;
        this.target.y = y;
        this.target.width = width;
        this.target.height = height;
    }

    public Rectangle getTarget() {
        return target;
    }

    public Pawn clone() {
        try {
            Pawn p = (Pawn) super.clone();
            return p;

        } catch (CloneNotSupportedException e) {
            throw new InternalError();
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

    /**
     * Checks if the pawn is on the board
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

    /** removes a pawn from the board */
    public void remove() {
        this.setDoubled(null);
        this.setHasEaten(false);
        this.setLocation(-1);
    }

    /**
     * Moves the pawn on the correct location.
     * 
     * @param die the value of the die
     */
    public void move(int die) {

        if (this.getLocation() + die > (50 + this.getColor().toInt() * 13) % 52 && this.hasEaten()
                && this.getLocation() <= (50 + this.getColor().toInt() * 13) % 52) {

            this.setEndLocation(this.getLocation() + (die) - (50 + this.getColor().toInt() * 13) % 52 - 1);
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

        if (die > 6) {
            return false;
        }

        if (this.isDoubled != null) {
            if (die % 2 == 0) {
                die = die / 2;
            } else {
                return false;
            }
        }

        if (this.endLocation + die < 6) {

            this.endLocation += die;
            return true;

        } else {

            return false;

        }
    }
}
