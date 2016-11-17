package de.unima.dws.semanta.ui.home;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.airhacks.afterburner.injection.Injector;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.search.SearchView;
import de.unima.dws.semanta.ui.main.MainView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomePresenter implements Initializable{

	@FXML
	private TextField textFieldSearch;
	
	@FXML
	private ComboBox<Difficulty> comboBoxDifficulty;
	
	@FXML
	private ProgressIndicator indicator;

	@Inject
	private Application application;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		indicator.setVisible(false);
		comboBoxDifficulty.getItems().add(Difficulty.BEGINNER);
		comboBoxDifficulty.getItems().add(Difficulty.ADVANCED);
		comboBoxDifficulty.getItems().add(Difficulty.EXPERT);
		comboBoxDifficulty.getSelectionModel().select(Difficulty.EXPERT);
		textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				textFieldSearch.setStyle("-fx-border-color: #00cc66 ;-fx-border-width: 2px;");
			} else {
				textFieldSearch.setStyle("-fx-border-color: red ;-fx-border-width: 2px;");
			}
		});
	}
	

	public void getTopicsAction() {
		
//		listViewTopics.getItems().clear();
//		
//		List<ResourceInfo> t  = Semanta.getTopics(textFieldSearch.getText(), 50);
//		listViewTopics.getItems().addAll(t);

	 };
	
	
	/**
	 * Based on the user input, retrieve a list
	 * of ResourceInfo for selection to be displayed
	 * in the following scene. 
	 */
	public void searchTopics() {
		if(validateInput(textFieldSearch)) {
			indicator.setVisible(true);
			Task<List<ResourceInfo>> longTask = new Task<List<ResourceInfo>>() {
	            @Override
	            protected List<ResourceInfo> call() throws Exception {
	            	//TODO sanitize string
	            	return application.getTopicResults(textFieldSearch.getText());
	            }
	        };
	       longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent t) {
	    			indicator.setVisible(false);
					try {
						System.out.println("success");
						List<ResourceInfo> resourceInfos = longTask.get();
						Map<Object, Object> customProperties = new HashMap<>();
				        customProperties.put("resourceInfos", resourceInfos);
				        Injector.setConfigurationSource(customProperties::get);
						Stage stage = (Stage) textFieldSearch.getScene().getWindow();
						stage.setScene(new Scene(new SearchView().getView()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
	            }
	        });
	        new Thread(longTask).start();
		}
	}

	/**
	 * Get the most ranked topic resource and directly
	 * generate crossword puzzle
	 */
	public void generateCrossword() {
		if(validateInput(textFieldSearch)) {
			indicator.setVisible(true);
			Task<Crossword> longTask = new Task<Crossword>() {
		            @Override
		            protected Crossword call() throws Exception {
		            	//TODO sanitize string
		            	return application.generateCrossword(textFieldSearch.getText());
		            }
		        };
		       longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		            @Override
		            public void handle(WorkerStateEvent t) {
		    			indicator.setVisible(false);
						try {
							System.out.println("success");
							Crossword crossword = longTask.get();
							Map<Object, Object> customProperties = new HashMap<>();
					        customProperties.put("crossword", crossword);
					        Injector.setConfigurationSource(customProperties::get);
							Stage stage = (Stage) textFieldSearch.getScene().getWindow();
							stage.setScene(new Scene(new MainView().getView()));
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
		            }
		        });
		        new Thread(longTask).start();
		}
	}
	
	private boolean validateInput(TextField textField) {
		if(textField.getText() == null || textField.getText().isEmpty()) {
			textField.setStyle("-fx-border-color: red ;-fx-border-width: 2px;");
			return false;
		}
		return true;
	}
}
