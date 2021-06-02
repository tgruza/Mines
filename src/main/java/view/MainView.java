package view;

import controller.GameController;
import controller.ToolBarController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class MainView extends JFrame {

    public MainView() throws IOException {
        GameController gameController = new GameController();
        this.add(gameController);
        this.setJMenuBar(gameController.getToolBarController());
        this.setTitle("Mines");
        this.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/gameIcon.jpg"))));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
