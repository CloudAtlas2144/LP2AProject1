import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Window extends JFrame {
    JPanel panel = new JPanel();

    public Window() {
        this.setTitle("Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 400);

        // // We set a new BorderLayout on the window
        // this.setLayout(new BorderLayout());
        // We add buttons the content panel

        // Add a button the central area
        // this.getContentPane().add(new JButton("CENTER"), BorderLayout.CENTER);
        // // Add a button the northern area
        // this.getContentPane().add(new JButton("NORTH"), BorderLayout.NORTH);
        // // Add a button the southern area
        // this.getContentPane().add(new JButton("SOUTH"), BorderLayout.SOUTH);
        // // Add a button the western area
        // this.getContentPane().add(new JButton("WEST"), BorderLayout.WEST);
        // // Add a button the eastern area
        // this.getContentPane().add(new JButton("EAST"), BorderLayout.EAST);

        this.setLayout(new GridLayout(3, 2));
        this.getContentPane().add(new JButton("Upper Left"));
        this.getContentPane().add(new JButton("Upper Right"));
        this.getContentPane().add(new JButton("Middle Left"));
        this.getContentPane().add(new JButton("Middle Right"));
        this.getContentPane().add(new JButton("Lower Left"));
        this.getContentPane().add(new JButton("Lower Right"));

        this.setVisible(true);
    }
}