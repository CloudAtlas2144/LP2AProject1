import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Panel extends JPanel {
    public void paintComponent(Graphics g) {
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
            g.drawImage(bp, (this.getWidth() / 15) * 6, 0, pawn_size, pawn_size, this);
            System.out.println(this.getWidth());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
