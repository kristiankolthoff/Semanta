package de.unima.dws.semanta.ui.home;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.Diff;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.recommendation.RecommendationPresenter;
import de.unima.dws.semanta.ui.home.recommendation.RecommendationView;
import de.unima.dws.semanta.ui.home.search.SearchPresenter;
import de.unima.dws.semanta.ui.home.search.SearchView;
import de.unima.dws.semanta.ui.main.MainView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import sun.management.HotspotMemoryMBean;

public class HomePresenter implements Initializable{

	@FXML
	private TextField textFieldSearch;
	
	@FXML
	private TextField textFieldWords;
	
	@FXML
	private ComboBox<Difficulty> comboBoxDifficulty;
	
	@FXML
	private ProgressIndicator indicator;
	
	@FXML
	private Pane paneRecommendation;
	
	@Inject
	private Application application;
	
	private Timeline anim;
	
	private final static double RECOMMENDATION_WIDTH = 246*4+65;
	private final static double RECOMMENDATION_HEIGHT = 313;
	private static int numOfRecommendations;
	
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
 
        HBox hBox = new HBox();
		initializeRecommendations(hBox);
	}
	
	public void slideLeft() {
		
	}
	
	public void slideRight() {
		
	}
	
	
	private void initializeRecommendations(HBox hBox) {
		this.application.generateRecommendations(vals -> {
			System.out.println(vals);
			 Platform.runLater(new Runnable() {
			      @Override public void run() {
			    	  for(ResourceInfo recommendation : vals) {
							System.out.println("Create recommendation view");
							//TODO only inject application
							RecommendationView view = new RecommendationView((f) -> recommendation);
							((RecommendationPresenter)view.getPresenter()).setHomePresenter(HomePresenter.this);
							Separator separator = new Separator(Orientation.VERTICAL);
							separator.setVisible(false);
							separator.setPadding(new Insets(5));
							hBox.getChildren().add(separator);
							hBox.getChildren().add(view.getView());
						}
						numOfRecommendations = vals.size();
						paneRecommendation.setMaxSize(RECOMMENDATION_WIDTH, RECOMMENDATION_HEIGHT);
						paneRecommendation.setClip(new Rectangle(RECOMMENDATION_WIDTH, RECOMMENDATION_HEIGHT));
						paneRecommendation.getChildren().add(hBox);
						startAnimation(hBox, (int) RECOMMENDATION_WIDTH, numOfRecommendations); 
			      }
			    });
		});
	}
	
    private void startAnimation(final HBox hbox, final int RECOMMENDATION_WIDTH, final int NUM_OF_RECOMMENDATIONS) {
    	 final int SLIDE_FREQ = 4; 
    	
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
            trans.setByX(-RECOMMENDATION_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
        
        final int ITS = (NUM_OF_RECOMMENDATIONS % 2 == 0) ? 
        		NUM_OF_RECOMMENDATIONS/4 : NUM_OF_RECOMMENDATIONS/4 + 1;
        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
            trans.setByX((ITS - 1) * RECOMMENDATION_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
 
        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= ITS; i++) {
            if (i == ITS) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
            }
        }
        anim = new Timeline(keyFrames.toArray(new KeyFrame[ITS]));
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();
    }


	/**
	 * Based on the user input, retrieve a list
	 * of ResourceInfo for selection to be displayed
	 * in the following scene. 
	 */
	public void searchTopics() {
		if(validateInput(textFieldSearch)) {
			searchTopics(textFieldSearch.getText());
		}
	}
	
	public void searchTopics(String topic) {
			anim.stop();
			indicator.setVisible(true);
			Task<List<ResourceInfo>> longTask = new Task<List<ResourceInfo>>() {
	            @Override
	            protected List<ResourceInfo> call() throws Exception {
	            	//TODO sanitize string
	            	return application.getTopicResults(topic);
	            }
	        };
	       longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent t) {
					System.out.println("success");
					try {
						List<ResourceInfo> searchInfo = longTask.get();
						if(searchInfo.isEmpty()) {
							textFieldSearch.setStyle("-fx-border-color: red ;-fx-border-width: 2px;");
						} else {
							SearchView view = new SearchView((f) -> searchInfo);
							((SearchPresenter)view.getPresenter()).setHomePresenter(HomePresenter.this);
							((SearchPresenter)view.getPresenter()).initialize();
							Stage stage = (Stage) textFieldSearch.getScene().getWindow();
							stage.setScene(new Scene(view.getView()));
						}
						indicator.setVisible(false);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					
	            }
	        });
	        new Thread(longTask).start();
	}
	
	public void generateCrossword(String topic, Stage stage, Difficulty difficulty) {
		anim.stop();
		indicator.setVisible(true);
		Task<Crossword> longTask = new Task<Crossword>() {
		         @Override
		         protected Crossword call() throws Exception {
		         //TODO sanitize string
		         return application.generateCrossword(topic, difficulty, Integer.valueOf(textFieldWords.getText()));
		         }
		};
		longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		         @Override
		         public void handle(WorkerStateEvent t) {
		        	 try {
						if(longTask.get() != null) {
							System.out.println("success");
							stage.setScene(new Scene(new MainView((f) -> application).getView()));
						 } else {
							 textFieldSearch.setStyle("-fx-border-color: red ;-fx-border-width: 2px;");
						 }
						 indicator.setVisible(false);
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         }
		});
		new Thread(longTask).start();
	}
	
	public void generateCrossword(ResourceInfo topic, Stage stage, Difficulty difficulty) {
		anim.stop();
		indicator.setVisible(true);
		Task<Crossword> longTask = new Task<Crossword>() {
		         @Override
		         protected Crossword call() throws Exception {
		         //TODO sanitize string
		         return application.generateCrossword(topic, difficulty, Integer.valueOf(textFieldWords.getText()));
		         }
		};
		longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		         @Override
		         public void handle(WorkerStateEvent t) {
		        	 indicator.setVisible(false);
					 System.out.println("success");
					 stage.setScene(new Scene(new MainView((f) -> application).getView()));
		         }
		});
		new Thread(longTask).start();
	}

	public void generateCrossword() {
		if(validateInput(textFieldSearch)) {
			generateCrossword(textFieldSearch.getText(),(Stage) textFieldSearch.getScene().getWindow(), 
					comboBoxDifficulty.getValue());
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
