import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class GUIPawn extends JButton {

    private static final long serialVersionUID = 1L;

    private Image img;
    private int pawnSize;
    private Pawn boundedPawn;

    // TODO : #DUMB color comes from boundedPawn...
    public GUIPawn(int pawnSize, Pawn boundedPawn) {
        this.pawnSize = pawnSize;
        this.boundedPawn = boundedPawn;
        loadImage();
    }

    // @Override
    protected void paintComponent(Graphics g) {
        // TODO : May have to change Observer
        g.drawImage(img, 0, 0, this.pawnSize, this.pawnSize, this);
        System.out.println(this.pawnSize);
    }

    /**
     * Loads the image corresponding to the pawn's color
     */
    private void loadImage() {
        try {
            switch (this.boundedPawn.getColor().toInt()) {
            case 0:// RED
                img = ImageIO.read(new File("RedPawn.png"));
                break;
            case 1:// BLUE
                img = ImageIO.read(new File("BluePawn.png"));
                break;
            case 2:// GREEN
                img = ImageIO.read(new File("GreenPawn.png"));
                break;
            case 3:// YELLOW
                img = ImageIO.read(new File("YellowPawn.png"));
                break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Pawn getPawn() {
        return this.boundedPawn;
    }
}
