package edu.csu.caloriecounter.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import edu.csu.caloriecounter.settings.SettingsManager;

/**
 * JavaFX bootstrap for the Calorie Counter App.
 *
 * Responsibilities:
 * - Initialize user preferences
 * - Initialize primary UI stage
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 * @author Robert
 * @since 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        SettingsManager.getInstance().load();
        stage.setTitle("Calorie Counter");
        stage.setScene(new Scene(new Label("Calorie Counter App"), 480, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}