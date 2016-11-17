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

import org.apache.jena.rdf.model.ResourceFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	CrosswordGenerator generator = new InternalGreedyCrosswordGenerator(
    			new SimpleCrosswordGenerator(), 10, null);
		Crossword crossword = generator.generate(
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("tested").addHint("something that can be tested")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("semanta").addHint("name of this application")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("barbara").addHint("well known girl name")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("software").addHint("opposite of hardware")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("engineering").addHint("something that can be tested,"
						+ " something that can be tested")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("amen").addHint("word of the catholic curch")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("timberners").addHint("inventor of the internet")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("norbertlammert").addHint("president of the bundestag")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("robben").addHint("famous soccer player of the netherlands")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("oliverkahn").addHint("well knwon german goal keeper, whats his name?")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("ronaldo").addHint("best soccer player of all time")),
				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("angel").addHint("they can fly and help people, what is it?")));
		crossword.normalize();
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("crossword", crossword);
        Injector.setConfigurationSource(customProperties::get);
        MainView appView = new MainView();
//        HomeView appView = new HomeView();
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