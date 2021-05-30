package controller;

import model.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController extends JPanel implements ActionListener {

    private int SCREEN_WIDTH = 300;
    private int SCREEN_HEIGHT = 300;
    private int CELL_SIZE = 25;
    private int UNIT_SIZE = (SCREEN_WIDTH*SCREEN_HEIGHT)/ CELL_SIZE;
    private boolean runnable = false;
    private int numOfMines = 10;
    private Cell cell;


    public GameController() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        startGame();
    }

    public void startGame() {
        runnable = true;
        createCells();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (runnable) {
            for (int i = 0; i < SCREEN_HEIGHT / CELL_SIZE; i++) {
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE);
            }


        }
    }

    public void setMines() {
        
    }

    public void createCells() {
        for (int i = 0; i < (SCREEN_HEIGHT / CELL_SIZE) - 2; i++) {
            for (int j = 0; j < (SCREEN_HEIGHT / CELL_SIZE) - 2; j++) {
                cell = new Cell(CELL_SIZE);
                this.add(cell);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
