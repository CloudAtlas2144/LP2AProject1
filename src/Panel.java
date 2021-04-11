import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Panel extends JPanel implements ActionListener {

    // serial version number requested by Java
    private static final long serialVersionUID = 1L;

    private int cellW;
    private int cellH;

    private JPanel container = new JPanel();

    private Coordinates coordinates;
    private static boolean initPanel = false;

    private static int pawn_size;

    private static GUIPawn[][] guiPawns = new GUIPawn[4][4];

    // @SulyvanDal Oui je sais c'est cheum mdr, j'y travaille
    public void InitPanel() {
        this.cellW = this.getWidth() / 15;
        this.cellH = this.getHeight() / 15;
        this.coordinates = new Coordinates(0, 0);

        // RED
        for (int i = 0; i < 4; i++) {
            guiPawns[0][i] = new GUIPawn(pawn_size, Board.pRed.pawns[i]);
        }

        // BLUE
        for (int i = 0; i < 4; i++) {
            guiPawns[1][i] = new GUIPawn(pawn_size, Board.pBlue.pawns[i]);
        }

        // GREEN
        for (int i = 0; i < 4; i++) {
            guiPawns[2][i] = new GUIPawn(pawn_size, Board.pYellow.pawns[i]);
        }

        // YELLOW
        for (int i = 0; i < 4; i++) {
            guiPawns[3][i] = new GUIPawn(pawn_size, Board.pYellow.pawns[i]);
        }
    }

    // TODO : Ajouter la gestion de Home
    public void paintComponent(Graphics g) {
        if (initPanel == false) {
            pawn_size = this.getWidth() / 15;
            InitPanel();
            initPanel = true;
        }
        try {
            Image ludo = ImageIO.read(new File("ludo_board1.png"));
            g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);

            GUIPawn pawnToDraw;

            for (Pawn p : Board.mainArray) {
                if (p.isDoubled() != null) {
                    // TODO : Handle this part
                } else {
                    pawnToDraw = findGUIP(p); // finds the GUIPawn containing the given Pawn
                    pawnToDraw.addActionListener(this); // gives this class access to the events happening to this pawn
                    this.getOnMap(pawnToDraw.getPawn().getLocation()); // stores the graphical coordinates of the pawn
                                                                       // in Coordinates
                    this.add(pawnToDraw);
                    pawnToDraw.setLocation(this.coordinates.getX(), this.coordinates.getY());
                }
            }

            for (Pawn p : Board.redArray) {
                int x = this.cellW * (1 + p.getLocation());
                int y = this.cellH * 7;
            }

            for (Pawn p : Board.blueArray) {
                int x = this.cellW * 7;
                int y = this.cellH * (13 - p.getLocation());

            }

            for (Pawn p : Board.greenArray) {
                int x = this.cellW * 7;
                int y = this.cellH * (1 + p.getLocation());

            }

            for (Pawn p : Board.yelArray) {
                int x = this.cellW * (13 - p.getLocation());
                int y = this.cellH * 7;

            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent arg0) {
        // TODO : return the pawn on which the user has clicked
    }

    // Returns the position on the main map of the pawn depending
    // upon its position offset relative to the blue entry point

    // TODO : Trouver une solution moins cheum
    private void getOnMap(int location) {
        int x = 0;
        int y = 0;

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

        this.coordinates.setX(x);
        this.coordinates.setY(y);

    }

    private static GUIPawn findGUIP(Pawn pawn) {
        for (int i = 0; i < 4; i++) {
            if (guiPawns[pawn.getColor().toInt()][i].getPawn() == pawn) {
                System.out.println("findGUIP() recognized the pawn.");
                return guiPawns[pawn.getColor().toInt()][i];
            }
        }
        System.out.println("findGUIP() could not recognize the given pawn.");
        return new GUIPawn(pawn_size, pawn);
    }
}
