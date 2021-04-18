public class Main {
    public static void main(String[] args) {
        new Board();

        boolean isEnd = false; // check if the pawn played is correct

        Color colorTurn = Board.firstToStart(); // the variable indicate which player have to play
        while (true) {

            switch (colorTurn) {

            case RED:

                isEnd = Board.playerTurn(Board.pRed);
                colorTurn = Color.BLUE;
                break;

            case BLUE:

                isEnd = Board.playerTurn(Board.pBlue);
                colorTurn = Color.GREEN;
                break;

            case GREEN:

                isEnd = Board.playerTurn(Board.pGreen);
                colorTurn = Color.YELLOW;
                break;

            case YELLOW:

                isEnd = Board.playerTurn(Board.pYellow);
                colorTurn = Color.RED;
                break;
            }

            if (isEnd) {
                // apparition licorne chevauché par poutine avec son sabre spaghetti
                break;

            }

        }
    }

}
