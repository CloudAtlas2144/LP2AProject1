import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Hashtable;

public class UserInterface extends JFrame {

    private static Hashtable mainT = new Hashtable<Integer, Coordinates>();

    UserInterface() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Ludo Game");
        this.setSize(600, 600);

        Panel pan = new Panel();
        InitMaps(pan);

        this.setContentPane(pan);
        this.setResizable(false);
        this.setVisible(true);
    }

    private static void InitMaps(Panel pan) {
        int cellW = pan.getWidth() / 15;
        int cellH = pan.getHeight() / 15;

        for (int i = 0; i < 6; i++) {
            Coordinates coordinates = new Coordinates(cellW * 6, cellH * 13 - i);
            mainT.put(i, coordinates);
        }
    }

    public static Coordinates getOnMainMap(Integer location) {
        return (Coordinates) mainT.get(location);
    }
}
