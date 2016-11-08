package de.unima.dws.semanta.ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainPresenter implements Initializable{

	@FXML
	AnchorPane anchorPaneMain;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		GridPane grid = new GridPane();
		grid.setMinSize(4, 4);
		grid.setGridLinesVisible(true);
		anchorPaneMain.getChildren().add(grid);
	}

}
