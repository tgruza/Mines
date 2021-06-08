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

    @Override
    public void setFlagCellIcon(Cell cell) {
        cell.setIcon(new ImageIcon(icons.getFlagIcon()));
    }

    @Override
    public void setButtonCellIcon(Cell cell) {
        cell.setIcon(new ImageIcon(icons.getButtonImage()));
    }

    @Override
    public void setSunIcon(JButton jButton, char status) {
        jButton.setIcon(new ImageIcon(icons.getSunIcon(status)));
    }
}
