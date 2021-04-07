import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends JFrame {
    private Panel pan = new Panel();

    public Window() {
        this.setTitle("Animation");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(pan);
        this.setVisible(true);
        go();
    }

    private void go() {
        // Infinite loop
        while (true) {
            int x = pan.getPosX(), y = pan.getPosY();
            x++;
            y++;
            if (x == this.getWidth() || y == this.getHeight()) {
                x = -50;
                y = -50;
            }
            pan.setPosX(x);
            pan.setPosY(y);

            // Asks to the JPanel to redraw itself by calling paintComponent()
            pan.repaint();
            try {
                // Stops the program for 10 ms (risky function -> try and catch)
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
