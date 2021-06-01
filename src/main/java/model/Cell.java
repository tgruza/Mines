package model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Cell extends JToggleButton {
    private int CELL_SIZE;
    private int COORD_X;
    private int COORD_Y;
    private boolean mine;

    public Cell(int CELL_SIZE, int COORD_X, int COORD_Y) {
        this.CELL_SIZE = CELL_SIZE;
        this.COORD_X = COORD_X;
        this.COORD_Y = COORD_Y;
        this.setName(COORD_X + " " + COORD_Y);
        this.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.setAlignmentX(500);
        this.setBorder(null);
        this.setModel(new CellModel());
        this.setText(COORD_X + " " + COORD_Y);
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getCOORD_X() {
        return COORD_X;
    }

    public int getCOORD_Y() {
        return COORD_Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return CELL_SIZE == cell.CELL_SIZE &&
                COORD_X == cell.COORD_X &&
                COORD_Y == cell.COORD_Y &&
                mine == cell.mine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(CELL_SIZE, COORD_X, COORD_Y, mine);
    }

    public void reset() {
        super.setSelected(false);
    }

    @Override
    public void setSelected(boolean b) {
        if (!isSelected()) {
            super.setSelected(b);
        }
    }
}
