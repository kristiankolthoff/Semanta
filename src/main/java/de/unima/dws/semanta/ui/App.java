package de.unima.dws.semanta.ui;


import com.airhacks.afterburner.injection.Injector;

import de.unima.dws.semanta.crossword.generation.CrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.InternalGreedyCrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.SimpleCrosswordGenerator;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
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
//    	Crossword crossword = new Crossword();
//    	crossword.addWord(new HAWord(
//				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
//		crossword.addWord(new HAWord(
//				new HAEntity(null, "semanta", null, null, null)), 1, 0, 1, 6);
//		crossword.addWord(new HAWord(
//				new HAEntity(null, "aborting", null, null, null)), 1, 6, 8, 6);
//    	CrosswordGenerator generator = new SimpleCrosswordGenerator();
    	CrosswordGenerator generator = new InternalGreedyCrosswordGenerator(
    			new SimpleCrosswordGenerator(), 50, null);
		Crossword crossword = generator.generate(
				new HAWord(new HAEntity(null, "tested", null, null, null)),
				new HAWord(new HAEntity(null, "semanta", null, null, null)),
				new HAWord(new HAEntity(null, "barbara", null, null, null)),
				new HAWord(new HAEntity(null, "software", null, null, null)),
				new HAWord(new HAEntity(null, "engineering", null, null, null)),
				new HAWord(new HAEntity(null, "amen", null, null, null)),
				new HAWord(new HAEntity(null, "timberners", null, null, null)),
				new HAWord(new HAEntity(null, "norbertlammert", null, null, null)),
				new HAWord(new HAEntity(null, "robben", null, null, null)),
				new HAWord(new HAEntity(null, "oliverkahn", null, null, null)),
				new HAWord(new HAEntity(null, "ronaldo", null, null, null)),
				new HAWord(new HAEntity(null, "angel", null, null, null)));
		crossword.normalize();
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("crossword", crossword);
        Injector.setConfigurationSource(customProperties::get);
//        MainView appView = new MainView();
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