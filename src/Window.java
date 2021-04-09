import javax.swing.JFrame;

public class Window extends JFrame {

    // serial version number requested by Java
    private static final long serialVersionUID = 1L;

    private Panel pan;

    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Ludo Game");
        this.setSize(600, 600);
        this.setAlwaysOnTop(true);

        this.pan = new Panel();
        this.setContentPane(pan);
        this.pan.repaint();

        this.setResizable(false);
        this.setVisible(true);
    }

    public Panel getPanel() {
        return this.pan;
    }
}
