package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Objects;

public class MainView extends JFrame {

    private JPanel mainPanel = new JPanel();
    private JPanel jPanel = new JPanel();

    public MainView() {
    }

    public void showJPanel(JPanel panel) {
        panel.setBackground(Color.lightGray);
        panel.setFocusable(true);
    }

    public void setKeyAdapter(KeyListener keyListener) {
        jPanel.addKeyListener(keyListener);
    }

    public void showFrame() throws IOException {
        showJPanel(jPanel);
        showJPanel(mainPanel);
        mainPanel.add(jPanel);
        this.add(mainPanel);
        this.setTitle("Mines");
        this.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/gameIcon.jpg"))));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public JPanel getjPanel() {
        return jPanel;
    }
}
