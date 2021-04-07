public enum Color {
    RED(0), BLUE(1), GREEN(2), YELLOW(3);

    private final int color;

    Color(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
