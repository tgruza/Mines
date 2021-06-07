package controller;

import model.Cell;
import service.CellService;
import service.CellServiceImpl;
import view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MainController implements ActionListener {

    private final MainView mainView = new MainView();
    private final ToolBarController toolBarController = new ToolBarController();
    private final CellService cellService;

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int CELL_SIZE = 25;
    private int numbOfCellsInRow;
    private int numbOfCellsRows;
    private boolean running;
    private int numOfMines;
    private Cell[][] cell;
    private Random random;
    String cellPressedCoords = "";
    private int numberOfMinesAround;
    private String difficultLevel = "";


    public MainController() {
        mainView.setKeyAdapter(new MyKeyAdapter());

        toolBarController.setNewGameButton(new MainController.setNewGameButton());
        toolBarController.setExitMenuButton(new MainController.setExitMenuButton());
        toolBarController.setDifficultyButton(new MainController.setDifficultyButton());

        cellService = new CellServiceImpl();
        random = new Random();


        startGame();
    }

    public void startGame() {
        running = true;
        deleteCells();
        startValues();
        createCells();
        setMines();
        checkNumberOfMinesAround();
        setImagesToCells();
        addListenersAndCells();
        mainView.pack();
    }

    //get "difficult values" that was set by user and set app window and number of mines
    public void startValues() {
        if (difficultLevel.equals("")) {
            numbOfCellsInRow = 10;
            numbOfCellsRows = 10;
            SCREEN_WIDTH = 305;
            SCREEN_HEIGHT = 305;
            numOfMines = 12;
        }
        if (difficultLevel.equals("easy")) {
            numbOfCellsInRow = 8;
            numbOfCellsRows = 8;
            SCREEN_WIDTH = 245;
            SCREEN_HEIGHT = 245;
            numOfMines = 10;
        }
        if (difficultLevel.equals("medium")) {
            numbOfCellsInRow = 16;
            numbOfCellsRows = 16;
            SCREEN_WIDTH = 482;
            SCREEN_HEIGHT = 482;
            numOfMines = 40;
        }
        if (difficultLevel.equals("hard")) {
            numbOfCellsRows = 16;
            numbOfCellsInRow = 30;
            SCREEN_WIDTH = 900;
            SCREEN_HEIGHT = 482;
            numOfMines = 99;
        }

        mainView.getjPanel().setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        mainView.pack();
        cell = new Cell[numbOfCellsInRow][numbOfCellsRows];
    }

    //delete cells if exists
    public void deleteCells() {
        if (cell != null) {
            for (int y = 0; y < numbOfCellsRows; y++) {
                for (int x = 0; x < numbOfCellsInRow; x++) {
                    mainView.getjPanel().remove(cell[x][y]);
                }
            }
        }
    }

    //add coordinates and size for each cell in array
    public void createCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y] = new Cell(CELL_SIZE, x * CELL_SIZE, y * CELL_SIZE);
            }
        }
    }

    //get random cell and set, or not mine on it
    public void setMines() {
        int x;
        int y;
        for (int i = 0; i < numOfMines; i++) {
            x = random.nextInt(numbOfCellsInRow);
            y = random.nextInt(numbOfCellsRows);

            cell[x][y].setMine(true);
        }
    }

    //check number of mines around the each cell in array
    public void checkNumberOfMinesAround() {
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

    //check if cell is mine and add proper image to it
    public void setImagesToCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                if (cell[x][y].isMine()) {
                    cellService.setMineCellsIcon(cell[x][y]);
                }
                if (!cell[x][y].isMine()) {
                    cellService.setSafeCellsIcon(cell[x][y], cell[x][y].getNumberOfMinesAround());
                }
            }
        }

    }

    //adds listeners to cell and cells to JPanel
    public void addListenersAndCells() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y].addActionListener(this);
                mainView.getjPanel().add(cell[x][y]);
            }
        }
    }

    //check if cell has blind spot nearby and if yes uncover it and check again
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

    //if user clicked on mine uncover all cells, and end game
    public void gameOver() {
        for (int y = 0; y < numbOfCellsRows; y++) {
            for (int x = 0; x < numbOfCellsInRow; x++) {
                cell[x][y].setSelected(true);
                cell[x][y].setEnabled(false);
            }
        }
        running = false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        cellPressedCoords = actionEvent.getActionCommand();
        checkCell(cellPressedCoords);
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!running) {
                    startGame();
                }
            }
        }
    }


    //ToolBar listeners and settings
    public ToolBarController getToolBarController() {
        return toolBarController;
    }

    class setNewGameButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!running) {
                startGame();
            }
        }
    }

    class setExitMenuButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(10);
        }

    }

    class setDifficultyButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            try {
                frame.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/gameIcon.jpg"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JPanel panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(200, 70));
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
            panel1.setOpaque(true);
            JPanel inputPanel = new JPanel();
            JButton button = new JButton("Enter");
            button.setActionCommand("Enter");


            ButtonGroup group = new ButtonGroup();
            JRadioButton easy = new JRadioButton("Easy");
            easy.setActionCommand("easy");
            JRadioButton medium = new JRadioButton("Medium");
            medium.setActionCommand("medium");
            JRadioButton hard = new JRadioButton("Hard");
            hard.setActionCommand("hard");
            group.add(easy);
            group.add(medium);
            group.add(hard);
            inputPanel.add(easy);
            inputPanel.add(medium);
            inputPanel.add(hard);
            inputPanel.add(button);

            button.addActionListener(actionEvent -> {
                if (easy.isSelected() || medium.isSelected() || hard.isSelected()) {
                    difficultLevel = group.getSelection().getActionCommand();
                    startGame();
                    frame.dispose();
                }
            });

            panel1.add(inputPanel);
            frame.getContentPane().add(BorderLayout.CENTER, panel1);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        }


    }

    public void showFrame() throws IOException {
        mainView.setJMenuBar(toolBarController);
        mainView.showFrame();
    }
}
