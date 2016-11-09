package de.unima.dws.semanta.ui.home;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import de.unima.dws.semanta.Semanta;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.ui.App;
import de.unima.dws.semanta.ui.main.MainView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePresenter implements Initializable{

	
	@FXML
	TextField TxtFieldSearch;
	
	@FXML
	Button BtnSearch;
	
	@FXML
	ListView<ResourceInfo> LstViewTopics;
	
	@FXML
	ProgressIndicator indicator;
	
	@FXML
	Label label1;
	
	@FXML
	Label label2;
	
	@FXML
	Button button;

	@Inject
	Semanta semanta;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("HomePresenter created");
	}
	
	public void startGameAction() {
		
        MainView appView = new MainView();
        Scene scene = new Scene(appView.getView());


	 };
	

	public void getTopicsAction() {
		
		LstViewTopics.getItems().clear();
		
        final List<ResourceInfo> topics = SparqlService.getTopics(TxtFieldSearch.getText().trim(), 10);
        
        for (Iterator<ResourceInfo> iterator = topics.iterator(); iterator.hasNext();) {
			ResourceInfo topic = (ResourceInfo) iterator.next();
			LstViewTopics.getItems().add(topic);
		}

	 };
	
	public void findResources() {
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
