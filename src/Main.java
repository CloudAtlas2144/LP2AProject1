import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        ImageIcon die = null;
        try {
            die = new ImageIcon(ImageIO.read(new File("img/die_0.png")).getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String[] options = { "Let's go!", "Wait for granny..." };
        int option = JOptionPane.showOptionDialog(null, "Ready for a new thrilling game?", "Ludo Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, die, options, options[0]);

        if (option == 0) {
            die = null;
            new Board();

            boolean isEnd = false; // check if the pawn played is correct

            Color colorTurn = Board.firstToStart(); // the variable indicate which player have to play
            while (true) {
                System.out.println("pause");
                switch (colorTurn) {

                case BLUE:
                    isEnd = Board.playerTurn(Board.pBlue);
                    colorTurn = Color.RED;
                    break;

                case RED:
                    isEnd = Board.playerTurn(Board.pRed);
                    colorTurn = Color.GREEN;
                    break;

                case GREEN:
                    isEnd = Board.playerTurn(Board.pGreen);
                    colorTurn = Color.YELLOW;
                    break;

                case YELLOW:
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

}
