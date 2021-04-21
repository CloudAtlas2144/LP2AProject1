import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;

public class InfoPanel implements ActionListener {

    JFrame frame = new JFrame();

    JPanel playerPanel;
    Image pImg = Board.allPawns[0].img;
    String txt_turn_color = "Blue";
    Boolean pawnSelect = false;

    JPanel diePanel;
    private Image[] dieImg = new Image[7];
    private int dieValue = 0;

    // JLabel player_label = new JLabel("Blue player : it's your turn",
    // JLabel.LEFT);

    InfoPanel() {
        frame.setLayout(new GridLayout(2, 0));
        loadDieImages();

        // player_label.setForeground(Color.BLACK);

        playerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(pImg, 20, 20, 60, 60, this);
                g.drawString(txt_turn_color, 100, 45);
                if (pawnSelect) {
                    g.drawString("Select a pawn to play.", 100, 65);
                }
            }
        };

        diePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(dieImg[dieValue], 20, 20, 60, 60, this);

            }
        };
        // player_panel.add(player_label);
        // player_label.repaint(50, 100, 100, 100);
        // player_label.setVerticalTextPosition(50);

        frame.add(playerPanel);
        frame.add(diePanel);
        frame.setSize(300, 400);
        frame.setTitle("Turn manager");
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public void showStartAttempt() {

    }

    public void showTurn(Color color, int dieValue) {
        pawnSelect = true;
        pImg = Board.allPawns[color.toInt()].img;
        txt_turn_color = color.toMixedCase() + " player : it's your turn!";
        this.dieValue = dieValue;

        switch (color) {
        case BLUE:
            break;
        case RED:
            break;
        case GREEN:
            break;
        case YELLOW:
            break;
        default:
            break;
        }
        frame.repaint();
    }

    /**
     * Loads the images of the different sides of the die.
     */
    private void loadDieImages() {
        try {
            for (int i = 0; i < 7; i++) {
                String fileName = String.format("img/die_%d.png", i);
                dieImg[i] = ImageIO.read(new File(fileName));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
