import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 💠 KalkulatorAdvanced.java
 * 
 * Versi kompleks kalkulator JavaFX dengan komentar lengkap
 * Fitur: operasi dasar, desimal, persen, validasi, efek neon.
 */
public class KalkulatorAdvanced extends Application {

    // ======= VARIABEL GLOBAL =======
    private TextField display;        // Menampilkan angka & hasil
    private double num1 = 0;          // Menyimpan angka pertama
    private String operator = "";     // Menyimpan operator (+, -, *, /, %)
    private boolean start = true;     // Menandai awal input baru

    @Override
    public void start(Stage stage) {
        // ======= 1. INPUT DISPLAY =======
        display = new TextField();
        display.setFont(Font.font("Consolas", 24));
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(false);
        display.setPrefHeight(70);
        display.setStyle(
            "-fx-background-color: #2b2b2b;" +
            "-fx-text-fill: #00ffcc;" +
            "-fx-border-color: #555;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        // ======= 2. GRID BUTTONS =======
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);

        // Daftar tombol (baris x kolom)
        String[][] buttons = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "%", "+"},
            {"C", "←", "=", ""}
        };

        // Buat tombol dinamis dari array di atas
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                String text = buttons[i][j];
                if (text.isEmpty()) continue; // lewati sel kosong
                Button btn = createButton(text);
                btn.setOnAction(e -> handleButton(text));
                grid.add(btn, j, i);
            }
        }

        // ======= 3. ROOT LAYOUT =======
        VBox root = new VBox(20, display, grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #101010, #1f1f1f);"
        );

        // ======= 4. SCENE & STAGE =======
        Scene scene = new Scene(root, 340, 500);
        stage.setTitle("💠 Kalkulator Advanced - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Membuat tombol dengan gaya modern
     */
    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Poppins", 18));
        btn.setPrefSize(70, 70);
        btn.setTextFill(Color.WHITE);
        btn.setStyle(
            "-fx-background-color: #333;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #555;"
        );

        // Efek cahaya saat ditekan
        DropShadow shadow = new DropShadow(15, Color.web("#00ffcc"));
        btn.setOnMousePressed(e -> {
            btn.setEffect(shadow);
            btn.setStyle("-fx-background-color: #00bfa5; -fx-background-radius: 12;");
        });
        btn.setOnMouseReleased(e -> {
            btn.setEffect(null);
            btn.setStyle("-fx-background-color: #333; -fx-border-color: #555; -fx-background-radius: 12;");
        });

        return btn;
    }

    /**
     * Menangani setiap tombol yang ditekan
     */
    private void handleButton(String text) {
        switch (text) {
            case "C": // Reset kalkulator
                display.clear();
                num1 = 0;
                operator = "";
                start = true;
                break;

            case "←": // Hapus satu karakter terakhir
                if (!display.getText().isEmpty()) {
                    display.setText(display.getText().substring(0, display.getText().length() - 1));
                }
                break;

            case "=": // Proses hasil
                if (operator.isEmpty()) return;
                try {
                    double num2 = Double.parseDouble(display.getText());
                    double result = calculate(num1, num2, operator);
                    display.setText(String.valueOf(result));
                    operator = "";
                    start = true;
                } catch (NumberFormatException ex) {
                    showError("Input tidak valid!");
                }
                break;

            case "+": case "-": case "*": case "/": case "%":
                // Simpan operator dan angka pertama
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    operator = text;
                    start = true;
                }
                break;

            case ".":
                // Tambahkan titik desimal jika belum ada
                if (!display.getText().contains(".")) {
                    display.appendText(".");
                    start = false;
                }
                break;

            default: // Angka 0–9
                if (start) {
                    display.clear();
                    start = false;
                }
                display.appendText(text);
                break;
        }
    }

    /**
     * Melakukan perhitungan sesuai operator
     */
    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return (b != 0) ? a / b : showError("Tidak bisa dibagi nol!");
            case "%": return (a * b) / 100;
            default: return 0;
        }
    }

    /**
     * Menampilkan pesan kesalahan dalam popup alert
     */
    private double showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(msg);
        alert.showAndWait();
        return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
