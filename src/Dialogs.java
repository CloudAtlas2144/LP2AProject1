import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;

public class Dialogs {
    public static void main(String[] args) {
        // JOptionPane jop1 = new JOptionPane();
        // ImageIcon die_ = new ImageIcon("img/die_unknown.png");
        // JOptionPane.showMessageDialog(null, "Message informatif", "Information",
        // JOptionPane.INFORMATION_MESSAGE, die_);
        // int option = JOptionPane.showConfirmDialog(null, "En êtes vous vraiment
        // sûr?", "Oh Hello there",
        // JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, die_);
        // switch (option) {
        // case JOptionPane.YES_OPTION:
        // System.out.println("Hello");
        // break;
        // default:
        // break;
        // }

        Image[] die = new Image[7];
        try {
            for (int i = 0; i < 7; i++) {
                String fileName = String.format("img/die_%d.png", i);
                die[i] = ImageIO.read(new File(fileName)).getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ImageIcon die2 = new ImageIcon(die[6]);

        String[] options = { "Roll for me...", "Re-roll" };
        int option = JOptionPane.showOptionDialog(null, "Roll the die", "Die", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, die2, options, options[1]);

    }

}
