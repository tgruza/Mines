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
    private final int numbOfCellsInRow = (SCREEN_WIDTH / CELL_SIZE) - 2;
    private final int numbOfCellsRows = (SCREEN_HEIGHT / CELL_SIZE) - 2;
    private boolean runnable = false;
    private int numOfMines = 12;
    private Cell[][] cell = new Cell[numbOfCellsRows][numbOfCellsInRow];
    private int probability;
    private Random random;
    String cellPressedCoords = "";
    private int numberOfMinesAround;


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
        setMines();
        setNumberOfMinesAround();
        setImagesToCells();
        addListenersAndCells();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
//        if (runnable) {
//            for (int i = 0; i < SCREEN_HEIGHT / CELL_SIZE; i++) {
//                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE);
//            }
//
//        }
    }

    public void createCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y] = new Cell(CELL_SIZE, x * CELL_SIZE, y * CELL_SIZE);
            }
        }
    }

    public void setMines() {
        int x;
        int y;
        for (int i = 0; i < numOfMines; i++) {
            x = random.nextInt(numbOfCellsInRow);
            y = random.nextInt(numbOfCellsRows);
            probability = random.nextInt(15);
            cell[x][y].setMine(true);
        }
    }

    public void setNumberOfMinesAround() {
        numberOfMinesAround = 0;

        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                if (y > 0) {
                    if (cell[x][y - 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x < (numbOfCellsInRow - 1) && y > 0) {
                    if (cell[x + 1][y - 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x < (numbOfCellsInRow - 1)) {
                    if (cell[x + 1][y].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x < (numbOfCellsInRow - 1) && y < (numbOfCellsRows - 1)) {
                    if (cell[x + 1][y + 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (y < (numbOfCellsRows - 1)) {
                    if (cell[x][y + 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x > 0 && y < (numbOfCellsRows - 1)) {
                    if (cell[x - 1][y + 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x > 0) {
                    if (cell[x - 1][y].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                if (x > 0 && y > 0) {
                    if (cell[x - 1][y - 1].isMine()) {
                        numberOfMinesAround++;
                    }
                }
                cell[x][y].setNumberOfMinesAround(numberOfMinesAround);
                numberOfMinesAround = 0;
            }
        }
    }

    public void setImagesToCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                if (cell[x][y].isMine()) {
                    cellService.setMineCellsIcon(cell[x][y]);
                } if (!cell[x][y].isMine()) {
                    cellService.setSafeCellsIcon(cell[x][y], cell[x][y].getNumberOfMinesAround());
                }
            }
        }

    }

    public void addListenersAndCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y].addActionListener(this);
                this.add(cell[x][y]);
            }
        }
    }

    public void checkCell(String cellPressed) {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                if (cell[x][y].getActionCommand().equals(cellPressed) && !cell[x][y].isMine()) {
                    if (cell[x][y].getNumberOfMinesAround() == 0) {
                        cell[x][y].setEnabled(false);
                        //up
                        if (y > 0) {
                            cell[x][y - 1].setSelected(true);
                            if (cell[x][y - 1].getNumberOfMinesAround() == 0 && cell[x][y - 1].isEnabled()) {
                                checkCell(cell[x][y - 1].getActionCommand());
                            }
                            cell[x][y - 1].setEnabled(false);
                        }
                        //up/right
                        if (x < (numbOfCellsInRow - 1) && y > 0) {
                            cell[x + 1][y - 1].setSelected(true);
                            if (cell[x + 1][y - 1].getNumberOfMinesAround() == 0 && cell[x + 1][y - 1].isEnabled()) {
                                checkCell(cell[x + 1][y - 1].getActionCommand());
                            }
                            cell[x + 1][y - 1].setEnabled(false);
                        }
                        //right
                        if (x < (numbOfCellsInRow - 1)) {
                            cell[x + 1][y].setSelected(true);
                            if (cell[x + 1][y].getNumberOfMinesAround() == 0 && cell[x + 1][y].isEnabled()) {
                                checkCell(cell[x + 1][y].getActionCommand());
                            }
                            cell[x + 1][y].setEnabled(false);
                        }
                        //right/down
                        if (x < (numbOfCellsInRow - 1) && y < (numbOfCellsRows - 1)) {
                            cell[x + 1][y + 1].setSelected(true);
                            if (cell[x + 1][y + 1].getNumberOfMinesAround() == 0 && cell[x + 1][y + 1].isEnabled()) {
                                checkCell(cell[x + 1][y + 1].getActionCommand());
                            }
                            cell[x + 1][y + 1].setEnabled(false);
                        }
                        //down
                        if (y < (numbOfCellsRows - 1)) {
                            cell[x][y + 1].setSelected(true);
                            if (cell[x][y + 1].getNumberOfMinesAround() == 0 && cell[x][y + 1].isEnabled()) {
                                checkCell(cell[x][y + 1].getActionCommand());
                            }
                            cell[x][y + 1].setEnabled(false);
                        }
                        //down/left
                        if (x > 0 && y < (numbOfCellsRows - 1)) {
                            cell[x - 1][y + 1].setSelected(true);
                            if (cell[x - 1][y + 1].getNumberOfMinesAround() == 0 && cell[x - 1][y + 1].isEnabled()) {
                                checkCell(cell[x - 1][y + 1].getActionCommand());
                            }
                            cell[x - 1][y + 1].setEnabled(false);
                        }
                        //left
                        if (x > 0) {
                            cell[x - 1][y].setSelected(true);
                            if (cell[x - 1][y].getNumberOfMinesAround() == 0 && cell[x - 1][y].isEnabled()) {
                                checkCell(cell[x - 1][y].getActionCommand());
                            }
                            cell[x - 1][y].setEnabled(false);
                        }
                        //left/up
                        if (x > 0 && y > 0) {
                            cell[x - 1][y - 1].setSelected(true);
                            if (cell[x - 1][y - 1].getNumberOfMinesAround() == 0 && cell[x - 1][y - 1].isEnabled()) {
                                checkCell(cell[x - 1][y - 1].getActionCommand());
                            }
                            cell[x - 1][y - 1].setEnabled(false);
                        }
                    }
                    if (cell[x][y].getNumberOfMinesAround() != 0) {
                        cell[x][y].setEnabled(false);
                    }
                    break;
                }
                if (cell[x][y].getActionCommand().equals(cellPressed) && cell[x][y].isMine()) {
                    gameOver();
                    break;
                }
            }
        }
    }

    public void gameOver() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y].setSelected(true);
                cell[x][y].setEnabled(false);
            }
        }
        runnable = false;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        cellPressedCoords = actionEvent.getActionCommand();
        checkCell(cellPressedCoords);
    }
}
