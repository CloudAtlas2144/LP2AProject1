import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Class responsible for handling the Graphical User Interface of the game.
 */
public class GamePanel extends JPanel {

    JFrame frame;
    Point mouse = new Point(0, 0);
    final int GP_WIDTH = 600, GP_HEIGHT = 600;
    private int cellW = (int) GP_WIDTH / 15;
    private int cellH = (int) GP_HEIGHT / 15;
    int pSize = cellW;

    private Pawn selectedPawn = null;

    Pawns[] allPawns = { Board.pRed, Board.pBlue, Board.pGreen, Board.pYellow };

    /**
     * Instantiates a new {@code JFrame} window and adds a {@code MouseListener}.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(GP_WIDTH, GP_HEIGHT));

        addMouseListener(new MouseAdapter() {
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
     * {@code Board.mainArray, Board.redArray, Board.blueArray, Board.greenArray}
     * and {@code Board.yelArray}
     * 
     * @param g {@code Graphics} object that will be painted
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image ludo = null;
        try {
            ludo = ImageIO.read(new File("ludo_board.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);

        for (Pawn p : Board.mainArray) {
            getOnMap(p);
            g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
            if (p.isDoubled() != null) {
                g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
            }
        }

        for (Pawn p : Board.redArray) {
            getOnEndMap(p);
            g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
            if (p.isDoubled() != null) {
                g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
            }
        }

        for (Pawn p : Board.blueArray) {
            getOnEndMap(p);
            g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
            if (p.isDoubled() != null) {
                g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
            }
        }

        for (Pawn p : Board.greenArray) {
            getOnEndMap(p);
            g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
            if (p.isDoubled() != null) {
                g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
            }
        }

        for (Pawn p : Board.yelArray) {
            getOnEndMap(p);
            g.drawImage(p.img, p.gLoc.x, p.gLoc.y, pSize, pSize, this);
            if (p.isDoubled() != null) {
                g.drawImage(p.img, p.gLoc.x + 4, p.gLoc.y + 4, pSize, pSize, this);
            }
        }

    }

    // TODO : Optimize to make one function
    /**
     * Determines the x and y coordinates on the window of a pawn belonging to
     * {@code Board.mainArray} and defines the position of its target.
     * 
     * @see Board
     * 
     * @param pawn the pawn to locate
     */
    private void getOnMap(Pawn pawn) {
        if (pawn.getLocation() == -1) {
            // TODO : Handled stored pawns
        } else if (pawn.getLocation() != -1) {
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
            pawn.gLoc.x = x;
            pawn.gLoc.y = y;

            // We create a target on the pawn
            pawn.target = new Rectangle(x, y, cellW, cellW);
        }
    }

    /**
     * Determines the x and y coordinates on the window of a pawn belonging to the
     * color pawn array lists of the {@code Board} class variable and defines the
     * position of its target.
     * 
     * @see Board
     * 
     * @param pawn the pawn to locate
     */
    private void getOnEndMap(Pawn pawn) {
        switch (pawn.getColor().toInt()) {
        case 0:// RED
            pawn.gLoc.x = this.cellW * (1 + pawn.getEndLocation());
            pawn.gLoc.y = this.cellH * 7;
            break;
        case 1:// BLUE
            pawn.gLoc.x = this.cellW * 7;
            pawn.gLoc.y = this.cellH * (13 - pawn.getEndLocation());
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
            System.out.println("Exception : GamePanel.getOnCMap() : Unexpected color value.");
            break;
        }

        pawn.target = new Rectangle(pawn.gLoc.x, pawn.gLoc.y, cellW, cellW);
    }

    /**
     * Verifies if the user has clicked on a pawn of the wanted color. If not, it
     * stops the running {@code Thread} for 30 milliseconds and keeps checking until
     * the conditon is fulfilled.
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
}
