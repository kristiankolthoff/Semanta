package de.unima.dws.semanta.ui.home;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import de.unima.dws.semanta.Semanta;
import de.unima.dws.semanta.model.HAEntity;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HomePresenter implements Initializable{

	@FXML
	Label label1;
	
	@FXML
	Label label2;
	
	@FXML
	TextField text1;
	
	@FXML
	TextField text2;
	
	@FXML
	Button button;
	
	@FXML
	ProgressIndicator indicator;
	
	@Inject
	Semanta semanta;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		text1.setFocusTraversable(true);
		System.out.println("HomePresenter created");
	}
	
	public void findResources() {
		text2.setFocusTraversable(true);
		indicator.setVisible(true);
		System.out.println("hello world from afterburner.fx");
		Task<List<HAEntity>> longTask = new Task<List<HAEntity>>() {
	            @Override
	            protected List<HAEntity> call() throws Exception {
	            	List<HAEntity> haEntities = semanta.fetchEntities("germany", 5, true);
					for(HAEntity entity : haEntities) {
						System.out.println(entity);
					}
					return haEntities;
	            }
	        };
	 
	       longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent t) {
	    			indicator.setVisible(false);
					try {
						List<HAEntity> entities = longTask.get();
						label1.setText(entities.get(0).getAnswer());
						label2.setText(entities.get(2).getAnswer());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
	            }
	        });
	        new Thread(longTask).start();
	}

}
