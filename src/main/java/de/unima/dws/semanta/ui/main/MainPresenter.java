package de.unima.dws.semanta.ui.main;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;


import de.unima.dws.semanta.crossword.model.Cell;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MainPresenter implements Initializable{

	@FXML
	private AnchorPane anchorPaneMain;
	
	@FXML
	private Button buttonValidate;
	
	@Inject
	private Crossword crossword;
	
	private Map<Cell, TextField> cellMap;
	private Map<TextField, Cell> textMap;
	private Map<Cell, HAWord> wordMap;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		GridPane grid = new GridPane();
		grid.setPrefWidth(1000);
		grid.setPrefHeight(1000);
//		grid.setBackground(new Background(new BackgroundImage(new Image("http://www.publicdomainpictures.net/pictures/60000/velka/dark-texture-background.jpg"), 
//				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, true, true))));
		grid.setStyle("-fx-background-color : #C0C0C0");
		int padding = 30;
		grid.setPadding(new Insets(padding, padding, padding, padding));
		grid.setHgap(1);
		grid.setVgap(2);
		this.cellMap = new HashMap<>();
		this.textMap = new HashMap<>();
		this.wordMap = new HashMap<>();
		for (int i = 0; i < crossword.getWidth(); i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(30);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < crossword.getHeight(); i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(30);
            grid.getRowConstraints().add(rowConst);         
        }
		grid.setGridLinesVisible(false);
		for(HAWord word : crossword) {
			for(Cell cell : word) {
				TextField text = new TextField();
				text.setText(cell.getLabel());
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
		anchorPaneMain.getChildren().add(grid);
//		Image image = new Image("https://upload.wikimedia.org"
//				+ "/wikipedia/commons/thumb/c/cb/Stephen_Maguire%2C_Ronnie_"
//				+ "O%E2%80%99Sullivan%2C_and_Michaela_Tabb_at_German_Masters_Snooker"
//				+ "_Final_%28DerHexer%29_2012-02-05_05_cropped.jpg/300px-Stephen_Maguire"
//				+ "%2C_Ronnie_O%E2%80%99Sullivan%2C_and_Michaela_Tabb_at_German_"
//				+ "Masters_Snooker_Final_%28DerHexer%29_2012-02-05_05_cropped.jpg");
//		ImageView imageView = new ImageView(image);
//		anchorPaneMain.getChildren().add(imageView);
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

}
