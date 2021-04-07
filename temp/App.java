import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App extends JFrame {

    Color color = Color.BLUE;
    JPanel panel = new JPanel();
    JPanel grid = new JPanel();
    JLabel label = new JLabel("écris ce que tu veux");
    JLabel label11 = new JLabel("11");
    JLabel label12 = new JLabel("12");
    JLabel label13 = new JLabel("13");
    JLabel label21 = new JLabel("21");
    JLabel label22 = new JLabel("22");
    JLabel label23 = new JLabel("23");
    JLabel label31 = new JLabel("31");
    JLabel label32 = new JLabel("32");
    JLabel label33 = new JLabel("33");

    public App() {

        this.setTitle("Morpion");
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid.setLayout(new GridLayout(3, 3));
        grid.setPreferredSize(new Dimension(300, 230));
        grid.setBackground(Color.white);
        grid.add(label11);
        grid.add(label12);
        grid.add(label13);
        grid.add(label21);
        grid.add(label22);
        grid.add(label23);
        grid.add(label31);
        grid.add(label32);
        grid.add(label33);

        label.setPreferredSize(new Dimension(300, 50));
        label.setForeground(Color.white);

        this.getContentPane().add(grid, BorderLayout.NORTH);
        this.getContentPane().add(label, BorderLayout.SOUTH);
        this.getContentPane().setBackground(Color.black);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        App fen = new App();
    }
}