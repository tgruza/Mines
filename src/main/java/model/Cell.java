package model;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    public Cell(int CELL_SIZE) {
        this.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.setBorder(null);
        this.setBackground(Color.LIGHT_GRAY);
    }
}
