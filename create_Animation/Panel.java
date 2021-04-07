import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;

public class Panel extends JPanel {
    private int posX = -50;
    private int posY = -50;

    public void paintComponent(Graphics g) {
        // We repaint the background
        g.setColor(Color.orange);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.red);
        g.fillOval(posX, posY, 50, 50);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
