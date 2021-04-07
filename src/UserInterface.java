import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserInterface extends JFrame {

    UserInterface() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Ludo Game");
        this.setSize(600, 600);
        Panel pan = new Panel();
        this.setContentPane(pan);
        this.setResizable(false);
        this.setVisible(true);
    }
}
