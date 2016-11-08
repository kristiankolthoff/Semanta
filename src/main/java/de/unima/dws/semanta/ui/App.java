package de.unima.dws.semanta.ui;


import com.airhacks.afterburner.injection.Injector;

import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.crossword.model.SemaCrossword;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.ui.home.HomeView;
import de.unima.dws.semanta.ui.main.MainView;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	Crossword crossword = new SemaCrossword();
    	crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
		crossword.addWord(new HAWord(
				new HAEntity(null, "semanta", null, null, null)), 1, 0, 1, 6);
		crossword.addWord(new HAWord(
				new HAEntity(null, "aborting", null, null, null)), 1, 6, 8, 6);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("crossword", crossword);
        Injector.setConfigurationSource(customProperties::get);

        MainView appView = new MainView();
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