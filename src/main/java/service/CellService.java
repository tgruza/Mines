package service;

import model.Cell;

public interface CellService {
    void setMineCells(Cell cell);
    void setSafeCells(Cell cell, int numberOfMines);
}
