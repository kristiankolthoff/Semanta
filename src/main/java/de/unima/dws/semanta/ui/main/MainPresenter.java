package de.unima.dws.semanta.ui.main;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;


import de.unima.dws.semanta.crossword.model.Cell;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
	private Map<Cell, Word> wordMap;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		GridPane grid = new GridPane();
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
		for(Word word : crossword) {
			for(Cell cell : word) {
				TextField text = new TextField();
				cell.getSolutionProperty().bind(text.textProperty());
				text.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.length() > 1) {
						text.setText(newValue.substring(0, 1));
					} else if(newValue.length() == 1){
						Cell cellSelected = textMap.get(text);
						System.out.println("selected: " + cellSelected);
						Word wordCurr = wordMap.get(cellSelected);
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
	}
	
	public void validate() {
		System.out.println("validate");
		for(Word word : crossword) {
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
