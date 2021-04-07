public class Pawn {
    private Color color;
    private boolean isDoubled;
    private boolean hasEaten;
    private int location;

    // envie que ça marche
    Pawn(Color color) {
        this.color = color;
        this.isDoubled = false;

        this.location = 0;
    }

    public int getLocation() {
        return location;
    }

    public boolean isDoubled() {
        return isDoubled;
    }

    public void setDoubled(boolean isDoubled) {
        this.isDoubled = isDoubled;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
    }

    public boolean hasEaten() {
        return hasEaten;
    }
}
