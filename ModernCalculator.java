import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ModernCalculator extends Application {

    private TextField display;
    private double num1 = 0;
    private String operator = "";
    private boolean start = true;

    @Override
    public void start(Stage stage) {
        stage.setTitle("🧮 Kalkulator Modern JavaFX");

        display = new TextField();
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setStyle("""
                -fx-font-size: 24px;
                -fx-background-color: #E3F2FD;
                -fx-text-fill: #000000;
                -fx-border-color: #90CAF9;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                """);
        display.setPrefHeight(60);

        GridPane buttonGrid = createButtonGrid();

        VBox root = new VBox(15, display, buttonGrid);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, #E1F5FE);");

        Scene scene = new Scene(root, 350, 500);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        String[][] buttons = {
                {"7", "8", "9", "÷"},
                {"4", "5", "6", "×"},
                {"1", "2", "3", "-"},
                {"0", ".", "=", "+"},
                {"C"}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String text = buttons[row][col];
                Button btn = createButton(text);
                grid.add(btn, col, row);
            }
        }
        return grid;
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(70, 70);
        btn.setStyle("""
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-background-color: #BBDEFB;
                -fx-text-fill: black;
                -fx-background-radius: 10;
                -fx-cursor: hand;
                """);
        btn.setEffect(new DropShadow(3, Color.LIGHTGRAY));

        // Hover effect
        btn.setOnMouseEntered(e -> btn.setStyle("""
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-background-color: #90CAF9;
                -fx-text-fill: white;
                -fx-background-radius: 10;
                """));
        btn.setOnMouseExited(e -> btn.setStyle("""
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-background-color: #BBDEFB;
                -fx-text-fill: black;
                -fx-background-radius: 10;
                """));

        // Animasi klik
        btn.setOnAction(e -> {
            animateButton(btn);
            handleButton(text);
        });

        return btn;
    }

    private void animateButton(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(120), btn);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(0.9);
        st.setToY(0.9);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    private void handleButton(String value) {
        double num2 = 0;
        if (value.matches("[0-9\\.]")) {
            if (start) {
                display.clear();
                start = false;
            }
            display.appendText(value);
        } else if (value.matches("[+\\-×÷]")) {
            operator = value;
            num1 = Double.parseDouble(display.getText());
            display.clear();
        } else if (value.equals("=")) {
            if (operator.isEmpty()) return;

            num2 = Double.parseDouble(display.getText());
            double result = calculate(num1, num2, operator);
            display.setText(String.valueOf(result));
            operator = "";
            start = true;
        } else if (value.equals("C")) {
            display.clear();
            operator = "";
            num1 = num2 = 0;
            start = true;
