import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel {

    // We override the JPanel.paintComponent
    public void paintComponent(Graphics g) {
        try {
            // We read the file and turn it into a usable image
            Image img = ImageIO.read(new File("ludo_board1.png"));
            // We display the image and set JPanel as the image observer
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            // We can also not specify the image's display dimensions :
            // g.drawImage(img, 0, 0, this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // // this.setBackground(Color.ORANGE);
        // // Sets the color for the next shapes to draw
        // g.setColor(Color.GREEN.darker());
        // // Creates and fills and oval shape
        // g.fillOval(this.getWidth() / 2 - 50, this.getHeight() / 2 - 50, 100, 100);
        // g.drawRect(this.getWidth() / 2 - 50, this.getHeight() / 2 - 50, 100, 100);
        // g.setColor(Color.BLUE.brighter());
        // g.setColor(Color.WHITE);
        // g.drawString("This is the ludo game", 130, 30);

    }

}
