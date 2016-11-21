package de.unima.dws.semanta.ui.home.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.HomePresenter;
import de.unima.dws.semanta.ui.home.search.info.InfoPresenter;
import de.unima.dws.semanta.ui.home.search.info.InfoView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SearchPresenter implements Initializable{

	@FXML
	private VBox vBoxResults;
	
	@Inject
	private ArrayList<ResourceInfo> infos;
	
	private HomePresenter homePresenter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void setHomePresenter(HomePresenter homePresenter) {
		this.homePresenter = homePresenter;
	}
	
	public void initialize() {
		for(ResourceInfo info : infos) {
			InfoView view = new InfoView((f) -> info);
			((InfoPresenter)view.getPresenter()).setHomePresenter(homePresenter);
            vBoxResults.getChildren().add(view.getView());
            vBoxResults.getChildren().add(new Separator(Orientation.HORIZONTAL));
		}
	}

}
