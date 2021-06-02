package service;

import model.Cell;

public interface CellService {
    void setMineCellsIcon(Cell cell);
    void setSafeCellsIcon(Cell cell, int numberOfMines);
    void setFlagCellIcon(Cell cell);
}
