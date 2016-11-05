package de.unima.dws.semanta.ui;


import com.airhacks.afterburner.injection.Injector;

import de.unima.dws.semanta.ui.home.HomeView;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Map<Object, Object> customProperties = new HashMap<>();
        Injector.setConfigurationSource(customProperties::get);

        HomeView appView = new HomeView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("Semanta : Topic-Based Crossword Puzzle Generation");
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}