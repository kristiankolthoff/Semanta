package de.unima.dws.semanta.ui.home.recommendation;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;


import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.HomePresenter;
import de.unima.dws.semanta.utilities.Settings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RecommendationPresenter implements Initializable{

	@FXML
	private ImageView imageView;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Text textType;
	
	@FXML
	private Text textAbstract;
	
	@FXML
	private ComboBox<Difficulty> comboBoxDifficulty;
	
	@Inject
	private ResourceInfo recommendation;
	
	private HomePresenter homePresenter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(recommendation.getImageURL().isPresent()) {
			imageView.setImage(new Image(recommendation.getImageURL().get()));			
		} else {
			imageView.setImage(new Image(Settings.DEFAULT_IMG));
		}
		labelName.setText(recommendation.getLabel());
		textType.setText(recommendation.getTypes());
		textAbstract.setText(recommendation.getSummary());
		comboBoxDifficulty.getItems().add(Difficulty.BEGINNER);
		comboBoxDifficulty.getItems().add(Difficulty.ADVANCED);
		comboBoxDifficulty.getItems().add(Difficulty.EXPERT);
		comboBoxDifficulty.getSelectionModel().select(Difficulty.EXPERT);
	}
	
	public void generateCrossword() {
		homePresenter.generateCrossword(recommendation.getLabel(), (Stage) textAbstract.getScene().getWindow());
	}

	public void setHomePresenter(HomePresenter homePresenter) {
		this.homePresenter = homePresenter;
	}
	
}
