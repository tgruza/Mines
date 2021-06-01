package service;

import model.Cell;
import model.Icons;

import javax.swing.*;
import java.awt.*;

public class CellServiceImpl implements CellService {
    Icons icons = new Icons();

    @Override
    public void setMineCells(Cell cell) {
       cell.setIcon(new ImageIcon(icons.getButtonImage()));
       cell.setForeground(Color.white);
       cell.setSelectedIcon(new ImageIcon(icons.getMineImage()));

    }

    @Override
    public void setSafeCells(Cell cell, int numberOfMines) {
        cell.setIcon(new ImageIcon(icons.getButtonImage()));
        cell.setForeground(Color.white);
        cell.setSelectedIcon(new ImageIcon(icons.getNumberIcons(numberOfMines)));
    }
}
