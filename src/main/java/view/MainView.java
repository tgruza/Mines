package view;

import controller.GameController;

import javax.swing.*;

public class MainView extends JFrame {

    private JMenuBar menuBar;

    public void showMenuBar() {
        menuBar = new JMenuBar();
    }

    public MainView() {
        this.add(new GameController());
        //this.setJMenuBar(menuBar);
        this.setTitle("Mines");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
