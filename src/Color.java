public enum Color {
    BLUE(0), RED(1), GREEN(2), YELLOW(3);

    private final int color;

    Color(int color) {
        this.color = color;
    }

    public int toInt() {
        return color;
    }
}
