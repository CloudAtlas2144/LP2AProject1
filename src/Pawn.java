import java.awt.*;
import java.io.*;
import javax.imageio.*;

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

        loadPawnImage();
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

        if (this.getLocation() + die >= 52 - this.getColor().toInt() * 13 && this.hasEaten()) {

            this.setEndLocation(this.getLocation() + die - 52 - this.getColor().toInt() * 13);
            Board.getMainArray().remove(this);

        } else {

            this.location = (this.location + die) % 52;

        }

    }

    /**
     * Move the pawn to the correct location if it is on the home column
     * 
     * @param die the value of the die
     * @return true if the pawn has moved
     */
    public boolean moveEndLocation(int die) {

        if ((this.getEndLocation() + die) % 6 == 0) {
            this.setEndLocation(6);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads the Image file corresponding to the pawn's color and adds it to the
     * {@code img} variable.
     */
    private void loadPawnImage() {
        String imgName = "";

        switch (this.color.toInt()) {
        case 0:// BLUE
            imgName = "BluePawn.png";
            break;
        case 1:// RED
            imgName = "RedPawn.png";
            break;
        case 2:// GREEN
            imgName = "GreenPawn.png";
            break;
        case 3:// YELLOW
            imgName = "YellowPawn.png";
            break;
        default:
            System.out.println("Exception : Pawn.loadPawnImage() : Unexpected color value.");
            break;
        }

        try {
            this.img = ImageIO.read(new File(imgName));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
