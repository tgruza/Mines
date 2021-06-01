package service;

import model.Cell;
import model.Icons;

import javax.swing.*;

public class CellServiceImpl implements CellService {
    Icons icons = new Icons();

    @Override
    public void setMineCellsIcon(Cell cell) {
       cell.setIcon(new ImageIcon(icons.getButtonImage()));
       cell.setDisabledSelectedIcon(new ImageIcon(icons.getMineImage()));

    }

    @Override
    public void setSafeCellsIcon(Cell cell, int numberOfMines) {
        cell.setIcon(new ImageIcon(icons.getButtonImage()));
        cell.setDisabledSelectedIcon(new ImageIcon(icons.getNumberIcons(numberOfMines)));
    }
}
