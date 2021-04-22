import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;

/**
 * Class responsible for handling the {@code JFrame} displaying information
 * about the player turn and the value of the die.
 */
public class InfoPanel {

    /** Window that displays the different elements. */
    private JFrame frame = new JFrame();

    /** Waiting time after a turn pass or a die re-roll. */
    private int waitTime = 2000;

    /** {@code JPanel} displaying information about the player turn. */
    private JPanel playerPanel;
    /** {@code Color} of the current turn. */
    private Color currentColor;
    /** {@code Image} variable containing the icon of the current turn color. */
    private Image pImg = Board.allPawns[0].img;
    /** {@code String} holding information for the player. */
    private String turnText = "";
    /**
     * {@code true} if the player is allowed to select a pawn, {@code false}
     * otherwise.
     */
    private boolean pawnSelect = false;
    /** {@code true} if the turn is about to pass, {@code false} otherwise. */
    private boolean pass = false;

    /** {@code JButton} allowing the user to speed up the game notifications. */
    private JButton autoButton;

    /** {@code JPanel} displaying information about the die. */
    private JPanel diePanel;
    /** {@code Image} array containing the icons of the different die faces. */
    private Image[] dieImg = new Image[7];
    /** Holds the value of the die to display. */
    private int dieValue = 0;
    /** {@code String} telling the user the die value he rolled. */
    private String dieText = "";
    /** Contains the sum of the consecutives die rolls in case of a re-roll. */
    private int dieTotalValue;
    /**
     * {@code true} if the player has to re-roll the die, {@code false} otherwise.
     */
    private boolean reRoll = false;

    /**
     * Sets up a new {@code JFrame} providing information for the player and the
     * value of the die.
     */
    InfoPanel() {
        frame.setLayout(new BorderLayout());
        loadDieImages();

        /** Panel holding the {@code playerPanel} and the {@code diePanel}. */
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
                int offset = 20;
                if (dieTotalValue != 0) {
                    g.drawString("Die total value : " + dieTotalValue, 100, 45 + offset);
                    offset += 20;
                }
                if (reRoll) {
                    g.drawString("Re-rolling...", 100, 45 + offset);

                }
            }
        };

        autoButton = new JButton("I don't have time!");
        autoButton.setFocusable(false);

        autoButton.addMouseListener(new MouseAdapter() {
            /**
             * If the button is clicked we set the value of {@code waitTime} to 0, this has
             * as an effect to skip all animations where no user reaction is required.
             */
            public void mouseReleased(MouseEvent mouseEvent) {
                waitTime = 0;
                autoButton.setEnabled(false);
            }
        });

        frame.add(autoButton, BorderLayout.SOUTH);

        basePanel.add(playerPanel);
        basePanel.add(diePanel);

        frame.add(basePanel);
        frame.setSize(300, 300);
        frame.setTitle("Turn manager");
        frame.setLocationRelativeTo(null);

        // We make sure that both windows have the same behavior if one is minimized or
        // closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                Board.gamePanel.getFrame().setState(JFrame.ICONIFIED);
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Board.gamePanel.getFrame().setState(JFrame.NORMAL);
                super.windowDeiconified(e);
            }

        });

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
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
        turnText = color.toCamelCase() + " player : roll to start!";
        this.dieValue = dieValue;
        dieText = String.format("You rolled a %d!", dieValue);
        autoButton.setEnabled(false);
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
        turnText = color.toCamelCase() + " player starts playing!";
        pImg = Board.allPawns[color.toInt()].img;
        dieValue = 0;
        dieText = "";
        frame.repaint();
        frame.setTitle(color.toCamelCase() + " starts!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        frame.setLocation(Board.gamePanel.getFrame().getWidth() + Board.gamePanel.getFrame().getLocation().x,
                Board.gamePanel.getFrame().getLocation().y);
        autoButton.setEnabled(true);
    }

    /**
     * Shows which player has to play without stoping the running {@code Thread}.
     * 
     * @param color {@code Color} of the player
     */
    public void showTurn(Color color) {
        pImg = Board.allPawns[color.toInt()].img;
        turnText = color.toCamelCase() + " player : it's your turn!";
        currentColor = color;
        // playerPanel.repaint();
        frame.setTitle(color.toCamelCase() + " Turn");
    }

    /**
     * Shows the value of the die for a player turn, refreshes the lower part of the
     * {@code JFrame}, and waits for the number of seconds indicated in
     * {@code waitTime}. If the player has launched several times, it will also
     * display the total value of the die.
     * 
     * @param dieValue      value of the die
     * @param reRoll        indicates if the player has to reroll or not
     * @param dieTotalValue total value of the die
     */
    public void showRoll(int dieValue, boolean reRoll, int dieTotalValue) {
        pawnSelect = !reRoll;
        this.dieValue = dieValue;
        this.reRoll = reRoll;
        dieText = String.format("You rolled a %d!", dieValue);
        if (dieTotalValue > dieValue) {
            this.dieTotalValue = dieTotalValue;
        } else {
            this.dieTotalValue = 0;
        }
        // FIXME
        frame.repaint();
        if (reRoll) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Shows that the turn passes and and waits for the number of seconds indicated
     * in {@code waitTime}.
     */
    public void showPass() {
        pawnSelect = false;
        pass = true;
        // FIXME
        frame.repaint();
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        showTurn(currentColor.next());
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
            Main.imageNotFound();
        }
    }
}
