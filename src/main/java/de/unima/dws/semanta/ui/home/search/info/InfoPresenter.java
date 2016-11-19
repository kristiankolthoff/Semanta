package de.unima.dws.semanta.ui.home.search.info;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class InfoPresenter implements Initializable{

	@FXML
	private Label labelIndex;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Label labelType;
	
	@FXML
	private ComboBox<Difficulty> comboBoxDifficulty;
	
	@FXML
	private Text textAbstract;
	
	@Inject
	private ResourceInfo info;
	
	private Application application;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelIndex.setText(String.valueOf(info.getIndex() + "."));
		imageView.setImage(new Image(info.getImageURL()));
		labelName.setText(info.getLabel());
		labelType.setText(info.getTypes());
		textAbstract.setText(info.getSummary());
		comboBoxDifficulty.getItems().add(Difficulty.BEGINNER);
		comboBoxDifficulty.getItems().add(Difficulty.ADVANCED);
		comboBoxDifficulty.getItems().add(Difficulty.EXPERT);
		comboBoxDifficulty.getSelectionModel().select(Difficulty.EXPERT);
	}
	
	public void generate() {
		
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
