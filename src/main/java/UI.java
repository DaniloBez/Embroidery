import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * Клас UI відповідає за графічний інтерфейс JavaFX-додатку.
 * Він містить поля для введення розміру вишивки, кнопки для малювання та збереження у PNG.
 */
public class UI extends Application {

    private final BackEnd backEnd = new BackEnd();

    /**
     * Запускає головне вікно додатку.
     * @param stage основна сцена JavaFX
     */
    @Override
    public void start(Stage stage) {
        TextField rowField = new TextField();
        rowField.setPromptText("1");

        TextField colField = new TextField();
        colField.setPromptText("1");

        Button drawBtn = new Button("Намалювати");
        Button saveBtn = new Button("Зберегти PNG");

        VBox controlPanel = new VBox(10, new Label("Рядки:"), rowField,
                new Label("Стовпці:"), colField, drawBtn, saveBtn);
        controlPanel.setMinWidth(150);
        controlPanel.setStyle("-fx-padding: 10;");

        Pane drawPane = new Pane();
        drawPane.setMinSize(400, 400);

        ScrollPane scrollPane = new ScrollPane(drawPane);
        scrollPane.setPrefViewportWidth(500);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);

        BorderPane root = new BorderPane();
        root.setLeft(controlPanel);
        root.setCenter(scrollPane);

        drawBtn.setOnAction(_ -> backEnd.changeSize(drawPane, saveParse(rowField.getText()), saveParse(colField.getText())));

        saveBtn.setOnAction(_ -> backEnd.saveInPNGFile());

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Вишиванка - Безух Данило");
        stage.show();

        backEnd.drawEmbroidery(drawPane);
    }

    /**
     * Перетворює текст на число. Якщо не вдалося — повертає 1.
     * @param text рядок з числами
     * @return ціле число або 1
     */
    private int saveParse(String text){
        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            return 1;
        }
    }
}
