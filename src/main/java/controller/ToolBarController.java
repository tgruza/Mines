package controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ToolBarController extends JMenuBar {

    //Menu Buttons
    //Game Menu Buttons
    private JMenu gameButton,
            settingsMenuButton;
    private JMenuItem newGameButton;
    private JMenuItem exitMenuButton;
    //Settings Button
    private JMenuItem difficultyButton;

    public void setNewGameButton(ActionListener actionListener) {
        newGameButton.addActionListener(actionListener);
    }
    public void setExitMenuButton(ActionListener actionListener) {
        exitMenuButton.addActionListener(actionListener);
    }
    public void setDifficultyButton(ActionListener actionListener) {
        difficultyButton.addActionListener(actionListener);
    }


    public ToolBarController() {
        menu();
    }

    public void menu() {
        gameButton = new JMenu("Game");
        gameButton.setMnemonic(KeyEvent.VK_F2);

        this.add(gameButton);

        newGameButton = new JMenuItem("New Game", KeyEvent.VK_ENTER);
        newGameButton.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER, InputEvent.ALT_MASK));
        gameButton.add(newGameButton);

        gameButton.addSeparator();

        exitMenuButton = new JMenuItem("Exit", KeyEvent.VK_E);
        gameButton.add(exitMenuButton);


        //Settings Menu
        settingsMenuButton = new JMenu("Settings");
        settingsMenuButton.setMnemonic(KeyEvent.VK_S);
        this.add(settingsMenuButton);

        difficultyButton = new JMenuItem("Difficult Level", KeyEvent.VK_D);
        difficultyButton.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_D));
        settingsMenuButton.add(difficultyButton);
    }
}
