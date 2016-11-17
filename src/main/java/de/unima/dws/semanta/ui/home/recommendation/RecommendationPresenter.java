package de.unima.dws.semanta.ui.home.recommendation;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setImage(new Image(recommendation.getImageURL()));
		labelName.setText(recommendation.getLabel());
		textType.setText(recommendation.getTypes());
		textAbstract.setText(recommendation.getSummary());
		comboBoxDifficulty.getItems().add(Difficulty.BEGINNER);
		comboBoxDifficulty.getItems().add(Difficulty.ADVANCED);
		comboBoxDifficulty.getItems().add(Difficulty.EXPERT);
		comboBoxDifficulty.getSelectionModel().select(Difficulty.EXPERT);
	}
	
	public void generateCrossword() {
		
	}

}
