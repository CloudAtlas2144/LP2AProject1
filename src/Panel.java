import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Panel extends JPanel {

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

    public void paintComponent(Graphics g) {
        if (initPanel == false) {
            InitPanel();
            initPanel = true;
        }
        try {
            // We read the file and turn it into a usable image
            Image ludo = ImageIO.read(new File("ludo_board1.png"));
            // We display the image and set JPanel as the image observer
            g.drawImage(ludo, 0, 0, this.getWidth(), this.getHeight(), this);
            // We can also not specify the image's display dimensions :
            // g.drawImage(img, 0, 0, this);
            int pawn_size = this.getWidth() / 15;
            System.out.println(this.getWidth());

            Image rp = ImageIO.read(new File("RedPawn.png"));
            Image bp = ImageIO.read(new File("BluePawn.png"));
            Image gp = ImageIO.read(new File("GreenPawn.png"));
            Image yp = ImageIO.read(new File("YellowPawn.png"));

            // Test of the getOnMap() function
            this.getOnMap(0);
            g.drawImage(bp, this.coordinates.getX(), this.coordinates.getY(), pawn_size, pawn_size, this);
            this.getOnMap(4);
            g.drawImage(gp, this.coordinates.getX(), this.coordinates.getY(), pawn_size, pawn_size, this);
            for (int i = 9; i < 52; i++) {
                this.getOnMap(i);
                g.drawImage(gp, this.coordinates.getX(), this.coordinates.getY(), pawn_size, pawn_size, this);
            }

            System.out.println(this.getWidth());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Returns the position on the main map of the pawn depending
    // upon its position offset relative to the blue entry point

    // TODO : Provide a different location depending upon the color of the pawn
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
