import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Panel extends JPanel {

    // serial version number requested by Java
    private static final long serialVersionUID = 1L;

    private int cellW;
    private int cellH;

    private Coordinates coordinates;
    private static boolean initPanel = false;

    // @SulyvanDal Oui je sais c'est cheum mdr, j'y travaille
    public void InitPanel() {
        this.cellW = this.getWidth() / 15;
        this.cellH = this.getHeight() / 15;
        this.coordinates = new Coordinates(0, 0);
    }

    // TODO : Ajouter la gestion de Home
    public void paintComponent(Graphics g) {
        if (initPanel == false) {
            InitPanel();
            initPanel = true;
        }
        try {
            Image ludo = ImageIO.read(new File("ludo_board1.png"));
            g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);

            int pawn_size = this.getWidth() / 15;
            System.out.println(this.getWidth());

            Image rp = ImageIO.read(new File("RedPawn.png"));
            Image bp = ImageIO.read(new File("BluePawn.png"));
            Image gp = ImageIO.read(new File("GreenPawn.png"));
            Image yp = ImageIO.read(new File("YellowPawn.png"));

            Image[] pawnImages = { rp, bp, gp, yp };

            for (Pawn p : Board.mainArray) {
                if (p.isDoubled() == null) {
                    this.getOnMap(p.getLocation());
                    g.drawImage(pawnImages[p.getColor().toInt()], this.coordinates.getX(), this.coordinates.getY(),
                            pawn_size, pawn_size, this);
                    g.drawImage(pawnImages[p.getColor().toInt()], this.coordinates.getX() + 4,
                            this.coordinates.getY() + 4, pawn_size, pawn_size, this);
                } else {
                    this.getOnMap(p.getLocation());
                    g.drawImage(pawnImages[p.getColor().toInt()], this.coordinates.getX(), this.coordinates.getY(),
                            pawn_size, pawn_size, this);
                }
            }

            for (Pawn p : Board.redArray) {
                int x = this.cellW * (1 + p.getLocation());
                int y = this.cellH * 7;
                if (p.isDoubled() == null) {
                    g.drawImage(rp, x, y, pawn_size, pawn_size, this);
                    g.drawImage(rp, x + 4, y + 4, pawn_size, pawn_size, this);
                } else {
                    g.drawImage(rp, x, y, pawn_size, pawn_size, this);
                }
            }

            for (Pawn p : Board.blueArray) {
                int x = this.cellW * 7;
                int y = this.cellH * (13 - p.getLocation());
                if (p.isDoubled() == null) {
                    g.drawImage(bp, x, y, pawn_size, pawn_size, this);
                    g.drawImage(bp, x + 4, y + 4, pawn_size, pawn_size, this);
                } else {
                    g.drawImage(bp, x, y, pawn_size, pawn_size, this);
                }
            }

            for (Pawn p : Board.greenArray) {
                int x = this.cellW * 7;
                int y = this.cellH * (1 + p.getLocation());
                if (p.isDoubled() == null) {
                    g.drawImage(gp, x, y, pawn_size, pawn_size, this);
                    g.drawImage(gp, x + 4, y + 4, pawn_size, pawn_size, this);
                } else {
                    g.drawImage(gp, x, y, pawn_size, pawn_size, this);
                }
            }

            for (Pawn p : Board.yelArray) {
                int x = this.cellW * (13 - p.getLocation());
                int y = this.cellH * 7;
                if (p.isDoubled() == null) {
                    g.drawImage(yp, x, y, pawn_size, pawn_size, this);
                    g.drawImage(yp, x + 4, y + 4, pawn_size, pawn_size, this);
                } else {
                    g.drawImage(yp, x, y, pawn_size, pawn_size, this);
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
}
