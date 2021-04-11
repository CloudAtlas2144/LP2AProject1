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

    public void duplicate(Pawn p1, Pawn p2) {
        p2.setLocation(p1.getLocation());
        p2.setHasEaten(p1.hasEaten);
        p1.setDoubled(null);
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

}
