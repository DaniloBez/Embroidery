import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * BackEnd відповідає за логіку створення вишивки, масштабування та збереження у PNG.
 */
public class BackEnd {
    int[][] embroidery;
    final static int EMBROIDERY_SIZE = 20;
    final static int RECTANGLE_SIZE = 10;
    final static Random rand = new Random();

    /**
     * Малює чорні елементи вишивки.
     * Призначення чорних пікселів вручну (залежить від імені користувача).
     */
    private void drawBlack(){
        embroidery = new int[20][20];

        embroidery[0][1] = 1;
        embroidery[1][0] = 1;
        embroidery[2][2] = 1;
        embroidery[2][1] = 1;
        embroidery[1][2] = 1;
        embroidery[3][0] = 1;
        embroidery[0][3] = 1;
        embroidery[3][3] = 1;
        embroidery[4][1] = 1;
        embroidery[4][2] = 1;
        embroidery[2][4] = 1;
        embroidery[1][4] = 1;
        embroidery[4][4] = 1;
        embroidery[5][5] = 1;
        embroidery[6][5] = 1;
        embroidery[7][5] = 1;
        embroidery[6][6] = 1;
        embroidery[5][6] = 1;
        embroidery[5][7] = 1;
        embroidery[8][8] = 1;
        embroidery[7][9] = 1;
        embroidery[9][7] = 1;
        embroidery[9][9] = 1;
    }

    /**
     * Малює випадкові червоні пікселі у верхній лівій чверті.
     */
    private void drawRed(){
        for (int r = 0; r < embroidery.length / 2; r++) {
            for (int c = 0; c < embroidery.length / 2; c++) {
                if (embroidery[r][c] != 1 && rand.nextBoolean())
                    embroidery[r][c] = 2;
            }
        }
    }


    /**
     * Заповнює масив симетрично у всі сторони.
     */
    private void fillSymmetrically(){
        for (int r = 0; r <= embroidery.length / 2; r++) {
            for (int c = 0; c <= embroidery.length / 2; c++) {
                embroidery[embroidery.length - r - 1][c] = embroidery[r][c];
                embroidery[r][embroidery.length - c - 1] = embroidery[r][c];
                embroidery[embroidery.length - r - 1][embroidery.length - c - 1] = embroidery[r][c];
            }
        }
    }

    /**
     * Малює прямокутники на панелі відповідно до даних вишивки.
     * @param pane панель, на яку додаються прямокутники
     */
    private void drawAll(Pane pane){
        pane.getChildren().clear();

        for (int r = 0; r < embroidery.length; r++) {
            for (int c = 0; c < embroidery[r].length; c++) {
                Rectangle rectangle = new Rectangle(r * RECTANGLE_SIZE, c * RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE);

                switch (embroidery[r][c]){
                    case 1:
                        rectangle.setFill(Color.BLACK);
                        break;
                    case 2:
                        rectangle.setFill(Color.RED);
                        break;
                    default:
                        rectangle.setFill(Color.WHITE);
                        break;
                }
                pane.getChildren().add(rectangle);
            }
        }
    }

    /**
     * Малює початкову вишивку.
     * @param pane панель для малювання
     */
    public void drawEmbroidery(Pane pane){
        drawBlack();
        drawRed();
        fillSymmetrically();
        drawAll(pane);
    }

    /**
     * Масштабує вишивку і перемальовує.
     * @param pane панель для малювання
     * @param width кількість повторів по горизонталі
     * @param height кількість повторів по вертикалі
     */
    public void changeSize(Pane pane, int width, int height){
        resizeArray(width, height);
        drawAll(pane);
    }

    /**
     * Розширює масив вишивки шляхом повторення шаблону.
     * @param widthRepeats кількість повторів по ширині
     * @param heightRepeats кількість повторів по висоті
     */
    private void resizeArray(int widthRepeats, int heightRepeats) {
        int[][] temp = new int[EMBROIDERY_SIZE * heightRepeats][EMBROIDERY_SIZE * widthRepeats];

        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < temp[0].length; c++) {
                temp[r][c] = embroidery[r % EMBROIDERY_SIZE][c % EMBROIDERY_SIZE];
            }
        }

        embroidery = temp;
    }

    /**
     * Зберігає зображення вишивки у PNG з назвою у форматі дати-часу.
     */
    public void saveInPNGFile(){
        BufferedImage image = getBufferedImage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String fileName = "image_" + timestamp + ".png";

        try {
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("Зображення збережено як output.png");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Перетворює embroidery[][] на BufferedImage для збереження.
     * @return BufferedImage з даними вишивки
     */
    private BufferedImage getBufferedImage() {
        BufferedImage image = new BufferedImage(embroidery.length, embroidery[0].length, BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < embroidery.length; r++) {
            for (int c = 0; c < embroidery[r].length; c++) {
                image.setRGB(r, c, switch (embroidery[r][c]) {
                    case 1 -> java.awt.Color.BLACK.getRGB();
                    case 2 -> java.awt.Color.RED.getRGB();
                    default -> java.awt.Color.WHITE.getRGB();
                });
            }
        }
        return image;
    }
}
