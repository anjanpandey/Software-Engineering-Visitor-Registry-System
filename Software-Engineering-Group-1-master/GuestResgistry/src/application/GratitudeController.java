/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
// @SuppressWarnings("restriction")
public class GratitudeController implements Initializable {

	/**
	 * Initializes the controller class.
	 */

	// ScreensController myController;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	/*
	 * @Override public void setScreenParent(ScreensController screenParent){
	 * myController = screenParent; }
	 * 
	 * 
	 */

	@FXML
	private void goHome(ActionEvent event) throws IOException {
		Parent newScene = FXMLLoader.load(getClass().getResource("Map.fxml"));
		Stage new_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		new_Stage.setTitle("Your Information");
		new_Stage.setScene(new Scene(newScene, 1680, 1200));
		new_Stage.show();
		
		Form.timer.restartIdleTimer();
			
			newScene.setOnMouseClicked(mouseHandler);
		    newScene.setOnMouseDragged(mouseHandler);
		    newScene.setOnMouseEntered(mouseHandler);
		    newScene.setOnMouseExited(mouseHandler);
		    newScene.setOnMouseMoved(mouseHandler);
		    newScene.setOnMousePressed(mouseHandler);
		    newScene.setOnMouseReleased(mouseHandler);
	}
	
	/***************************************************************************
	 +	 *********************** Mouse Handler *************************************
	 +	 **************************************************************************/
	 	
	 	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() 
	 	{
	 		 
	         @Override
	         public void handle(MouseEvent mouseEvent)
	         {
	         	Form.timer.restartIdleTimer();
	         }
	     };

}
