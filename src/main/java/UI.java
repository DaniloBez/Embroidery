import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class UI extends Application {

    @Override
    public void start(Stage stage) {
        TextField rowField = new TextField();
        rowField.setPromptText("Рядки");

        TextField colField = new TextField();
        colField.setPromptText("Стовпці");

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

        drawBtn.setOnAction(e -> {});

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Генератор сітки");
        stage.show();
    }
}
