public class Main {
    public static void main(String[] args) {
        new Board();

        boolean isEnd = false; // check if the pawn played is correct

        Color colorTurn = Board.firstToStart(); // the variable indicate which player have to play
        while (true) {
            System.out.println("pause");
            switch (colorTurn) {

            case BLUE:
                // FIXME : TEMPORARY
                Board.infoPanel.showTurn(Color.BLUE, 2);
                isEnd = Board.playerTurn(Board.pBlue);
                colorTurn = Color.RED;
                break;

            case RED:
                // FIXME : TEMPORARY
                Board.infoPanel.showTurn(Color.RED, 3);
                isEnd = Board.playerTurn(Board.pRed);
                colorTurn = Color.GREEN;
                break;

            case GREEN:
                // FIXME : TEMPORARY
                Board.infoPanel.showTurn(Color.GREEN, 4);
                isEnd = Board.playerTurn(Board.pGreen);
                colorTurn = Color.YELLOW;
                break;

            case YELLOW:
                // FIXME : TEMPORARY
                Board.infoPanel.showTurn(Color.YELLOW, 6);
                isEnd = Board.playerTurn(Board.pYellow);
                colorTurn = Color.BLUE;
                break;
            }

            if (isEnd) {
                // apparition licorne chevauché par poutine avec son sabre spaghetti
                break;

            }

        }
    }

}
