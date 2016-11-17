package de.unima.dws.semanta.ui.home.search;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;


import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.search.info.InfoView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SearchPresenter implements Initializable{

	@FXML
	private VBox vBoxResults;
	
	@Inject
	private List<ResourceInfo> resourceInfos;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(ResourceInfo info : resourceInfos) {
			InfoView view = new InfoView((f) -> info);
            vBoxResults.getChildren().add(view.getView());
            vBoxResults.getChildren().add(new Separator(Orientation.HORIZONTAL));
		}
	}

}
