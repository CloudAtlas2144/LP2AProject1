import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Class responsible for handling the {@code JFrame} displaying the board of the
 * game as well as the pawns. This class also handles the mouse interactions
 * with the pawns. Extends {@code JPanel}.
 */
public class GamePanel extends JPanel {

    /** Window that displays the game board and the pawns. */
    private JFrame frame;
    /** {@code Point} containing the position of the user's mouse. */
    private Point mouse = new Point(0, 0);
    /** Width and height of the {@code GamePanel}. */
    private final int GP_WIDTH = 600, GP_HEIGHT = 600;
    /** Width of a cell in the window. */
    private int cellW = (int) GP_WIDTH / 15;
    /** Height of a cell in the window. */
    private int cellH = (int) GP_HEIGHT / 15;
    /** Size of a pawn in the window. */
    private int pSize = cellW;

    /** Contains a reference to the last pawn clicked by the user. */
    private Pawn selectedPawn = null;
    /** Groups all the {@code Pawns} in one variable */
    private Pawns[] allPawns = Board.getAllPawns();
    /** Contains the position of the stored pawns in their base. */
    private Pawn[][] storedPawns = new Pawn[4][4];

    /**
     * Instantiates a new {@code JFrame} window and adds a {@code MouseListener}.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(GP_WIDTH, GP_HEIGHT));

        addMouseListener(new MouseAdapter() {
            /**
             * Executes the contained code each time the panel is clicked. If the user
             * clicked on a {@code Pawn} we assign the {@code Pawn} that was clicked to the
             * {@code selectedPawn} variable.
             */
            public void mouseReleased(MouseEvent mouseEvent) {
                mouse = mouseEvent.getPoint();

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        Rectangle target = allPawns[i].pawns[j].getTarget();
                        if (target.contains(mouse)) {
                            selectedPawn = allPawns[i].pawns[j];
                            waitForSelection(false);
                        }
                    }
                }
            }
        });

        frame = new JFrame("Ludo Game");
        frame.add(this);
        frame.pack();
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
                Board.getInfoPanel().getFrame().setState(JFrame.ICONIFIED);
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Board.getInfoPanel().getFrame().setState(JFrame.NORMAL);
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
     * Paints the ludo game board on the screen as well as the pawns contained in
     * {@code allPawns}.
     * 
     * @param g {@code Graphics} graphical context used to paint the objects
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image ludo = null;
        try {
            ludo = ImageIO.read(new File("img/ludo_board.png"));
        } catch (IOException exception) {
            frame.dispose();
            Main.imageNotFound();
        }

        g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Pawn p = allPawns[i].pawns[j];

                // If the pawn is not carried by a double pawn, that is, the pawn is not
                // contained in the isDoubled field of another pawn, we display it
                if (p.getLocation() != -10) {
                    getOnMap(p);
                    g.drawImage(p.getImg(), p.getgLoc().x, p.getgLoc().y, pSize, pSize, this);
                    if (p.isDoubled() != null) {
                        g.drawImage(p.getImg(), p.getgLoc().x + 4, p.getgLoc().y + 4, pSize, pSize, this);
                    }
                } else {
                    p.setTarget(1, 1, -10, -10);
                }
            }
        }
    }

    /**
     * Determines the x and y coordinates on the {@code JPanel} of a pawn belonging
     * to {@code allPawns[]} and defines the position of its target. If the pawn has
     * finished, its target is made unreachable
     * 
     * @param pawn the pawn to locate
     */
    private void getOnMap(Pawn pawn) {

        // The pawn is on its final line
        if (pawn.getEndLocation() != -1 && pawn.getEndLocation() != 6) {
            switch (pawn.getColor()) {
            case BLUE:
                pawn.getgLoc().x = this.cellW * 7;
                pawn.getgLoc().y = this.cellH * (13 - pawn.getEndLocation());
                break;
            case RED:
                pawn.getgLoc().x = this.cellW * (1 + pawn.getEndLocation());
                pawn.getgLoc().y = this.cellH * 7;
                break;
            case GREEN:
                pawn.getgLoc().x = this.cellW * 7;
                pawn.getgLoc().y = this.cellH * (1 + pawn.getEndLocation());
                break;
            case YELLOW:
                pawn.getgLoc().x = this.cellW * (13 - pawn.getEndLocation());
                pawn.getgLoc().y = this.cellH * 7;
                break;
            default:
                System.out.println("Exception : GamePanel.getOnMap() : Unexpected color value.");
                break;
            }

            pawn.setTarget(pawn.getgLoc().x, pawn.getgLoc().y, cellW, cellW);

        } // The pawn has finished
        else if (pawn.getEndLocation() == 6) {
            switch (pawn.getColor()) {
            case BLUE:
                pawn.getgLoc().x = this.cellW * 7;
                pawn.getgLoc().y = this.cellH * 8;
                break;
            case RED:
                pawn.getgLoc().x = this.cellW * 6;
                pawn.getgLoc().y = this.cellH * 7;
                break;
            case GREEN:
                pawn.getgLoc().x = this.cellW * 7;
                pawn.getgLoc().y = this.cellH * 6;
                break;
            case YELLOW:
                pawn.getgLoc().x = this.cellW * 8;
                pawn.getgLoc().y = this.cellH * 7;
                break;
            default:
                System.out.println("Exception : GamePanel.getOnMap() : Unexpected color value.");
                break;
            }
            pawn.setTarget(-10, -10, 1, 1);
        } // The pawn is in its base
        else if (pawn.getLocation() == -1) {
            int x = 0, y = 0;

            switch (pawn.getColor()) {
            case BLUE:
                x = (int) (cellW * 1.5);
                y = (int) (cellH * 10.5);
                break;
            case RED:
                x = (int) (cellW * 1.5);
                y = (int) (cellH * 1.5);
                break;
            case GREEN:
                x = (int) (cellW * 10.5);
                y = (int) (cellH * 1.5);
                break;
            case YELLOW:
                x = (int) (cellW * 10.5);
                y = (int) (cellH * 10.5);
                break;
            }

            int index = storePawn(pawn);

            switch (index) {
            case 0:
                break;
            case 1:
                x += cellW * 2;
                break;
            case 2:
                y += cellH * 2;
                break;
            case 3:
                x += cellW * 2;
                y += cellH * 2;
                break;
            }
            // We set the graphical location of the pawn
            pawn.getgLoc().x = x;
            pawn.getgLoc().y = y;

            // We create a target on the pawn
            pawn.setTarget(x, y, cellW, cellW);

        } // The pawn is on the board but not on its final line
        else if (pawn.getLocation() != -1) {
            int x = 0;
            int y = 0;

            int location = pawn.getLocation();

            if (location < 5) {
                x = this.cellW * 6;
                y = this.cellH * (13 - location);
            } else if (location < 11) {
                x = this.cellW * (10 - location);
                y = this.cellH * 8;
            } else if (location < 13) {
                x = 0;
                y = this.cellH * (18 - location);
            } else if (location < 18) {
                x = this.cellW * (location - 12);
                y = this.cellH * 6;
            } else if (location < 24) {
                x = this.cellW * 6;
                y = this.cellH * (23 - location);
            } else if (location < 26) {
                x = this.cellW * (location - 17);
                y = 0;
            } else if (location < 31) {
                x = this.cellW * 8;
                y = this.cellH * (location - 25);
            } else if (location < 37) {
                x = this.cellW * (location - 22);
                y = this.cellH * 6;
            } else if (location < 39) {
                x = this.cellW * 14;
                y = this.cellH * (location - 30);
            } else if (location < 44) {
                x = this.cellW * (52 - location);
                y = this.cellH * 8;
            } else if (location < 50) {
                x = this.cellW * 8;
                y = this.cellH * (location - 35);
            } else if (location < 52) {
                x = this.cellW * (57 - location);
                y = this.cellH * 14;
            }

            // We set the graphical location of the pawn
            pawn.getgLoc().x = x;
            pawn.getgLoc().y = y;

            // We create a target on the pawn
            pawn.setTarget(x, y, cellW, cellW);
        }
    }

    /**
     * Uses {@code Thread} synchronization to either stop or revive the threads
     * running this function.
     * </p>
     * In practice this function is used to wait for the selection of the pawn by
     * the user.
     * 
     * @param stop If {@code true}, stops the running thread, if {@code false},
     *             revives all threads stuck on that function
     */
    private synchronized void waitForSelection(boolean stop) {
        if (stop) {
            try {
                wait();
            } catch (InterruptedException exception) {
                frame.dispose();
                Main.unexpectedError();
            }
        } else {
            notifyAll();
        }

    }

    /**
     * Verifies if the user has clicked on a pawn of the wanted color. If not, calls
     * {@code waitForSelection()} to wait until a new pawn is clicked.
     * 
     * @param color {@code Color} of the pawn to find
     * @return the pawn selected by the user
     */
    public Pawn getSelectedPawn(Color color) {
        boolean loop = true;
        Pawn pawnFound = null;
        do {
            if (selectedPawn != null) {
                if (selectedPawn.getColor() == color) {
                    loop = false;
                    pawnFound = selectedPawn;
                    selectedPawn = null;
                } else {
                    selectedPawn = null;
                }
            } else {
                waitForSelection(true);
            }
        } while (loop);
        return pawnFound;
    }

    /**
     * Determines the position of a given pawn in its storage zone, represented by
     * its index in the {@code storedPawns} array. If the pawn is not in the array
     * yet, it will be added to it.
     * 
     * @param p {@code Pawn} to find in the array
     * @return an integer corresponding to the index of the pawn in the array
     */
    private int storePawn(Pawn p) {
        int freePosition = 0;
        for (int i = 0; i < 4; i++) {
            if (storedPawns[p.getColor().toInt()][i] == p) {
                return i;
            } else if (storedPawns[p.getColor().toInt()][i] == null) {
                freePosition = i;
            }
        }
        storedPawns[p.getColor().toInt()][freePosition] = p;
        return freePosition;
    }

    /**
     * Removes a pawn from the storage zone represented by the {@code storedPawns}
     * array.
     * 
     * @param p {@code Pawn} to remove from the storage zone
     */
    public void unstorePawn(Pawn p) {
        int i = 0;
        boolean found = false;
        while (found == false) {
            if (storedPawns[p.getColor().toInt()][i] == p) {
                storedPawns[p.getColor().toInt()][i] = null;
                found = true;
            } else {
                i++;
            }
        }
        return;
    }
}
