package model;

import javax.swing.*;

public class CellModel extends JToggleButton.ToggleButtonModel {

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