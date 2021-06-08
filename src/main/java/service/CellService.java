package service;

import model.Cell;

import javax.swing.*;

public interface CellService {
    void setMineCellsIcon(Cell cell);
    void setSafeCellsIcon(Cell cell, int numberOfMines);
    void setFlagCellIcon(Cell cell);
    void setButtonCellIcon(Cell cell);
    void setSunIcon(JButton jButton, char status);
}
