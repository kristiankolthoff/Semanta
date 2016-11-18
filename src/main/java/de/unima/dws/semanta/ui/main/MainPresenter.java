package de.unima.dws.semanta.ui.main;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.crossword.model.Cell;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.crossword.model.Orientation;
import de.unima.dws.semanta.ui.home.HomeView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainPresenter implements Initializable{

	@FXML
	private BorderPane borderPane;
	
	@FXML
	private ListView<String> listViewAcross;
	
	@FXML
	private ListView<String> listViewDown;
	
	@FXML
	private Label labelAnswerA;
	
	@FXML 
	private Label labelAnswerB;
	
	@FXML
	private Label labelAnswerC;
	
	@FXML
	private Label labelAnswerD;
	
	@FXML
	private Slider sliderZoom;
	
	@FXML
	private Label labelTopic;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Label labelLinkWithTopic;
	
	@FXML
	private ImageView imageViewEntity;
	
	@FXML
	private Text textAbtract;
	
	@FXML
	private VBox vBoxFacts;

	@Inject
	private Application application;
	private Crossword crossword;
	
	
	private Map<Cell, TextField> cellMap;
	private Map<TextField, Cell> textMap;
	private Map<Cell, HAWord> wordMap;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeListViews();
		initialzeCrosswordGrid();
	}
	
	private void initialzeCrosswordGrid() {
		listViewAcross.getItems().clear();
		listViewDown.getItems().clear();
		crossword = application.getCrossword();
		GridPane grid = new GridPane();
		grid.setPrefWidth(1000);
		grid.setPrefHeight(1000);
		grid.setStyle("-fx-background-color : #C0C0C0");
		int padding = 30;
		grid.setPadding(new Insets(padding, padding, padding, padding));
		grid.setHgap(0);
		grid.setVgap(0);
		this.cellMap = new HashMap<>();
		this.textMap = new HashMap<>();
		this.wordMap = new HashMap<>();
		for (int i = 0; i < crossword.getWidth(); i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.prefWidthProperty().bind(sliderZoom.valueProperty());
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < crossword.getHeight(); i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.prefHeightProperty().bind(sliderZoom.valueProperty());
            grid.getRowConstraints().add(rowConst);        
        }
		grid.setGridLinesVisible(false);
		for(HAWord word : crossword) {
			//Insert the hints of the words into fields
			if(word.getOrientation() == Orientation.HORIZONTAL) {
				listViewAcross.getItems().add(word.getIndex() + ". " + word.getHAEntity().getHintsBeautified());
			} else if(word.getOrientation() == Orientation.VERTICAL) {
				listViewDown.getItems().add(word.getIndex() + ". " + word.getHAEntity().getHintsBeautified());
			}
			for(Cell cell : word) {
				//Build crossword textfield cells
				TextField text = new TextField();
				text.setAlignment(Pos.CENTER);
				text.setPrefSize(1000, 1000);
				text.setText(cell.getLabel());
				if(cell.getLabel().equals("LA")) {
					text.setStyle("-fx-border-color: #00cc66");
					text.setStyle("-fx-background-color: transparent");
					text.setText(String.valueOf(word.getIndex()));
				}
				cell.getSolutionProperty().bind(text.textProperty());
				text.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.length() > 1) {
						text.setText(newValue.substring(0, 1));
					} else if(newValue.length() == 1){
						Cell cellSelected = textMap.get(text);
						System.out.println("selected: " + cellSelected);
						HAWord wordCurr = wordMap.get(cellSelected);
						boolean next = false;
						for(Cell cellCurr : wordCurr) {
							if(next) {
								System.out.println("next: " + cellCurr);
								cellMap.get(cellCurr).requestFocus();;
								break;
							}
							if(cellCurr.equals(cellSelected)) {
								next = true;
							}
					}
					}
				});
				cellMap.put(cell, text);
				textMap.put(text, cell);
				wordMap.put(cell, word);
				grid.add(text, cell.getX(), cell.getY());
			}
		}
		borderPane.setCenter(grid);
	}

	private void initializeListViews() {
		listViewAcross.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            final ListCell<String> cell = new ListCell<String>() {
	                private Text text;

	                @Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (!isEmpty()) {
	                        text = new Text(item.toString());
	                        text.setWrappingWidth(listViewAcross.getPrefWidth());
	                        setGraphic(text);
	                    }
	                }
	            };

	            return cell;
	        }
	    });
		listViewDown.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            final ListCell<String> cell = new ListCell<String>() {
	                private Text text;

	                @Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (!isEmpty()) {
	                        text = new Text(item.toString());
	                        text.setWrappingWidth(listViewDown.getPrefWidth());
	                        setGraphic(text);
	                    }
	                }
	            };

	            return cell;
	        }
	    });
	}
	
	
	public void validate() {
		System.out.println("validate");
		for(HAWord word : crossword) {
			for(Cell cell : word) {
				if(cell.isValid()) {
					System.out.println("Cell valid : " +  cell);
					cellMap.get(cell).setStyle("-fx-border-color: #00cc66 ;-fx-border-width: 2px ;");
				} else {
					cellMap.get(cell).setStyle("-fx-border-color: red ;-fx-border-width: 2px ;");
				}
			}
		}
	}
	
	public void backToHome() {
		Stage stage = (Stage) borderPane.getScene().getWindow();
		stage.setScene(new Scene(new HomeView().getView()));
	}
	
	public void regenerateCrossword() {
		this.borderPane.getChildren().clear();
		ProgressIndicator indicator = new ProgressIndicator();
		indicator.setPrefSize(60, 60);
		indicator.setMaxSize(60, 60);
		indicator.setProgress(-1);
		indicator.setVisible(true);
		this.borderPane.setCenter(indicator);
		Task<Void> longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	//TODO sanitize string
            	application.regenerateCrossword();
				return null;
            }
        };
       longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
    			indicator.setVisible(false);
				System.out.println("success");
				initialzeCrosswordGrid();
            }
        });
        new Thread(longTask).start();
		
	}
	
	public void save() {
		
	}

	public void cloud() {
		
	}
}
