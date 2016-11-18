package de.unima.dws.semanta.ui.home.recommendation;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.airhacks.afterburner.injection.Injector;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.Semanta;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.main.MainView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
	
	private Application application;
	
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
		Crossword crossword = this.application.generateCrossword("test");
		crossword.normalize();
//		Map<Object, Object> customProperties = new HashMap<>();
//        customProperties.put("crossword", crossword);
//        Injector.setConfigurationSource(customProperties::get);
		Stage stage = (Stage) textAbstract.getScene().getWindow();
		stage.setScene(new Scene(new MainView((f) -> application).getView()));
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
