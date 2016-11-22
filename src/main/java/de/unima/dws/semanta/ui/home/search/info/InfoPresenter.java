package de.unima.dws.semanta.ui.home.search.info;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.unima.dws.semanta.Application;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.ui.home.HomePresenter;
import de.unima.dws.semanta.utilities.Settings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	
	private HomePresenter homePresenter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadImageOnBackgroundThread();
		labelIndex.setText(String.valueOf(info.getIndex() + "."));
		labelName.setText(info.getLabel());
//		labelType.setText(info.getTypes());
		textAbstract.setText(info.getSummary());
		comboBoxDifficulty.getItems().add(Difficulty.BEGINNER);
		comboBoxDifficulty.getItems().add(Difficulty.ADVANCED);
		comboBoxDifficulty.getItems().add(Difficulty.EXPERT);
		comboBoxDifficulty.getSelectionModel().select(Difficulty.EXPERT);
	}
	
	private void loadImageOnBackgroundThread() {
		 Task<Void> imageDisplayTask = new Task<Void>() {
	            @Override
	            public Void call() throws InterruptedException {
	            	if(info.getImageURL().isPresent()) {
	        			imageView.setImage(new Image(info.getImageURL().get()));			
	        		} else {
	        			imageView.setImage(new Image(Settings.DEFAULT_IMG));
	        		}
	            	return null;
	            }
	        };
	        Thread th = new Thread(imageDisplayTask);
	        th.setDaemon(true);
	        th.start();
	}
	
	public void generate() {
		homePresenter.generateCrossword(info, 
				(Stage) labelIndex.getScene().getWindow(), comboBoxDifficulty.getValue());
	}

	public void setHomePresenter(HomePresenter homePresenter) {
		this.homePresenter = homePresenter;
	}

}
