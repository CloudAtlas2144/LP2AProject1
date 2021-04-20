import javax.swing.*;

public class Dialogs {
    public static void main(String[] args) {
        JOptionPane jop1 = new JOptionPane();
        ImageIcon die = new ImageIcon("img/die_unknown.png");
        JOptionPane.showMessageDialog(null, "Message informatif", "Information", JOptionPane.INFORMATION_MESSAGE, die);
        int option = JOptionPane.showConfirmDialog(null, "En êtes vous vraiment sûr?", "Oh Hello there",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, die);
        switch (option) {
        case JOptionPane.YES_OPTION:
            System.out.println("Hello");
            break;
        default:
            break;
        }

        String[] options = { "Roll for me...", "Re-roll" };
        option = JOptionPane.showOptionDialog(null, "Roll the dice", "Die", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, die, options, options[2]);

    }

}
