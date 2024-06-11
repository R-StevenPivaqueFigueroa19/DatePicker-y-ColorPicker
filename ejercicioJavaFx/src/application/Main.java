package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private static final Map<String, String> colorNames;

    static {
        colorNames = new HashMap<>();
        // Definir más colores según sea necesario
        colorNames.put("#FF0000", "Rojo");
        colorNames.put("#00FF00", "Verde");
        colorNames.put("#0000FF", "Azul");
        colorNames.put("#FFFF00", "Amarillo");
        colorNames.put("#000000", "Negro");
        colorNames.put("#FFFFFF", "Blanco");
        colorNames.put("#FFA500", "Naranja");
        colorNames.put("#800080", "Púrpura");
        colorNames.put("#00FFFF", "Cian");
        colorNames.put("#FFC0CB", "Rosa");
        // Puedes añadir más colores a esta lista
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label dateLabel = new Label("Selecciona una fecha:");
        dateLabel.setFont(new Font("Arial", 16));
        dateLabel.setTextFill(Color.DARKSLATEGRAY);

        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-font-size: 14px;");

        Label colorLabel = new Label("Selecciona un color:");
        colorLabel.setFont(new Font("Arial", 16));
        colorLabel.setTextFill(Color.DARKSLATEGRAY);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setStyle("-fx-font-size: 14px;");

        Button confirmButton = new Button("Confirmar");
        confirmButton.setFont(new Font("Arial", 14));
        confirmButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        );

        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(
            "-fx-background-color: #45a049; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        ));

        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        ));

        // Acción al presionar el botón
        confirmButton.setOnAction(event -> {
            String colorName = getColorName(colorPicker.getValue());

            System.out.println("Fecha seleccionada: " + datePicker.getValue());
            System.out.println("Color seleccionado: " + colorName);
        });

        // Organizar controles en un VBox
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0;");
        vbox.getChildren().addAll(dateLabel, datePicker, colorLabel, colorPicker, confirmButton);

        // Configurar la escena y mostrarla
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CONTROLES DATEPICKER Y COLORPICKER");
        primaryStage.show();
    }

    private String getColorName(Color color) {
        String baseColorHex = getNearestBaseColor(color);
        String baseColorName = colorNames.get(baseColorHex);

        if (baseColorName == null) {
            return "Color personalizado (" + toHexString(color) + ")";
        }

        double brightnessSelected = color.getBrightness();
        double brightnessBase = Color.web(baseColorHex).getBrightness();

        if (brightnessSelected > brightnessBase) {
            return "Variante clara de " + baseColorName;
        } else if (brightnessSelected < brightnessBase) {
            return "Variante oscura de " + baseColorName;
        } else {
            return baseColorName;
        }
    }

    private String getNearestBaseColor(Color color) {
        String nearestColorHex = null;
        double minDistance = Double.MAX_VALUE;

        for (String hex : colorNames.keySet()) {
            Color baseColor = Color.web(hex);
            double distance = colorDistance(color, baseColor);
            if (distance < minDistance) {
                minDistance = distance;
                nearestColorHex = hex;
            }
        }

        return nearestColorHex;
    }

    private double colorDistance(Color c1, Color c2) {
        double r1 = c1.getRed() * 255;
        double g1 = c1.getGreen() * 255;
        double b1 = c1.getBlue() * 255;
        double r2 = c2.getRed() * 255;
        double g2 = c2.getGreen() * 255;
        double b2 = c2.getBlue() * 255;

        return Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));
    }

    private String toHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

