import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    // Instantiation of this class will create a new window
    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Adds a title to the window
        this.setTitle("Test");
        // Resize the window
        this.setSize(600, 600);
        // Open the window at this location on screen
        // this.setLocation(400, 200);
        // Center the window on the screen
        this.setLocationRelativeTo(null);
        // Disable window resizing
        this.setResizable(false);
        // Keep the window on top no matter what
        this.setAlwaysOnTop(false);
        // Disable frame and buttons
        // this.setUndecorated(true);

        Panel circle = new Panel();

        this.setContentPane(circle);

        // Create a panel to add to the window
        // JPanel pan = new JPanel();
        // Set a background color
        // pan.setBackground(Color.ORANGE);
        // Associate the panel to our window
        // this.setContentPane(pan);

        // Show the window
        this.setVisible(true);
    }
}
