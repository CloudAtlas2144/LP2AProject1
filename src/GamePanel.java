import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Class responsible for handling the Graphical User Interface of the game.
 */
public class GamePanel extends JPanel {

    private JFrame frame;
    private Point mouse = new Point(0, 0);
    final int GP_WIDTH = 600, GP_HEIGHT = 600;
    private int cellW = (int) GP_WIDTH / 15;
    private int cellH = (int) GP_HEIGHT / 15;
    private int pSize = cellW;

    /** Contains a reference to the last pawn clicked by the user. */
    private Pawn selectedPawn = null;
    /** Groups all the {@code Pawns} in one variable */
    private Pawns[] allPawns = { Board.pBlue, Board.pRed, Board.pGreen, Board.pYellow };
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
             * clicked on a {@code Pawn} we assigne the {@code Pawn} that was clicked to the
             * {@code selectedPawn} variable.
             */
            public void mouseReleased(MouseEvent mouseEvent) {
                mouse = mouseEvent.getPoint();

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        Rectangle target = allPawns[i].pawns[j].target;
                        if (target.contains(mouse)) {
                            selectedPawn = allPawns[i].pawns[j];
                        }
                    }
                }
            }
        });

        frame = new JFrame("Ludo Game");
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
            ludo = ImageIO.read(new File("ludo_2.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Pawn p = allPawns[i].pawns[j];
                getOnMap(p);
                g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
                if (p.isDoubled() != null) {
                    g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
                }
            }
        }
    }

    /**
     * Determines the x and y coordinates on the {@code JPanel} of a pawn belonging
     * to {@code allPawns} and defines the position of its target. If the pawn has
     * finished, its target is made unreachable
     * 
     * @see Board
     * 
     * @param pawn the pawn to locate
     */
    private void getOnMap(Pawn pawn) {

        /** The pawn is on its final line */
        if (pawn.getEndLocation() != -1 && pawn.getEndLocation() != 6) {
            switch (pawn.getColor().toInt()) {
            case 0:// BLUE
                pawn.gLoc.x = this.cellW * 7;
                pawn.gLoc.y = this.cellH * (13 - pawn.getEndLocation());
                break;
            case 1:// RED
                pawn.gLoc.x = this.cellW * (1 + pawn.getEndLocation());
                pawn.gLoc.y = this.cellH * 7;
                break;
            case 2:// GREEN
                pawn.gLoc.x = this.cellW * 7;
                pawn.gLoc.y = this.cellH * (1 + pawn.getEndLocation());
                break;
            case 3:// YELLOW
                pawn.gLoc.x = this.cellW * (13 - pawn.getEndLocation());
                pawn.gLoc.y = this.cellH * 7;
                break;
            default:
                System.out.println("Exception : GamePanel.getOnMap() : Unexpected color value.");
                break;
            }

            pawn.target = new Rectangle(pawn.gLoc.x, pawn.gLoc.y, cellW, cellW);

        } /** The pawn has finished */
        else if (pawn.getEndLocation() == 6) {
            switch (pawn.getColor().toInt()) {
            case 0:// BLUE
                pawn.gLoc.x = this.cellW * 7;
                pawn.gLoc.y = this.cellH * 8;
                break;
            case 1:// RED
                pawn.gLoc.x = this.cellW * 6;
                pawn.gLoc.y = this.cellH * 7;
                break;
            case 2:// GREEN
                pawn.gLoc.x = this.cellW * 7;
                pawn.gLoc.y = this.cellH * 6;
                break;
            case 3:// YELLOW
                pawn.gLoc.x = this.cellW * 8;
                pawn.gLoc.y = this.cellH * 7;
                break;
            default:
                System.out.println("Exception : GamePanel.getOnMap() : Unexpected color value.");
                break;
            }
            pawn.target = new Rectangle(-10, -10, 1, 1);
        } /** The pawn is in its base */
        else if (pawn.getLocation() == -1) {
            int x = 0, y = 0;

            switch (pawn.getColor().toInt()) {
            case 0:// BLUE
                x = (int) (cellW * 1.5);
                y = (int) (cellH * 10.5);
                break;
            case 1:// RED
                x = (int) (cellW * 1.5);
                y = (int) (cellH * 1.5);
                break;
            case 2:// GREEN
                x = (int) (cellW * 10.5);
                y = (int) (cellH * 1.5);
                break;
            case 3:// YELLOW
                x = (int) (cellW * 10.5);
                y = (int) (cellH * 10.5);
                break;
            default:
                System.out.println("Exception : GamePanel.getOnMap() : Unexpected color value.");
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
            pawn.gLoc.x = x;
            pawn.gLoc.y = y;

            // We create a target on the pawn
            pawn.target = new Rectangle(x, y, cellW, cellW);

        } /** The pawn is on the board but not on its final line */
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
            } else {
                JOptionPane.showMessageDialog(null, "Unhandled Location", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            // We set the graphical location of the pawn
            pawn.gLoc.x = x;
            pawn.gLoc.y = y;

            // We create a target on the pawn
            pawn.target = new Rectangle(x, y, cellW, cellW);

        }

    }

    /**
     * Verifies if the user has clicked on a pawn of the wanted color. If not, stops
     * the running {@code Thread} for 30 milliseconds and keeps checking until the
     * conditon is fulfilled.
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
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
     * @return an integer corresponding to the index of pawn
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
