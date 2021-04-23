import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 * Class responsible for handling player turn distribution and I/O error
 * handling.
 */
public class Main {
    public static void main(String[] args) {

        // We prompt a pop up message to ask the user to start the game
        ImageIcon die = null;
        try {
            die = new ImageIcon(ImageIO.read(new File("img/die_0.png")).getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        } catch (IOException exception) {
            imageNotFound();
        }
        String[] options = { "Let's go!", "Wait for Granny..." };
        int option = JOptionPane.showOptionDialog(null, "Ready for a new thrilling game?", "Ludo Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, die, options, options[0]);

        // The game begins if the player clicked on "Let's go!"
        if (option == 0) {
            die = null;

            new Board();

            // true if a player has won the game, false otherwise
            boolean isEnd = false;

            // indicates which player has to play
            Color colorTurn = Board.firstToStart();

            while (!isEnd) {
                isEnd = Board.playerTurn(Board.allPawns[colorTurn.toInt()]);
                colorTurn = colorTurn.next();

                if (isEnd) {
                    showWinner(colorTurn);
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
        String[] options = { "Blame Granny!", "I must admit..." };
        int option = JOptionPane.showOptionDialog(null, "Did you steal our precious images?", "Image not found",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
        if (option == 1) {
            JOptionPane.showMessageDialog(null, "Well, that's too bad cause you can't play now...", "Goodbye",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String str = "The ./img directory must contain the following files :\n\t  die_0.png\n\t  die_1.png\n\t  die_2.png\n\t  die_3.png\n\t  die_4.png\n\t  die_5.png\n\t  die_6.png\n\t  BluePawn.png\n\t  GreenPawn.png\n\t  RedPawn.png\n\t  YellowPawn.png\n\t  ludo_board.png\n\t  fireworks.png";
            JOptionPane.showMessageDialog(null, str, "Image not found", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(1);
    }

    /**
     * Displays a message indicating that an internal error has occured and quits
     * the program. This function is called when an {@code InterruptedException} is
     * called.
     */
    public static void unexpectedError() {
        String str = "Unexpected internal error.\nWoops... Looks like Granny crashed into daddy's car again.";
        JOptionPane.showMessageDialog(null, str, "Unexpected error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * Displays the winner of the game.
     * 
     * @param color Color of the player
     */
    private static void showWinner(final Color color) {
        Board.infoPanel.getFrame().dispose();
        JFrame winFrame = new JFrame();
        JPanel winPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponents(g);
                try {
                    Image fireworks = ImageIO.read(new File("img/fireworks.png"));
                    g.drawImage(fireworks, 0, 0, 512, 512, this);
                    String str = color.toCamelCase() + " player : you win!";
                    java.awt.Color awtColor = null;
                    switch (color) {
                    case BLUE:
                        awtColor = java.awt.Color.CYAN;
                        break;
                    case RED:
                        awtColor = java.awt.Color.RED;
                        break;
                    case GREEN:
                        awtColor = java.awt.Color.GREEN;
                        break;
                    case YELLOW:
                        awtColor = java.awt.Color.YELLOW;
                        break;
                    }
                    g.setColor(awtColor);
                    g.setFont(new Font("Courier", Font.BOLD, 32));
                    g.drawString(str, 20, this.getHeight() / 2 - 5);
                } catch (IOException exception) {
                    Main.imageNotFound();
                }
            }
        };
        winPanel.setPreferredSize(new Dimension(512, 512));
        winFrame.add(winPanel);
        winFrame.pack();
        winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winFrame.setLocationRelativeTo(Board.gamePanel);
        winFrame.setVisible(true);
    }
}
