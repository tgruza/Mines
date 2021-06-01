package controller;

import model.Cell;
import service.CellService;
import service.CellServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameController extends JPanel implements ActionListener {

    private final CellService cellService;
    private final int SCREEN_WIDTH = 300;
    private final int SCREEN_HEIGHT = 300;
    private final int CELL_SIZE = 25;
    private final int UNIT_SIZE = (SCREEN_WIDTH*SCREEN_HEIGHT)/ CELL_SIZE;
    private final int numbOfCellsInRow = (SCREEN_WIDTH / CELL_SIZE);
    private final int numbOfCellsRows = (SCREEN_HEIGHT / CELL_SIZE);
    private final int numOfCells = numbOfCellsInRow * numbOfCellsRows;
    private boolean runnable = false;
    private int numOfMines = 30;
    private Cell[][] cell = new Cell[numbOfCellsRows][numbOfCellsInRow];
    private int probability;
    private Random random;
    String cellPressedCoords = "";
    int CellPressedX;
    int CellPressedY;


    public GameController() {
        cellService = new CellServiceImpl();
        random = new Random();
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

    public void createCells() {
        for (int y = 0; y < numbOfCellsRows - 2; y++) {
            for (int x = 0; x < numbOfCellsInRow - 2; x++) {
                cell[x][y] = new Cell(CELL_SIZE, x * CELL_SIZE, y * CELL_SIZE);
                setMines(cell[x][y]);
                cell[x][y].addActionListener(this);//new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        System.out.println(actionEvent.getActionCommand());
//                       // checkCell();
//                    }
//                });
                if (cell[x][y].isMine()) {
                    numOfMines--;
                    cellService.setMineCells(cell[x][y]);
                } else {
                    cellService.setSafeCells(cell[x][y], 1);
                }
                this.add(cell[x][y]);
            }
            System.out.println();
        }
    }

    public void setMines(Cell cell) {
        if (numOfMines > 0) {
            probability = random.nextInt(15);
            cell.setMine(probability > 10);
        }
    }

    public void setCells(Cell cell) {

    }

    public void checkCell(String cellPressed) {

        for (int y = 0; y < numbOfCellsRows - 2; y++) {
            for (int x = 0; x < numbOfCellsInRow - 2; x++) {
                if (cell[x][y].getText().equals(cellPressed)) {

                    System.out.println("Działa " + cellPressed);
                }
            }
        }
    }

    public void gameOver() {
        System.out.println("Game Over");
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        cellPressedCoords = actionEvent.getActionCommand();
        checkCell(cellPressedCoords);
    }
}
