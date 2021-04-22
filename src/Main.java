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
            imageNotFound();
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
                isEnd = Board.playerTurn(Board.allPawns[colorTurn.toInt()]);
                colorTurn = colorTurn.next();

                if (isEnd) {
                    // apparition licorne chevauché par poutine avec son sabre spaghetti
                    break;

                }
            }
        }
        return;
    }

    /**
     * Displays a warning message to indicate that a file could not be found and
     * terminates the program returning an error value.
     */
    public static void imageNotFound() {
        String[] options = { "Absolutely not!", "I must admit..." };
        int option = JOptionPane.showOptionDialog(null, "Did you steal our precious images?", "Image not found",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
        if (option == 1) {
            JOptionPane.showMessageDialog(null, "Well, that's too bad cause you can't play now...", "Goodbye",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String str = "The ./img directory must contain the following files :\n\t  die_0.png\n\t  die_1.png\n\t  die_2.png\n\t  die_3.png\n\t  die_4.png\n\t  die_5.png\n\t  die_6.png\n\t  BluePawn.png\n\t  GreenPawn.png\n\t  RedPawn.png\n\t  YellowPawn.png\n\t  ludo_board.png\n";
            JOptionPane.showMessageDialog(null, str, "Image not found", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(1);
    }

}
