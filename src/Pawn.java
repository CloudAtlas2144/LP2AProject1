public class Pawn {
    private Color color;
    private Pawn isDoubled;
    private boolean hasEaten;
    private int location;
    private int endlocation;

    Pawn(Color color) {
        this.color = color;
        this.isDoubled = null;
        this.hasEaten = false;
        this.location = -1;
        this.endlocation = -1;
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

    public int getEndlocation() {
        return endlocation;
    }

    public void setEndlocation(int endlocation) {
        this.endlocation = endlocation;
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

    public boolean endBoard(int die) {
        boolean test = true;

        if (this.getLocation() + die >= 52 - this.getColor().toInt() * 13 && this.hasEaten()) {
            this.setEndlocation(0);
            Board.getMainArray().remove(this);
        }

        return test;
    }

    public boolean moveEndLocation(int die) {

        if ((this.getEndlocation() + die) % 6 == 0) {
            this.setEndlocation(6);
            return true;
        } else {
            return false;
        }
    }

}
