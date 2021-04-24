/**
 * Enum defining the color values of the different players and the functions to
 * manage them.
 */
public enum Color {
    BLUE(0), RED(1), GREEN(2), YELLOW(3);

    private final int color;

    private Color(int color) {
        this.color = color;
    }

    /**
     * Converts the {@code Color} to an integer value.
     * 
     * @return the color's numerical constant
     */
    public int toInt() {
        return color;
    }

    /**
     * Returns the text of the {@code Color} formatted to camel case.
     * 
     * @return the {@code String} of the color name
     */
    public String toCamelCase() {
        String end = this.toString().substring(1).toLowerCase();
        String str = this.toString().substring(0, 1) + end;
        return str;
    }

    /**
     * Returns the next {@code Color} according the order of the colors in the
     * {@code Enum}.
     * 
     * @return the following color
     */
    public Color next() {
        switch (color) {
        case 0:
            return RED;

        case 1:
            return GREEN;

        case 2:
            return YELLOW;

        case 3:
            return BLUE;
        }
        return null;
    }
}
