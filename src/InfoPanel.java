import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;

public class InfoPanel {

    JFrame frame = new JFrame();

    JPanel playerPanel;
    Image pImg = Board.allPawns[0].img;
    String turnText = "Blue";
    boolean pawnSelect = false;
    boolean pass = false;

    JPanel diePanel;
    private Image[] dieImg = new Image[7];
    private int dieValue = 0;
    String dieText = "";
    boolean reRoll = false;

    /**
     * Sets up a new {@code JFrame} providing information for the player and the
     * value of the die.
     */
    InfoPanel() {
        frame.setLayout(new BorderLayout());

        loadDieImages();

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new GridLayout(2, 0));

        playerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(pImg, 20, 20, 60, 60, this);
                g.drawString(turnText, 100, 45);
                if (pawnSelect) {
                    g.drawString("Select a pawn to play.", 100, 65);
                } else if (pass) {
                    g.drawString("Turn passes...", 100, 65);
                }
            }
        };

        diePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(dieImg[dieValue], 20, 20, 60, 60, this);
                g.drawString(dieText, 100, 45);
                if (reRoll) {
                    g.drawString("Re-rolling...", 100, 65);
                }
            }
        };

        JButton autoButton = new JButton("Auto-roll");
        autoButton.setFocusable(false);

        autoButton.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouseEvent) {
                // TODO : add code if auto-roll
            }
        });

        frame.add(autoButton, BorderLayout.SOUTH);
        basePanel.add(playerPanel);
        basePanel.add(diePanel);

        frame.add(basePanel);
        frame.setSize(300, 300);
        frame.setTitle("Turn manager");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Displays which value the player of the given color has gotten at the start
     * roll.
     * 
     * @param color    {@code Color} of the player
     * @param dieValue value of the die rolled by the player
     */
    public void showStartAttempt(Color color, int dieValue) {
        frame.setTitle("First Roll");
        pImg = Board.allPawns[color.toInt()].img;
        turnText = color.toMixedCase() + " player : roll to start!";
        this.dieValue = dieValue;
        dieText = String.format("You rolled a %d!", dieValue);
        frame.repaint();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Displays which player has gotten the highest value at the start roll and thus
     * who starts playing.
     * 
     * @param color {@code Color} of the player
     */
    public void showStartingPlayer(Color color) {
        frame.setTitle(color.toMixedCase() + " starts!");
        turnText = color.toMixedCase() + " player starts playing!";
        pImg = Board.allPawns[color.toInt()].img;
        dieValue = 0;
        dieText = "";
        frame.repaint();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        frame.setLocation(Board.gamePanel.getFrame().getWidth() + Board.gamePanel.getFrame().getLocation().x,
                Board.gamePanel.getFrame().getLocation().y);
    }

    /**
     * Shows which player has to play.
     * 
     * @param color {@code Color} of the player
     */
    public void showTurn(Color color) {
        frame.setTitle(color.toMixedCase() + " Turn");
        pImg = Board.allPawns[color.toInt()].img;
        turnText = color.toMixedCase() + " player : it's your turn!";
        playerPanel.repaint();
    }

    /**
     * Shows the value of the die for a player turn.
     * 
     * @param dieValue value of the die
     * @param reRoll   indicates if the player has to reroll or not
     */
    public void showRoll(int dieValue, boolean reRoll) {
        pawnSelect = !reRoll;
        this.dieValue = dieValue;
        this.reRoll = reRoll;
        dieText = String.format("You rolled a %d!", dieValue);
        diePanel.repaint();
        if (reRoll) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Shows that the turn passes.
     */
    public void showPass() {
        pawnSelect = false;
        pass = true;
        playerPanel.repaint();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Loads the images of the different sides of the die.
     */
    private void loadDieImages() {
        try {
            for (int i = 0; i < 7; i++) {
                String fileName = String.format("img/die_%d.png", i);
                dieImg[i] = ImageIO.read(new File(fileName));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
