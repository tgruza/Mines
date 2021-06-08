package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class Icons {
    private Image mineIcon;
    private Image numberIcon;
    private Image buttonIcon;
    private Image flagIcon;
    private Image sunIcon;

    public Icons() {
    }

    public Image getButtonImage() {
        try {

            buttonIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icon.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return buttonIcon;
    }

    public Image getMineImage() {
        try {

            mineIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/minem.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mineIcon;
    }

    public Image getNumberIcons(int number) {
        try {
            switch (number) {
                case 0:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/iconPressed.png")));
                    break;
                case 1:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/1m.png")));
                    break;
                case 2:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/2m.png")));
                    break;
                case 3:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/3m.png")));
                    break;
                case 4:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/4m.png")));
                    break;
                case 5:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/5m.png")));
                    break;
                case 6:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/6m.png")));
                    break;
                case 7:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/7m.png")));
                    break;
                case 8:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/8m.png")));
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return numberIcon;
    }

    public Image getSunIcon(char status) {
        try {
            switch (status) {
                case 'w':
                    sunIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/winner.png")));
                    break;
                case 'h':
                    sunIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/happy.png")));
                    break;
                case 'd':
                    sunIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/warningSun.png")));
                    break;
                case 'o':
                    sunIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/gameOver.png")));
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sunIcon;
    }

    public Image getFlagIcon() {
        try {

            flagIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/flagIconm.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flagIcon;
    }
}
