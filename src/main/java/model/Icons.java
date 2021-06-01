package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Icons {
    private Image mineIcon;
    private Image numberIcon;
    private Image buttonIcon;

    public Icons() {
    }

    public Image getButtonImage() {
        try {

            buttonIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/blank.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return buttonIcon;
    }

    public Image getMineImage() {
        try {

            mineIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/mineIcon.png")));
            //mineIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\mineIcon.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mineIcon;
    }

    public Image getNumberIcons(int number) {
        try {
            switch (number) {
                case 1:
                    numberIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/1.png")));
                    break;
                case 2:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\2.png"));
                    break;
                case 3:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\3.png"));
                    break;
                case 4:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\4.png"));
                    break;
                case 5:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\5.png"));
                    break;
                case 6:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\6.png"));
                    break;
                case 7:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\7.png"));
                    break;
                case 8:
                    numberIcon = ImageIO.read(new File("C:\\Kurs JAVA\\MineSweeper\\src\\main\\resources\\8.png"));
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return numberIcon;
    }
}
