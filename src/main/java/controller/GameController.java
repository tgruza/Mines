package controller;

import model.Cell;
import service.CellService;
import service.CellServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GameController extends JPanel implements ActionListener {

    private ToolBarController toolBarController = new ToolBarController();
    private final CellService cellService;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private final int CELL_SIZE = 25;
    private int numbOfCellsInRow;
    private int numbOfCellsRows;
    private boolean running;
    private int numOfMines;
    private Cell[][] cell;
    private int probability;
    private Random random;
    String cellPressedCoords = "";
    private int numberOfMinesAround;
    private String difficultLevel = "";
    Timer timer = new Timer(0, this);


    public GameController() {
        toolBarController.setNewGameButton(new setNewGameButton());
        toolBarController.setExitMenuButton(new setExitMenuButton());
        toolBarController.setDifficultyButton(new setDifficultyButton());
        toolBarController.setUserSettingsButton(new setUserSettingsButton());

        //setValues();
        cellService = new CellServiceImpl();
        random = new Random();

        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        running = true;
        startValues();
        //deleteCells();
        createCells();
        setMines();
        setNumberOfMinesAround();
        setImagesToCells();
        addListenersAndCells();
        this.revalidate();
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
    }

    public void startValues() {
        if (difficultLevel.isEmpty()) {
            numbOfCellsInRow = 10;
            numbOfCellsRows = 10;
            SCREEN_WIDTH = 300;
            SCREEN_HEIGHT = 300;
            numOfMines = 12;
        }
        if (difficultLevel.equals("easy")) {
            numbOfCellsInRow = 8;
            numbOfCellsRows = 8;
            SCREEN_WIDTH = 250;
            SCREEN_HEIGHT = 250;
            numOfMines = 10;
        }
        if (difficultLevel.equals("medium")) {
            numbOfCellsInRow = 16;
            numbOfCellsRows = 16;
            SCREEN_WIDTH = 450;
            SCREEN_HEIGHT = 450;
            numOfMines = 40;
        }
        if (difficultLevel.equals("hard")) {
            numbOfCellsRows = 16;
            numbOfCellsInRow = 30;
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 450;
            numOfMines = 99;
        }
        this.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        cell = new Cell[numbOfCellsInRow][numbOfCellsRows];
    }

    public void deleteCells() {
        if (cell != null) {
            for (int y = 0; y < numbOfCellsRows; y++) {
                for (int x = 0; x < numbOfCellsInRow; x++) {
                    this.remove(cell[x][y]);
                }
            }
        }
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
                            //cell[x][y - 1].setSelected(true);
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
        running = false;
        timer.stop();
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

    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
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
            JFrame frame = new JFrame("Difficult Level");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(200, 70));
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
            panel1.setOpaque(true);
            JPanel inputpanel = new JPanel();
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
                inputpanel.add(easy);
                inputpanel.add(medium);
                inputpanel.add(hard);
                inputpanel.add(button);

            button.addActionListener(actionEvent -> {
                if (easy.isSelected() || medium.isSelected() || hard.isSelected()) {
                    difficultLevel = group.getSelection().getActionCommand();
                    startGame();
                }
            });

            panel1.add(inputpanel);
            frame.getContentPane().add(BorderLayout.CENTER, panel1);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        }


    }

    class setUserSettingsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!running) {
                startGame();
            }
        }
    }

}
